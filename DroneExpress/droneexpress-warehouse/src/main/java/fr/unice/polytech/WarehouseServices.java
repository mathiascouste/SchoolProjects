package fr.unice.polytech;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.unice.polytech.business.DroneDatetimeWarehouse;
import fr.unice.polytech.business.Warehouse;
import fr.unice.polytech.tools.DroneExpressLogger;


/**
 * Services exposed by the Warehouse module.
 * 
 * @author: Mael / Laureen.
 */
@Produces(MediaType.APPLICATION_JSON)
public class WarehouseServices {

    private static DroneExpressLogger logger = new DroneExpressLogger(WarehouseServices.class);

    private LocalDateTime stringToLocalDateTime(String date) {
        int Y = new Integer(date.substring(0, 4)).intValue();
        int M = new Integer(date.substring(4, 6)).intValue();
        int D = new Integer(date.substring(6, 8)).intValue();
        int h = new Integer(date.substring(8, 10)).intValue();
        int m = new Integer(date.substring(10, 12)).intValue();
        LocalDateTime ldt = LocalDateTime.of(Y, M, D, h, m);
        return ldt;
    }

    private WarehouseDAO warehouseDAO = new WarehouseDAO();

    @Path("/unlock")
    @PUT
    public Response unlock(DroneDatetimeWarehouse ddtw) {
        MyWarehouse warehouse = warehouseDAO.findById(ddtw.getWareHouseId());
        warehouse.unlockById(ddtw.getDisponibilitySlotId());
        return Response.ok().build();
    }

    @POST
    @Path("/incoming/{warehouse: \\d+}/{drone: \\d+}/{date: \\d{12}}")
    public Response incomingFlight(@PathParam("warehouse") long warehouseId,
                                   @PathParam("drone") long droneId,
                                   @PathParam("date") String date) {
        MyWarehouse warehouse = warehouseDAO.findById(warehouseId);
        if (warehouse != null) {
            warehouse.incomingFlight(droneId, stringToLocalDateTime(date));
            return Response.ok().entity("").build();
        } else {
            return Response.noContent().entity("").build();
        }
    }

    @POST
    @Path("/cancelincoming/{warehouse: \\d+}/{drone: \\d+}/{date: \\d{12}}")
    public Response cancelIncomingFlight(@PathParam("warehouse") long warehouseId,
                                         @PathParam("drone") long droneId,
                                         @PathParam("date") String date) {
        MyWarehouse warehouse = warehouseDAO.findById(warehouseId);
        if (warehouse != null) {
            warehouse.cancelIncomingFlight(droneId, stringToLocalDateTime(date));
            return Response.ok().entity("").build();
        } else {
            return Response.noContent().entity("").build();
        }
    }

    @POST
    @Path("/outgoing/{warehouse: \\d+}/{drone: \\d+}/{date: \\d{12}}")
    public Response outgoingFlight(@PathParam("warehouse") long warehouseId,
                                   @PathParam("drone") long droneId,
                                   @PathParam("date") String date) {

        MyWarehouse warehouse = warehouseDAO.findById(warehouseId);
        if (warehouse != null) {
            warehouse.outgoingFlight(droneId, stringToLocalDateTime(date));
            return Response.ok().build();
        } else {
            return Response.noContent().build();
        }
    }

    @POST
    @Path("/canceloutgoing/{warehouse: \\d+}/{drone: \\d+}/{date: \\d{12}}")
    public Response cancelOutgoingFlight(@PathParam("warehouse") long warehouseId,
                                         @PathParam("drone") long droneId,
                                         @PathParam("date") String date) {

        MyWarehouse warehouse = warehouseDAO.findById(warehouseId);
        if (warehouse != null) {
            warehouse.cancelOutgoingFlight(droneId, stringToLocalDateTime(date));
            return Response.ok().build();
        } else {
            return Response.noContent().build();
        }
    }

