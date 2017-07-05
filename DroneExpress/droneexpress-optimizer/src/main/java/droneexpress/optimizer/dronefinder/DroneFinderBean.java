package droneexpress.optimizer.dronefinder;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import droneexpress.optimizer.statistics.StatResponse;
import droneexpress.optimizer.statistics.Statistics;
import fr.unice.polytech.business.DroneDatetimeWarehouse;
import fr.unice.polytech.business.Flight;
import fr.unice.polytech.business.Warehouse;
import fr.unice.polytech.servicesconsumers.DroneManagement;
import fr.unice.polytech.servicesconsumers.FlightPlan;
import fr.unice.polytech.servicesconsumers.WarehouseManagement;
import fr.unice.polytech.tools.DroneExpressLogger;


@Stateless(name = "DroneFinder")
public class DroneFinderBean implements DroneFinder {

    private static DroneExpressLogger logger = new DroneExpressLogger(DroneFinderBean.class);

    @EJB
    Statistics statistics;

    @Override
    public Flight findDroneAndDate(Flight flightToCreate) {
        long departure = flightToCreate.getDepartureId();
        long arrival = flightToCreate.getArrivalId();
        LocalDateTime departureDateTime = flightToCreate.getDepartureDate();

        statistics.pushRequest(departure, departureDateTime);

        List<Solution> solutions = new ArrayList<Solution>();

        // retourne premier vol a vide faisant ce trajet

        logger.logInfo("Ask FlightPlan for empty flights");
        Flight f = new FlightPlan().getFirstEmptyFlight(departure, arrival, departureDateTime);
        if (f != null) {
            solutions.add(new Solution(f));
        }

        // Appeller entrepôts
        // retourne drones, entrepot respectif, distance jusqu'à entrepot de
        // depart

        logger.logInfo("Ask Warehouses for availables drones");
        List<DroneDatetimeWarehouse> dDtWs = new WarehouseManagement().findFreeDrone(departure,
                departureDateTime, 2 * estimateFlightLength(flightToCreate.getDepartureId(),
                        flightToCreate.getArrivalId()));
        for (DroneDatetimeWarehouse dDtW : dDtWs) {
            solutions.add(new Solution(dDtW));
        }

        Solution best = null;
        for (int i = 0; i < solutions.size(); i++) {
            solutions.get(i).calculateScore(departureDateTime);
            if (best == null || solutions.get(i).getScore() < best.getScore()) {
                best = solutions.get(i);
            }
        }

        solutions.remove(best);
        freeRessources(solutions);

        Flight returnFlight = null;
        if (best != null) {
            returnFlight = executeBestSolution(best, flightToCreate);
        } else {
            new DroneManagement().alertNoDrone(flightToCreate.getDepartureId(),
                    "No drone in this place ...");
        }

        return returnFlight;
    }

    class LatLon {

        public double latitude;
        public double longitude;

        public LatLon(double latitude, double longiture) {
            this.latitude = latitude;
            this.longitude = longiture;
        }
    }

    private static List<Warehouse> warehouses = new ArrayList<>();

    private static Warehouse addWarehouse(long id) {
        System.out.println("add warehouse : " + id);
        Warehouse w = new WarehouseManagement().getWarehouse(id);
        if (w != null) {
            warehouses.add(w);
        }
        return w;
    }

    private static Warehouse warehouseById(long id) {
        for (Warehouse w : warehouses) {
            if (w.getId() == id) {
                return w;
            }
        }
        return null;
    }

    private LatLon getLatLon(long warehouseId) {
        Warehouse w = warehouseById(warehouseId);
        if (w == null) {
            w = addWarehouse(warehouseId);
        }
        if (w != null) {
            return new LatLon(w.getLatitude(), w.getLongitude());
        }
        return null;
    }

    private long estimateFlightLength(long departure, long arrival) {
        double speed = 20; // m.s-1
        double distance = estimateDistance(departure, arrival);
        double timeSpent = distance / speed; // second

        return (long) (timeSpent / 60 + 1);
    }

    private double estimateDistance(long departure, long arrival) {
        return estimateDistance(departure, getLatLon(arrival));
    }

