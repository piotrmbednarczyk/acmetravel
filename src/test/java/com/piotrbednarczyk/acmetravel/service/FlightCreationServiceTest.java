package com.piotrbednarczyk.acmetravel.service;

import com.piotrbednarczyk.acmetravel.ApplicationTest;
import com.piotrbednarczyk.acmetravel.controller.dto.FlightData;
import com.piotrbednarczyk.acmetravel.model.Flight;
import com.piotrbednarczyk.acmetravel.repository.FlightRepository;
import com.piotrbednarczyk.acmetravel.repository.error.FlightCreationException;
import com.piotrbednarczyk.acmetravel.service.FlightCreationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Date;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

class FlightCreationServiceTest extends ApplicationTest {

    @Autowired
    private FlightCreationService flightCreationService;

    @Test
    void shouldAddNewFlight() {
        FlightData flightData = getFlightData();

        Flight flight = flightCreationService.addNewFlight(flightData);

        assertThat(flight, notNullValue());
        assertThat(flight.getFlightCode(), equalTo(flightData.getFlightCode()));
    }

    @Test
    void shouldThrowExceptionOnMissingRoute() {
        Assertions.assertThrows(FlightCreationException.class, () -> {
            FlightData flightData = getFlightData();
            flightData.setAirlineId(-2L);
            flightCreationService.addNewFlight(flightData);
        });
    }

    @Test
    void shouldThrowExceptionOnMissingAircraftType() {
        Assertions.assertThrows(FlightCreationException.class, () -> {
            FlightData flightData = getFlightData();
            flightData.setAircraftIcaoCode("XXX");
            flightCreationService.addNewFlight(flightData);
        });
    }

    private FlightData getFlightData() {
        return FlightData.builder()
                .flightCode("BA111")
                .aircraftIcaoCode("AN30")
                .airlineId(1308L)
                .departureDate(new Date())
                .destinationAirportId(1L)
                .sourceAirportId(2L)
                .seatAvailability(100)
                .price(new BigDecimal("123.45"))
                .build();
    }
}