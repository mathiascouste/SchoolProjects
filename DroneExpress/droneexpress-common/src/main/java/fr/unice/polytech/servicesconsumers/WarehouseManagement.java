package fr.unice.polytech.servicesconsumers;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.unice.polytech.business.DroneDatetimeWarehouse;
import fr.unice.polytech.business.Warehouse;
import fr.unice.polytech.tools.HTTPHelper;
import fr.unice.polytech.tools.HTTPHelper.Method;
import fr.unice.polytech.utils.Utils;


/**
 * Warehouse has a list of the drones that are currently stored and a list of the flight plans
 * planed.
 * 
 * @author Laureen Ginier
 */
public class WarehouseManagement {

    public void unlock(DroneDatetimeWarehouse ddtw) {
        String urlParam = "/unlock";
        String url = HTTPHelper.EXTERNAL_ADDRESS_WAREHOUSE + urlParam;
        HTTPHelper httpHelp = new HTTPHelper().setUrl(url).setRequestMethod(Method.PUT);

        String jsonStr;
        try {
            jsonStr = new ObjectMapper().writeValueAsString(ddtw);

            httpHelp.setRequestBody(jsonStr);
            httpHelp.call();
        } catch (JsonProcessingException e1) {
            e1.printStackTrace();
        }
    }