    private double estimateDistance(long departure, LatLon arr) {
        LatLon dep = getLatLon(departure);

        double depLon = dep.longitude, depLat = dep.latitude;
        double arrLon = arr.longitude, arrLat = arr.latitude;

        double rlat1 = Math.PI * depLat / 180;
        double rlat2 = Math.PI * arrLat / 180;
        double rlon1 = Math.PI * depLon / 180;
        double rlon2 = Math.PI * arrLon / 180;

        double R = 6378000; // rayon terre

        double d = R * (Math.PI / 2 - Math.asin(Math.sin(rlat2) * Math.sin(rlat1)
                + Math.cos(rlon2 - rlon1) * Math.cos(rlat2) * Math.cos(rlat1)));

        return d;
    }

    private void freeRessources(List<Solution> unusedSolutions) {
        for (Solution s : unusedSolutions) {
            s.unlockRessource();
        }
    }

    private Flight executeBestSolution(Solution best, Flight reference) {
        Flight returnFlight = null;
        if (best.emptyFlight != null) {
            logger.logInfo("loading an empty flight (" + best.emptyFlight.getIdent() + ")");
            best.emptyFlight.setParcelId(reference.getParcelId());
            returnFlight = new FlightPlan().loadFlight(best.emptyFlight);
        } else if (best.returnFromWarehouse != null) {
            DroneDatetimeWarehouse ddtw = best.returnFromWarehouse;

            createGoEmptyPath(ddtw);

            Flight flightToCreate = new Flight();
            flightToCreate.setDepartureId(reference.getDepartureId());
            flightToCreate.setArrivalId(reference.getArrivalId());
            flightToCreate.setDepartureDate(ddtw.getAvailability());
            flightToCreate.setDroneId(ddtw.getDroneId());
            flightToCreate.setParcelId(reference.getParcelId());

            logger.logInfo("creating a new flight");
            returnFlight = new FlightPlan().createFlight(flightToCreate);

            if (ddtw.getMustBeBackAt() != null) {
                LocalDateTime arrivalTime = createReturnEmptyPath(ddtw);
                Flight flightToCreateReturn = new Flight();
                flightToCreateReturn.setDepartureId(reference.getArrivalId());
                flightToCreateReturn.setArrivalId(reference.getDepartureId());
                flightToCreateReturn.setDepartureDate(arrivalTime.minusMinutes(estimateFlightLength(
                        reference.getArrivalId(), reference.getDepartureId())));
                flightToCreateReturn.setDroneId(ddtw.getDroneId());

                logger.logInfo("creating a new return flight");
                returnFlight = new FlightPlan().createFlight(flightToCreateReturn);
            }
        }

        return returnFlight;
    }

    @Override
    public Response findDroneAndBack(long departure, double latitude, double longitude,
                                     LocalDateTime departureDateTime) {
        long start = System.currentTimeMillis();
        statistics.pushRequest(departure, departureDateTime);

        List<Solution> solutions = new ArrayList<Solution>();

        logger.logInfo("Demande aux entrepôts s'ils ont des drones disponibles");
        List<DroneDatetimeWarehouse> dDtWs = new WarehouseManagement().findFreeDrone(departure,
                departureDateTime,
                (long) (2 * estimateDistance(departure, new LatLon(latitude, longitude))));

        for (DroneDatetimeWarehouse dDtW : dDtWs) {
            solutions.add(new Solution(dDtW));
        }

        Solution best = null;
        for (int i = 0; i < solutions.size(); i++) {
            solutions.get(i).calculateScore(departureDateTime);
            if (best == null || solutions.get(i).getScore() < best.getScore()) {
                best = solutions.get(i);
            }
        }
        Response resp = null;
        if (best == null) {
            new DroneManagement().alertNoDrone(departure, "Not enought drone");
            statistics.pushResponse(StatResponse.UNFINDABLE, 0, System.currentTimeMillis() - start);
        } else {
            resp = best.createResponseWithArrival(departure);
            DroneDatetimeWarehouse ifonly = best.getReturnFromWarehouse();
            if (ifonly != null) {
                createGoEmptyPath(ifonly);
                if (ifonly.getWareHouseId() == departure) {
                    statistics.pushResponse(StatResponse.LOCALLY, 0,
                            System.currentTimeMillis() - start);
                } else {
                    statistics.pushResponse(StatResponse.DISPLACED, ifonly.getTransportDistance(),
                            System.currentTimeMillis() - start);
                }
            } else {
                statistics.pushResponse(StatResponse.EMPTY, 0, System.currentTimeMillis() - start);
            }
        }
        return resp;
    }

