package com.piotrbednarczyk.acmetravel.controller;

import com.piotrbednarczyk.acmetravel.controller.dto.FlightData;
import com.piotrbednarczyk.acmetravel.model.Flight;
import com.piotrbednarczyk.acmetravel.repository.FlightRepository;
import com.piotrbednarczyk.acmetravel.service.FlightCreationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static java.util.Collections.singletonMap;
import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.web.bind.annotation.RequestMethod.PATCH;

@RestController
@RequestMapping("/flights")
@Validated
public class FlightController {

    public static final String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private FlightCreationService flightCreationService;

    @GetMapping(value = "search")
    public List<Flight> getFlightByDate(@RequestParam(value = "date")
                                            @DateTimeFormat(iso = DATE) Date date) {
        return flightRepository.findFlightByDepartureDate(date);
    }

    @PostMapping
    public ResponseEntity<Flight> newFlight(@RequestBody @Valid FlightData flightData) {
        return new ResponseEntity<>(flightCreationService.addNewFlight(flightData), CREATED);
    }

    @PostMapping(path = "/{flightCode}/{departureDate}/bookings")
    public ResponseEntity<Map> bookFlight(@PathVariable @Size(max = 6, min = 5) String flightCode,
                                          @PathVariable @DateTimeFormat(pattern = DATE_PATTERN) Date departureDate) {

        if(!flightRepository.findByFlightCodeAndDepartureDate(flightCode, getDateValue(departureDate)).isPresent()) {
            return flightNotFoundResponse();
        }

        if(flightRepository.bookFlight(flightCode, getDateValue(departureDate)) == 0) {
            return noSeatsAvailableResponse();
        }

        return new ResponseEntity<>(CREATED);
    }

    @RequestMapping(method = PATCH, path = "/{flightCode}/{departureDate}")
    public ResponseEntity<Map> changeFlightPrice(@PathVariable @Size(max = 6, min = 5) String flightCode,
                                                 @PathVariable @DateTimeFormat(pattern = DATE_PATTERN) Date departureDate,
                                                    @RequestBody @Min(0) BigDecimal price) {
        if(flightRepository.updateTicketPrice(flightCode, getDateValue(departureDate), price) == 0) {
            return flightNotFoundResponse();
        }

        return new ResponseEntity<>(OK);
    }

    private ResponseEntity<Map> flightNotFoundResponse() {
        return getMapResponseEntity("Flight not found");
    }

    private ResponseEntity<Map> noSeatsAvailableResponse() {
        return getMapResponseEntity("No seats available");
    }

    private ResponseEntity<Map> getMapResponseEntity(String message) {
        return new ResponseEntity<>(singletonMap("message", message), NOT_FOUND);
    }

    private String getDateValue(Date departureDate) {
        return new SimpleDateFormat(DATE_PATTERN).format(departureDate);
    }
}