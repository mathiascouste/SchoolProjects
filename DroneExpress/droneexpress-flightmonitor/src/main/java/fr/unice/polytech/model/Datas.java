package fr.unice.polytech.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import fr.unice.polytech.business.Flight;
import fr.unice.polytech.business.Warehouse;
import fr.unice.polytech.servicesconsumers.FlightPlan;
import fr.unice.polytech.servicesconsumers.WarehouseManagement;


public abstract class Datas {

    public static LocalDateTime START;
    public static LocalDateTime END;

    public static final List<Warehouse> warehouses = new ArrayList<>();
    public static final List<Flight> flights = new ArrayList<>();

    public static List<Flight> past = new ArrayList<>();
    public static List<Flight> current = new ArrayList<>();
    public static List<Flight> future = new ArrayList<>();

    public static void loadWarehouses() {
        Datas.warehouses.clear();
        Datas.warehouses.addAll(new WarehouseManagement().getWarehouses());
    }

    public static void loadFlights() {
        Datas.flights.clear();
        Datas.flights.addAll(new FlightPlan().findFlights());
        if (Datas.flights.size() > 0) {
            findStartEnd();
        }
    }

    private static void findStartEnd() {
        for (Flight f : flights) {
            if (START == null || START.isAfter(f.getDepartureDate())) {
                START = f.getDepartureDate();
            }
            if (END == null || END.isBefore(f.getArrivalDate())) {
                END = f.getArrivalDate();
            }
        }
        START = START.minusMinutes(1);
        END = END.plusMinutes(1);
        filterPastCurrentFutureFlight(START);
    }

    public static LocalDateTime getSliderTime(double coef) {
        Duration duration = Duration.between(START, END);
        long minute = (long) (duration.toMinutes() * coef);
        return START.plusMinutes(minute);
    }

    public static void filterPastCurrentFutureFlight(LocalDateTime currentTime) {
        past.clear();
        current.clear();
        future.clear();
        for (Flight f : flights) {
            if (currentTime.isBefore(f.getDepartureDate())) {
                future.add(f);
            } else if (currentTime.isAfter(f.getArrivalDate())) {
                past.add(f);
            } else {
                current.add(f);
            }
        }
    }
}
