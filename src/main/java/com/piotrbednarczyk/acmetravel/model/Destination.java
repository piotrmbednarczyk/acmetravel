package com.piotrbednarczyk.acmetravel.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Destination {

    private String country;
    private String city;
    private String airportName;
}
