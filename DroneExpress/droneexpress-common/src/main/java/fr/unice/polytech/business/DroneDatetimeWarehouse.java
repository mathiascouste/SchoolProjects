package fr.unice.polytech.business;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;


/**
 * Business object specific to our Optimizer module. It contains the data returned by the
 * WarehouseService but in a more user-friendly manner.
 * 
 * @author
 */
public class DroneDatetimeWarehouse {

    private long warehouseId;
    private long droneId;
    private LocalDateTime availability;
    private LocalDateTime startFly;
    private Duration transportDuration;
    private double transportDistance;
    private List<Long> jumps;
    private LocalDateTime mustBeBackAt;
    private Long disponibilitySlotId;

    public DroneDatetimeWarehouse() {

    }

    public DroneDatetimeWarehouse(long warehouseId, long droneId,
            LocalDateTime availabilityDateTime, Duration timeToReachWarehouse,
            double distanceToReachWarehouse, List<Long> jumps, LocalDateTime startFly) {
        this.warehouseId = warehouseId;
        this.droneId = droneId;
        this.availability = availabilityDateTime;
        this.transportDuration = timeToReachWarehouse;
        this.transportDistance = distanceToReachWarehouse;
        this.jumps = jumps;
        this.startFly = startFly;
        this.mustBeBackAt = null;
    }

    public long getDroneId() {
        return this.droneId;
    }

    public LocalDateTime getAvailability() {
        return this.availability;
    }

    public long getWareHouseId() {
        return this.warehouseId;
    }

    public Duration getTransportDuration() {
        return this.transportDuration;
    }

    public double getTransportDistance() {
        return this.transportDistance;
    }

    public List<Long> getJumps() {
        return jumps;
    }

    public LocalDateTime getStartFly() {
        return startFly;
    }

    public long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public void setDroneId(long droneId) {
        this.droneId = droneId;
    }

    public void setAvailability(LocalDateTime availability) {
        this.availability = availability;
    }

    public void setStartFly(LocalDateTime startFly) {
        this.startFly = startFly;
    }

    public void setTransportDuration(Duration transportDuration) {
        this.transportDuration = transportDuration;
    }

    public void setTransportDistance(double transportDistance) {
        this.transportDistance = transportDistance;
    }

    public void setJumps(List<Long> jumps) {
        this.jumps = jumps;
    }

    public LocalDateTime getMustBeBackAt() {
        return mustBeBackAt;
    }

    public void setMustBeBackAt(LocalDateTime mustBeBackAt) {
        this.mustBeBackAt = mustBeBackAt;
    }

    public Long getDisponibilitySlotId() {
        return disponibilitySlotId;
    }

    public void setDisponibilitySlotId(Long disponibilitySlotId) {
        this.disponibilitySlotId = disponibilitySlotId;
    }

}
