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
    Spinner printerType;

    //Initialize EditText
    EditText buildingInput;
    EditText roomInput;
    EditText osInput;
    EditText brandInput;
    EditText lastScannedInput;
    EditText dateAddedInput;
    EditText modifiedByInput;
    EditText serialNumberInput;

    //Initialize TextView
    TextView osTextView;
    TextView printerTextView;

    //setting globals for data to be passed back to previous page
    String itemTypeText;
    String buildingText;
    String roomText;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item_page);

        initUI();

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
        modifiedByInput     = findViewById(R.id.modifiedInput);
        osTextView          = findViewById(R.id.OSText);
        printerTextView     = findViewById(R.id.typeText);
        printerType         = findViewById(R.id.printerTypeSpinner);
        serialNumberInput   = findViewById(R.id.serialNumberInput);
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
            printerTextView.setVisibility(View.INVISIBLE);
            printerType.setVisibility(View.GONE);
        }
        else if(itemTypeText.equals("Printer")){
            osTextView.setVisibility(View.INVISIBLE);
            osInput.setVisibility(View.INVISIBLE);
            printerTextView.setVisibility(View.VISIBLE);
            printerType.setVisibility(View.VISIBLE);
        }
    }

    //checks to see if all fields are entered and then submits item entry to the database
    //TODO: Use new DB structure and see discord for the replacement of using the Date field and instead
    // use the Calendar class pm Dylan for more info
    public void addItem(){
        DatabaseReference ref;
        String serialNumberText = serialNumberInput.getText().toString();
        itemTypeText            = itemType.getSelectedItem().toString();
        buildingText            = buildingInput.getText().toString().trim();
        roomText                = roomInput.getText().toString().trim();
        String brandText        = brandInput.getText().toString().trim();
        String modifiedByText   = modifiedByInput.getText().toString().trim();
        String osText           = osInput.getText().toString().trim();
        String currentDate      = Calendar.getInstance().getTime().toString();
        Integer computerRoom    = Integer.parseInt(roomText);
        String printerTypeText  = printerType.getSelectedItem().toString();
        User dummyUser          = new User("scottdaluga16","scottdaluga16@augustana.edu");


        if(!emptyText(itemTypeText,buildingText,roomText,brandText,modifiedByText,osText,serialNumberText)){
            if(itemTypeText.equals("Computer")){
                ref = FirebaseDatabase.getInstance().getReference("Computer").child(serialNumberText);
                Computer computer = new Computer(serialNumberText,buildingText,computerRoom,osText,brandText,currentDate,currentDate,dummyUser);
                ref.setValue(computer);
                Toast.makeText(this,"Successfully added Computer", Toast.LENGTH_LONG).show();
            }else if(itemTypeText.equals("Printer")){
                ref = FirebaseDatabase.getInstance().getReference("Printer").child(serialNumberText);
                Printer printer = new Printer(serialNumberText,buildingText,computerRoom,printerTypeText,brandText,currentDate,dummyUser);
                ref.setValue(printer);
                Toast.makeText(this,"Successfully added Printer", Toast.LENGTH_LONG).show();
            }
                //if we add another item type;
        }else{
            Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_LONG).show();
        }

    }

    //helper method to check if any inputted text is empty;
    private boolean emptyText(String itemType, String building,String room,String brand, String modifiedBy,String os, String serialNumber){
        if(TextUtils.isEmpty(serialNumber)){
            return true;
        }else if(TextUtils.isEmpty(building)){
            return true;
        }else if(TextUtils.isEmpty(room)){
            return true;
        }else if(TextUtils.isEmpty(brand)) {
            return true;
        }else if(TextUtils.isEmpty(modifiedBy)){
            return true;
        }else if(itemType.equals("Computer")){
            if(TextUtils.isEmpty(os)){
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

}
