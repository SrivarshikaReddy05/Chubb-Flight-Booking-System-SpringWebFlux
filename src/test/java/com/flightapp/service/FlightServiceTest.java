package com.flightapp.service;

import com.flightapp.dto.FlightSearchRequest;
import com.flightapp.dto.InventoryRequest;
import com.flightapp.model.Booking;
import com.flightapp.model.FlightInventory;
import com.flightapp.repository.BookingRepository;
import com.flightapp.repository.FlightInventoryRepository;
import com.flightapp.service.impl.FlightServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.time.LocalDateTime;

class FlightServiceTest {

	private FlightService flightService;

	private FlightInventoryRepository flightRepo = Mockito.mock(FlightInventoryRepository.class);
	private BookingRepository bookingRepo = Mockito.mock(BookingRepository.class);

	@BeforeEach
	void init() {
		flightService = new FlightServiceImpl(flightRepo, bookingRepo);
	}

	@Test
	void testAddInventory() {

		InventoryRequest req = new InventoryRequest();
		req.setFromPlace("HYD");
		req.setToPlace("DEL");
		req.setPrice(4500);

		FlightInventory inv = new FlightInventory();
		inv.setId("INV1");

		Mockito.when(flightRepo.save(Mockito.any())).thenReturn(Mono.just(inv));

		StepVerifier.create(flightService.addInventory(req)).expectNext("INV1").verifyComplete();
	}

	@Test
	void testGetByPnr() {
		Booking booking = new Booking();
		booking.setPnr("PNR001");

		Mockito.when(bookingRepo.findByPnr("PNR001")).thenReturn(Mono.just(booking));

		StepVerifier.create(flightService.getByPnr("PNR001")).expectNextMatches(b -> b.getPnr().equals("PNR001"))
				.verifyComplete();
	}
}
