package com.example.inventoryauditapp.classes;

import java.util.ArrayList;

public class Room {

    //field
    private ArrayList<String> assets;

    public Room() {
        //empty constructor required by Firebase
    }

    public Room(ArrayList<String> assets) {
        this.assets = assets;
    }

    public ArrayList<String> getAssets() {
        return assets;
    }

    public void setAssets(ArrayList<String> assets) {
        this.assets = assets;
    }
}
