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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

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

    //setting globals for data to be passed back to previous page
    String itemTypeText;
    String buildingText;
    String roomText;





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
                intent.putExtra("item", itemTypeText);
                intent.putExtra("building", buildingText);
                intent.putExtra("room", roomText);
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
    //TODO: Use new DB structure and see discord for the replacement of using the Date field and instead
    // use the Calendar class pm Dylan for more info
    public void addItem(){
        DatabaseReference ref;
        itemTypeText            = itemType.getSelectedItem().toString();
        buildingText            = buildingInput.getText().toString().trim();
        roomText                = roomInput.getText().toString().trim();
        String brandText        = brandInput.getText().toString().trim();
        String dateAdded        = dateAddedInput.getText().toString().trim();
        String modifiedByText   = modifiedByInput.getText().toString().trim();
        String osText           = osInput.getText().toString().trim();
        String lastScannedText  = lastScannedInput.getText().toString().trim();
        //Date currentDate        = Calendar.getInstance().getTime();
        Date currentDate        = convertStringToDate(dateAdded);
        Integer computerRoom    = Integer.parseInt(roomText);
        User dummyUser          = new User("scottdaluga16","scottdaluga16@augustana.edu");
        //String computerID       = getNextComputerIndex();
        String computerID       = "111006";
        String printerID        = "211006";

        if(!emptyText(itemTypeText,buildingText,roomText,brandText,dateAdded,modifiedByText,osText,lastScannedText)){
            if(itemTypeText.equals("Computer")){
                Date lastScannedDate = convertStringToDate(lastScannedText);
                ref = FirebaseDatabase.getInstance().getReference(buildingText).child(roomText).child(itemTypeText).child(computerID);
                //TODO: Constructors changed to use the Calendar class and make it a string to store the date, pm Dylan For more info
                //Computer computer = new Computer(Integer.parseInt(computerID),buildingText,computerRoom,osText,brandText,lastScannedDate,currentDate,dummyUser);
                //ref.setValue(computer);
                Toast.makeText(this,"Successfully added Computer", Toast.LENGTH_LONG).show();
            }else if(itemTypeText.equals("Printer")){
                ref = FirebaseDatabase.getInstance().getReference(buildingText).child(roomText).child(itemTypeText).child(printerID);
                //TODO: Constructors changed to use the Calendar class and make it a string to store the date, pm Dylan For more info
                //public Printer(int idNumber, String building, int roomNumber, String brand, Date dateAdded, User modifiedBy)
//                Printer printer = new Printer(211006,buildingText,computerRoom,brandText,currentDate,dummyUser);
//                ref.setValue(printer);
                Toast.makeText(this,"Successfully added Printer", Toast.LENGTH_LONG).show();
            }
                //if we add another item type;
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
    //TODO: May no longer need this if we do not require the user to input date data in the app, pm Dylan for more info
    public Date convertStringToDate(String s){
        String[] a = s.split("/");
        int month = Integer.parseInt(a[0]);
        int day = Integer.parseInt(a[1]);
        int year = Integer.parseInt(a[2]);
        Date date = new Date(year, month, day);
        return date;
    }
}
