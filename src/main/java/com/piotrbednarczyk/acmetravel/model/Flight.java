package com.piotrbednarczyk.acmetravel.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "flights_Piotrbed")
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Flight {

    @EmbeddedId
    private FlightId id = new FlightId();

    @Column(name = "Airline_Name")
    private String airlineName;

    @Column(name = "Departure_Airport")
    private String departureAirport;

    @Column(name = "Destination_Airport")
    private String destinationAirport;

    @Column(name = "Aircraft_Type")
    private String aircraftType;

    @Column(name = "Price")
    private BigDecimal price;

    @Column(name = "Seat_Availability")
    private Integer seatAvailability;

    public String getFlightCode() {
        return id.getFlightCode();
    }

    public void setFlightCode(String flightCode) {
        this.id.setFlightCode(flightCode);
    }

    public Date getDepartureDate() {
        return id.getDepartureDate();
    }

    public void setDepartureDate(Date departureDate) {
        this.id.setDepartureDate(departureDate);
    }
}
