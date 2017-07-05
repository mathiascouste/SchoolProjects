package fr.unice.polytech;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import fr.unice.polytech.business.Flight;


public class FlightPlanStorageMock {

    private static long NFLIGHT = 0;

    private static volatile FlightPlanStorageMock instance = null;

    private static HashSet<Flight> lockedFlight;
    private static HashMap<Long, Flight> flights;

    private Lock lock;

    private FlightPlanStorageMock() {
        flights = new HashMap<Long, Flight>();
        lockedFlight = new HashSet<Flight>();
        this.lock = new ReentrantLock();
    }

    public static FlightPlanStorageMock getInstance() {
        if (instance == null) {
            synchronized (FlightPlanStorageMock.class) {
                if (instance == null) {
                    instance = new FlightPlanStorageMock();
                }
            }
        }
        return instance;
    }

    public void create(Flight flight) {
        flight.setIdent(++NFLIGHT);
        flights.put(flight.getIdent(), flight);
    }

    public Flight read(Long id) {
        return flights.get(id);
    }

    public void delete(Long id) {
        flights.remove(id);
    }

    public Collection<Flight> findAll() {
        return flights.values();
    }

    public List<Flight> findAllUnlocked() {
        List<Flight> unlockeds = new ArrayList<Flight>();
        for (Flight f : flights.values()) {
            if (!isLocked(f)) {
                unlockeds.add(f);
            }
        }
        return unlockeds;
    }

    /**
     * Returns all the flights planned in the future (compared to the present moment).
     * 
     * @return a list of future flights
     */
    public Collection<Flight> allFutureFlights() {
        LocalDateTime relative_now = LocalDateTime.now().plusMinutes(1);
        List<Flight> futureFlights = new ArrayList<>();
        for (Flight f : flights.values()) {
            if (f.getDepartureDate().isAfter(relative_now)) {
                futureFlights.add(f);
            }
        }
        return futureFlights;
    }

    public boolean lockFlight(Flight flight) {
        this.lock.lock();
        if (this.isLocked(flight)) {
            this.lock.unlock();
            return false;
        } else {
            lockedFlight.add(flight);
            this.lock.unlock();
            return true;
        }
    }

    public void unlockFlight(Flight flight) {
        this.lock.lock();
        lockedFlight.remove(flight);
        this.lock.unlock();
    }

    public boolean isLocked(Flight flight) {
        return lockedFlight.contains(flight);
    }

}
