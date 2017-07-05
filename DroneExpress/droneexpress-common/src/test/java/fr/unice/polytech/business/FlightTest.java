package fr.unice.polytech.business;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class FlightTest {

    private Flight flight;
    private Flight flightUnload;
    private Flight emplyConstrucorFlight;

    @Before
    public void setUp() throws Exception {
        flight = new Flight(1234, 5678, LocalDateTime.now(), LocalDateTime.now(), 1, 1);
        flightUnload = new Flight(1234, 5678, LocalDateTime.now(),
                LocalDateTime.now().plusSeconds(10), 1);
        emplyConstrucorFlight = new Flight();
    }

    @After
    public void tearDown() throws Exception {
        flight = null;
        flightUnload = null;
        emplyConstrucorFlight = null;
    }

    @Test
    public void testFlight() {
        assertNotNull(emplyConstrucorFlight);
    }

    @Test
    public void testFlightLongLongLocalDateTimeLocalDateTimeLong() {
        assertNotNull(flightUnload);
    }

    @Test
    public void testFlightLongLongLocalDateTimeLocalDateTimeLongBoolean() {
        assertNotNull(flight);
    }

    @Test
    public void testGetIdent() {
        assertTrue(flight.getIdent() == -1);
        assertTrue(flightUnload.getIdent() == -1);
        assertTrue(emplyConstrucorFlight.getIdent() == -1);
    }

    @Test
    public void testGetDepartureId() {
        assertTrue(flight.getDepartureId() == 1234);
        assertTrue(flightUnload.getDepartureId() == 1234);
        assertTrue(emplyConstrucorFlight.getDepartureId() == 0);
    }

    @Test
    public void testSetDepartureId() {
        flight.setDepartureId(1);
        assertTrue(flight.getDepartureId() == 1);
        flight.setDepartureId(Long.MAX_VALUE);
        assertTrue(flight.getDepartureId() == Long.MAX_VALUE);

        flight.setDepartureId(Long.MAX_VALUE + 1);
        assertTrue(flight.getDepartureId() == Long.MIN_VALUE);
    }

    @Test
    public void testGetArrivalId() {
        assertTrue(flight.getArrivalId() == 5678);
        assertTrue(flightUnload.getArrivalId() == 5678);
        assertTrue(emplyConstrucorFlight.getArrivalId() == 0);
    }

    @Test
    public void testSetArrivalId() {
        flight.setArrivalId(1);
        assertTrue(flight.getArrivalId() == 1);
        flight.setArrivalId(Long.MAX_VALUE);
        assertTrue(flight.getArrivalId() == Long.MAX_VALUE);

        flight.setArrivalId(Long.MAX_VALUE + 1);
        assertTrue(flight.getArrivalId() == Long.MIN_VALUE);
    }

    @Test
    public void testGetDroneId() {
        assertTrue(flight.getDroneId() == 1);
        assertTrue(flightUnload.getDroneId() == 1);
        assertTrue(emplyConstrucorFlight.getDroneId() == 0);
    }

    @Test
    public void testSetDroneId() {
        flight.setDroneId(1);
        assertTrue(flight.getDroneId() == 1);
        flight.setDroneId(Long.MAX_VALUE);
        assertTrue(flight.getDroneId() == Long.MAX_VALUE);

        flight.setDroneId(Long.MAX_VALUE + 1);
        assertTrue(flight.getDroneId() == Long.MIN_VALUE);
    }

    @Test
    public void testIsLoaded() {
        assertTrue(flight.getParcelId() >= 0);
        assertFalse(flightUnload.getParcelId() >= 0);
        assertFalse(emplyConstrucorFlight.getParcelId() >= 0);
    }

    @Test
    public void testSetLoaded() {
        flight.setParcelId(1);
        assertTrue(flight.getParcelId() >= 0);
    }

    @Test
    public void testGetDepartureDate() {
        LocalDateTime now = LocalDateTime.now();
        assertTrue(flight.getDepartureDate().getYear() == now.getYear());
        assertTrue(flight.getDepartureDate().getMonthValue() == now.getMonthValue());
        assertTrue(flight.getDepartureDate().getDayOfMonth() == now.getDayOfMonth());
        assertTrue(flight.getDepartureDate().getHour() == now.getHour());
        assertTrue(flight.getDepartureDate().getMinute() == now.getMinute());
        assertTrue(flight.getDepartureDate().getSecond() == now.getSecond());

    }

    @Test
    public void testSetDepartureDate() {
        flight.setDepartureDate(LocalDateTime.now().plusYears(1));
        LocalDateTime now = LocalDateTime.now();
        assertTrue(flight.getDepartureDate().getYear() == now.getYear() + 1);
        assertTrue(flight.getDepartureDate().getMonthValue() == now.getMonthValue());
        assertTrue(flight.getDepartureDate().getDayOfMonth() == now.getDayOfMonth());
        assertTrue(flight.getDepartureDate().getHour() == now.getHour());
        assertTrue(flight.getDepartureDate().getMinute() == now.getMinute());
        assertTrue(flight.getDepartureDate().getSecond() == now.getSecond());
    }

    @Test
    public void testGetArrivalDate() {
        LocalDateTime now = LocalDateTime.now();
        assertTrue(flight.getArrivalDate().getYear() == now.getYear());
        assertTrue(flight.getArrivalDate().getMonthValue() == now.getMonthValue());
        assertTrue(flight.getArrivalDate().getDayOfMonth() == now.getDayOfMonth());
        assertTrue(flight.getArrivalDate().getHour() == now.getHour());
        assertTrue(flight.getArrivalDate().getMinute() == now.getMinute());
        assertTrue(flight.getArrivalDate().getSecond() == now.getSecond());
    }

    @Test
    public void testSetArrivalDate() {
        LocalDateTime now = LocalDateTime.now();
        flight.setArrivalDate(LocalDateTime.now().plusYears(2));
        assertTrue(flight.getArrivalDate().getYear() == now.getYear() + 2);
        assertTrue(flight.getArrivalDate().getMonthValue() == now.getMonthValue());
        assertTrue(flight.getArrivalDate().getDayOfMonth() == now.getDayOfMonth());
        assertTrue(flight.getArrivalDate().getHour() == now.getHour());
        assertTrue(flight.getArrivalDate().getMinute() == now.getMinute());
        assertTrue(flight.getArrivalDate().getSecond() == now.getSecond());
    }

    @Test
    public void testMerge() {
        Flight flightMerged = new Flight();

        flight.merge(flightMerged);

        assertTrue(flight.getDroneId() == flightMerged.getDroneId());
        assertTrue(flight.getParcelId() >= 0 == flightMerged.getParcelId() >= 0);
        assertTrue(flight.getDepartureId() == flightMerged.getDepartureId());
        assertTrue(flight.getArrivalId() == flightMerged.getArrivalId());
        assertTrue(flight.getDepartureDate() == flightMerged.getDepartureDate());
        assertTrue(flight.getArrivalDate() == flightMerged.getArrivalDate());
    }

}
