package fr.unice.polytech;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


/**
 * Mock of a database containing all the warehouses.
 * 
 * @author gl307364
 */
public class WarehouseDAO {

    /** List of all the warehouses */
    private static List<MyWarehouse> warehouses = new ArrayList<MyWarehouse>();

    /** Counter to auto-increment the ident */
    private static long nextId = 1;

    // Creation of some warehouses to run our software
    static {
        LocalDateTime now = LocalDateTime.now();
        now = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), now.getHour(),
                now.getMinute());

        add(new MyWarehouse("Polytech Montpellier", 43.632, 3.862));
        add(new MyWarehouse("Polytech Grenoble", 45.184, 5.753));
        add(new MyWarehouse("Polytech Marseille", 43.232, 5.443));
        MyWarehouse niceSophia = new MyWarehouse("Polytech Nice-Sophia", 43.616, 7.072);
        add(niceSophia);
        MyWarehouse orleans = new MyWarehouse("Polytech Orleans", 47.844261, 1.938);
        add(orleans);
        add(new MyWarehouse("Polytech Lyon", 45.779406, 4.868));
        add(new MyWarehouse("Polytech Annecy-Chambéry", 45.641, 5.868));
        add(new MyWarehouse("Polytech Clermont-Ferrand", 45.760, 3.109));
        add(new MyWarehouse("Polytech Lille", 50.607, 3.136));
        add(new MyWarehouse("Polytech Nantes", 47.282, -1.516));
        MyWarehouse parisSud = new MyWarehouse("Polytech Paris-Sud", 48.709, 2.171);
        add(parisSud);
        add(new MyWarehouse("Polytech UPMC", 48.852, 2.349));
        add(new MyWarehouse("Polytech Tours", 47.364, 0.685));

        /*long id = 1;
        for (int i = 0; i < warehouses.size(); i++) {
            for (int j = 0; j < 10; j++) {
                warehouses.get(i).incomingFlight(id++, now);
            }
        }*/
        niceSophia.incomingFlight(1, now);
        orleans.incomingFlight(2, now);
    }

    /**
     * Add a warehouse in the database. Set its id automatically.
     * 
     * @param w the warehouse to add to the database (its id must be -1).
     * @return true if the warehouse has been added to the DB, otherwise false.
     */
    private static boolean add(MyWarehouse w) {
        w.setWarehouseId(nextId++);
        return warehouses.add(w);
    }

    /**
     * Add a warehouse in the database.
     * 
     * @param w warehouse to add.
     * @return true if the warehouse has been added to the DB, false otherwise.
     */
    public boolean create(MyWarehouse w) {
        return add(w);
    }

    /**
     * Delete a warehouse from the database.
     * 
     * @param w the warehouse to remove from the database
     * @return true if the warehouse has been deleted, false else.
     */
    public boolean delete(MyWarehouse w) {
        return warehouses.remove(w);
    }

    /**
     * Returns all the existing warehouses.
     * 
     * @return list of all the warehouses.
     */
    public List<MyWarehouse> findAll() {
        return warehouses;
    }

    /**
     * Retrieves a warehouse by its ident.
     * 
     * @param id ident of the warehouse one is looking for
     * @return the warehouse having this ident or null if it does not exist.
     */
    public MyWarehouse findById(long id) {
        for (MyWarehouse w : warehouses) {
            if (w.getId() == id) {
                return w;
            }
        }
        return null;
    }

    /**
     * Returns the counter of warehouses (represents the next ident). Used to simplify the junit
     * tests.
     * 
     * @deprecated
     * @return next ident that will be used for the next warehouse to add in the db
     */
    @Deprecated
    public long getNextIdCounter() {
        return this.nextId;
    }

}
