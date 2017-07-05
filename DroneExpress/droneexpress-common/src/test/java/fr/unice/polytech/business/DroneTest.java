package fr.unice.polytech.business;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class DroneTest {

    private Drone drone;

    @Before
    public void setUp() throws Exception {
        drone = new Drone(1, 2.4, true);
    }

    @After
    public void tearDown() throws Exception {
        drone = null;
    }

    @Test
    public void testDrone() {
        assertNotNull(drone);
    }

    @Test
    public void testGetDroneID() {
        assertTrue(drone.getDroneID() == -1);
        Drone d = new Drone(0, 0, true);
        assertTrue(d.getDroneID() == -1);
    }

    @Test
    public void testSetDroneID() {
        drone.setDroneID(4L);
        assertTrue(drone.getDroneID() == 4);
    }

    @Test
    public void testGetType() {
        assertTrue(drone.getType() == 1);
    }

    @Test
    public void testSetType() {
        drone.setDroneID(2L);
        assertTrue(drone.getDroneID() == 2);
    }

    @Test
    public void testGetMaxLoad() {
        assertTrue(drone.getMaxLoad() == 2.4);
    }

    @Test
    public void testSetMaxLoad() {
        drone.setMaxLoad(2.5);
        assertTrue(drone.getMaxLoad() == 2.5);
    }

    @Test
    public void testEqualsObject() {
        Drone d = drone;
        assertTrue(drone.equals(d));
        assertTrue(d.equals(drone));
        assertFalse(drone.equals(null));
        assertFalse(drone.equals(new String("Out Of Scope")));
    }

    @Test
    public void testIsFree() {
        assertTrue(drone.isFree());
    }

    @Test
    public void testSetFree() {
        drone.setFree(false);
        assertFalse(drone.isFree());
    }

}
