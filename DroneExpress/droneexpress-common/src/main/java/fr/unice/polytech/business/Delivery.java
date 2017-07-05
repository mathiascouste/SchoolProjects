package fr.unice.polytech.business;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import fr.unice.polytech.business.json.LocalDateTimeDeserializer;
import fr.unice.polytech.business.json.LocalDateTimeSerializer;


/**
 * Class representing a delivery.
 * 
 * @author Mathias Cousté
 */
public class Delivery {

    /** Ident of the delivery. */
    private long id;

    /** Ident of the parcel to deliver. */
    private long parcelId;

    /** Location of departure. */
    private long departureLocation;

    /** Location of arrival */
    private long arrivalLocation;

    /** Date at which the parcel is brought to DroneExpress */
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime departureTime;

    /** Optional expected date of delivery */
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime arrivalTime;

    /**
     * Default constructor
     */
    public Delivery() {
        this.id = -1;
        this.parcelId = -1;
        this.departureLocation = -1;
        this.arrivalLocation = -1;
        this.departureTime = null;
        this.arrivalTime = null;
    }

    /**
     * Instanciates a delivery
     * 
     * @param id ident of the delivery
     * @param parcelId ident of the parcel to deliver
     * @param departureLocation location of the departure
     * @param arrivalLocation location of the arrival
     * @param departureTime date from which it can be delivered
     * @param arrivalTime optional expected date of delivery
     */
    @JsonCreator
    public Delivery(@JsonProperty(value = "id", required = true) long id,
            @JsonProperty(value = "parcelId", required = true) long parcelId,
            @JsonProperty(value = "departureLocation", required = true) long departureLocation,
            @JsonProperty(value = "arrivalLocation", required = true) long arrivalLocation,
            @JsonProperty(value = "departureTime", required = true) LocalDateTime departureTime,
            @JsonProperty(value = "arrivalTime", required = false) LocalDateTime arrivalTime) {
        this.id = id;
        this.parcelId = parcelId;
        this.departureLocation = departureLocation;
        this.arrivalLocation = arrivalLocation;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }

    /**
     * Returns the ident of the delivery.
     * 
     * @return long representing the ident
     */
    public Long getId() {
        return id;
    }

    /**
     * Replaces the ident of the delivery.
     * 
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Returns the ident of the parcel to deliver.
     * 
     * @return long representing the ident of the parcel.
     */
    public Long getParcelId() {
        return parcelId;
    }

    /**
     * Replaces the parcel conerned by this delivery.
     * 
     * @param parcelId ident of the new parcel to deliver.
     */
    public void setParcelId(Long parcelId) {
        this.parcelId = parcelId;
    }

    /**
     * Returns the location where is currently located the parcel.
     * 
     * @return first location in the system.
     */
    public Long getDepartureLocation() {
        return departureLocation;
    }

    /**
     * Modifies the location where is located the parcel at this entrance in the system.
     * 
     * @param departureLocation new location of the parcel
     */
    public void setDepartureLocation(Long departureLocation) {
        this.departureLocation = departureLocation;
    }

    /**
     * Returns de location where the parcel has to be delivered.
     * 
     * @return delivery location
     */
    public Long getArrivalLocation() {
        return arrivalLocation;
    }

    /**
     * Modifies the location where the parcel has to be delivered.
     * 
     * @param arrivalLocation new location of delivery.
     */
    public void setArrivalLocation(Long arrivalLocation) {
        this.arrivalLocation = arrivalLocation;
    }

    /**
     * Returns the date of entrance in the DroneExpress system.
     * 
     * @return date from which the parcel can be delivered.
     */
    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    /**
     * Modifies the date from which the parcel can be delivered.
     * 
     * @param departureTime new date of potential departure
     */
    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    /**
     * Returns the optional expected date of delivery.
     * 
     * @return expected date of delivery.
     */
    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    /**
     * Modifies the expected date of delivery.
     * 
     * @param arrivalTime the new expected date of delivery.
     */
    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
}
