package fr.unice.polytech.business;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import fr.unice.polytech.business.json.LocalDateTimeDeserializer;
import fr.unice.polytech.business.json.LocalDateTimeSerializer;


public class Flight {

    private long ident;

    private long departureId;

    private long arrivalId;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime departureDate;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime arrivalDate;

    private long droneId;

    private long parcelId;

    public Flight() {
        this(0, 0, LocalDateTime.now(), LocalDateTime.now(), 0);
    }

    public Flight(long departureWarehouseId, long arrivalWarehouseId, LocalDateTime departure,
            LocalDateTime arrival, long droneId) {
        this(departureWarehouseId, arrivalWarehouseId, departure, arrival, droneId, -1);
    }

    @JsonCreator
    public Flight(@JsonProperty(value = "departureId", required = false) long departureWarehouseId,
            @JsonProperty(value = "arrivalId", required = true) long arrivalWarehouseId,
            @JsonProperty(value = "departureDate", required = true) LocalDateTime departure,
            @JsonProperty(value = "arrivalDate", required = false) LocalDateTime arrival,
            @JsonProperty(value = "droneId", required = true) long droneId,
            @JsonProperty(value = "parcelId", required = true) long parcelId) {
        this.ident = -1L;
        this.departureId = departureWarehouseId;
        this.arrivalId = arrivalWarehouseId;
        this.departureDate = departure;
        this.arrivalDate = arrival;
        this.droneId = droneId;
        this.parcelId = parcelId;
    }

    /**
     * Merge the information of Flight f into this one. That means the information of this flight
     * are updated with the ones from f. And f is not modified.
     * 
     * @param f new flight information to use to update
     * @return true if the merge has been done, false if the flights have not the same ident
     */
    public boolean merge(Flight f) {
        boolean success = true;
        if (ident != f.getIdent()) {
            success = false;
        }
        droneId = f.getDroneId();
        parcelId = f.getParcelId();
        departureId = f.getDepartureId();
        arrivalId = f.getArrivalId();
        departureDate = f.getDepartureDate();
        arrivalDate = f.getArrivalDate();
        return success;
    }

    public long getIdent() {
        return ident;
    }

    public long getDepartureId() {
        return departureId;
    }

    public void setDepartureId(long departureWarehouseId) {
        this.departureId = departureWarehouseId;
    }

    public long getArrivalId() {
        return arrivalId;
    }

    public void setArrivalId(long arrivalWarehouseId) {
        this.arrivalId = arrivalWarehouseId;
    }

    public long getDroneId() {
        return droneId;
    }

    public void setDroneId(long droneId) {
        this.droneId = droneId;
    }

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    public LocalDateTime getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDateTime departure) {
        this.departureDate = departure;
    }

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    public LocalDateTime getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(LocalDateTime arrival) {
        this.arrivalDate = arrival;
    }

    public void setIdent(long ident) {
        this.ident = ident;
    }

    public long getParcelId() {
        return parcelId;
    }

    public void setParcelId(long parcelId) {
        this.parcelId = parcelId;
    }
}
