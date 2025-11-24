package com.flightapp.dto;

import java.time.LocalDate;
import java.util.List;


import com.flightapp.model.Passenger;
import com.flightapp.model.enums.MealType;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class BookingRequest {
    @NotBlank
    private String userName;
    @Email @NotBlank
    private String userEmail;
    @NotNull
    private LocalDate journeyDate;
    @NotNull
    private List<Passenger> passengers;
    private MealType mealType;

    public BookingRequest() {}
    // getters & setters
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }
    public LocalDate getJourneyDate() { return journeyDate; }
    public void setJourneyDate(LocalDate journeyDate) { this.journeyDate = journeyDate; }
    public List<Passenger> getPassengers() { return passengers; }
    public void setPassengers(List<Passenger> passengers) { this.passengers = passengers; }
    public MealType getMealType() { return mealType; }
    public void setMealType(MealType mealType) { this.mealType = mealType; }
}