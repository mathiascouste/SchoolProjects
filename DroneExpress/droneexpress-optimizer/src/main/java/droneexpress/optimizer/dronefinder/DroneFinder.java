package droneexpress.optimizer.dronefinder;

import java.time.LocalDateTime;
import java.util.StringJoiner;

import javax.ejb.Local;

import fr.unice.polytech.business.Flight;


@Local
public interface DroneFinder {

    public Flight findDroneAndDate(Flight flightToCreate);

    // Coordinate de la forme : "N:12.123W:165.654321"
    // Date de la forme : 201510251137
    public Response findDroneAndBack(long departure, double latitude, double longitude,
                                     LocalDateTime departureDateTime);

    public class Response {

        private long flightId;
        private long droneId;
        private LocalDateTime departure;
        private boolean useEmptyFlight;
        private long arrival = -1;

        public String toJSON() {
            StringJoiner date = new StringJoiner(",", "{", "}");
            date.add("\"year\":" + this.departure.getYear())
                    .add("\"month\":" + this.departure.getMonth().getValue())
                    .add("\"day\":" + this.departure.getDayOfMonth())
                    .add("\"hour\":" + this.departure.getHour())
                    .add("\"minute\":" + this.departure.getMinute());

            StringJoiner res = new StringJoiner(",", "{", "}");
            res.add("\"droneId\":" + droneId).add("\"departure\":" + date.toString());
            if (arrival >= 0) {
                res.add("\"arrival\":" + arrival);
            } else {
                res.add("\"useEmptyFlight\":" + (this.useEmptyFlight ? "true" : "false"));
                if (this.useEmptyFlight) {
                    res.add("\"flightid\":" + this.flightId);
                }
            }
            return res.toString();
        }

        public long getDroneId() {
            return droneId;
        }

        public void setDroneId(long droneId) {
            this.droneId = droneId;
        }

        public LocalDateTime getDeparture() {
            return departure;
        }

        public void setDeparture(LocalDateTime departure) {
            this.departure = departure;
        }

        public boolean isUseEmptyFlight() {
            return useEmptyFlight;
        }

        public void setUseEmptyFlight(boolean useEmptyFlight) {
            this.useEmptyFlight = useEmptyFlight;
        }

        public long getArrival() {
            return arrival;
        }

        public void setArrival(long arrival) {
            this.arrival = arrival;
        }

        public long getFlightId() {
            return flightId;
        }

        public void setFlightId(long flightId) {
            this.flightId = flightId;
        }
    }
}
