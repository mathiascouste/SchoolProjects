package droneexpress.optimizer.webservice;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import droneexpress.optimizer.statistics.Statistics;


@Stateless
@Path("/statistics")
public class StatisticsEndpoint {

    @EJB
    Statistics statistics;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/serverload")
    public Response getServerLoad(@QueryParam("unit") String unit,
                                  @QueryParam("length") int length) {
        if (unit == null) {
            unit = "minute";
        }
        if (length <= 0) {
            length = 1;
        }

        ServerLoad serverLoad = statistics.getServerLoad(unit, length);

        return Response.status(Response.Status.OK).entity(serverLoad.toJSON().toString()).build();
    }

    public class ServerLoad {

        public long totalRequests;
        public double averageResponseDelay;
        public List<Integer> requestByTime;
        public String timeUnit;

        public ServerLoad() {
            totalRequests = 0;
            averageResponseDelay = 0;
            requestByTime = new ArrayList<>();
            timeUnit = "";
        }

        public JSONObject toJSON() {
            JSONObject json = new JSONObject();
            json.put("totalrequest", this.totalRequests);
            json.put("averageresponsedelay", this.averageResponseDelay);
            JSONArray array = new JSONArray();
            for (Integer i : requestByTime) {
                array.put(i);
            }
            json.put("requestsbytime", array);
            return json;
        }
    }
}
