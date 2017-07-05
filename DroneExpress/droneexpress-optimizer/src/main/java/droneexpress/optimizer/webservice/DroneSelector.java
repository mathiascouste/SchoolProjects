package droneexpress.optimizer.webservice;

import java.time.LocalDateTime;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import droneexpress.optimizer.dronefinder.DroneFinder;
import fr.unice.polytech.business.Flight;
import fr.unice.polytech.tools.DroneExpressLogger;


@Stateless
@Path("/droneselector")
public class DroneSelector {

    private static DroneExpressLogger logger = new DroneExpressLogger(DroneSelector.class);

    @EJB
    DroneFinder droneFinder;

    /**
     * example: http://localhost:8080/droneexpress-optimizer/droneselector/finddroneanddate/1/2/1/
     * 201602041230
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/finddroneanddate/{parcelid : \\d+}/{departure : \\d+}/{arrival : \\d+}/{departuredatetime : \\d{12}}")
    public Response findDroneAndDate(@PathParam("parcelid") long parcelId,
                                     @PathParam("departure") long departure,
                                     @PathParam("arrival") long arrival,
                                     @PathParam("departuredatetime") String departureDateTime) {
        logger.logInfo("Request : find drone " + departure + "->" + arrival + " since "
                + departureDateTime + " + find date of availability");

        Flight responseFlight = null;

        try {
            Flight referenceFlight = new Flight();
            referenceFlight.setParcelId(parcelId);
            referenceFlight.setDepartureId(departure);
            referenceFlight.setArrivalId(arrival);
            referenceFlight.setDepartureDate(stringToLocalDateTime(departureDateTime));
            responseFlight = droneFinder.findDroneAndDate(referenceFlight);
        } catch (Exception e) {
            e.printStackTrace();
            logger.logError(e.toString());
            for (StackTraceElement s : e.getStackTrace()) {
                logger.logError("at " + s.getFileName() + " : " + s.getLineNumber());
            }
        }

        if (responseFlight != null) {
            try {
                return Response.status(Response.Status.OK)
                        .entity(new ObjectMapper().writeValueAsString(responseFlight)).build();
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

        }
        return Response.status(Response.Status.NOT_FOUND).build();

    }

    // Coordinate de la forme : "N:12.123E:165.654321"
    // Date de la forme : YYYYMMDDhhmm
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/finddroneandback/{departure : \\d+}/{latitude : \\d{1,2}\\.\\d{2,}}/{longitude : \\d{1,3}\\.\\d{2,}}/{departuredatetime : \\d{12}}")
    public Response findDroneAndBack(@PathParam("departure") long departure,
                                     @PathParam("latitude") String latitude,
                                     @PathParam("longitude") String longitude,
                                     @PathParam("departuredatetime") String departureDateTime) {
        logger.logInfo("Requête reçue : trouver un drone qui part de " + departure + " à N:"
                + latitude + "E:" + longitude + " à partir de " + departureDateTime
                + " + trouver entrepôt de retour");
        DroneFinder.Response resp = droneFinder.findDroneAndBack(departure,
                Double.parseDouble(latitude), Double.parseDouble(longitude),
                stringToLocalDateTime(departureDateTime));

        if (resp != null) {
            return Response.status(Response.Status.OK).entity(resp.toJSON()).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    private LocalDateTime stringToLocalDateTime(String date) {
        int Y = Integer.parseInt(date.substring(0, 4));
        int M = Integer.parseInt(date.substring(4, 6));
        int D = Integer.parseInt(date.substring(6, 8));
        int h = Integer.parseInt(date.substring(8, 10));
        int m = Integer.parseInt(date.substring(10, 12));
        LocalDateTime ldt = LocalDateTime.of(Y, M, D, h, m);
        return ldt;
    }
}
