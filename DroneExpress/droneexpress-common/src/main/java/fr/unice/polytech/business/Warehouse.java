package fr.unice.polytech.business;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * Business object representing a warehouse. Common object shared by several modules. It does not
 * contain any business logic.
 * 
 * @author Amir Ben Slimane
 */
public class Warehouse {

    /** Ident of the warehouse */
    protected long warehouseId;
    /** Name of the warehouse */
    protected String name;
    /** latitude where is located the warehouse */
    protected double latitude;
    /** longitude where is located the warehouse */
    protected double longitude;

    /**
     * Instantiates a warehouse.
     * 
     * @param id ident of the warehouse
     * @param name name of the warehouse
     * @param latitude latitude where is located the wharehouse
     * @param longitude longitude where is located the warehouse
     */
    @JsonCreator
    public Warehouse(@JsonProperty(value = "id", required = false) long id,
            @JsonProperty(value = "name", required = true) String name,
            @JsonProperty(value = "latitude", required = false) double latitude,
            @JsonProperty(value = "longitude", required = false) double longitude) {
        this.warehouseId = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
    }

    /**
     * Returns the warehouse fixed ident of the warehouse.
     * 
     * @return long representing the ident of the warehouse.
     */
    public long getId() {
        return warehouseId;
    }

    /**
     * Returns the user friendly name of the warehouse.
     * 
     * @return the name of the warehouse.
     */
    public String getName() {
        return name;
    }

    /**
     * Replaces the actual name of the warehouse by the given one.
     * 
     * @param name the new name of the warehouse.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the latitude where is located the warehouse.
     * 
     * @return latitude location of the warehouse.
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Replaces the actual latitude of the warehouse by the specified one.
     * 
     * @param latitude the new latitude of the warehouse.
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    /**
     * Returns the longitude where is located the warehouse.
     * 
     * @return longitude location of the warehouse.
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Replaces the actual longitude location of the warehouse by the specified one.
     * 
     * @param longitude the new longitude location of the warehouse.
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (warehouseId ^ (warehouseId >>> 32));
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Warehouse)) {
            return false;
        }
        Warehouse other = (Warehouse) obj;
        if (warehouseId != other.warehouseId) {
            return false;
        }
        return true;
    }

    public void setWarehouseId(long warehouseId) {
        this.warehouseId = warehouseId;
    }

}
