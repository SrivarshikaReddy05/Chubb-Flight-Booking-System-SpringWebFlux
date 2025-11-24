package com.flightapp.dto;

import java.time.LocalDate;

import com.flightapp.model.enums.TripType;

public class FlightSearchRequest {
	private String fromPlace;
	private String toPlace;
	private LocalDate journeyDate;
	private TripType tripType;

	public FlightSearchRequest() {
	}

	public String getFromPlace() {
		return fromPlace;
	}

	public void setFromPlace(String fromPlace) {
		this.fromPlace = fromPlace;
	}

	public String getToPlace() {
		return toPlace;
	}

	public void setToPlace(String toPlace) {
		this.toPlace = toPlace;
	}

	public LocalDate getJourneyDate() {
		return journeyDate;
	}

	public void setJourneyDate(LocalDate journeyDate) {
		this.journeyDate = journeyDate;
	}

	public TripType getTripType() {
		return tripType;
	}

	public void setTripType(TripType tripType) {
		this.tripType = tripType;
	}
}
