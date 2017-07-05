package fr.unice.polytech.business;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


/**
 * Basic tests of Warehouse class.
 * 
 * @author Amir Ben Slimane
 */
public class WarehouseTest {

    private Warehouse warehouse;
    private final String NAME = "MyWarehouse";
    private final String NEW_NAME = "Chez AmiralBS";
    private final long IDENT = 7;
    private final double LAT = 5.0;
    private final double LONG = -15.0;
    private final double NEW_LAT = 2.0;
    private final double NEW_LONG = 4.0;

    @Before
    public void setUp() throws Exception {
        warehouse = new Warehouse(IDENT, NAME, LAT, LONG);
    }

    @After
    public void tearDown() throws Exception {
        warehouse = null;
    }

    @Test
    public void testWarehouse() {
        assertNotNull(warehouse);
        assertTrue(warehouse.getId() == IDENT);
        assertTrue(NAME.equals(warehouse.getName()));
        assertTrue(warehouse.getLatitude() == LAT);
        assertTrue(warehouse.getLongitude() == LONG);
    }

    @Test
    public void testGetWarehouseId() {
        assertTrue(warehouse.getId() == IDENT);
    }

    @Test
    public void testGetName() {
        assertTrue(NAME.equals(warehouse.getName()));
    }

    @Test
    public void testSetName() {
        warehouse.setName(NEW_NAME);
        assertTrue(NEW_NAME.equals(warehouse.getName()));
    }

    @Test
    public void testGetLocation() {
        assertTrue(warehouse.getLatitude() == LAT);
        assertTrue(warehouse.getLongitude() == LONG);
    }

    @Test
    public void testSetLocation() {
        warehouse.setLatitude(NEW_LAT);
        warehouse.setLongitude(NEW_LONG);
        assertTrue(warehouse.getLatitude() == NEW_LAT);
        assertTrue(warehouse.getLongitude() == NEW_LONG);
    }

}
