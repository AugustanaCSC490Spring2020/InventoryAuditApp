package com.example.inventoryauditapp;

public class Printer {

    private String serialNumber;
    private String building;
    private int roomNumber;
    private String type;
    private String brand;
    private String dateAdded;
    private User modifiedBy;

    public Printer() {
        //empty constructor required by Firebase
    }

    // constructor
    public Printer(String serialNumber, String building, int roomNumber, String type, String brand, String dateAdded, User modifiedBy) {
        this.serialNumber = serialNumber;
        this.building = building;
        this.roomNumber = roomNumber;
        this.type = type;
        this.brand = brand;
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


    //TODO: Change this to work with the listview display
    @Override
    public String toString() {
        return "Printer{" +
                "serialNumber=" + serialNumber +
                ", building='" + building + '\'' +
                ", roomNumber=" + roomNumber +
                ", type='" + type + '\'' +
                ", brand='" + brand + '\'' +
                ", dateAdded=" + dateAdded +
                ", modifiedBy=" + modifiedBy +
                '}';
    }
}
