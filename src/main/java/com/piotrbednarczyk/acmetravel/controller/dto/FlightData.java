package com.piotrbednarczyk.acmetravel.controller.dto;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FlightData {

    @NotNull
    @Pattern(regexp = "[A-Z0-9]{2}[0-9]{1,4}", message = "Must be IATA airline designator")
    private String flightCode;

    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    @FutureOrPresent
    private Date departureDate;

    @NotNull
    private Long airlineId;

    @NotNull
    private Long sourceAirportId;

    @NotNull
    private Long destinationAirportId;

    @NotNull
    @Length(max = 4, min = 2)
    private String aircraftIcaoCode;

    @NotNull
    @DecimalMin("0.0")
    private BigDecimal price;

    @Min(0)
    private Integer seatAvailability;
}
