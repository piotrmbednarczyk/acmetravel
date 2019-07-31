package com.piotrbednarczyk.acmetravel;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

class AcmeTravelApplicationTest extends ApplicationTest {

    @LocalServerPort
    private int port;

    TestRestTemplate restTemplate = new TestRestTemplate();

    HttpHeaders headers = new HttpHeaders();

    @Before
    public void setup() {
        restTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory());
    }

    @Test
    public void shouldGetDestinations() throws Exception {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = restTemplate.getForEntity(
                createURLWithPort("/destinations?airlineId=1308&page=1"),
                String.class);

        assertThat(response.getStatusCode(), equalTo(OK));
        assertThat(response.getBody(), is(not(emptyString())));
    }

    @Test
    public void shouldGetFlightsByDate() throws Exception {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = restTemplate.getForEntity(
                createURLWithPort("/flights/search?date=2017-03-15"),
                String.class);

        assertThat(response.getStatusCode(), equalTo(OK));
        assertThat(response.getBody(), is(not(emptyString())));
    }

    @Test
    public void shouldCreateNewFlight() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(getFlightDataBody(), headers);

        ResponseEntity<String> response = restTemplate.postForEntity(
                createURLWithPort("/flights/"),
                request,
                String.class);

        assertThat(response.getStatusCode(), equalTo(CREATED));
        assertThat(response.getBody(), is(not(emptyString())));

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.getBody());
        JsonNode code = root.path("flightCode");

        assertThat(code.asText(), equalTo("XX123"));
    }

    @Test
    public void shouldBookFlight() throws Exception {
        HttpHeaders headers = new HttpHeaders();

        ResponseEntity<String> response = restTemplate.postForEntity(
                createURLWithPort("/flights/BA001/2017-03-15 20:30:00/bookings"),
                new HttpEntity<>(headers),
                String.class);

        assertThat(response.getStatusCode(), equalTo(CREATED));
        assertThat(response.getBody(), is(nullValue()));
    }

    @Test
    public void shouldChangeFlightPrice() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/flights/BA001/2017-03-15 20:30:00/"),
                HttpMethod.PATCH,
                new HttpEntity<>("1000.25", headers),
                String.class);

        assertThat(response.getStatusCode(), equalTo(OK));
        assertThat(response.getBody(), is(nullValue()));
    }

    private String getFlightDataBody() {
        return "{\n" +
                "\"flightCode\": \"XX123\",\n" +
                "\"departureDate\": \"2021-08-29T19:32:35.100+0000\",\n" +
                "\"aircraftIcaoCode\": \"AN30\",\n" +
                "\"destinationAirportId\": 1,\n" +
                "\"sourceAirportId\": 2,\n" +
                "\"airlineId\": 1308,\n" +
                "\"price\": 1200.777,\n" +
                "\"seatAvailability\": 106\n" +
                "}";
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}
