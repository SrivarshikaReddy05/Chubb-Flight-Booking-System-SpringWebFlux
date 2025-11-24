package com.flightapp.service;

import com.flightapp.dto.BookingRequest;
import com.flightapp.model.Booking;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

public interface BookingService {

    Mono<Booking> bookFlight(String flightId, BookingRequest request);

    Mono<Booking> getByPnr(String pnr);

    Mono<Boolean> cancelBookingByPnr(String pnr);

    Flux<Booking> getAllBookings();
}
