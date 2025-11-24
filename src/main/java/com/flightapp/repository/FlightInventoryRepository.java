package com.flightapp.repository;

import java.time.LocalDateTime;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.flightapp.model.FlightInventory;

import reactor.core.publisher.Flux;

public interface FlightInventoryRepository extends ReactiveMongoRepository<FlightInventory, String> {
    Flux<FlightInventory> findByFromPlaceAndToPlaceAndDepartureTimeBetween(
            String fromPlace, String toPlace, LocalDateTime start, LocalDateTime end);
}