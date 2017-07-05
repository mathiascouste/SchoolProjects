package fr.unice.polytech.business;

/**
 * Class representing GPS coordinates of a position.
 * 
 * @author Laureen Ginier
 */
public class GPSCoordinates {

    /** Latitude */
    private double latitude;

    /** Longitude */
    private double longitude;

    /**
     * Instanciates GPS coordinates
     * 
     * @param lat latitude position
     * @param lon longitude position
     */
    public GPSCoordinates(double lat, double lon) {
        this.latitude = lat;
        this.longitude = lon;
    }

    /**
     * Returns the latitude of the location.
     * 
     * @return latitude
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Replaces the latitude position.
     * 
     * @param latitude new latitude position.
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    /**
     * Returns the longitude position of the location
     * 
     * @return double longitude position
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Replaces the longitude position.
     * 
     * @param longitude new longitude position.
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

}
