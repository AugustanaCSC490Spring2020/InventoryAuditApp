package com.example.inventoryauditapp.classes;

import java.util.ArrayList;

public class Building {

    //field
    private ArrayList<Room> rooms;

    public Building() {
        //empty constructor required by Firebase
    }

    //constructor
    public Building(ArrayList<Room> rooms) {
        this.rooms = rooms;
    }

    //getter/setter
    public ArrayList<Room> getRooms() {
        return rooms;
    }

    public void setRooms(ArrayList<Room> rooms) {
        this.rooms = rooms;
    }
}
