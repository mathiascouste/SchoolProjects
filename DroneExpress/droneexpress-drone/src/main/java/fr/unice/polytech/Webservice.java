package fr.unice.polytech;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONObject;


/**
 * Created by mael on 11/6/15.
 */

/**
 * Complete DelayedDrone.txt according to the content received
 */
@Produces(MediaType.APPLICATION_JSON)
public class Webservice {

  /**
   * Receive a post then create the file DelayedDrone.txt and complete it with in content
   * @param in is the json object wich described the the drone delayed
   * @return bad request if in parse fail otherwise return ok
   */
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public Response postDelayedDrone(String in) {
    try {
      BufferedWriter file = new BufferedWriter(new FileWriter("DelayedDrone.txt", true));
      file.write(getLog(new JSONObject(in)));
      file.newLine();
      file.close();
      return Response.status(Response.Status.OK).entity("").build();
    } catch (IOException e) {
      return Response.status(Response.Status.BAD_REQUEST).entity("").build();
    }
  }

  private String getLog(JSONObject json) {
    int wId = json.getInt("warehouseId");
    String message = json.getString("message");
    String msg = "warehouse:" + wId + ", message:" + message;
    return msg;
  }
}
