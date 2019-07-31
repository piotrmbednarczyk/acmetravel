package com.piotrbednarczyk.acmetravel.service;

import com.piotrbednarczyk.acmetravel.controller.dto.FlightData;
import com.piotrbednarczyk.acmetravel.model.*;
import com.piotrbednarczyk.acmetravel.repository.AircraftTypeRepository;
import com.piotrbednarczyk.acmetravel.repository.FlightRepository;
import com.piotrbednarczyk.acmetravel.repository.RouteRepository;
import com.piotrbednarczyk.acmetravel.repository.error.FlightCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static java.util.Objects.requireNonNull;
import static org.springframework.transaction.annotation.Isolation.REPEATABLE_READ;

@Service
@Transactional(isolation = REPEATABLE_READ)
public class FlightCreationServiceImpl implements FlightCreationService {

    @Autowired
    FlightRepository flightRepository;

    @Autowired
    RouteRepository routeRepository;

    @Autowired
    AircraftTypeRepository aircraftTypeRepository;

    @Override
    public Flight addNewFlight(FlightData flightData) {
        requireNonNull(flightData);

        Route route = validateRoute(flightData);
        AircraftType aircraft = validateAircraftType(flightData);

        return flightRepository.save(getFlight(flightData, aircraft, route));
    }

    private Route validateRoute(FlightData flightData) {
        Optional<Route> routeOptional = routeRepository.findById(getRouteId(flightData));
        return routeOptional.orElseThrow(() ->
                new FlightCreationException("No route for: " + getRouteId(flightData)));
    }

    private AircraftType validateAircraftType(FlightData flightData) {
        Optional<AircraftType> aircraftOptional = aircraftTypeRepository.findById(flightData.getAircraftIcaoCode());
        return aircraftOptional.orElseThrow(() ->
                new FlightCreationException("No aircraft type for ICAO: " + flightData.getAircraftIcaoCode()));
    }

    private Flight getFlight(FlightData flightData, AircraftType aircraft, Route route) {
        return Flight.builder()
                .aircraftType(aircraft.getModel())
                .airlineName(route.getAirline().getName())
                .departureAirport(route.getSourceAirport().getIata())
                .destinationAirport(route.getDestinationAirport().getIata())
                .price(flightData.getPrice())
                .seatAvailability(flightData.getSeatAvailability())
                .id(new FlightId(flightData.getFlightCode(), flightData.getDepartureDate()))
                .build();
    }

    private RouteId getRouteId(FlightData flightData) {
        return RouteId.builder()
                .airlineId(flightData.getAirlineId())
                .destinationAirportId(flightData.getDestinationAirportId())
                .sourceAirportId(flightData.getSourceAirportId())
                .build();
    }
}
