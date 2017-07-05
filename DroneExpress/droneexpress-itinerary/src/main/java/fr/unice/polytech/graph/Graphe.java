package fr.unice.polytech.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class Graphe {

    private List<Sommet> sommets;
    private List<Arc> chemin;

    private Lock lock;

    public Graphe() {
        this.sommets = new ArrayList<>();
        this.chemin = new ArrayList<>();
        this.lock = new ReentrantLock();
    }

    public List<Arc> itineraire(Sommet debut, Sommet fin) {
        this.lock.lock();
        /*
         * Initialisation
         */
        List<Sommet> sommetsCopie = new ArrayList<>(sommets);
        for (Sommet sommet : sommetsCopie) {
            sommet.setPoids(Double.POSITIVE_INFINITY);
            sommet.setPrecedent(null);
        }
        debut.setPoids(0);
        List<Sommet> pasVisites = sommetsCopie;

        Sommet visite = null;
        double resTemp;
        while (!pasVisites.isEmpty()) {
            visite = Sommet.minimum(pasVisites);
            pasVisites.remove(visite);
            for (Arc arc : visite.getChemins()) {
                resTemp = visite.getPoids() + arc.getDistance();
                if (arc.getPuit().getPoids() > resTemp) {
                    arc.getPuit().setPoids(resTemp);
                    arc.getPuit().setPrecedent(visite);
                }
            }
        }

        chemin = new ArrayList<>();
        visite = fin;

        while (visite != debut) {
            if (visite == null || visite.getPrecedent() == null) {
                return chemin;
            }
            chemin.add(visite.getPrecedent().arcVers(visite));
            visite = visite.getPrecedent();
        }
        // chemin.add(0, new Arc(debut, visite));
        this.lock.unlock();
        return chemin;
    }

    public List<Sommet> itineraireSommets(Sommet debut, Sommet fin) {
        List<Sommet> res = new ArrayList<>();
        itineraire(debut, fin);
        res.add(debut);
        for (Arc arc : chemin) {
            res.add(arc.getPuit());
        }
        return res;
    }

    public List<Sommet> getSommets() {
        return sommets;
    }

    public void setSommets(List<Sommet> sommets) {
        this.sommets = sommets;
    }
}
