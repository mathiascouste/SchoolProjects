package fr.unice.polytech;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONObject;

import fr.unice.polytech.business.Drone;
import fr.unice.polytech.tools.DroneExpressLogger;


/**
 * Created by mael on 11/6/15. Updated by Amir on 11/11/15. Updated by Laureen on 12/11/15.
 */

@Produces(MediaType.APPLICATION_JSON)
public class DroneManagementService {

    private static DroneExpressLogger logger = new DroneExpressLogger(DroneManagementService.class);

    /**
     * Receive a post then create the file DelayedDrone.txt and complete it with in content
     * 
     * @param in is the json object wich described the the drone delayed
     * @return bad request if in parse fail otherwise return ok
     */
    @Path("/delayed")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postDelayedDrone(String in) {

        try {
            BufferedWriter file = new BufferedWriter(new FileWriter("DelayedDrone.txt", true));
            file.write(Utils.parseAlert(new JSONObject(in)));
            file.newLine();
            file.close();
            return Response.status(Response.Status.OK).entity("").build();
        } catch (IOException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("").build();
        }
    }

    /**
     * get the drone object from the id then wrap it and send it
     * 
     * @param droneId is the id of the drone instance
     * @return the drone instance wrapped in json
     */
    @Path("/drone/{droneId}")
    @GET
    public Response getDroneInfo(@PathParam("droneId") long droneId) {
        try {
            logger.logInfo("Call Service getDroneInfo : id = " + droneId);
            Drone drone = Database.getDroneById(droneId);

            String droneJSON = Utils.getMapper().writeValueAsString(drone);

            logger.logInfo("res = " + droneJSON);
            return Response.status(Response.Status.OK).entity(droneJSON).build();
        } catch (Exception e) {
            logger.logError("DroneManagementService.getDroneInfo");
            return Response.status(Response.Status.BAD_REQUEST).entity("").build();
        }
    }

    /**
     * get all the drones in the system then wrap them in json and send them
     * 
     * @return all the drones instances wrapped in json
     */
    @Path("/drones")
    @GET
    public Response getDronesInfo() {
        try {
            String droneJSON = Utils.getMapper().writeValueAsString(Database.drones);

            return Response.status(Response.Status.OK).entity(droneJSON).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("").build();
        }
    }

}
