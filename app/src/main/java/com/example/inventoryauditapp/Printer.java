package com.example.inventoryauditapp;

import java.util.Date;

public class Printer {

    private int idNumber;
    private String building;
    private int roomNumber;
    private String type;
    private String brand;
    private Date dateAdded;
    private User modifiedBy;

    public Printer() {
        //empty constructor required by Firebase
    }

    // constructor
    public Printer(int idNumber, String building, int roomNumber, String type, String brand, Date dateAdded, User modifiedBy) {
        this.idNumber = idNumber;
        this.building = building;
        this.roomNumber = roomNumber;
        this.type = type;
        this.brand = brand;
        this.dateAdded = dateAdded;
        this.modifiedBy = modifiedBy;
    }

    // alternate constructor with no type
    public Printer(int idNumber, String building, int roomNumber, String brand, Date dateAdded, User modifiedBy) {
        this.idNumber = idNumber;
        this.building = building;
        this.roomNumber = roomNumber;
        this.type = "";
        this.brand = brand;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
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


    //TODO: Change this to work with the listview display
    @Override
    public String toString() {
        return "Printer{" +
                "idNumber=" + idNumber +
                ", building='" + building + '\'' +
                ", roomNumber=" + roomNumber +
                ", type='" + type + '\'' +
                ", brand='" + brand + '\'' +
                ", dateAdded=" + dateAdded +
                ", modifiedBy=" + modifiedBy +
                '}';
    }
}
