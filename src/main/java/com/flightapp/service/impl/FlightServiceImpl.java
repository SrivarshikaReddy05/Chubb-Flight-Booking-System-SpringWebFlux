package com.flightapp.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.springframework.stereotype.Service;

import com.flightapp.dto.BookingRequest;
import com.flightapp.dto.FlightSearchRequest;
import com.flightapp.dto.InventoryRequest;
import com.flightapp.model.Booking;
import com.flightapp.model.FlightInventory;
import com.flightapp.model.enums.BookingStatus;
import com.flightapp.repository.BookingRepository;
import com.flightapp.repository.FlightInventoryRepository;
import com.flightapp.service.FlightService;
import com.flightapp.util.PnrGenerator;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class FlightServiceImpl implements FlightService {

	private final FlightInventoryRepository inventoryRepository;
	private final BookingRepository bookingRepository;

	public FlightServiceImpl(FlightInventoryRepository inventoryRepository, BookingRepository bookingRepository) {
		this.inventoryRepository = inventoryRepository;
		this.bookingRepository = bookingRepository;
	}

	@Override
	public Mono<String> addInventory(InventoryRequest req) {
		FlightInventory inv = new FlightInventory();
		inv.setAirlineId(req.getAirlineId());
		inv.setAirlineName(req.getAirlineName());
		inv.setFromPlace(req.getFromPlace());
		inv.setToPlace(req.getToPlace());
		inv.setDepartureTime(req.getDepartureTime());
		inv.setArrivalTime(req.getArrivalTime());
		inv.setPrice(req.getPrice());
		inv.setTotalSeats(req.getTotalSeats());
		return inventoryRepository.save(inv).map(FlightInventory::getId);
	}

	@Override
	public Flux<FlightInventory> searchFlights(FlightSearchRequest req) {
		// search by date range (day start to end)
		LocalDate d = req.getJourneyDate();
		LocalDateTime start = d.atStartOfDay();
		LocalDateTime end = d.plusDays(1).atStartOfDay().minusNanos(1);
		return inventoryRepository.findByFromPlaceAndToPlaceAndDepartureTimeBetween(req.getFromPlace(),
				req.getToPlace(), start, end);
	}

	@Override
	public Mono<Booking> bookTicket(String flightId, BookingRequest req) {
		// Simplified seating / availability logic: not deducting seats in depth.
		return inventoryRepository.findById(flightId).flatMap(inv -> {
			Booking b = new Booking();
			b.setFlightId(inv.getId());
			b.setAirlineName(inv.getAirlineName());
			b.setUserName(req.getUserName());
			b.setUserEmail(req.getUserEmail());
			b.setJourneyDate(req.getJourneyDate());
			b.setPassengers(req.getPassengers());
			b.setMealType(req.getMealType() == null ? com.flightapp.model.enums.MealType.NONE : req.getMealType());
			b.setStatus(BookingStatus.CONFIRMED);
			b.setPnr(PnrGenerator.generatePnr());
			double total = inv.getPrice() * (req.getPassengers() == null ? 1 : req.getPassengers().size());
			b.setTotalAmount(total);
			return bookingRepository.save(b);
		});
	}

	@Override
	public Mono<Booking> getByPnr(String pnr) {
		return bookingRepository.findByPnr(pnr);
	}

	@Override
	public Flux<Booking> getHistory(String email) {
		return bookingRepository.findByUserEmail(email);
	}

	@Override
	public Mono<Boolean> cancelBookingByPnr(String pnr) {
		return bookingRepository.findByPnr(pnr).flatMap(b -> {
			// Can cancel only if journeyDate is more than 24 hours in future
			LocalDate jd = b.getJourneyDate();
			long hoursUntilJourney = LocalDateTime.now().until(jd.atStartOfDay(), ChronoUnit.HOURS);
			if (hoursUntilJourney >= 24) {
				b.setStatus(BookingStatus.CANCELLED);
				return bookingRepository.save(b).thenReturn(true);
			} else {
				return Mono.just(false);
			}
		}).switchIfEmpty(Mono.just(false));
	}
}
