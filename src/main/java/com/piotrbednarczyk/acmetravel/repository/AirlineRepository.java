package com.piotrbednarczyk.acmetravel.repository;

import com.piotrbednarczyk.acmetravel.model.Airline;
import org.springframework.data.repository.CrudRepository;

public interface AirlineRepository extends CrudRepository<Airline, Long> {
}
