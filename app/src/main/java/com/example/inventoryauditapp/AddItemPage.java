package com.example.inventoryauditapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddItemPage extends AppCompatActivity {

    //Initialize Buttons
    Button submit;
    Button cancel;

    //Initialize Spinner
    Spinner itemType;

    //Initialize EditText
    EditText buildingInput;
    EditText roomInput;
    EditText osInput;
    EditText brandInput;
    EditText lastScannedInput;
    EditText dateAddedInput;
    EditText modifiedByInput;

    //Initialize TextView
    TextView osTextView;
    TextView lastScannedTextView;

    //Setting Globals for the computer and printer IDs
    Integer computerID;
    Integer printerID;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item_page);

        initUI();

        //Submit Button
        //changeActivity(submit, InventorySearchResultsPage.class);

        //Cancel Button
        changeActivity(cancel, InventorySearchResultsPage.class);

        //sets a listener to see if the spinner is changed
        itemType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                handleItemSpinnerChange();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //left intentionally blank
            }
        });

        //sets an on click listener for when the user submits their entries
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem();
                Intent intent = new Intent(getBaseContext(), InventorySearchResultsPage.class);
                startActivity(intent);
            }
        });

    }

    //Initializes Components
    private void initUI() {
        submit              = findViewById(R.id.submitButton);
        cancel              = findViewById(R.id.cancelButton);
        itemType            = findViewById(R.id.itemTypeSpinner);
        buildingInput       = findViewById(R.id.buildingInput);
        roomInput           = findViewById(R.id.roomInput);
        osInput             = findViewById(R.id.osInput);
        brandInput          = findViewById(R.id.brandInput);
        lastScannedInput    = findViewById(R.id.lastScannedInput);
        dateAddedInput      = findViewById(R.id.dateAddedInput);
        modifiedByInput     = findViewById(R.id.modifiedInput);
        osTextView          = findViewById(R.id.OSText);
        lastScannedTextView = findViewById(R.id.lastScannedText);
    }

    /**
     *
     * @param button - button that is clicked on Main Page
     * @param page - the new activity to change to
     */
    private void changeActivity(Button button, final Class<? extends Activity> page) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), page);
                startActivity(intent);
            }
        });
    }

    /**
     * OS and Last Scanned should only be visible when a Computer is selected
     */
    public void handleItemSpinnerChange(){
        String itemTypeText = itemType.getSelectedItem().toString();
        if(itemTypeText.equals("Computer")){
            osTextView.setVisibility(View.VISIBLE);
            osInput.setVisibility(View.VISIBLE);
            lastScannedTextView.setVisibility(View.VISIBLE);
            lastScannedInput.setVisibility(View.VISIBLE);
        }
        else if(itemTypeText.equals("Printer")){
            osTextView.setVisibility(View.INVISIBLE);
            osInput.setVisibility(View.INVISIBLE);
            lastScannedTextView.setVisibility(View.INVISIBLE);
            lastScannedInput.setVisibility(View.INVISIBLE);
        }
    }

    //checks to see if all fields are entered and then submits item entry to the database
    public void addItem(){
        DatabaseReference ref;
        String itemTypeText     = itemType.getSelectedItem().toString();
        String buildingText     = buildingInput.getText().toString().trim();
        String roomText         = roomInput.getText().toString().trim();
        String brandText        = brandInput.getText().toString().trim();
        String dateAdded        = dateAddedInput.getText().toString().trim();
        String modifiedByText   = modifiedByInput.getText().toString().trim();
        String osText           = osInput.getText().toString().trim();
        String lastScannedText  = lastScannedInput.getText().toString().trim();
        //Date currentDate        = Calendar.getInstance().getTime();
        Date currentDate        = convertStringToDate(dateAdded);
        Integer computerRoom    = Integer.parseInt(roomText);
        User dummyUser          = new User("scottdaluga16","scottdaluga16@augustana.edu");

        if(!emptyText(itemTypeText,buildingText,roomText,brandText,dateAdded,modifiedByText,osText,lastScannedText)){
            Date lastScannedDate = convertStringToDate(lastScannedText);
            if(itemTypeText.equals("Computer")){
                ref = FirebaseDatabase.getInstance().getReference(buildingText).child(roomText).child(itemTypeText).child("111008");
                Computer computer = new Computer(111008,buildingText,computerRoom,osText,brandText,lastScannedDate,currentDate,dummyUser);
                ref.setValue(computer);
                Toast.makeText(this,"Successfully added Computer", Toast.LENGTH_LONG).show();
            }

        }else{
            Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_LONG).show();
        }

    }

    //helper method to check if any inputted text is empty;
    private boolean emptyText(String itemType, String building,String room,String brand, String date, String modifiedBy,String os, String lastScanned){
        if(TextUtils.isEmpty(building)){
            return true;
        }else if(TextUtils.isEmpty(room)){
            return true;
        }else if(TextUtils.isEmpty(brand)){
            return true;
        }else if(TextUtils.isEmpty(date)){
            return true;
        }else if(TextUtils.isEmpty(modifiedBy)){
            return true;
        }else if(itemType.equals("Computer")){
            if(TextUtils.isEmpty(os)){
                return true;
            }else if(TextUtils.isEmpty(lastScanned)){
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }
    /**
     * This method takes in a date as a string and converts it to a Date object
     *
     * @param s the date as a String formatted "mm/dd/yyyy"
     * @return a Date object that has been converted from a String
     */
    public Date convertStringToDate(String s){
        String[] a = s.split("/");
        int month = Integer.parseInt(a[0]);
        int day = Integer.parseInt(a[1]);
        int year = Integer.parseInt(a[2]);
        Date date = new Date(year, month, day);
        return date;
    }
}