    @Path("/{warehouseId}/{from}/{usetimeinminute : \\d+}")
    @GET
    public Response getDronesWarehouses(@PathParam("warehouseId") long warehouseId,
                                        @PathParam("from") String from,
                                        @PathParam("usetimeinminute") int useTimeInMinute) {

        logger.logInfo("Requested : find an available drone in warehouse " + warehouseId + " at "
                + from + " for a duration of " + useTimeInMinute + " minutes");
        LocalDateTime date = stringToLocalDateTime(from);
        List<DroneDatetimeWarehouse> availableDrones = new ArrayList<DroneDatetimeWarehouse>();

        for (MyWarehouse w : warehouseDAO.findAll()) {
            DroneDatetimeWarehouse ddtw = w.findDroneToGoTo(warehouseId, date, useTimeInMinute);
            if (ddtw != null) {
                availableDrones.add(ddtw);
            }
        }

        JSONObject js = new JSONObject();
        List<JSONObject> drones = new ArrayList<JSONObject>();
        logger.logInfo("Number of possible response : " + availableDrones.size());
        for (DroneDatetimeWarehouse ddtw : availableDrones) {
            drones.add(createDroneJson(ddtw));
        }
        js.put("drones", drones);
        return Response.ok().entity(js.toString()).build();
    }

    @Path("/warehouses")
    @GET
    public Response getWarehouses() {
        try {
            String droneJSON = new ObjectMapper().writeValueAsString(warehouseDAO.findAll());

            return Response.status(Response.Status.OK).entity(droneJSON).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("").build();
        }
    }

    @Path("/{warehouseId: \\d+}")
    @GET
    public Warehouse getWarehouse(@PathParam("warehouseId") long warehouseId) {
        Warehouse warehouse = warehouseDAO.findById(warehouseId);
        if (warehouse != null) {
            return warehouse;
        } else {
            throw new NotFoundException("warehouse not found");
        }

    }

    @Path("/{warehouseId: \\d+}/slots")
    @GET
    public Response getWarehouseSlots(@PathParam("warehouseId") long warehouseId) {
        MyWarehouse warehouse = warehouseDAO.findById(warehouseId);
        if (warehouse != null) {
            JSONObject jsonObject = new JSONObject();
            JSONArray jsonArray = new JSONArray();
            jsonObject.put("slots", jsonArray);
            for (DisponibilitySlot ds : warehouse.getSlots()) {
                JSONObject j = new JSONObject();
                j.put("id", ds.getId());
                j.put("droneid", ds.getDroneId());
                j.put("begining", ds.getBegining());
                j.put("end", ds.getEnd() != null ? ds.getEnd().toString() : "end of time");
                jsonArray.put(j);
            }
            jsonArray = new JSONArray();
            jsonObject.put("lockedSlot", jsonArray);
            for (DisponibilitySlot ds : warehouse.getLockedSlots()) {
                jsonArray.put(ds.getId());
            }

            return Response.ok(jsonObject.toString()).build();
        } else {
            throw new NotFoundException("warehouse not found");
        }

    }

    private JSONObject createDroneJson(DroneDatetimeWarehouse ddtw) {
        JSONObject js = new JSONObject();
        js.put("warehouseId", ddtw.getWareHouseId());
        js.put("droneId", ddtw.getDroneId());
        js.put("disponibility", getJsondate(ddtw.getAvailability()));
        js.put("transportDistance", ddtw.getTransportDistance());
        js.put("transportTime", ddtw.getTransportDuration().toMinutes());
        js.put("jumps", getJSONArray(ddtw.getJumps()));
        js.put("startfly", getJsondate(ddtw.getStartFly()));
        if (ddtw.getMustBeBackAt() == null) {
            js.put("isMustBeBackSet", false);
        } else {
            js.put("isMustBeBackSet", true);
            js.put("mustBeBackAt", getJsondate(ddtw.getMustBeBackAt()));
        }
        js.put("disponibilityslotid", ddtw.getDisponibilitySlotId());
        return js;
    }

    private JSONArray getJSONArray(List<Long> jumps) {
        JSONArray array = new JSONArray();
        if (jumps != null) {
            for (Long l : jumps) {
                array.put(l.longValue());
            }
        }
        return array;
    }

    private JSONObject getJsondate(LocalDateTime date) {
        JSONObject js = new JSONObject();
        js.put("year", date.getYear());
        js.put("month", date.getMonthValue());
        js.put("day", date.getDayOfMonth());
        js.put("hour", date.getHour());
        js.put("minute", date.getMinute());
        return js;
    }
}
