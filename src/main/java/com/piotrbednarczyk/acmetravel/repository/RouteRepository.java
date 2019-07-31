package com.piotrbednarczyk.acmetravel.repository;

import com.piotrbednarczyk.acmetravel.model.Route;
import com.piotrbednarczyk.acmetravel.model.RouteId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface RouteRepository extends CrudRepository<Route, RouteId> {

}
