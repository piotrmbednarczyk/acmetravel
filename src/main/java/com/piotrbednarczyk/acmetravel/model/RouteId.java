package com.piotrbednarczyk.acmetravel.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@ToString
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RouteId implements Serializable {

    @Column(name ="Destination_Airport_ID")
    private Long destinationAirportId;

    @Column(name ="Source_Airport_ID")
    private Long sourceAirportId;

    @Column(name ="Airline_ID")
    private Long airlineId;
}
