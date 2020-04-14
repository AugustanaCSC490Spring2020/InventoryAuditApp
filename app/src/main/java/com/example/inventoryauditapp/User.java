package com.example.inventoryauditapp;

public class User {

    private String username;
    private String email;

    public User(){
        //empty constructor required by Firebase
    }

    //constructor
    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

    //getters
    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    //setters
    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return username;
    }
}
