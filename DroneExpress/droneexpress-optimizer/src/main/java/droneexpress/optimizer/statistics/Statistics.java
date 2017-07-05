package droneexpress.optimizer.statistics;

import java.time.LocalDateTime;

import javax.ejb.Local;

import droneexpress.optimizer.webservice.StatisticsEndpoint.ServerLoad;


@Local
public interface Statistics {

    void pushRequest(long departure, LocalDateTime dateTime);

    void pushResponse(int type, double travelledDistance, long delay);

    ServerLoad getServerLoad(String unit, int length);
}
