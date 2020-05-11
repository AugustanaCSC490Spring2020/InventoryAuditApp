package com.example.inventoryauditapp.classes;

import java.io.Serializable;
import java.util.ArrayList;

public class Audit implements Serializable {

    private ArrayList<String> confirmedItems;
    private ArrayList<String> messages;

    public Audit(ArrayList<String> confirmedItems, ArrayList<String> messages) {
        this.confirmedItems = confirmedItems;
        this.messages = messages;
    }

    public Audit(){
        confirmedItems = new ArrayList<>();
        messages = new ArrayList<>();
    }

    public void addConfirmation(String serialNum, String message){
        confirmedItems.add(serialNum);
        messages.add(message);
    }

    public String getItemNum(int i){
        return confirmedItems.get(i);
    }

    public String getMessage(int i){
        return messages.get(i);
    }

    public int getAuditSize(){
        return confirmedItems.size();
    }

}
