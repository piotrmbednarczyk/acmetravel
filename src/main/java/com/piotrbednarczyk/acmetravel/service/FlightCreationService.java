package com.piotrbednarczyk.acmetravel.service;

import com.piotrbednarczyk.acmetravel.controller.dto.FlightData;
import com.piotrbednarczyk.acmetravel.model.Flight;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

public interface FlightCreationService {

    Flight addNewFlight(FlightData flightData);
}
