package com.example.inventoryauditapp;

import java.util.Date;

public class Computer {

    private int idNumber;
    private String building;
    private int roomNumber;
    private String os;
    private String brand;
    private Date lastScanned;
    private Date dateAdded;
    private User modifiedBy;

    public Computer(){
        //empty constructor required by Firebase
    }

    //constructor
    public Computer(int idNumber, String building, int roomNumber, String os, String brand, Date lastScanned, Date dateAdded, User modifiedBy) {
        this.idNumber = idNumber;
        this.building = building;
        this.roomNumber = roomNumber;
        this.os = os;
        this.brand = brand;
        this.lastScanned = lastScanned;
        this.dateAdded = dateAdded;
        this.modifiedBy = modifiedBy;
    }


    //getters and setters for all fields
    public int getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(int idNumber) {
        this.idNumber = idNumber;
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

    public Date getLastScanned() {
        return lastScanned;
    }

    public void setLastScanned(Date lastScanned) {
        this.lastScanned = lastScanned;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
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
        return "Computer{" +
                "idNumber=" + idNumber +
                ", building='" + building + '\'' +
                ", roomNumber=" + roomNumber +
                ", os='" + os + '\'' +
                ", brand='" + brand + '\'' +
                ", lastScanned=" + lastScanned +
                ", dateAdded=" + dateAdded +
                ", modifiedBy=" + modifiedBy +
                '}';
    }
}
