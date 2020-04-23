package com.example.inventoryauditapp;

import java.util.Calendar;
import java.util.Date;

public class Computer {

    private String serialNumber;
    private String building;
    private int roomNumber;
    private String os;
    private String brand;
    private String lastScanned;
    private String dateAdded;
    private User modifiedBy;

    public Computer(){
        //empty constructor required by Firebase
    }

    //constructor
    public Computer(String serialNumber, String building, int roomNumber, String os, String brand,  String lastScanned, String dateAdded, User modifiedBy) {
        this.serialNumber = serialNumber;
        this.building = building;
        this.roomNumber = roomNumber;
        this.os = os;
        this.brand = brand;
        this.lastScanned = lastScanned;
        this.dateAdded = dateAdded;
        this.modifiedBy = modifiedBy;
    }


    //getters and setters for all fields
    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getLastScanned() {
        return lastScanned;
    }

    public void setLastScanned(String lastScanned) {
        this.lastScanned = lastScanned;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public User getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(User modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    @Override
    public String toString() {
        return serialNumber + " | " + brand;
    }
}