    private void createGoEmptyPath(DroneDatetimeWarehouse ddtw) {
        logger.logInfo("Creating empty path");
        Long currentWarehouse = new Long(ddtw.getWareHouseId());
        LocalDateTime departureTime = ddtw.getStartFly();
        logger.logInfo(ddtw.getJumps().toString());
        for (Long l : ddtw.getJumps()) {
            logger.logInfo("Allé : " + l.intValue());
            Flight flight = new Flight();
            flight.setDroneId(ddtw.getDroneId());
            flight.setDepartureId(currentWarehouse.longValue());
            flight.setArrivalId(l.longValue());
            flight.setDepartureDate(departureTime);

            logger.logInfo("create empty flight from " + flight.getDepartureId() + " to "
                    + flight.getArrivalId() + " with drone " + flight.getDroneId());

            Flight returnF = new FlightPlan().createFlight(flight);
            departureTime = returnF.getArrivalDate();
            currentWarehouse = l;
        }
    }

    private LocalDateTime createReturnEmptyPath(DroneDatetimeWarehouse ddtw) {
        LocalDateTime arrivalTime = ddtw.getMustBeBackAt();
        Long currentWarehouse = new Long(ddtw.getWareHouseId());
        for (Long l : ddtw.getJumps()) {
            logger.logInfo("Retour : " + l.intValue());
            Flight flight = new Flight();
            flight.setDroneId(ddtw.getDroneId());
            flight.setDepartureId(l.longValue());
            flight.setArrivalId(currentWarehouse.longValue());
            flight.setDepartureDate(arrivalTime.minusMinutes(
                    estimateFlightLength(currentWarehouse.longValue(), l.longValue())));

            Flight returnF = new FlightPlan().createFlight(flight);
            arrivalTime = returnF.getDepartureDate();
            currentWarehouse = l;
        }
        return arrivalTime;
    }

    private static class Solution {

        public static final double TIME_COEF = 3;
        public static final double DISTANCE_COEF = 0.1;
        public static final double EMPTY_BONUS = -100;
        /*
         * A prendre en compte - retard temps par rapport à demande -
         * remplissage de drone à vide - distance pour apporter un drone
         */
        private Flight emptyFlight = null;
        private DroneDatetimeWarehouse returnFromWarehouse = null;

        private long timeDiff;
        private boolean emptyFiller = false;
        private double distance = 0;
        private double score = 0;

        public Solution(Flight emptyFlight) {
            this.emptyFlight = emptyFlight;
        }

        public void unlockRessource() {
            if (emptyFlight != null) {
                new FlightPlan().unlockFlight(emptyFlight);
            } else if (returnFromWarehouse != null) {
                new WarehouseManagement().unlock(returnFromWarehouse);
            }
        }

        public Solution(DroneDatetimeWarehouse returnFromWarehouse) {
            this.returnFromWarehouse = returnFromWarehouse;
        }

        public Response createResponseWithArrival(long arrival) {
            Response response = new Response();
            if (emptyFlight != null) {
                response.setDroneId(emptyFlight.getDroneId());
                response.setDeparture(emptyFlight.getDepartureDate());
                response.setArrival(arrival);
                logger.logInfo("Best solution : droneID = " + response.getDroneId() + " [EMPTY]");
            } else if (returnFromWarehouse != null) {
                response.setDroneId(returnFromWarehouse.getDroneId());
                response.setDeparture(returnFromWarehouse.getAvailability());
                response.setArrival(arrival);
                logger.logInfo("Best solution : droneID = " + response.getDroneId());
            }
            return response;
        }

        public void calculateScore(LocalDateTime requiredDepartureDateTime) {
            if (emptyFlight != null) {
                this.timeDiff = requiredDepartureDateTime.until(this.emptyFlight.getDepartureDate(),
                        ChronoUnit.MINUTES);
                this.emptyFiller = true;
                this.distance = 0;
            }
            if (this.returnFromWarehouse != null) {
                this.emptyFiller = false;
                this.timeDiff = requiredDepartureDateTime
                        .until(this.returnFromWarehouse.getAvailability(), ChronoUnit.MINUTES);
                this.distance = this.returnFromWarehouse.getTransportDistance();
            }
            this.score = TIME_COEF * this.timeDiff;
            this.score += DISTANCE_COEF * this.distance;
            this.score += this.emptyFiller ? EMPTY_BONUS : 0;
        }

        public double getScore() {
            return this.score;
        }

        public DroneDatetimeWarehouse getReturnFromWarehouse() {
            return returnFromWarehouse;
        }

    }
}
