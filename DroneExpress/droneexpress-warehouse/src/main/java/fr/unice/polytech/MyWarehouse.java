package fr.unice.polytech;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.fasterxml.jackson.annotation.JsonIgnore;

import fr.unice.polytech.business.DroneDatetimeWarehouse;
import fr.unice.polytech.business.Warehouse;
import fr.unice.polytech.tools.Distance;


/**
 * Business object representing a warehouse according to Warehouse. Contains business logic.
 * 
 * @author Laureen GINIER
 */
public class MyWarehouse extends Warehouse {

    /** Drone speed in m/s */
    private static final int SPEED = 20;

    /** Seconds per minute */
    private static final int SECONDS_PER_MINUTE = 60;

    /** Instantiated warehouses counter used as an auto-incremented ident */
    // private static long nWarehouses = 0;
    /** Set of drones that are currently available in this warehouse */
    @JsonIgnore
    private Set<Long> drones;

    @JsonIgnore
    private Set<DisponibilitySlot> slots;

    @JsonIgnore
    private HashSet<DisponibilitySlot> lockedSlots;
    @JsonIgnore
    private Lock lock;

    /**
     * Instantiates a warehouse with the given name and localized at the specified latitude and
     * longitude. The ident is unique and auto-incremented.
     * 
     * @param name name of the warehouse.
     * @param latitude latitude where is located the warehouse.
     * @param longitude longitude where is located the warehouse.
     */
    public MyWarehouse(String name, double latitude, double longitude) {
        super(-1, name, latitude, longitude);
        this.drones = new HashSet<Long>();
        this.slots = new HashSet<>();
        this.lockedSlots = new HashSet<>();
        this.lock = new ReentrantLock();
    }

    /**
     * Returns the list of drones currently available in this warehouse.
     * 
     * @return a set of available drones
     */
    public Set<Long> getDrones() {
        return this.drones;
    }

    /**
     * Indicates that a drone is now available in this warehouse.
     * 
     * @param droneId id of the drone newly available
     * @return true if drone successfully passed available (not already part of the available
     *         drones), false else.
     */
    public boolean incomingDrone(long droneId) {
        return drones.add(droneId);
    }

    /**
     * Indicates that a drone left the warehouse and is not available anymore.
     * 
     * @param droneId id of the leaving drone
     * @return true if the drone successfully passed "leaving" (if it was in the list of available
     *         drones), else false.
     */
    public boolean leavingDrone(long droneId) {
        return drones.remove(droneId);
    }

    /**
     * Informs the warehouse that a new flight is planned to land at the specified date and
     * performed by the given drone.
     * 
     * @param droneId drone that will performed the flight.
     * @param date scheduled arrival date.
     */
    public void incomingFlight(long droneId, LocalDateTime date) {
        DisponibilitySlot slot = findFirstSlotWithDrone(droneId, date);
        LocalDateTime end = slot != null ? slot.getBegining() : null;
        DisponibilitySlot ds = new DisponibilitySlot();
        ds.setBegining(date);
        ds.setEnd(end);
        ds.setDroneId(droneId);
        this.slots.add(ds);
    }

    public void cancelIncomingFlight(long droneId, LocalDateTime date) {
        DisponibilitySlot slot = findConcernedByOutgoing(droneId, date);
        this.slots.remove(slot);
    }

    /**
     * Informs the warehouse that a the specified drone is planned to take off at the given date.
     * 
     * @param droneId drone that will performed the flight.
     * @param date scheduled departure date.
     */
    public void outgoingFlight(long droneId, LocalDateTime date) {
        DisponibilitySlot slot = findConcernedByOutgoing(droneId, date);
        if (slot != null) {
            if (isLocked(slot)) {
                unlockSlot(slot);
            }
            slot.setEnd(date);
        }
    }

    public void cancelOutgoingFlight(long droneId, LocalDateTime date) {
        DisponibilitySlot slot = findConcernedByOutgoing(droneId, date);
        slot.setEnd(null);
    }

