package com.piotrbednarczyk.acmetravel.controller;

import com.piotrbednarczyk.acmetravel.model.Airport;
import com.piotrbednarczyk.acmetravel.model.Destination;
import com.piotrbednarczyk.acmetravel.repository.AirportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;
import java.util.List;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/destinations")
@Validated
public class DestinationController {

    private static final long PAGE_SIZE = 10L;

    @Autowired
    private AirportRepository airportRepository;

    @GetMapping
    public List<Destination> getDestinations(@RequestParam Long airlineId,
                                             @RequestParam @Min(1) int page) {
        return airportRepository.findDestinationAirportByAirline(airlineId, getOffset(page), PAGE_SIZE)
                .stream()
                .map(airportToDestination())
                .collect(toList());
    }

    private Function<Airport, Destination> airportToDestination() {
        return airport -> Destination.builder()
                .country(airport.getCountry())
                .city(airport.getCity())
                .airportName(airport.getName())
                .build();
    }

    private long getOffset(int page) {
        return PAGE_SIZE * (page - 1);
    }
}
