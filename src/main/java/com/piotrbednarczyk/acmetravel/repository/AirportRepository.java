package com.piotrbednarczyk.acmetravel.repository;

import com.piotrbednarczyk.acmetravel.model.Airport;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AirportRepository extends Repository<Airport, Long> {

    @Query(value = "select distinct a.* from routes r \n" +
            "\tleft join airports a on r.destination_airport_id = a.airport_id \n" +
            "\twhere r.airline_id = :airlineId\n" +
            "\torder by a.airport_id LIMIT :offset, :size",
            nativeQuery = true)
    List<Airport> findDestinationAirportByAirline(@Param("airlineId") Long airlineId,
                                                  @Param("offset") Long offset,
                                                  @Param("size") Long size);
}
