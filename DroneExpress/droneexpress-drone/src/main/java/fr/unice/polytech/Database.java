package fr.unice.polytech;

import java.util.*;

/**
 * Contains the number and the data of the different drones
 */
import fr.unice.polytech.business.Drone;


public class Database {

    public static final List<Drone> drones = new ArrayList<>();
    protected static Map<Long, String> warehousesProblems = new HashMap<>();
    protected static final int MAX_LOAD_LIGHT = 3;

    static {
        for (int i = 0; i < 4; i++) {
            drones.add(new Drone(Drone.LIGHT_DRONE, MAX_LOAD_LIGHT, true));
        }
        drones.add(new Drone(Drone.LIGHT_DRONE, MAX_LOAD_LIGHT, false));
    }

    /**
     * give the drone instance from the droneId
     * @param droneId is the id of the drone instance
     * @return the drone instance
     */
    public static Drone getDroneById(Long droneId) {
        for (Drone drone : drones) {
            if (drone.getDroneID().equals(droneId)) {
                return drone;
            }
        }
        return null;
    }

    public static Map<Long, String> getWarehousesProblems() {
        return warehousesProblems;
    }
}