    /**
     * Finds a drone that can perform a flight from this warehouse to the given one and that can
     * arrive in the other warehouse at the specified date (or later).
     * 
     * @param destWarehouseId id of the warehouse that needs a drone.
     * @param expectedDate date at which the drone must be in the warehouse of destination.
     * @return the drone that can handle this flight at the closest date to the specified one, or
     *         null if any drone is available.
     */
    public DroneDatetimeWarehouse findDroneToGoTo(long destWarehouseId, LocalDateTime expectedDate,
                                                  long useTimeInMinute) {
        double speed = SPEED; // m.s-1
        double distance = estimateDistance(this.getId(), destWarehouseId);
        if (distance > 250000) {
            return null;
        }
        double travelTimeSeconds = distance / speed;
        double travelTimeMinutes = travelTimeSeconds / SECONDS_PER_MINUTE;
        Duration timeToGo = Duration.ofMinutes((long) travelTimeMinutes);

        DroneDatetimeWarehouse ddtw = null;
        if (destWarehouseId == this.getId()) {
            DisponibilitySlot slot = findLocalSlot(expectedDate, useTimeInMinute);
            if (slot != null) {
                ddtw = new DroneDatetimeWarehouse();
                if (slot.getBegining().isAfter(expectedDate)) {
                    ddtw.setAvailability(slot.getBegining());
                    ddtw.setStartFly(slot.getBegining());
                } else {
                    ddtw.setAvailability(expectedDate);
                    ddtw.setStartFly(expectedDate);
                }
                ddtw.setDroneId(slot.getDroneId());
                if (slot.getEnd() != null) {
                    ddtw.setMustBeBackAt(slot.getEnd());
                }
                ddtw.setTransportDistance(distance);
                ddtw.setTransportDuration(timeToGo);
                ddtw.setWarehouseId(this.warehouseId);
                ddtw.setDisponibilitySlotId(slot.getId());
            }
        } else {
            LocalDateTime newExpectedDate = expectedDate.minusMinutes((long) travelTimeMinutes);
            DisponibilitySlot slot = findLocalSlot(newExpectedDate,
                    (long) (useTimeInMinute + 2 * travelTimeMinutes));
            if (slot != null) {
                ddtw = new DroneDatetimeWarehouse();
                if (slot.getBegining().isAfter(newExpectedDate)) {
                    ddtw.setAvailability(slot.getBegining().plusMinutes((long) travelTimeMinutes));
                    ddtw.setStartFly(slot.getBegining());
                } else {
                    ddtw.setAvailability(newExpectedDate.plusMinutes((long) travelTimeMinutes));
                    ddtw.setStartFly(newExpectedDate);
                }
                ddtw.setDroneId(slot.getDroneId());
                if (slot.getEnd() != null) {
                    ddtw.setMustBeBackAt(slot.getEnd());
                }
                ddtw.setTransportDistance(distance);
                ddtw.setTransportDuration(timeToGo);
                ddtw.setWarehouseId(this.warehouseId);
                List<Long> jumps = new ArrayList<>();
                jumps.add(new Long(destWarehouseId));
                ddtw.setJumps(jumps);
                ddtw.setDisponibilitySlotId(slot.getId());
            }
        }
        return ddtw;
    }

    private DisponibilitySlot findLocalSlot(LocalDateTime expectedDate, long useTimeInMinute) {
        DisponibilitySlot first = null;

        for (DisponibilitySlot slot : this.slots) {
            if (slot.getEnd() != null && (expectedDate.isAfter(slot.getEnd())
                    || expectedDate.isEqual(slot.getEnd()))) {
                continue;
            }

            long lenght = slot.getLenght();

            if (expectedDate.isAfter(slot.getBegining())
                    || expectedDate.isEqual(slot.getBegining())) {
                if (slot.getEnd() == null || expectedDate.isBefore(slot.getEnd())) {
                    lenght = slot.getDurationBetweenEndAndThis(expectedDate);
                }
            }

            if (lenght >= useTimeInMinute) {
                if (first == null) {
                    if (lockSlot(slot)) {
                        first = slot;
                    }
                } else if (first.getBegining().isAfter(slot.getBegining())) {
                    if (lockSlot(slot)) {
                        unlockSlot(first);
                        first = slot;
                    }
                }
            }
        }
        return first;
    }

    /**
     * Estimates the distance between two locations
     * 
     * @param departure location of departure
     * @param arrival location of arrival
     * @return distance in meters separating the two locations
     */
    private double estimateDistance(long departure, long arrival) {
        if (departure == arrival) {
            return 0;
        }
        WarehouseDAO warehouseDAO = new WarehouseDAO();
        MyWarehouse dep = warehouseDAO.findById(departure);
        MyWarehouse arr = warehouseDAO.findById(arrival);

        return Distance.gpsDistance(dep.latitude, dep.longitude, arr.latitude, arr.longitude);
    }

    // Slot mechanism

    private DisponibilitySlot findFirstSlotWithDrone(long droneId, LocalDateTime date) {
        DisponibilitySlot first = null;
        for (DisponibilitySlot ds : this.slots) {
            if (ds.getDroneId() == droneId) {
                if (ds.getBegining().isAfter(date) || ds.getBegining().isEqual(date)) {
                    if (first == null || first.getBegining().isAfter(ds.getBegining())) {
                        first = ds;
                    }
                }
            }
        }
        return first;
    }

    private DisponibilitySlot findConcernedByOutgoing(long droneId, LocalDateTime date) {
        for (DisponibilitySlot ds : this.slots) {
            if (ds.getDroneId() == droneId) {
                if (ds.getBegining().isBefore(date) || ds.getBegining().isEqual(date)) {
                    if (ds.getEnd() == null || ds.getEnd().isAfter(date)
                            || ds.getEnd().isEqual(date)) {
                        return ds;
                    }
                }
            }
        }
        return null;
    }

    public Set<DisponibilitySlot> getSlots() {
        return slots;
    }

    public void setSlots(Set<DisponibilitySlot> slots) {
        this.slots = slots;
    }

    public boolean lockSlot(DisponibilitySlot slot) {
        /*this.lock.lock();
        if (this.isLocked(slot)) {
            this.lock.unlock();
            return false;
        } else {
            lockedSlots.add(slot);
            this.lock.unlock();
            return true;
        }*/
        return true;
    }

    public void unlockSlot(DisponibilitySlot slot) {
        /*this.lock.lock();
        lockedSlots.remove(slot);
        this.lock.unlock();*/
    }

    public boolean isLocked(DisponibilitySlot slot) {
        /*lock.lock();
        boolean isLocked = lockedSlots.contains(slot);
        lock.unlock();
        return isLocked;*/
        return false;
    }

    public void unlockById(Long disponibilitySlotId) {
        for (DisponibilitySlot ds : this.getSlots()) {
            if (ds.getId() == disponibilitySlotId) {
                unlockSlot(ds);
                return;
            }
        }
    }

    public HashSet<DisponibilitySlot> getLockedSlots() {
        return lockedSlots;
    }

}
