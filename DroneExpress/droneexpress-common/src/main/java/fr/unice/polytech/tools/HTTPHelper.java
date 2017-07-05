package fr.unice.polytech.tools;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Random;

import com.fasterxml.jackson.databind.ObjectMapper;


public class HTTPHelper {

    // Modules addresses
    public static final String EXTERNAL_ADDRESS_ITINERARY = "http://localhost:8181/cxf/itinerary";
    public static final String EXTERNAL_ADDRESS_FLIGHTPLAN = "http://localhost:8181/cxf/flightplan";
    public static final String EXTERNAL_ADDRESS_WAREHOUSE = "http://localhost:8181/cxf/warehouse";
    public static final String EXTERNAL_ADDRESS_DRONE = "http://localhost:8181/cxf/dronemanagement";
    public static final String EXTERNAL_ADDRESS_OPTIMIZER = "http://localhost:8080/droneexpress-optimizer";

    public static final int OK = 200;

    public enum Method {
        GET,
        UPDATE,
        PUT,
        POST,
        DELETE
    }

    private String url;
    private Method method;
    private String requestBody;
    private int responseCode;
    private String responseBody;

    public HTTPHelper() {}

    public HTTPHelper(String url) {
        this.url = url;
    }

    public HTTPHelper setUrl(String url) {
        this.url = url;
        return this;
    }

    public HTTPHelper setRequestMethod(Method method) {
        this.method = method;
        return this;
    }

    public HTTPHelper setRequestBody(String body) {
        this.requestBody = body;
        return this;
    }

    public String call() {
        try {
            Thread.sleep(2 + new Random().nextInt(2));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String res = null;

        URL urlink;
        try {
            urlink = new URL(url);
            HttpURLConnection con = (HttpURLConnection) urlink.openConnection();

            con.setRequestMethod(this.method.toString());
            con.setRequestProperty("Content-Type", "application/json");

            if (this.requestBody != null) {
                con.setDoOutput(true);
                DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                wr.writeBytes(this.requestBody);
                wr.flush();
                wr.close();
            }

            responseCode = con.getResponseCode();
            res = "[" + url + "] returns code " + responseCode;
            if (responseCode == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer responseBuffer = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    responseBuffer.append(inputLine);
                }
                in.close();
                responseBody = responseBuffer.toString();
                res += ", return message is : " + responseBody;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return res;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public <T> T getResponseAs(Class<T> clas) {
        try {
            return new ObjectMapper().readValue(this.responseBody, clas);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String LocalDateTimeToStringFormat(LocalDateTime ldt) {
        String res = "";
        String YYYY = "" + ldt.getYear();
        String MM = "" + ldt.getMonth().getValue();
        String DD = "" + ldt.getDayOfMonth();
        String hh = "" + ldt.getHour();
        String mm = "" + ldt.getMinute();
        res += YYYY;
        res += (MM.length() == 1 ? "0" : "") + MM;
        res += (DD.length() == 1 ? "0" : "") + DD;
        res += (hh.length() == 1 ? "0" : "") + hh;
        res += (mm.length() == 1 ? "0" : "") + mm;
        return res;
    }
}
