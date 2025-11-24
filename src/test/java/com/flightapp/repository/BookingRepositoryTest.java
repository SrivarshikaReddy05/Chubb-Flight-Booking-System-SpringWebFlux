package com.flightapp.repository;

import com.flightapp.model.Booking;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import org.junit.jupiter.api.BeforeEach;

@DataMongoTest
class BookingRepositoryTest {

	@Autowired
	private BookingRepository bookingRepo;

	@BeforeEach
	void cleanDB() {
		bookingRepo.deleteAll().block();
	}

	@Test
	void testFindByPnr() {
		Booking b = new Booking();
		b.setPnr("PNR100");
		b.setFlightId("F1");

		StepVerifier.create(bookingRepo.save(b).then(bookingRepo.findByPnr("PNR100")))
				.expectNextMatches(found -> found.getPnr().equals("PNR100")).verifyComplete();
	}

	@Test
	void testDeleteByPnr() {
		Booking b = new Booking();
		b.setPnr("DEL123");
		b.setFlightId("F1");

		StepVerifier.create(bookingRepo.save(b).then(bookingRepo.deleteByPnr("DEL123"))).verifyComplete();

		StepVerifier.create(bookingRepo.findByPnr("DEL123")).verifyComplete();
	}
}
