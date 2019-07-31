package com.piotrbednarczyk.acmetravel.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;


@Table(name = "routes")
@Entity
@Getter
@Setter
public class Route {

    @EmbeddedId
    RouteId id;

    @MapsId("airlineId")
    @ManyToOne
    @JoinColumn(name = "Airline_ID", referencedColumnName = "Airline_ID")
    private Airline airline;

    @MapsId("sourceAirportId")
    @ManyToOne
    @JoinColumn(name = "Source_Airport_ID", referencedColumnName = "Airport_Id")
    private Airport sourceAirport;

    @MapsId("destinationAirportId")
    @ManyToOne
    @JoinColumn(name = "Destination_Airport_ID", referencedColumnName = "Airport_Id")
    private Airport destinationAirport;

    @Column(name = "Codeshare")
    private String codeshare;

    @Column(name = "Equipment")
    private String equipment;
}
