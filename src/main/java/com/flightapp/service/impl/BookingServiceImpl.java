package com.flightapp.service.impl;

import com.flightapp.dto.BookingRequest;
import com.flightapp.model.Booking;
import com.flightapp.model.FlightInventory;
import com.flightapp.repository.BookingRepository;
import com.flightapp.repository.FlightInventoryRepository;
import com.flightapp.service.BookingService;
import com.flightapp.util.PnrGenerator;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

@Service
public class BookingServiceImpl implements BookingService {

    private final FlightInventoryRepository flightRepo;
    private final BookingRepository bookingRepo;

    public BookingServiceImpl(FlightInventoryRepository flightRepo,
                              BookingRepository bookingRepo) {
        this.flightRepo = flightRepo;
        this.bookingRepo = bookingRepo;
    }

    @Override
    public Mono<Booking> bookFlight(String flightId, BookingRequest request) {

        return flightRepo.findById(flightId)
                .flatMap(flight -> {

                    Booking b = new Booking();
                    b.setFlightId(flightId);
                    b.setUserName(request.getUserName());
                    b.setUserEmail(request.getUserEmail());
                    b.setJourneyDate(request.getJourneyDate());
                    b.setPassengers(request.getPassengers());
                    b.setMealType(request.getMealType());
                    b.setTotalAmount(flight.getPrice() * request.getPassengers().size());
                    b.setPnr(PnrGenerator.generatePnr());

                    return bookingRepo.save(b);
                });
    }

    @Override
    public Mono<Booking> getByPnr(String pnr) {
        return bookingRepo.findByPnr(pnr);
    }

    @Override
    public Mono<Boolean> cancelBookingByPnr(String pnr) {
        return bookingRepo.findByPnr(pnr)
                .flatMap(b -> bookingRepo.delete(b).thenReturn(true))
                .defaultIfEmpty(false);
    }

    @Override
    public Flux<Booking> getAllBookings() {
        return bookingRepo.findAll();
    }
}
