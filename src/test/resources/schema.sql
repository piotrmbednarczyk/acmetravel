CREATE TABLE `aircraft_type` (
  `ICAO_Code` varchar(4) NOT NULL COMMENT 'ICAO aircraft type designator is a two-, three- or four-character alphanumeric code designating every aircraft type (and some sub-types) that may appear in flight planning. These codes are defined by the International Civil Aviation Organization, and published in ICAO Document 8643 Aircraft Type Designators.',
  `IATA_Code` char(3) DEFAULT NULL COMMENT 'IATA codes encountered by the general public, which are used for airline timetables.',
  `Model` varchar(100) NOT NULL,
  KEY `ICAO_IDX` (`ICAO_Code`) USING BTREE,
  KEY `idx_aircraft_type_Model` (`Model`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `airlines` (
  `Airline_ID` int(11) NOT NULL COMMENT 'Unique OpenFlights identifier for this airline',
  `Name` varchar(50) DEFAULT NULL COMMENT 'Name of the airline.',
  `Alias` varchar(100) DEFAULT NULL COMMENT 'Alias of the airline. For example, All Nippon Airways is commonly known as "ANA".',
  `IATA` char(2) DEFAULT NULL COMMENT '2-letter IATA code, if available.',
  `ICAO` char(3) DEFAULT NULL COMMENT '3-letter ICAO code, if available.',
  `Callsign` varchar(50) DEFAULT NULL COMMENT 'Airline callsign',
  `Country` varchar(50) DEFAULT NULL COMMENT 'Country or territory where airline is incorporated.',
  `Active` char(1) DEFAULT NULL COMMENT '"Y" if the airline is or has until recently been operational, "N" if it is defunct. This field is not reliable: in particular, major airlines that stopped flying long ago, but have not had their IATA code reassigned (eg. Ansett/AN), will incorrectly show as “Y”',
  PRIMARY KEY (`Airline_ID`),
  KEY `idx_airlines_Name` (`Name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `airports` (
  `Airport_ID` int(11) NOT NULL COMMENT 'Unique OpenFlights identifier for this airport',
  `Name` varchar(75) DEFAULT NULL COMMENT 'Name of airport. May or may not contain the City name',
  `City` varchar(50) DEFAULT NULL COMMENT 'Main city served by airport. May be spelled differently from Name.',
  `Country` varchar(50) DEFAULT NULL COMMENT 'Country or territory where airport is located. Cross-reference to ISO 3166-1 codes',
  `IATA` char(3) DEFAULT NULL COMMENT '3-letter IATA code. Null if not assigned/unknown.',
  `ICAO` char(4) DEFAULT NULL COMMENT '4-letter ICAO code.\nNull if not assigned.',
  `Latitude` decimal(10,0) DEFAULT NULL COMMENT 'Decimal degrees, usually to six significant digits. Negative is South, positive is North.',
  `Longitude` decimal(10,0) DEFAULT NULL COMMENT 'Decimal degrees, usually to six significant digits. Negative is West, positive is East.',
  `Altitude` int(11) DEFAULT NULL COMMENT 'In feet.',
  `Timezone` int(11) DEFAULT NULL COMMENT 'Hours offset from UTC. Fractional hours are expressed as decimals, eg. India is 5.5.',
  `DST` char(1) DEFAULT NULL COMMENT 'Daylight savings time. One of E (Europe), A (US/Canada), S (South America), O (Australia), Z (New Zealand), N (None) or U (Unknown)',
  `Tz_Timezone` varchar(50) DEFAULT NULL COMMENT 'Timezone in "tz" (Olson) format, eg. "America/Los_Angeles".',
  `Type` varchar(45) DEFAULT NULL,
  `Source` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`Airport_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `routes` (
  `Airline` varchar(3) NOT NULL COMMENT '2-letter (IATA) or 3-letter (ICAO) code of the airline',
  `Airline_ID` int(11) NOT NULL COMMENT 'Unique OpenFlights identifier for airline',
  `Source_Airport` varchar(4) NOT NULL COMMENT '3-letter (IATA) or 4-letter (ICAO) code of the source airport.',
  `Source_Airport_ID` int(11) NOT NULL COMMENT 'Unique OpenFlights identifier for source airport ',
  `Destination_Airport` varchar(4) NOT NULL COMMENT '3-letter (IATA) or 4-letter (ICAO) code of the destination airport.',
  `Destination_Airport_ID` int(11) NOT NULL COMMENT 'Unique OpenFlights identifier for destination airport ',
  `Codeshare` char(1) DEFAULT NULL COMMENT '"Y" if this flight is a codeshare (that is, not operated by Airline, but another carrier), empty otherwise.',
  `Stops` tinyint(1) DEFAULT NULL COMMENT 'Number of stops on this flight ("0" for direct)',
  `Equipment` varchar(20) DEFAULT NULL COMMENT '3-letter IATA codes for plane type(s) generally used on this flight, separated by spaces',
  PRIMARY KEY (`Destination_Airport_ID`,`Source_Airport_ID`,`Airline_ID`),
  KEY `Airline_ID_idx` (`Airline_ID`),
  KEY `Airport_ID_idx` (`Source_Airport_ID`),
  CONSTRAINT `Airline_FK` FOREIGN KEY (`Airline_ID`) REFERENCES `airlines` (`Airline_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `Destination_Airport_FK` FOREIGN KEY (`Destination_Airport_ID`) REFERENCES `airports` (`Airport_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `Source_Airport_FK` FOREIGN KEY (`Source_Airport_ID`) REFERENCES `airports` (`Airport_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `flights_Piotrbed` (
  `Flight_Code` varchar(6) NOT NULL COMMENT 'IATA airline designator (2-character code) followed by flight number (up to 4 digits). Example: BA001. ',
  `Airline_Name` varchar(50) NOT NULL,
  `Departure_Airport` char(3) NOT NULL COMMENT 'IATA 3-Letter Airport Code representing departure airport',
  `Destination_Airport` char(3) NOT NULL COMMENT 'IATA 3-Letter Airport Code representing destination airport',
  `Departure_Date` datetime NOT NULL,
  `Aircraft_Type` varchar(100) NOT NULL COMMENT 'Human readable description of aircraft type',
  `Seat_Availability` smallint(6) NOT NULL DEFAULT '0' COMMENT 'Number of seats available on flight',
  `Price` decimal(6,2) NOT NULL,
  PRIMARY KEY (`Flight_Code`,`Departure_Date`),
  KEY `Aircraft_Type_FK_idx` (`Aircraft_Type`),
  KEY `Airline_Name_FK_idx` (`Airline_Name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;