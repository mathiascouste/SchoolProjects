package fr.unice.polytech.business;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


public class Drone {

    public static final int LIGHT_DRONE = 1;
    public static final int HEAVY_DRONE = 2;

    private Long droneID;
    private int type;
    private double maxLoad;
    private boolean free;

    @JsonCreator
    public Drone(@JsonProperty(value = "type", required = true) int type,
            @JsonProperty(value = "maxload", required = true) double maxLoad,
            @JsonProperty(value = "free", required = true) boolean free) {
        droneID = -1L;
        this.type = type;
        this.maxLoad = maxLoad;
        this.free = free;
    }

    public Long getDroneID() {
        return droneID;
    }

    public void setDroneID(Long droneID) {
        this.droneID = droneID;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double getMaxLoad() {
        return maxLoad;
    }

    public void setMaxLoad(double maxLoad) {
        this.maxLoad = maxLoad;
    }

    @Override
    public String toString() {
        return "[DRONE] id=" + droneID + " type=" + type + " maxLoad=" + maxLoad;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (droneID == null ? 0 : droneID.hashCode());
        result = prime * result + (free ? 1231 : 1237);
        long temp;
        temp = Double.doubleToLongBits(maxLoad);
        result = prime * result + (int) (temp ^ temp >>> 32);
        result = prime * result + type;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Drone) {
            Drone d = (Drone) obj;
            return droneID.equals(d.getDroneID());
        }
        return false;
    }

    public boolean isFree() {
        return free;
    }

    public void setFree(boolean free) {
        this.free = free;
    }
}
