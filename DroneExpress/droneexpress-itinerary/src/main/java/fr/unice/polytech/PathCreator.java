package fr.unice.polytech;

import java.util.ArrayList;
import java.util.List;

import fr.unice.polytech.graph.Arc;
import fr.unice.polytech.graph.Graphe;
import fr.unice.polytech.servicesconsumers.WarehouseManagement;


public class PathCreator {

    private static final double DRONE_AUTONOMY = 250000;
    private static List<Warehouse> warehouses = new ArrayList<>();
    private static PathCreator original;

    private Graphe graphe;

    static {
        loadWarehouses();
        original = new PathCreator();
        original.buildGraphe();
        showArcs();
    }

    public PathCreator() {
        this.graphe = new Graphe();
    }

    public static Warehouse getWarehouseById(long id) {
        for (Warehouse w : warehouses) {
            if (w.getWarehouseId() == id) {
                return w;
            }
        }
        return null;
    }

    public static List<Arc> itineraire(Warehouse start, Warehouse end) {
        return original.graphe.itineraire(start, end);
    }

    private static void loadWarehouses() {
        for (fr.unice.polytech.business.Warehouse w : new WarehouseManagement().getWarehouses()) {
            warehouses.add(new Warehouse(w));
        }
    }

    private void buildGraphe() {
        for (int i = 0; i < warehouses.size(); i++) {
            Warehouse w1 = warehouses.get(i);
            for (int j = 0; j < warehouses.size(); j++) {
                Warehouse w2 = warehouses.get(j);
                if (w1 != w2) {
                    double distance = w1.distance(w2);
                    if (distance <= DRONE_AUTONOMY) {
                        Arc a = new Arc(w1, w2);
                        a.calculerDistance();
                        w1.getArcs().add(a);
                    }
                }
            }
        }
        graphe.getSommets().addAll(warehouses);
    }

    private static void showArcs() {
        for (Arc a : Arc.getArcs()) {
            Warehouse from = (Warehouse) a.getSource();
            Warehouse to = (Warehouse) a.getPuit();
            System.out.println(from.getName() + "--(" + a.getDistance() + ")-->" + to.getName());
        }
    }
}
