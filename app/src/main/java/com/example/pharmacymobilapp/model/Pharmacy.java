package com.example.pharmacymobilapp.model;

public class Pharmacy {
    private int id;
    private String name;
    private String address;
    private String openingHours;

    // Constructor
    public Pharmacy(String name, String address, String openingHours) {
        this.name = name;
        this.address = address;
        this.openingHours = openingHours;
    }

    // Getter for name
    public String getName() {
        return name;
    }

    // Setter for name
    public void setName(String name) {
        this.name = name;
    }

    // Getter for address
    public String getAddress() {
        return address;
    }

    // Setter for address
    public void setAddress(String address) {
        this.address = address;
    }

    // Getter for openingHours
    public String getOpeningHours() {
        return openingHours;
    }

    // Setter for openingHours
    public void setOpeningHours(String openingHours) {
        this.openingHours = openingHours;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
