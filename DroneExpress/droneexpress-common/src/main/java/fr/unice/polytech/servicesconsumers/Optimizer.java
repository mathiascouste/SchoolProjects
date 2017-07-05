package fr.unice.polytech.servicesconsumers;

import fr.unice.polytech.business.Flight;
import fr.unice.polytech.tools.HTTPHelper;
import fr.unice.polytech.tools.HTTPHelper.Method;


public class Optimizer {

    public Flight findDroneAndDate(long parcelId, long departure, long arrival,
                                   String departureDateTime) {
        Flight response = null;
        String url = HTTPHelper.EXTERNAL_ADDRESS_OPTIMIZER + "/droneselector/finddroneanddate/"
                + parcelId + "/" + departure + "/" + arrival + "/" + departureDateTime;
        HTTPHelper helper = new HTTPHelper().setRequestMethod(Method.GET).setUrl(url);
        helper.call();
        if (helper.getResponseCode() == HTTPHelper.OK) {
            response = helper.getResponseAs(Flight.class);
        }
        return response;
    }
}
