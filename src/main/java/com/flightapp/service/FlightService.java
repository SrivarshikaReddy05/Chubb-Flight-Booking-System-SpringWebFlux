package com.flightapp.service;

import com.flightapp.dto.BookingRequest;
import com.flightapp.dto.FlightSearchRequest;
import com.flightapp.dto.InventoryRequest;
import com.flightapp.model.Booking;
import com.flightapp.model.FlightInventory;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FlightService {
	Mono<String> addInventory(InventoryRequest req);

	Flux<FlightInventory> searchFlights(FlightSearchRequest req);

	Mono<Booking> bookTicket(String flightId, BookingRequest req);

	Mono<Booking> getByPnr(String pnr);

	Flux<Booking> getHistory(String email);

	Mono<Boolean> cancelBookingByPnr(String pnr);
}