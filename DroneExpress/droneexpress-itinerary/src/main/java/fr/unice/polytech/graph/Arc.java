package fr.unice.polytech.graph;

import java.util.ArrayList;
import java.util.List;


public class Arc {

    private static List<Arc> arcs = new ArrayList<>();
    private Sommet source, puit;
    private double distance = 0;

    public Arc(Sommet source, Sommet puit) {
        this.source = source;
        this.puit = puit;
        arcs.add(this);
        this.calculerDistance();
    }

    public Arc() {
        this(null, null);
    }

    public Sommet getSource() {
        return source;
    }

    public void setSource(Sommet sommetA) {
        this.source = sommetA;
    }

    public Sommet getPuit() {
        return puit;
    }

    public void setPuit(Sommet sommetB) {
        this.puit = sommetB;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public void calculerDistance() {
        distance = source.distance(puit);
    }

    public static List<Arc> getArcs() {
        return arcs;
    }

}
