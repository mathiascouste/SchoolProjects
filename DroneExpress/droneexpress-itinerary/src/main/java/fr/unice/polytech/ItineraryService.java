package fr.unice.polytech;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.unice.polytech.business.Delivery;
import fr.unice.polytech.business.Flight;
import fr.unice.polytech.graph.Arc;
import fr.unice.polytech.servicesconsumers.FlightPlan;
import fr.unice.polytech.servicesconsumers.Optimizer;
import fr.unice.polytech.tools.DroneExpressLogger;
import fr.unice.polytech.utils.Utils;


/**
 * Services exposed by the Itinerary module.
 * 
 * @author Mathias Cousté
 */
public class ItineraryService {

    /** Logger */
    private static DroneExpressLogger logger = new DroneExpressLogger(ItineraryService.class);

    /** Constructs and plans the shortest itinerary to take to deliver the parcel */
    @Path("")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createItinerary(String deliveryStr)
            throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper om = new ObjectMapper();
        Delivery delivery = om.readValue(deliveryStr, Delivery.class);
        if (createItineraryAndFlightPlan(delivery)) {
            return Response.ok().entity(om.writeValueAsString(delivery)).build();
        } else {
            return Response.status(Status.NOT_FOUND).entity("No itinerary found").build();
        }
    }

    /**
     * Request to change the delivery address of a parcel.
     * 
     * @param parcelId parcel to deliver
     * @param newDeliveryPointId ident of the warehouse drive-in where one wants the parcel to be
     *        delivered
     * @return the code to retrieve the parcel in the drive-in if the changement can be done, a
     *         warning message if it is too late to change the delivery address.
     */
    @Path("update/{parcelId: \\d+}/{newDestination: \\d+}")
    @PUT
    public Response changeItinerary(@PathParam("parcelId") long parcelId,
                                    @PathParam("newDestination") long newDeliveryPointId) {
        // ask FlightPlan all the flight related to this parcel
        List<Flight> currentItinerary = new FlightPlan().findFlightsByParcelId(parcelId);
        // Sort the flights by date of departure
        sortFlightsByDate(currentItinerary);

        // Check the state of the delivery (delivering the house or not).
        // House is being delivered...
        if (currentItinerary.size() == 0) {
            throw new WebApplicationException(
                    "Your package is being delivered. It is too late to modify the delivery address",
                    Status.NOT_MODIFIED);
        }
        // Check if the destination is a new one, else tell the user that he is a noob
        Flight deliveryPoint = currentItinerary.get(currentItinerary.size() - 1);
        if (deliveryPoint.getIdent() == newDeliveryPointId) {
            throw new BadRequestException(
                    "Warehouse " + newDeliveryPointId + " is already the delivery address");
        }
        // House is not being delivered (the parcel is in a warehouse or flying to a warehouse)
        // calculates new itinerary
        List<Arc> newItinerary = calculateItinerary(currentItinerary.get(0).getDepartureId(),
                newDeliveryPointId);
        // compare old and new itineraries
        LocalDateTime startTime = getDiff(currentItinerary, newItinerary);
        // clean the existing different part (call FlightPlan)
        new FlightPlan().cleanItinerary(currentItinerary);
        // create the new different part.
        Delivery delivery = new Delivery();
        delivery.setParcelId(parcelId);

        if (createItineraryFlights(delivery, Utils.LocalDateTimeTOStringYYYYMMDDmmhh(startTime),
                newItinerary)) {
            // if ok return a generated code to the user (id of the delivery for instance)

            return Response.ok().build();
        } else {
            // else return error
            return Response.serverError().build();
        }
    }

    private LocalDateTime getDiff(List<Flight> originalItinerary, List<Arc> newItinerary) {
        LocalDateTime newStartTime = null;

        int min = originalItinerary.size() < newItinerary.size() ? originalItinerary.size()
                : newItinerary.size();
        int commonPart = -1;
        for (int i = 0; i < min; i++) {
            Flight f = originalItinerary.get(i);
            Arc a = newItinerary.get(newItinerary.size() - 1 - i);

            if (f.getDepartureId() == ((Warehouse) a.getSource()).getWarehouseId()
                    && f.getArrivalId() == ((Warehouse) a.getPuit()).getWarehouseId()) {
                commonPart = i;
            } else {
                i = min;
            }
        }
        if (commonPart != -1) {
            for (int i = 0; i < commonPart; i++) {
                newStartTime = originalItinerary.get(0).getArrivalDate();
                originalItinerary.remove(0);
                newItinerary.remove(newItinerary.size() - 1);
            }
        } else if (originalItinerary.size() != 0) {
            newStartTime = originalItinerary.get(0).getDepartureDate();
        }
        return newStartTime;
    }

    /**
     * Sort the list of flights by date of departure.
     * 
     * @param currentItinerary list of flights to be sorted
     */
    private void sortFlightsByDate(List<Flight> currentItinerary) {
        Collections.sort(currentItinerary, new Comparator<Flight>() {

            @Override
            public int compare(Flight f1, Flight f2) {
                return f1.getDepartureDate().compareTo(f2.getDepartureDate());
            }
        });
    }

    /**
     * Returns the second to last flight (on a temporal scale).
     * 
     * @param flights a list of flights
     * @return the second to last to be scheduled
     */
    private Flight getSecondToLastFlight(List<Flight> flights) {
        Flight last = null, secondToLast = null;
        for (Flight f : flights) {
            LocalDateTime fDepart = f.getDepartureDate();
            if (last == null || fDepart.isAfter(last.getDepartureDate())) {
                secondToLast = last;
                last = f;
            } else {
                if (secondToLast == null || fDepart.isAfter(secondToLast.getDepartureDate())) {
                    secondToLast = f;
                }
            }
        }
        return secondToLast;
    }

    /**
     * Creates the shortest itinerary to deliver the parcel.
     * 
     * @param delivery information about the delivery of the parcel
     * @return true if the itinerary has been created successfully, false otherwise.
     */
    private List<Arc> calculateItinerary(long departureId, long arrivalId) {

        Warehouse dep = PathCreator.getWarehouseById(departureId);
        Warehouse arr = PathCreator.getWarehouseById(arrivalId);

        if (dep == null || arr == null) {
            return null;
        }

        logger.logInfo("Creating path from " + dep.getName() + " to " + arr.getName());

        // Calculates the shortest itinerary between departure and arrival
        return PathCreator.itineraire(dep, arr);
    }

    /**
     * Creates the itinerary between departure and arrival. And give it to FlightPlan module that
     * creates all the flight plans corresponding.
     * 
     * @param delivery information about the delivery to perform
     * @return true if all flight plans have been created successfully, false otherwise
     */
    private boolean createItineraryAndFlightPlan(Delivery delivery) {
        String startTime = Utils.LocalDateTimeTOStringYYYYMMDDmmhh(delivery.getDepartureTime());
        List<Arc> arcs = calculateItinerary(delivery.getDepartureLocation(),
                delivery.getArrivalLocation());
        if (arcs == null) {
            return false;
        }
        return createItineraryFlights(delivery, startTime, arcs);
    }

    private boolean createItineraryFlights(Delivery delivery, String startTime, List<Arc> arcs) {
        // For each arc, creates the flight
        for (int i = arcs.size() - 1; i >= 0; i--) {
            Arc a = arcs.get(i);

            Warehouse from = (Warehouse) a.getSource();
            Warehouse to = (Warehouse) a.getPuit();
            logger.logInfo("step X : from " + from.getName() + " to " + to.getName());

            // Call the module Optimizer to create the best flight
            Flight arcFlight = new Optimizer().findDroneAndDate(delivery.getParcelId(),
                    from.getWarehouseId(), to.getWarehouseId(), startTime);
            if (arcFlight != null) {
                LocalDateTime nextStart = arcFlight.getArrivalDate().plusMinutes(10);
                startTime = Utils.LocalDateTimeTOStringYYYYMMDDmmhh(nextStart);
            } else {
                return false;
            }
        }
        delivery.setArrivalTime(Utils.stringToLocalDateTime(startTime).minusMinutes(10));
        return true;
    }
}
