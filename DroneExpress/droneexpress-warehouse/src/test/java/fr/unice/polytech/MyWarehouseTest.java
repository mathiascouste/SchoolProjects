package fr.unice.polytech;

import static org.junit.Assert.*;

import java.time.LocalDateTime;

import org.junit.*;

import fr.unice.polytech.business.DroneDatetimeWarehouse;


/**
 * Basic tests of MyWarehouse class.
 * 
 * @author Laureen GINIER
 */
public class MyWarehouseTest {

    private static WarehouseDAO warehouseDAO;
    private static MyWarehouse warehouseA;
    private static MyWarehouse warehouseB;
    private final static String NAME_A = "Warehouse A";
    private final static String NAME_B = "Warehouse B";
    private final static double LAT_A = 15.2;
    private final static double LONG_A = -5.7;
    private final static double LAT_B = 17.8;
    private final static double LONG_B = -3.2;
    // private final static double LAT_B = 145.9;
    // private final static double LONG_B = 175.3;
    private final static long DRONE_ID_1 = 1, DRONE_ID_2 = 2, DRONE_ID_3 = 3, DRONE_ID_4 = 4;

    @BeforeClass
    public static void onceExecutedBeforeAll() {
        // WarehouseDAO is used in the method findDroneToGoTo, so we need to add the created
        // warehouses of this test in that mock DB.
        warehouseDAO = new WarehouseDAO();
        warehouseA = new MyWarehouse(NAME_A, LAT_A, LONG_A);
        warehouseB = new MyWarehouse(NAME_B, LAT_B, LONG_B);
        warehouseDAO.create(warehouseA);
        warehouseDAO.create(warehouseB);
    }

    @AfterClass
    public static void onceExecutedAfterAll() {
        // We remove the test warehouses of the mock DB to not affect the "prod"
        warehouseDAO.delete(warehouseA);
        warehouseDAO.delete(warehouseB);
    }

    @Before
    public void setUp() throws Exception {}

    @After
    public void tearDown() throws Exception {
        // Clear the values to keep each test independent of the others
        warehouseA.getDrones().clear();
        warehouseB.getDrones().clear();
    }

    @Test
    public void testMyWarehouse() {
        long id = warehouseDAO.getNextIdCounter();
        // Checks well creation of the object
        assertNotNull(warehouseA);
        assertNotNull(warehouseB);
        assertNotNull(warehouseA.getDrones());
        // Lists must be empty
        assertTrue(warehouseA.getDrones().isEmpty());
        // Checks auto-incrementation of the ident
        assertTrue(warehouseA.getId() == id - 2);
        assertTrue(warehouseB.getId() == id - 1);
    }

    @Test
    public void testGetDrones() {
        int expectedSize = 1;
        assertNotNull(warehouseA.getDrones());
        assertTrue(warehouseA.getDrones().isEmpty());
        warehouseA.getDrones().add(DRONE_ID_1);
        assertTrue(warehouseA.getDrones().size() == expectedSize);
        assertTrue(warehouseA.getDrones().contains(DRONE_ID_1));
    }

    @Test
    public void testIncomingDrone() {
        int expectedSize = 1;
        assertTrue(warehouseA.getDrones().isEmpty());
        warehouseA.incomingDrone(DRONE_ID_1);
        assertTrue(warehouseA.getDrones().size() == expectedSize);
        assertTrue(warehouseA.getDrones().contains(DRONE_ID_1));
    }

    @Test
    public void testLeavingDrone() {
        warehouseA.getDrones().add(DRONE_ID_1);
        warehouseA.getDrones().add(DRONE_ID_2);
        int expectedSize = 2;
        assertTrue(warehouseA.getDrones().size() == expectedSize);

        warehouseA.leavingDrone(DRONE_ID_2);
        assertTrue(warehouseA.getDrones().size() == expectedSize - 1);
        assertTrue(warehouseA.getDrones().contains(DRONE_ID_1));
        assertFalse(warehouseA.getDrones().contains(DRONE_ID_2));
    }

    // Tests that it returns null if no drone is available in this warehouse
    @Test
    public void testFindDroneToGoToNone() {
        LocalDateTime inTwoHours = LocalDateTime.now().plusHours(2);
        assertNull(warehouseA.findDroneToGoTo(warehouseB.getId(), inTwoHours, 10));
    }

