package fr.unice.polytech.servicesconsumers;

import java.util.ArrayList;
import java.util.List;

import fr.unice.polytech.business.Drone;
import fr.unice.polytech.tools.HTTPHelper;
import fr.unice.polytech.tools.HTTPHelper.Method;


public class DroneManagement {

    public List<Drone> getDronesInfo() {
        String urlParam = "/drones";
        List<Drone> drones = new ArrayList<>();

        String url = HTTPHelper.EXTERNAL_ADDRESS_DRONE + urlParam;
        HTTPHelper httpHelp = new HTTPHelper().setUrl(url).setRequestMethod(Method.GET);
        httpHelp.call();
        if (httpHelp.getResponseCode() == HTTPHelper.OK) {

        }

        return drones;
    }

    public Drone getDroneInfo(Long droneId) {
        String urlParam = "/drone/" + droneId;
        Drone drone = null;

        String url = HTTPHelper.EXTERNAL_ADDRESS_DRONE + urlParam;
        HTTPHelper httpHelp = new HTTPHelper().setUrl(url).setRequestMethod(Method.GET);
        httpHelp.call();
        if (httpHelp.getResponseCode() == HTTPHelper.OK) {
            drone = httpHelp.getResponseAs(Drone.class);
        }

        return drone;
    }

    public boolean alertNoDrone(Long warehouseID, String message) {
        String urlParam = "/delayed";
        String jsonmessage = "{\"warehouseId\":" + warehouseID + ",\"message\":\"" + message
                + "\"}";

        String url = HTTPHelper.EXTERNAL_ADDRESS_DRONE + urlParam;
        HTTPHelper httpHelp = new HTTPHelper().setUrl(url).setRequestMethod(HTTPHelper.Method.POST)
                .setRequestBody(jsonmessage);
        httpHelp.call();
        if (httpHelp.getResponseCode() == HTTPHelper.OK) {
            return true;
        } else {
            return false;
        }
    }
}
