package com.flightapp.repository;

import java.time.LocalDateTime;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.flightapp.model.FlightInventory;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FlightInventoryRepository extends ReactiveMongoRepository<FlightInventory, String> {
	Flux<FlightInventory> findByFromPlaceAndToPlaceAndDepartureTimeBetween(String fromPlace, String toPlace,
			LocalDateTime start, LocalDateTime end);
	Mono<FlightInventory> findByAirlineIdAndFromPlaceAndToPlaceAndDepartureTime(
	        String airlineId, String fromPlace, String toPlace, LocalDateTime departureTime);

}