package fr.unice.polytech.business;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;

import org.junit.*;


public class DeliveryTest {

    private static Delivery defaultDelivery;
    private static Delivery delivery;
    private static final long DELIVERY_ID = 1, PARCEL_ID = 5, WAREHOUSE_ID1 = 1, WAREHOUSE_ID2 = 2;
    private static final double H_LAT = 35.7, H_LONG = 140.3, W_LAT = 14.2, W_LONG = 50.3;
    private static final String W_NAME1 = "W1", W_NAME2 = "W2";
    private static Warehouse departure;
    private static Warehouse arrival;
    // private GPSCoordinate houseCoord;
    private static LocalDateTime departureTime;
    private static LocalDateTime arrivalTime;

    @BeforeClass
    public static void beforeAllTests() {
        // houseCoord = new GPSCoordinate(H_LAT, H_LONG);
        departure = new Warehouse(WAREHOUSE_ID1, W_NAME1, H_LAT, H_LONG);
        arrival = new Warehouse(WAREHOUSE_ID2, W_NAME2, W_LAT, W_LONG);
        defaultDelivery = new Delivery();
        departureTime = LocalDateTime.now().plusMinutes(10);
        arrivalTime = LocalDateTime.now().plusHours(2);
    }

    @AfterClass
    public static void afterAllTests() {}

    @Before
    public void setUp() throws Exception {
        delivery = new Delivery(DELIVERY_ID, PARCEL_ID, departure.getId(), arrival.getId(),
                departureTime, arrivalTime);
    }

    @After
    public void tearDown() throws Exception {}

    /**
     * Test the constructors
     */
    @Test
    public void testDelivery() {
        // default constructor
        assertNotNull(defaultDelivery);
        assertTrue(defaultDelivery.getId() == -1);
        assertTrue(defaultDelivery.getParcelId() == -1);
        assertTrue(defaultDelivery.getDepartureLocation() == -1);
        assertTrue(defaultDelivery.getArrivalLocation() == -1);
        assertNull(defaultDelivery.getDepartureTime());
        assertNull(defaultDelivery.getArrivalTime());

        // constructor with parameter
        assertNotNull(delivery);
        assertTrue(delivery.getId() == DELIVERY_ID);
        assertTrue(delivery.getParcelId() == PARCEL_ID);
        assertTrue(delivery.getDepartureLocation() == WAREHOUSE_ID1);
        assertTrue(delivery.getArrivalLocation() == WAREHOUSE_ID2);
        assertTrue(delivery.getDepartureTime().equals(departureTime));
        assertTrue(delivery.getArrivalTime().equals(arrivalTime));
    }

    @Test
    public void testGetAndSetId() {
        long newId = 7;
        assertTrue(delivery.getId() == DELIVERY_ID);
        assertTrue(defaultDelivery.getId() == -1);
        delivery.setId(newId);
        assertTrue(delivery.getId() == newId);
    }

    @Test
    public void testGetAndSetParcelId() {
        long newId = 10;
        assertTrue(delivery.getParcelId() == PARCEL_ID);
        delivery.setParcelId(newId);
        assertTrue(delivery.getParcelId() == newId);
    }

    @Test
    public void testGetAndSetDepartureLocation() {
        long newId = 3;
        assertTrue(delivery.getDepartureLocation() == WAREHOUSE_ID1);
        delivery.setDepartureLocation(newId);
        assertTrue(delivery.getDepartureLocation() == newId);
    }

    @Test
    public void testGetAndSetArrivalLocation() {
        long newId = 4;
        assertTrue(delivery.getArrivalLocation() == WAREHOUSE_ID2);
        delivery.setArrivalLocation(newId);
        assertTrue(delivery.getArrivalLocation() == newId);
    }

    @Test
    public void testGetAndSetDepartureTime() {
        LocalDateTime now = LocalDateTime.now();
        assertTrue(delivery.getDepartureTime().equals(departureTime));
        delivery.setDepartureTime(now);
        assertTrue(delivery.getDepartureTime().equals(now));
    }

    @Test
    public void testGetAndSetArrivalTime() {
        LocalDateTime now = LocalDateTime.now();
        assertTrue(delivery.getArrivalTime().equals(arrivalTime));
        delivery.setArrivalTime(now);
        assertTrue(delivery.getArrivalTime().equals(now));
    }

}
