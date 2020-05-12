package com.example.inventoryauditapp.inventory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.inventoryauditapp.R;
import com.example.inventoryauditapp.classes.Computer;
import com.example.inventoryauditapp.classes.Item;
import com.example.inventoryauditapp.classes.Printer;
import com.example.inventoryauditapp.classes.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

public class AddItemPage extends AppCompatActivity {

    //Initialize Buttons
    Button submit;
    Button cancel;

    //Initialize Spinner
    Spinner itemType;
    Spinner printerType;

    //Initialize EditText
    EditText serialNumberInput;

    //Initialize TextView
    TextView osTextView;
    TextView printerTextView;

    //Initialize AutoCompleteTextView
    AutoCompleteTextView brandInput;
    AutoCompleteTextView buildingInput;
    AutoCompleteTextView osInput;
    AutoCompleteTextView roomInput;

    //Arrays for AutoCompleteTextViews
    ArrayList<String> buildings = new ArrayList<>();
    ArrayList<String> brands = new ArrayList<>();
    ArrayList<String> rooms = new ArrayList<>();
    ArrayList<String> operatingSystems = new ArrayList<>();

    //Arrays for Items
    ArrayList<Computer> computers = new ArrayList<>();
    ArrayList<Printer> printers = new ArrayList<>();

    //Set used for duplicates
    Set<String> set = new HashSet<>();

    //setting globals for data to be passed back to previous page
    String itemTypeText;
    String buildingText;
    String roomText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item_page);
        initUI();
        getItems();

        //Cancel Button
        changeActivityWithButton(cancel, InventorySearchResultsPage.class);

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
                boolean emptyForm = addItem();
                if(!emptyForm) {
                    Intent intent = new Intent(getBaseContext(), InventorySearchResultsPage.class);
                    intent.putExtra("item", itemTypeText);
                    intent.putExtra("building", buildingText);
                    intent.putExtra("room", roomText);
                    startActivity(intent);
                }
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
    private void changeActivityWithButton(Button button, final Class<? extends Activity> page) {
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
    public boolean addItem(){
        DatabaseReference ref;
        String serialNumberText     = serialNumberInput.getText().toString();
        itemTypeText                = itemType.getSelectedItem().toString();
        buildingText                = buildingInput.getText().toString().trim();
        roomText                    = roomInput.getText().toString().trim();
        String brandText            = brandInput.getText().toString().trim();
        String osText               = osInput.getText().toString().trim();
        String currentDate          = Calendar.getInstance().getTime().toString();
        Integer computerRoom        = !(roomText.equals("")) ? Integer.parseInt(roomText) : 0; //Lambda function to format as integer properly when form has input or is empty
        String printerTypeText      = printerType.getSelectedItem().toString();
        GoogleSignInAccount acct    = GoogleSignIn.getLastSignedInAccount(this);
        String userName             = acct.getDisplayName();
        String userEmail            = acct.getEmail();
        User appUser                = new User(userName, userEmail);


        if(!emptyText(itemTypeText,buildingText,roomText,brandText,osText,serialNumberText)){
            if(itemTypeText.equals("Computer")){
                ref = FirebaseDatabase.getInstance().getReference("Computer").child(serialNumberText);
                Computer computer = new Computer(serialNumberText,buildingText,computerRoom,osText,brandText,currentDate,currentDate,appUser);
                ref.setValue(computer);
                Toast.makeText(this,"Successfully added Computer", Toast.LENGTH_LONG).show();
            }else if(itemTypeText.equals("Printer")){
                ref = FirebaseDatabase.getInstance().getReference("Printer").child(serialNumberText);
                Printer printer = new Printer(serialNumberText,buildingText,computerRoom,printerTypeText,brandText,currentDate,appUser);
                ref.setValue(printer);
                Toast.makeText(this,"Successfully added Printer", Toast.LENGTH_LONG).show();
            }
            return false;
        }else{
            Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_LONG).show();
            return true;
        }

    }

    //helper method to check if any inputted text is empty;
    private boolean emptyText(String itemType, String building,String room,String brand, String os, String serialNumber){
        if(TextUtils.isEmpty(serialNumber) || serialNumber.equals("")){
            return true;
        }else if(TextUtils.isEmpty(building) || building.equals("")){
            return true;
        }else if(TextUtils.isEmpty(room) || room.equals("")){
            return true;
        }else if(TextUtils.isEmpty(brand) || brand.equals("")) {
            return true;
        }else if(itemType.equals("Computer")){
            if(TextUtils.isEmpty(os) || os.equals("")){
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    /*
    Used to call methods that gather item info from the database.
     */
    private void getItems() {
        getComputers();
        getPrinters();
    }

    /**
    This method's purpose is to gather all computers from the database.
     */
    private void getComputers() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Computer");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Computer c = ds.getValue(Computer.class);
                    computers.add(c);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    /**
     * This method is used to gather all printers from the database.
     */
    private void getPrinters() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Printer");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Printer p = ds.getValue(Printer.class);
                    printers.add(p);
                }
                callFormDetailFunctions();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    /**
     * This is for getting details to be suggested when adding a new item for
     * certain attributes of the item being added.
     */
    private void callFormDetailFunctions() {
        ArrayList<Item> items = new ArrayList<>();
        items.addAll(computers);
        items.addAll(printers);
        getBuildingNames(items);
        getBrandNames(items);
        getOSNames(computers);
        setAdapters();
    }

    /**
     * This method is used for getting all current building names to populate
     * an array adapter for suggestions to the user.
     * @param items - An ArrayList of abstract type Item and ensures unique
     *              values for buildings are added to the buildings variable.
     */
    private void getBuildingNames(ArrayList<Item> items) {
        for(Item i: items) {
            String building = i.getBuilding();
            if(!set.contains(building)) {
                set.add(building);
                buildings.add(building);
            }
        }
    }

    /**
     * This method is used for getting all current brand names to populate
     * an array adapter for suggestions to the user.
     * @param items - An ArrayList of abstract type Item and ensures unique
     *              values for brand are added to the brands variable.
     */
    private void getBrandNames(ArrayList<Item> items) {
        for(Item i: items) {
            String brand = i.getBrand();
            if(!set.contains(brand)) {
                set.add(brand);
                brands.add(brand);
            }
        }
    }

    /**
     * This method is used for getting all current OS names to populate
     * an array adapter for suggestions to the user.
     * @param comp - An ArrayList of computers and ensures unique
     *              values for OS are added to the OS variable.
     */
    private void getOSNames(ArrayList<Computer> comp) {
        for(Computer c: comp) {
            String os = c.getOs();
            if(!set.contains(os)) {
                set.add(os);
                operatingSystems.add(os);
            }
        }
    }

    /**
     * Method created to populate the AutoCompleteTextView suggestions using the results
     * gathered in the above methods, all of which are unique.
     */
    private void setAdapters() {
        buildingInput.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, buildings));
        brandInput.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, brands));
        osInput.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, operatingSystems));
        /*
        If we want to use pre-determined lists
        getResources().getStringArray(R.array.brands);
        getResources().getStringArray(R.array.operating_systems);
         */
    }


}
