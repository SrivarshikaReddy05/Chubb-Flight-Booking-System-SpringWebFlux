package com.flightapp.controller;

import com.flightapp.dto.BookingRequest;
import com.flightapp.model.Booking;
import com.flightapp.service.BookingService;
import com.flightapp.service.FlightService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.Collections;

@WebFluxTest(controllers = FlightController.class)
class FlightControllerTest {

	@Autowired
	private WebTestClient webClient;

	@MockBean
	private FlightService flightService;

	@Test
	void testBookFlight_Returns200AndBooking() {

		Booking booking = new Booking();
		booking.setId("B1");
		booking.setPnr("PNR12");
		booking.setFlightId("F1");

		BookingRequest req = new BookingRequest();
		req.setUserName("Sri");
		req.setUserEmail("sri@mail.com");
		req.setJourneyDate(LocalDate.now());
		req.setPassengers(Collections.emptyList());

		Mockito.when(flightService.bookTicket(Mockito.eq("F1"), Mockito.any())).thenReturn(Mono.just(booking));

		webClient.post().uri("/api/v1.0/flight/booking/F1").contentType(MediaType.APPLICATION_JSON).bodyValue(req)
				.exchange().expectStatus().isOk().expectBody().jsonPath("$.id").isEqualTo("B1").jsonPath("$.pnr")
				.isEqualTo("PNR12");
	}
}
