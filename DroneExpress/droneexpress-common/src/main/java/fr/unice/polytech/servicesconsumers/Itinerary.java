package fr.unice.polytech.servicesconsumers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.unice.polytech.business.Delivery;
import fr.unice.polytech.tools.HTTPHelper;
import fr.unice.polytech.tools.HTTPHelper.Method;


public class Itinerary {

    public Delivery createItinerary(Delivery delivery) {

        String url = HTTPHelper.EXTERNAL_ADDRESS_ITINERARY;

        try {
            HTTPHelper helper = new HTTPHelper().setUrl(url).setRequestMethod(Method.POST)
                    .setRequestBody(new ObjectMapper().writeValueAsString(delivery));
            helper.call();
            if (helper.getResponseCode() == HTTPHelper.OK) {
                return helper.getResponseAs(Delivery.class);
            } else {
                return null;
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean changeItinerary(long parcelId, long newArrivalWarehouseId) {
        String url = HTTPHelper.EXTERNAL_ADDRESS_ITINERARY + "/update/" + parcelId + "/"
                + newArrivalWarehouseId;
        HTTPHelper helper = new HTTPHelper().setUrl(url).setRequestMethod(Method.PUT);
        helper.call();
        return helper.getResponseCode() == HTTPHelper.OK;
    }
}
