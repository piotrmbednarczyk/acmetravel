package com.piotrbednarczyk.acmetravel.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Table(name = "airports")
@Entity
@Getter
@Setter
public class Airport {

    @Id
    @Column(name = "Airport_Id")
    private Long airportId;

    @Column(name = "Name")
    private String name;

    @Column(name = "City")
    private String city;

    @Column(name = "Country")
    private String country;

    @Column(name = "IATA")
    private String iata;
}
