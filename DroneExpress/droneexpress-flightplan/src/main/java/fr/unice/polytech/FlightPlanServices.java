package fr.unice.polytech;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.unice.polytech.business.Flight;
import fr.unice.polytech.business.Warehouse;
import fr.unice.polytech.servicesconsumers.WarehouseManagement;
import fr.unice.polytech.tools.Distance;
import fr.unice.polytech.tools.DroneExpressLogger;
import fr.unice.polytech.utils.Utils;


/**
 * @author Mael on 11/6/15. Updated by Laureen on 11/8/15.
 * @version 1.1
 */

@Produces(MediaType.APPLICATION_JSON)
public class FlightPlanServices {

    private FlightPlanStorageMock storage;
    private static DroneExpressLogger logger = new DroneExpressLogger(FlightPlanServices.class);

    public FlightPlanServices() {
        storage = FlightPlanStorageMock.getInstance();
    }

    @Path("/unlock")
    @PUT
    public Response unlockFlight(Flight flight) {
        this.storage.unlockFlight(flight);
        return Response.ok().build();
    }

    /**
     * Returns the earliest empty matching flight that flies from departure to arrival.
     * 
     * @param departure location where is located the package to deliver
     * @param arrival location where the package must be delivered
     * @param from date and time from when the package can be handled
     * @return earliest empty matching flight
     */
    @Path("/findemptyflight/{departure}/{arrival}/{from}")
    @GET
    public Flight findFirstEmptyFlight(@PathParam("departure") int departure,
                                       @PathParam("arrival") int arrival,
                                       @PathParam("from") String from) {
        LocalDateTime dateFrom = Utils.stringToLocalDateTime(from);
        logger.logInfo("Requested : an empty flight from " + departure + " to " + arrival
                + " since " + dateFrom);
        Flight firstInTime = null;
        for (Flight f : storage.findAll()) {
            if (!(f.getParcelId() >= 0)) {
                if (f.getDepartureId() == departure && f.getArrivalId() == arrival) {
                    if (dateFrom.isBefore(f.getDepartureDate())) {
                        if (firstInTime == null) {
                            if (storage.lockFlight(f)) {
                                firstInTime = f;
                            }
                        } else if (f.getDepartureDate().isBefore(firstInTime.getDepartureDate())) {
                            if (storage.lockFlight(f)) {
                                storage.unlockFlight(firstInTime);
                                firstInTime = f;
                            }
                        }
                    }
                }
            }
        }

        if (firstInTime == null) {
            logger.logInfo("Found empty flight : none");
            throw new NotFoundException();
        }
        logger.logInfo("Found empty flight : " + firstInTime.getDepartureId() + "("
                + firstInTime.getDepartureDate() + ")" + "->" + firstInTime.getArrivalId() + "("
                + firstInTime.getArrivalDate() + ") - with drone n#" + firstInTime.getDroneId());

        return firstInTime;
    }

    @Path("/createflight")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Flight createFlight(String flightStr) {

        Flight flight = null;
        try {
            flight = new ObjectMapper().readValue(flightStr, Flight.class);
        } catch (IOException e1) {
            logger.logError("Cannot read json");
            return null;
        }

        logger.logInfo("Request received : create a new flight");
        if (flight == null) {
            throw new BadRequestException("no POST data");
        }
        if (storage.read(flight.getIdent()) != null) {
            throw new ClientErrorException(Response.status(Response.Status.CONFLICT)
                    .entity("\"Existing flight " + flight.getIdent() + "\"")
                    .type(MediaType.TEXT_PLAIN_TYPE).build());
        }
        LocalDateTime estimatedArrivalDateTime = this.estimateArrivalDateTime(
                flight.getDepartureId(), flight.getArrivalId(), flight.getDepartureDate());
        flight.setArrivalDate(estimatedArrivalDateTime);
        storage.create(flight);

        tellWarehousesAboutFlight(flight);

        return flight;
    }