    public List<Warehouse> getWarehouses() {
        List<Warehouse> warehouses = new ArrayList<>();
        HTTPHelper helper = new HTTPHelper()
                .setUrl(HTTPHelper.EXTERNAL_ADDRESS_WAREHOUSE + "/warehouses")
                .setRequestMethod(Method.GET);
        helper.call();
        if (helper.getResponseCode() == HTTPHelper.OK) {
            String response = helper.getResponseBody();
            JSONArray warehousesArray = new JSONArray(response);
            for (int i = 0; i < warehousesArray.length(); i++) {
                JSONObject wjson = warehousesArray.getJSONObject(i);
                try {
                    warehouses.add(new Warehouse(wjson.getLong("id"), wjson.getString("name"),
                            wjson.getDouble("latitude"), wjson.getDouble("longitude")));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return warehouses;
    }

    public Warehouse getWarehouse(long warehouseId) {
        Warehouse warehouse = null;
        String urlParam = "/" + warehouseId;
        String url = HTTPHelper.EXTERNAL_ADDRESS_WAREHOUSE + urlParam;
        HTTPHelper httphelper = new HTTPHelper().setRequestMethod(Method.GET).setUrl(url);
        httphelper.call();
        if (httphelper.getResponseCode() == HTTPHelper.OK) {
            warehouse = httphelper.getResponseAs(Warehouse.class);
        }
        return warehouse;
    }

    public List<DroneDatetimeWarehouse> findFreeDrone(long warehouseId, LocalDateTime date,
                                                      long usetimeinminute) {

        List<DroneDatetimeWarehouse> availableDrones = new ArrayList<DroneDatetimeWarehouse>();

        String urlParam = "/" + warehouseId + "/" + HTTPHelper.LocalDateTimeToStringFormat(date)
                + "/" + usetimeinminute;

        String url = HTTPHelper.EXTERNAL_ADDRESS_WAREHOUSE + urlParam;
        HTTPHelper httpHelp = new HTTPHelper().setUrl(url).setRequestMethod(Method.GET);
        httpHelp.call();

        if (httpHelp.getResponseCode() == HTTPHelper.OK) {
            String response = httpHelp.getResponseBody();

            JSONObject json = new JSONObject(response);
            JSONArray drones = json.getJSONArray("drones");
            for (int i = 0; i < drones.length(); i++) {
                JSONObject drone = drones.getJSONObject(i);
                JSONArray jumpsArray = drone.getJSONArray("jumps");
                List<Long> jumps = new ArrayList<Long>();
                for (int j = 0; j < jumpsArray.length(); j++) {
                    jumps.add(new Long(jumpsArray.getLong(j)));
                }

                DroneDatetimeWarehouse ddtw = new DroneDatetimeWarehouse(
                        drone.getLong("warehouseId"), drone.getLong("droneId"),
                        Utils.jSonToLocalDateTime(drone.getJSONObject("disponibility")),
                        doubleToDuration(drone.getDouble("transportTime")),
                        drone.getDouble("transportDistance"), jumps,
                        Utils.jSonToLocalDateTime(drone.getJSONObject("startfly")));
                if (drone.getBoolean("isMustBeBackSet")) {
                    ddtw.setMustBeBackAt(
                            Utils.jSonToLocalDateTime(drone.getJSONObject("mustBeBackAt")));
                }
                ddtw.setDisponibilitySlotId(new Long(drone.getLong("disponibilityslotid")));
                availableDrones.add(ddtw);
            }
        }

        return availableDrones;
    }

    public boolean incomingFlight(long warehouseId, long droneId, LocalDateTime date) {
        String urlParam = "/incoming/" + warehouseId + "/" + droneId + "/"
                + localDateTimeTOPathParam(date);
        String url = HTTPHelper.EXTERNAL_ADDRESS_WAREHOUSE + urlParam;
        HTTPHelper httpHelp = new HTTPHelper().setRequestMethod(Method.POST).setUrl(url);
        httpHelp.call();
        return httpHelp.getResponseCode() == HTTPHelper.OK;
    }

    public boolean outgoingFlight(long warehouseId, long droneId, LocalDateTime date) {
        String urlParam = "/outgoing/" + warehouseId + "/" + droneId + "/"
                + localDateTimeTOPathParam(date);
        String url = HTTPHelper.EXTERNAL_ADDRESS_WAREHOUSE + urlParam;
        HTTPHelper httpHelp = new HTTPHelper().setRequestMethod(Method.POST).setUrl(url);
        httpHelp.call();

        return httpHelp.getResponseCode() == HTTPHelper.OK;
    }

    public boolean cancelIncomingFlight(long warehouseId, long droneId, LocalDateTime date) {
        String urlParam = "/cancelincoming/" + warehouseId + "/" + droneId + "/"
                + localDateTimeTOPathParam(date);
        String url = HTTPHelper.EXTERNAL_ADDRESS_WAREHOUSE + urlParam;
        HTTPHelper httpHelp = new HTTPHelper().setRequestMethod(Method.POST).setUrl(url);
        httpHelp.call();
        return httpHelp.getResponseCode() == HTTPHelper.OK;
    }

    public boolean cancelOutgoingFlight(long warehouseId, long droneId, LocalDateTime date) {
        String urlParam = "/canceloutgoing/" + warehouseId + "/" + droneId + "/"
                + localDateTimeTOPathParam(date);
        String url = HTTPHelper.EXTERNAL_ADDRESS_WAREHOUSE + urlParam;
        HTTPHelper httpHelp = new HTTPHelper().setRequestMethod(Method.POST).setUrl(url);
        httpHelp.call();

        return httpHelp.getResponseCode() == HTTPHelper.OK;
    }

    private String localDateTimeTOPathParam(LocalDateTime arrivalDate) {
        String year = "" + arrivalDate.getYear();
        String month = "" + arrivalDate.getMonthValue();
        String day = "" + arrivalDate.getDayOfMonth();
        String hour = "" + arrivalDate.getHour();
        String minute = "" + arrivalDate.getMinute();
        if (month.length() == 1) {
            month = "0" + month;
        }
        if (day.length() == 1) {
            day = "0" + day;
        }
        if (hour.length() == 1) {
            hour = "0" + hour;
        }
        if (minute.length() == 1) {
            minute = "0" + minute;
        }
        return year + month + day + hour + minute;
    }

    private Duration doubleToDuration(double d) {
        return Duration.of((long) d, ChronoUnit.MINUTES);
    }

}