    @Test
    @Ignore
    public void testFindDroneToGoToTwoIncomingFirstBetter() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime inTwentyMin = now.plusMinutes(20);
        warehouseA.incomingFlight(DRONE_ID_1, inTwentyMin);
        warehouseA.incomingFlight(DRONE_ID_2, inTwentyMin.plusMinutes(30));
        LocalDateTime h = now.plusHours(1);
        DroneDatetimeWarehouse ddtw = warehouseA.findDroneToGoTo(warehouseB.getId(), h, 10);
        assertTrue(ddtw.getWareHouseId() == warehouseA.getId());
        assertTrue(ddtw.getDroneId() == DRONE_ID_1);
        assertTrue(ddtw.getAvailability()
                .equals(inTwentyMin.plusMinutes(ddtw.getTransportDuration().toMinutes())));
        assertTrue(ddtw.getJumps().size() == 1);
        assertTrue(ddtw.getJumps().get(0) == warehouseB.getId());
        assertTrue(ddtw.getStartFly().equals(inTwentyMin));
    }

    // @Test
    // public void testFindDroneToGoToTwoIncomingSecondBetter() {
    // LocalDateTime now = LocalDateTime.now();
    // LocalDateTime inTwentyMin = now.plusMinutes(20);
    // warehouseA.incomingFlight(DRONE_ID_1, now.plusMinutes(50));
    // warehouseA.incomingFlight(DRONE_ID_2, inTwentyMin);
    // LocalDateTime h = now.plusHours(1);
    // DroneDatetimeWarehouse ddtw = warehouseA.findDroneToGoTo(warehouseB.getWarehouseId(), h);
    // assertTrue(ddtw.getWareHouseId() == warehouseA.getWarehouseId());
    // assertTrue(ddtw.getDroneId() == DRONE_ID_2);
    // assertTrue(ddtw.getAvailability()
    // .equals(inTwentyMin.plusMinutes(ddtw.getTransportDuration().toMinutes())));
    // assertTrue(ddtw.getJumps().size() == 1);
    // assertTrue(ddtw.getJumps().get(0) == warehouseB.getWarehouseId());
    // assertTrue(ddtw.getStartFly().equals(inTwentyMin));
    // }

    @Test
    @Ignore
    public void testFindDroneToGoToOneAvailOneIncoming() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime inTwoHours = now.plusHours(2);

        // The warehouse has 2 drones available D1 and D2.
        warehouseA.incomingDrone(DRONE_ID_1);
        warehouseA.incomingDrone(DRONE_ID_2);
        // D1 leaves in 4 hours and D3 arrives in 2h10.
        warehouseA.incomingFlight(DRONE_ID_3, inTwoHours.plusMinutes(10));
        warehouseA.outgoingFlight(DRONE_ID_1, now.plusHours(4));
        // We want a drone for a flight in 2h
        now = LocalDateTime.now();
        DroneDatetimeWarehouse ddtw = warehouseA.findDroneToGoTo(warehouseB.getId(), inTwoHours,
                10);
        // The best drone is DRONE_ID_2 because it is available directly, unlike DRONE_ID_3 which is
        // available 10 mn later.
        assertTrue(ddtw.getWareHouseId() == warehouseA.getId());
        assertTrue(ddtw.getDroneId() == DRONE_ID_2);
        LocalDateTime avail = now.plusMinutes(ddtw.getTransportDuration().toMinutes());
        // There is a gap of some seconds between the running of this test and of the algo
        assertTrue(avail.getSecond() - ddtw.getAvailability().getSecond() < 120);
        assertTrue(ddtw.getJumps().size() == 1);
        assertTrue(ddtw.getJumps().get(0) == warehouseB.getId());
        assertTrue(ddtw.getStartFly()
                .equals(ddtw.getAvailability().minus(ddtw.getTransportDuration())));
    }

    // Tests that an incoming drone can be used for the flight
    @Test
    public void testFindDroneToGoToNoDroneOneIncoming() {
        LocalDateTime inFortyFiveMin = LocalDateTime.now().plusMinutes(45);
        // Drone 1 will arrive in 45 mn
        warehouseA.incomingFlight(DRONE_ID_1, inFortyFiveMin);
        // Look for a drone to perform the flight in 20 mn
        DroneDatetimeWarehouse ddtw = warehouseA.findDroneToGoTo(warehouseB.getId(),
                LocalDateTime.now().plusMinutes(20), 10);
        assertTrue(ddtw.getWareHouseId() == warehouseA.getId());
        assertTrue(ddtw.getDroneId() == DRONE_ID_1);
        assertTrue(ddtw.getAvailability().equals(inFortyFiveMin.plus(ddtw.getTransportDuration())));
        assertTrue(ddtw.getJumps().size() == 1);
        assertTrue(ddtw.getJumps().get(0) == warehouseB.getId());
        assertTrue(ddtw.getStartFly().equals(inFortyFiveMin));
    }

    @Test
    @Ignore
    public void testFindDroneToGoToFourIncoming() {
        LocalDateTime now = LocalDateTime.now();
        warehouseA.incomingFlight(DRONE_ID_1, now.plusMinutes(10));
        warehouseA.incomingFlight(DRONE_ID_2, now.minusMinutes(20));
        warehouseA.outgoingFlight(DRONE_ID_3, now.plusMinutes(20));
        warehouseA.outgoingFlight(DRONE_ID_4, now.minusMinutes(20));
        DroneDatetimeWarehouse ddtw = warehouseA.findDroneToGoTo(warehouseA.getId(),
                now.plusHours(1), 10);
        assertTrue(ddtw.getDroneId() == DRONE_ID_1);
    }

    @Test
    @Ignore
    public void testFindDroneToGoToSameWarehouses() {
        LocalDateTime h = LocalDateTime.now().plusMinutes(45);
        warehouseA.incomingDrone(DRONE_ID_1);
        // Look for a drone to perform the flight in 45 mn
        DroneDatetimeWarehouse ddtw = warehouseA.findDroneToGoTo(warehouseA.getId(), h, 10);
        assertTrue(ddtw.getWareHouseId() == warehouseA.getId());
        assertTrue(ddtw.getDroneId() == DRONE_ID_1);
        assertTrue(ddtw.getAvailability() == h);
        assertNull(ddtw.getJumps());
        assertTrue(ddtw.getStartFly().equals(h));
    }
}
