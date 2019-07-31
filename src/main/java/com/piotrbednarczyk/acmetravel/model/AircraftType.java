package com.piotrbednarczyk.acmetravel.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Table(name = "aircraft_type")
@Entity
@Getter
@Setter
public class AircraftType {

    @Id
    @Column(name = "ICAO_Code")
    private String icaoCode;

    @Column(name = "IATA_Code")
    private String iataCode;

    @Column(name = "Model")
    private String model;
}
