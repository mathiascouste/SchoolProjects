package fr.unice.polytech;

import fr.unice.polytech.graph.Sommet;


public class Warehouse extends Sommet {

    private long warehouseId;
    private String name;
    private double latitude;
    private double longitude;

    public Warehouse(fr.unice.polytech.business.Warehouse w) {
        super();
        this.warehouseId = w.getId();
        this.name = w.getName();
        this.latitude = w.getLatitude();
        this.longitude = w.getLongitude();
    }

    public long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("id=").append(this.warehouseId).append(", ");
        sb.append("name=").append(this.name).append(", ");
        sb.append("lat=").append(this.latitude).append(", ");
        sb.append("lon=").append(this.longitude);
        return sb.toString();
    }

    @Override
    public double distance(Sommet sommet) {
        if (sommet instanceof Warehouse) {
            Warehouse other = (Warehouse) sommet;

            double depLon = this.longitude, depLat = this.latitude;
            double arrLon = other.longitude, arrLat = other.latitude;

            double rlat1 = Math.PI * depLat / 180;
            double rlat2 = Math.PI * arrLat / 180;
            double rlon1 = Math.PI * depLon / 180;
            double rlon2 = Math.PI * arrLon / 180;

            double R = 6378000; // rayon terre

            double d = R * (Math.PI / 2 - Math.asin(Math.sin(rlat2) * Math.sin(rlat1)
                    + Math.cos(rlon2 - rlon1) * Math.cos(rlat2) * Math.cos(rlat1)));

            return d;
        } else {
            return Double.POSITIVE_INFINITY;
        }
    }
}
