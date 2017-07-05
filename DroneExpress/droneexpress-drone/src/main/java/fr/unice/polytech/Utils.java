package fr.unice.polytech;

import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;


public class Utils {

    public static final String PATH_LOG = "c:/log.txt";
    private static ObjectMapper mapper = null;

    public static String parseAlert(JSONObject json) {
        String alert = "warehouse:" + json.getInt("warehouseId");
        Database.getWarehousesProblems().put(json.getLong("warehouseId"), alert);
        return alert;
    }

    public static ObjectMapper getMapper() {
        if (mapper == null) {
            mapper = new ObjectMapper();
        }
        return mapper;
    }
}
