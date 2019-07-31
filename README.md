# Acme Travel POC Application

Java based REST API POC for Acme Travel.

Application exposes following REST resources for given functionality:

1.  Retrieve a list of available flights on a given date: [http://localhost:8080/flights/search?date=yyyy-MM-dd](http://localhost:8080/flights/search?date=yyyy-MM-dd) Method: GET
2.  Retrieve a paginated list of destinations for a given airline: [http://localhost:8080/destinations?airlineId=_&page=_](http://localhost:8080/destinations?airlineId=_&page=_) Method: GET
3.  Simulate a ticket purchase by changing the seats available for a specified flight: [http://localhost:8080/flights/{flightCode}/{departureDate}/bookings](http://localhost:8080/flights//{flightCode}/{departureDate}/bookings) Method: POST
4.  Simulate new flight availability by adding a new flight for a specified route: [http://localhost:8080/flights](http://localhost:8080/flights/) Method: POST
5.  Simulate change in flight price by increasing/decreasing the ticket price for a specified flight: [http://localhost:8080/flights/{flightCode}/{departureDate}](http://localhost:8080/flights/{flightCode}/{departureDate}) Method: PATCH

Design comments:

Ad 3. Flights are represented in DB by composite key of flight code and departure date - for consistency of results both parameters are needed to book flight. Instead of using UPDATE method for flights resource I decided to introduce bookings sub-resource, what seems to be more perspective if application will be further developed. 

Ad 4. For flight creation following data in request body should be provided:

    {
        "flightCode":
        "departureDate":    "yyyy-MM-dd'T'HH:mm:ss.SSSXXX"
        "aircraftIcaoCode":
        "destinationAirportId":
        "sourceAirportId":
        "airlineId":
        "price":
        "seatAvailability":
    }
    
Example:

    {
        "flightCode": "XX123",
        "departureDate": "2019-08-29T19:32:35.100+0000",
        "aircraftIcaoCode": "AN30",
        "destinationAirportId": 1,
        "sourceAirportId": 2,
        "airlineId": 1308,
        "price": 1200.777,
        "seatAvailability": 106
    }
    
Most of the columns in flights table are derived from other tables, but are not foreign keys. I took the approach to keep consistency with other tables as far as possible, for ex. aircraftIcaoCode must be available in aircraft_type table and route for given airline and airports has to exist.

Ad 5. Request body should contain just new price as numeric value.

No security layer (authorization) is provided as there was no requirement for such.

Regarding technical design, I decided to use Spring Boot starters pack together wit hHibernate as ORM provider. For model classes Lombok is used as "syntactic sugar".

## Getting Started

### Prerequisites

* JDK8 or higher
* Maven 3.5.3 or higher

### Installing

It is recommended to build project from command line. Building from IDE may require installing Lombok plugin.

* Please first update correct login and password for the database access in /src/main/resources/application.properties file! Both were removed for security reasones.

Go to project directory. Usual maven build command should work fine:

```
mvn clean package
```

In target directory there is acmetravel-1.0.0-SNAPSHOT.jar file. You can run it as below:

```
java -jar acmetravel-1.0.0-SNAPSHOT.jar 
```

Alternatively, to just run the application from command line:

```
mvn spring-boot:run 
```

Server is started at [http://localhost:8080](http://localhost:8080)

## Running the tests

```
mvn clean test
```

## Authors

[**Piotr Bednarczyk**](mailto:job@piotrbednarczyk.com)