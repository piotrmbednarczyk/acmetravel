package com.piotrbednarczyk.acmetravel.repository;

import com.piotrbednarczyk.acmetravel.model.Flight;
import com.piotrbednarczyk.acmetravel.model.FlightId;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface FlightRepository extends CrudRepository<Flight, FlightId> {

    @Query(value = "select * from flights_Piotrbed " +
            "where flight_code = :flightCode and departure_date = :date", nativeQuery = true)
    Optional<Flight> findByFlightCodeAndDepartureDate(@Param("flightCode") String flightCode,
                                                      @Param("date") String departureDate);

    @Query(value = "select * from flights_Piotrbed where DATE(departure_date) = :date", nativeQuery = true)
    List<Flight> findFlightByDepartureDate(@Param("date") Date departureDate);

    @Transactional
    @Modifying
    @Query(value = "update flights_Piotrbed set seat_availability = seat_availability - 1\n" +
            "where flight_code = :flightCode and departure_date = :date and seat_availability > 0", nativeQuery = true)
    int bookFlight(@Param("flightCode") String flightCode, @Param("date") String departureDate);

    @Transactional
    @Modifying
    @Query(value = "update flights_Piotrbed set price = :price \n" +
            "where flight_code = :flightCode and departure_date = :date", nativeQuery = true)
    int updateTicketPrice(@Param("flightCode") String flightCode,
                          @Param("date") String departureDate,
                          @Param("price") BigDecimal price);
}
