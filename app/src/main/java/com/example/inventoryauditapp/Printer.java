package com.example.inventoryauditapp;

public class Printer extends Item {

    private String type;

    public Printer() {
        //empty constructor required by Firebase
    }

    // constructor
    public Printer(String serialNumber, String building, int roomNumber, String type, String brand, String dateAdded, User modifiedBy) {
        super(serialNumber, building, roomNumber, brand, dateAdded, modifiedBy);

        this.type = type;
    }

    //getters and setters for all fields
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
