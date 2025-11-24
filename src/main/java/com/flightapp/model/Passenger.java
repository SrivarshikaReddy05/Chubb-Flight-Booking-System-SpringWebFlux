package com.flightapp.model;

import com.flightapp.model.enums.Gender;

public class Passenger {
    private String name;
    private Gender gender;
    private int age;
    private String seatNumber;

    public Passenger() {}
    public Passenger(String name, int age, Gender gender, String seatNumber) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.seatNumber = seatNumber;
    }

    // getters & setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Gender getGender() { return gender; }
    public void setGender(Gender gender) { this.gender = gender; }
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    public String getSeatNumber() { return seatNumber; }
    public void setSeatNumber(String seatNumber) { this.seatNumber = seatNumber; }
}