package droneexpress.optimizer.statistics;

import java.time.LocalDateTime;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import droneexpress.optimizer.dao.StatisticsCrud;
import droneexpress.optimizer.webservice.StatisticsEndpoint;
import droneexpress.optimizer.webservice.StatisticsEndpoint.ServerLoad;


@Stateless(name = "Statistics")
public class StatisticsBean implements Statistics {

    @EJB
    StatisticsCrud statisticsCrud;

    @Override
    public void pushRequest(long departure, LocalDateTime dateTime) {
        StatRequest request = new StatRequest();
        request.setDeparture(new Long(departure));
        request.setDateTime_LocalDateTime(dateTime);
        statisticsCrud.saveRequest(request);
    }

    @Override
    public void pushResponse(int type, double travelledDistance, long delay) {
        StatResponse response = new StatResponse();
        response.setType(type);
        response.setTravelledDistance(travelledDistance);
        response.setDelay(delay);
        statisticsCrud.saveResponse(response);
    }

    @Override
    public ServerLoad getServerLoad(String unit, int length) {
        ServerLoad sl = new StatisticsEndpoint().new ServerLoad();
        sl.totalRequests = statisticsCrud.getRequestsCount();
        sl.averageResponseDelay = statisticsCrud.getAverageDelay();
        sl.timeUnit = unit;
        sl.requestByTime = statisticsCrud.getRequestsByTime(unit, length);
        return sl;
    }

}
