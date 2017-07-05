package fr.unice.polytech.servicesconsumers;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.unice.polytech.business.Flight;
import fr.unice.polytech.tools.HTTPHelper;
import fr.unice.polytech.tools.HTTPHelper.Method;


public class FlightPlan {

    private final String FLIGHTPLAN_FIND_EMPTY_FLIGHT = "/findemptyflight";
    private final String FLIGHTPLAN_CREATE_FLIGHT = "/createflight";
    private final String FLIGHTPLAN_UNLOCK_FLIGHT = "/unlock";

    public void unlockFlight(Flight flight) {
        String urlParam = FLIGHTPLAN_UNLOCK_FLIGHT;
        String url = HTTPHelper.EXTERNAL_ADDRESS_FLIGHTPLAN + urlParam;
        HTTPHelper httpHelp = new HTTPHelper().setUrl(url).setRequestMethod(Method.PUT);

        String jsonStr;
        try {
            jsonStr = new ObjectMapper().writeValueAsString(flight);

            httpHelp.setRequestBody(jsonStr);
            httpHelp.call();
        } catch (JsonProcessingException e1) {
            e1.printStackTrace();
        }
    }

    /**
     * Retourne le premier vol à vide allant de departure à arrival à partir de la date from
     */
    public Flight getFirstEmptyFlight(long departure, long arrival, LocalDateTime from) {
        Flight flight = null;
        String urlParam = FLIGHTPLAN_FIND_EMPTY_FLIGHT + "/" + departure + "/" + arrival + "/"
                + HTTPHelper.LocalDateTimeToStringFormat(from);
        String url = HTTPHelper.EXTERNAL_ADDRESS_FLIGHTPLAN + urlParam;
        HTTPHelper httpHelp = new HTTPHelper().setUrl(url).setRequestMethod(Method.GET);
        httpHelp.call();
        if (httpHelp.getResponseCode() == HTTPHelper.OK) {
            flight = httpHelp.getResponseAs(Flight.class);
        }
        return flight;
    }

    public Flight createFlight(Flight f) {
        Flight flight = null;
        String urlParam = FLIGHTPLAN_CREATE_FLIGHT;
        String url = HTTPHelper.EXTERNAL_ADDRESS_FLIGHTPLAN + urlParam;
        HTTPHelper httpHelp = new HTTPHelper().setUrl(url).setRequestMethod(Method.POST);

        String jsonStr;
        try {
            jsonStr = new ObjectMapper().writeValueAsString(f);

            httpHelp.setRequestBody(jsonStr);
            httpHelp.call();
            if (httpHelp.getResponseCode() == HTTPHelper.OK) {
                flight = httpHelp.getResponseAs(Flight.class);
            }
        } catch (JsonProcessingException e1) {
            e1.printStackTrace();
        }
        return flight;
    }

    public Flight loadFlight(Flight flight) {
        Flight f = null;
        String url = HTTPHelper.EXTERNAL_ADDRESS_FLIGHTPLAN + "/load/" + flight.getIdent() + "/"
                + flight.getParcelId();
        HTTPHelper helper = new HTTPHelper().setRequestMethod(Method.PUT).setUrl(url);
        helper.call();
        if (helper.getResponseCode() == HTTPHelper.OK) {
            f = helper.getResponseAs(Flight.class);
        }
        return f;
    }

    public List<Flight> findFlights() {
        List<Flight> flights = new ArrayList<>();
        String url = HTTPHelper.EXTERNAL_ADDRESS_FLIGHTPLAN + "/flights";
        HTTPHelper helper = new HTTPHelper().setUrl(url).setRequestMethod(Method.GET);
        helper.call();
        if (helper.getResponseCode() == HTTPHelper.OK) {
            ObjectMapper om = new ObjectMapper();
            JSONArray array = new JSONArray(helper.getResponseBody());
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                try {
                    flights.add(om.readValue(obj.toString(), Flight.class));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return flights;
    }

    public List<Flight> findFlightsByParcelId(long parcelId) {
        List<Flight> flights = new ArrayList<>();
        String url = HTTPHelper.EXTERNAL_ADDRESS_FLIGHTPLAN + "/flightsbyparcelid/" + parcelId;
        HTTPHelper helper = new HTTPHelper().setRequestMethod(Method.GET).setUrl(url);
        helper.call();
        if (helper.getResponseCode() == HTTPHelper.OK) {
            JSONArray flightsArray = new JSONArray(helper.getResponseBody());
            for (int i = 0; i < flightsArray.length(); i++) {
                JSONObject fjson = flightsArray.getJSONObject(i);
                try {
                    try {
                        flights.add(new ObjectMapper().readValue(fjson.toString(), Flight.class));
                    } catch (JsonParseException e) {
                        e.printStackTrace();
                    } catch (JsonMappingException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    // new Flight(fjson.getLong("departureId"), fjson.getLong("arrivalId"),
                    // fjson.get("departureDate"), fjson.getLong("arrivalDate"),
                    // fjson.getLong("droneId"), fjson.getLong("parcelId"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return flights;
    }

    public boolean cleanItinerary(List<Flight> flights) {
        String urlParam = "/cleanitinerary";
        String url = HTTPHelper.EXTERNAL_ADDRESS_FLIGHTPLAN + urlParam;
        HTTPHelper httpHelp = new HTTPHelper().setUrl(url).setRequestMethod(Method.PUT);
        String jsonStr;
        try {
            jsonStr = new ObjectMapper().writeValueAsString(flights);
            httpHelp.setRequestBody(jsonStr);
            httpHelp.call();
        } catch (JsonProcessingException e1) {
            e1.printStackTrace();
        }
        return httpHelp.getResponseCode() == HTTPHelper.OK;
    }
}