    @Path("/updateflight")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateFlight(Flight flight) {
        if (flight == null) {
            throw new BadRequestException("no PUT data");
        }
        logger.logInfo("Requested : update flight " + flight.getIdent());
        Flight existingFlight = storage.read(flight.getIdent());
        if (existingFlight == null) {
            throw new NotFoundException("No corresponding flight found");
        }

        existingFlight.merge(flight);
        if (storage.isLocked(flight)) {
            storage.unlockFlight(flight);
        }

        try {
            return Response.ok().entity(new ObjectMapper().writeValueAsString(existingFlight))
                    .build();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return Response.status(Status.INTERNAL_SERVER_ERROR).build();
    }

    @Path("/load/{id: \\d+}/{parcelid: \\d+}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response loadFlight(@PathParam("id") long id, @PathParam("parcelid") long parcelId) {
        logger.logInfo("Requested : load flight " + id);
        Flight existingFlight = storage.read(id);
        if (existingFlight == null) {
            throw new NotFoundException("No corresponding flight found");
        }
        if (existingFlight.getParcelId() >= 0) {
            return Response.status(Status.CONFLICT).entity("This flight is already loaded").build();
        }

        existingFlight.setParcelId(parcelId);
        if (storage.isLocked(existingFlight)) {
            storage.unlockFlight(existingFlight);
        }

        try {
            return Response.ok().entity(new ObjectMapper().writeValueAsString(existingFlight))
                    .build();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return Response.status(Status.INTERNAL_SERVER_ERROR).build();
    }

    /**
     * Retourne l'ensemble des vols qui vont de departure à arrival entre from et to (chargé ou non
     * selon le flag)
     */
    @Path("/flights")
    @GET
    public List<Flight> findFlights() {
        List<Flight> retList = new ArrayList<Flight>();
        for (Flight f : storage.findAll()) {
            retList.add(f);
        }

        return retList;
    }

    /**
     * Retrieve all the flights related to a specific parcel and that are not in progress. (And that
     * are not planned before 5 min for instance).
     * 
     * @param parcelId
     * @return
     */
    @Path("/flightsbyparcelid/{parcelId}")
    @GET
    public List<Flight> findFlightsByParcelId(@PathParam("parcelId") long parcelId) {
        List<Flight> relatedFlights = new ArrayList<>();
        for (Flight f : storage.findAll()) {
            if (f.getParcelId() == parcelId && f.getDepartureDate().isAfter(LocalDateTime.now())) {
                relatedFlights.add(f);
            }
        }
        return relatedFlights;
    }

    /**
     * Clean the given itinerary (list of flights) by deleting the flights that are not necessary
     * (the drone performing the flight is not expected in the warehouse of destination) or by
     * transforming them into empty ones.
     * 
     * @param flights
     * @return
     */
    @Path("/cleanitinerary")
    @PUT
    public boolean cleanItinerary(List<Flight> flights) {
        for (int i = flights.size() - 1; i >= 0; i--) {
            Flight f = flights.get(i);
            // The drone has a futur delivery scheduled in the arrival warehouse, so the flight
            // cannot be canceled.
            if (isNeeded(f.getDroneId(), f.getArrivalId(), f.getArrivalDate())) {
                // Transform it into an empty flight, if it is not already the case
                if (f.getParcelId() > 0) {
                    f.setParcelId(-1);
                }
                // The house is not delivered anymore, as the parcel must be brought into a drive-in
                // if (!f.getCheckpointsIds().isEmpty()) {
                // f.getCheckpointsIds().clear();
                // }
            } else {
                // The drone is not expected in the next destination so we can cancel the flight
                if (new WarehouseManagement().cancelIncomingFlight(f.getArrivalId(), f.getDroneId(),
                        f.getArrivalDate())
                        && new WarehouseManagement().cancelOutgoingFlight(f.getDepartureId(),
                                f.getDroneId(), f.getDepartureDate())) {
                    storage.delete(f.getIdent());
                }
            }
        }
        return false;
    }

    /**
     * Indicates if a drone will be used for a future flight leaving from the given warehouse.
     * 
     * @param droneId
     * @param warehouseId
     * @return
     */
    private boolean isNeeded(long droneId, long warehouseId, LocalDateTime date) {
        List<Flight> futureFlights = listFutureFlightsByDroneId(droneId, date);
        for (Flight f : futureFlights) {
            if (f.getDepartureId() == warehouseId) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns all the future flights planned for a specific drone.
     * 
     * @param droneId
     * @return
     */
    private List<Flight> listFutureFlightsByDroneId(long droneId, LocalDateTime date) {
        List<Flight> plannedFlight = new ArrayList<>();
        for (Flight f : storage.findAll()) {
            if (f.getDepartureDate().isAfter(date) && f.getDroneId() == droneId) {
                plannedFlight.add(f);
            }
        }
        return plannedFlight;
    }

    private LocalDateTime estimateArrivalDateTime(long departure, long arrival,
                                                  LocalDateTime takeOff) {
        double speed = 20; // m.s-1
        double distance = estimateDistance(departure, arrival);
        double timeSpent = distance / speed; // second
        double timeSpentInMinutes = timeSpent / 60;

        return takeOff.plusMinutes((long) timeSpentInMinutes);
    }

    private double estimateDistance(long departure, long arrival) {
        LatLon dep = getLatLon(departure);
        LatLon arr = getLatLon(arrival);
        if (dep == null || arr == null) {
            throw new BadRequestException(
                    "No warehouse exists with id " + departure + " or " + arrival);
        }

        return Distance.gpsDistance(dep.latitude, dep.longitude, arr.latitude, arr.longitude);
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

    private void tellWarehousesAboutFlight(Flight flight) {
        new WarehouseManagement().outgoingFlight(flight.getDepartureId(), flight.getDroneId(),
                flight.getDepartureDate());
        new WarehouseManagement().incomingFlight(flight.getArrivalId(), flight.getDroneId(),
                flight.getArrivalDate());
    }

    class LatLon {

        public double latitude;
        public double longitude;

        public LatLon(double latitude, double longiture) {
            this.latitude = latitude;
            this.longitude = longiture;
        }
    }
}
