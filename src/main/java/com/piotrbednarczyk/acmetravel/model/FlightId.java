package com.piotrbednarczyk.acmetravel.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class FlightId implements Serializable {

    @Column(name = "Flight_Code")
    @NotNull
    @Pattern(regexp = "[A-Z0-9]{2}[0-9]{1,4}", message = "Must be IATA airline designator")
    private String flightCode;

    @Column(name = "Departure_Date")
    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    private Date departureDate;
}
