package fr.unice.polytech;

import static org.junit.Assert.*;

import org.junit.*;


/**
 * Basic test of the class WarehouseDAO
 * 
 * @author Laureen Ginier
 */
public class WarehouseDAOTest {

    private static MyWarehouse warehouse;
    private static WarehouseDAO db;
    private final static String NAME = "Warehouse";
    private final static double LAT = 43.1;
    private final static double LONG = 3.5;
    private static int initialNumber;
    private final static int NB_WAREHOUSES_DB = 13;

    @BeforeClass
    public static void onceExecutedBeforeAll() {
        db = new WarehouseDAO();
        warehouse = new MyWarehouse(NAME, LAT, LONG);
        initialNumber = db.findAll().size();
    }

    @AfterClass
    public static void onceExecutedAfterAll() {}

    @Before
    public void setUp() throws Exception {}

    @After
    public void tearDown() throws Exception {}

    @Test
    public void testCreate() {
        try {
            assertTrue(db.create(warehouse));
            assertTrue(db.findAll().size() == initialNumber + 1);
            assertTrue(db.findAll().contains(warehouse));
        } finally {
            // Cleaning to have independent tests
            assertTrue(db.delete(warehouse));
        }
    }

    @Test
    public void testDelete() {
        db.create(warehouse);
        assertTrue(db.findAll().contains(warehouse));
        assertTrue(db.delete(warehouse));
        assertFalse(db.findAll().contains(warehouse));
    }

    @Test
    public void testFindAll() {
        assertNotNull(db.findAll());
        assertTrue(db.findAll().size() == NB_WAREHOUSES_DB);
    }

    @Test
    public void testFindById() {
        db.create(warehouse);
        try {
            assertTrue(db.findById(warehouse.getId()).equals(warehouse));
            assertNull(db.findById(initialNumber + 1000));
        } finally {
            db.delete(warehouse);
        }
    }

}
