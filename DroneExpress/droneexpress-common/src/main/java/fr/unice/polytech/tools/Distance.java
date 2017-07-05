package fr.unice.polytech.tools;

public abstract class Distance {

    public static double gpsDistance(double latA, double lonA, double latB, double lonB) {
        double depLon = lonA, depLat = latA;
        double arrLon = lonB, arrLat = latB;

        double rlat1 = Math.PI * depLat / 180;
        double rlat2 = Math.PI * arrLat / 180;
        double rlon1 = Math.PI * depLon / 180;
        double rlon2 = Math.PI * arrLon / 180;

        double R = 6378000; // rayon terre

        double d = R * (Math.PI / 2 - Math.asin(Math.sin(rlat2) * Math.sin(rlat1)
                + Math.cos(rlon2 - rlon1) * Math.cos(rlat2) * Math.cos(rlat1)));

        return d;
    }
}
