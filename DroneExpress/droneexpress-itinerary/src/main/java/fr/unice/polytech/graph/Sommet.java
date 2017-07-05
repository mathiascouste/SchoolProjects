package fr.unice.polytech.graph;

import java.util.ArrayList;
import java.util.List;


public abstract class Sommet {

    private List<Arc> arcs;
    private double poids;
    private Sommet precedent;

    public Sommet() {
        this.arcs = new ArrayList<>();
        this.poids = -1;
        this.precedent = null;
    }

    public static Sommet minimum(List<Sommet> listSommets) {
        Sommet min = listSommets.get(0);
        for (Sommet sommet : listSommets) {
            if (sommet.poids < min.poids) {
                min = sommet;
            }
        }

        return min;
    }

    public abstract double distance(Sommet sommet);

    public List<Sommet> sommetsAdjacents() {
        List<Sommet> res = new ArrayList<>();
        for (Arc arc : arcs) {
            if (!res.contains(arc.getPuit())) {
                res.add(arc.getPuit());
            }
        }
        return res;
    }

    public List<Arc> getChemins() {
        return arcs;
    }

    public double getPoids() {
        return poids;
    }

    public void setPoids(double poids) {
        this.poids = poids;
    }

    public Sommet getPrecedent() {
        return precedent;
    }

    public void setPrecedent(Sommet precedent) {
        this.precedent = precedent;
    }

    public List<Arc> getArcs() {
        return arcs;
    }

    public void setArcs(List<Arc> arcs) {
        this.arcs = arcs;
    }

    public Arc arcVers(Sommet visite) {
        for (Arc a : arcs) {
            if (a.getPuit() == visite) {
                return a;
            }
        }
        return null;
    }
}
