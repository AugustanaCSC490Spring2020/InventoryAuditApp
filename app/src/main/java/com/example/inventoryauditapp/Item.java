package com.example.inventoryauditapp;

abstract class Item {
    private String serialNumber;
    private String building;
    private int roomNumber;
    private String brand;
    private String dateAdded;
    private User modifiedBy;

    public Item (){
        // required by subclasses
    }

    public Item (String serialNumber, String building, int roomNumber,String brand, String dateAdded, User modifiedBy){
        this.serialNumber = serialNumber;
        this.building     = building;
        this.roomNumber   = roomNumber;
        this.brand        = brand;
        this.dateAdded    = dateAdded;
        this.modifiedBy   = modifiedBy;
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

    @Override
    public String toString() {
        return serialNumber + " | " + brand;
    }

}
