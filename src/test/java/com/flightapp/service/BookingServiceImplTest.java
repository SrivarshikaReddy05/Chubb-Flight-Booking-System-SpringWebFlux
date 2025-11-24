package com.flightapp.service;

import java.time.LocalDate;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.flightapp.dto.BookingRequest;
import com.flightapp.model.Booking;
import com.flightapp.model.FlightInventory;
import com.flightapp.model.Passenger;
import com.flightapp.model.enums.Gender;
import com.flightapp.model.enums.MealType;
import com.flightapp.repository.BookingRepository;
import com.flightapp.repository.FlightInventoryRepository;
import com.flightapp.service.impl.BookingServiceImpl;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class BookingServiceImplTest {

    @Mock
    private FlightInventoryRepository flightRepo;

    @Mock
    private BookingRepository bookingRepo;

    private BookingServiceImpl service;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        service = new BookingServiceImpl(flightRepo, bookingRepo);
    }

    @Test
    void testBookFlightSuccess() {

        FlightInventory flight = new FlightInventory();
        flight.setId("F1");
        flight.setAirlineName("Indigo");
        flight.setPrice(5000);

        BookingRequest req = new BookingRequest();
        req.setUserName("Sri");
        req.setUserEmail("sri@mail.com");
        req.setJourneyDate(LocalDate.now());
        req.setPassengers(Collections.singletonList(new Passenger("Ram", 22, Gender.MALE, "12A")));
        req.setMealType(MealType.VEG);

        Booking saved = new Booking();
        saved.setId("B1");
        saved.setFlightId("F1");

        when(flightRepo.findById("F1")).thenReturn(Mono.just(flight));
        when(bookingRepo.save(any(Booking.class))).thenReturn(Mono.just(saved));

        StepVerifier.create(service.bookFlight("F1", req))
                .expectNextMatches(b -> b.getId().equals("B1"))
                .verifyComplete();
    }
}