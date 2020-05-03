package com.example.inventoryauditapp;

public class Computer extends Item {

    private String os;
    private String lastScanned;

    public Computer(){
        //empty constructor required by Firebase
    }

    //constructor
    public Computer(String serialNumber, String building, int roomNumber, String os, String brand,  String lastScanned, String dateAdded, User modifiedBy) {
        super(serialNumber, building, roomNumber, brand, dateAdded, modifiedBy);

        this.os = os;
        this.lastScanned = lastScanned;
    }

    //getters and setters for all fields
    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getLastScanned() {
        return lastScanned;
    }

    public void setLastScanned(String lastScanned) {
        this.lastScanned = lastScanned;
    }


}
