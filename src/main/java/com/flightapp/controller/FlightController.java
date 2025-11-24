package com.flightapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flightapp.dto.BookingIdResponse;
import com.flightapp.dto.BookingRequest;
import com.flightapp.dto.FlightSearchRequest;
import com.flightapp.dto.InventoryRequest;
import com.flightapp.model.Booking;
import com.flightapp.model.FlightInventory;
import com.flightapp.service.FlightService;

import jakarta.validation.Valid;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1.0/flight")
@Validated
public class FlightController {

    private final FlightService service;

    public FlightController(FlightService service) {
        this.service = service;
    }

    // ------------------------------- ADD INVENTORY -------------------------------
    @PostMapping("/airline/inventory/add")
    public Mono<ResponseEntity<BookingIdResponse>> addInventory(@Valid @RequestBody InventoryRequest req) {
        return service.addInventory(req)
                .map(id -> ResponseEntity.status(HttpStatus.CREATED)
                        .body(new BookingIdResponse(id)));
    }

    // ------------------------------- SEARCH FLIGHTS -------------------------------
    @PostMapping("/search")
    public Flux<FlightInventory> searchFlights(@RequestBody FlightSearchRequest req) {
        return service.searchFlights(req);
    }

    // ------------------------------- BOOK TICKET -------------------------------
    @PostMapping("/booking/{flightId}")
    public Mono<Booking> bookTicket(
            @PathVariable String flightId,
            @Valid @RequestBody BookingRequest request) {

        return service.bookTicket(flightId, request);
    }

    // ------------------------------- GET TICKET BY PNR -------------------------------
    @GetMapping("/ticket/{pnr}")
    public Mono<ResponseEntity<Booking>> getTicketByPnr(@PathVariable String pnr) {
        return service.getByPnr(pnr)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // ------------------------------- BOOKING HISTORY -------------------------------
    @GetMapping("/booking/history/{email}")
    public Flux<Booking> getHistory(@PathVariable String email) {
        return service.getHistory(email);
    }

    // ------------------------------- CANCEL BOOKING BY PNR -------------------------------
//    @DeleteMapping("/booking/cancel/{pnr}")
//    public Mono<ResponseEntity<Void>> cancelBooking(@PathVariable String pnr) {
//
//        return service.getByPnr(pnr)
//                .flatMap(existingBooking ->
//                        service.cancelBookingByPnr(pnr)
//                                .flatMap(cancelled -> {
//                                    if (cancelled) {
//                                        return Mono.just(ResponseEntity.ok().<Void>build());
//                                    } else {
//                                        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
//                                    }
//                                })
//                )
//                .defaultIfEmpty(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
//    }
    @DeleteMapping("/booking/cancel/{pnr}")
    public Mono<ResponseEntity<Void>> cancelBooking(@PathVariable String pnr) {

        return service.getByPnr(pnr)
                .flatMap(existingBooking ->
                        service.cancelBookingByPnr(pnr)
                                .flatMap(cancelled -> {
                                    if (cancelled) {
                                        return Mono.just(ResponseEntity.ok().<Void>build());
                                    } else {
                                        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).<Void>build());
                                    }
                                })
                )
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.NOT_FOUND).<Void>build());
    }

}