package com.piotrbednarczyk.acmetravel.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "airlines")
@Entity
@Getter
@Setter
public class Airline {

    @Id
    @Column(name = "Airline_ID")
    private Long airlineId;

    @Column(name = "Name")
    private String name;

    @Column(name = "Alias")
    private String alias;

    @Column(name = "IATA")
    private String iata;

    @Column(name = "ICAO")
    private String icao;

    @Column(name = "Callsign")
    private String callsign;

    @Column(name = "Country")
    private String country;

    @Column(name = "Active")
    @Type(type="yes_no")
    private boolean active;
}
