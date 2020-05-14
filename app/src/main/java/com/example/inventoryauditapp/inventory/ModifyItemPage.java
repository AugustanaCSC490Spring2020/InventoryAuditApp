package com.example.inventoryauditapp.inventory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.inventoryauditapp.classes.Computer;
import com.example.inventoryauditapp.classes.Item;
import com.example.inventoryauditapp.classes.Printer;
import com.example.inventoryauditapp.R;
import com.example.inventoryauditapp.classes.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ModifyItemPage extends AppCompatActivity {

    //buttons at the bottom of the screen
    private Button submitButton;
    //TextViews of displayed information.  User cannot edit this info
    private TextView itemIDTextView;
    private TextView dateAddedDisplayTextView;
    private TextView lastModifiedDisplayTextView;

    //all text fields that the user can modify
    private EditText buildingEditText;
    private EditText roomEditText;
    private EditText OSEditText;
    private EditText brandEditText;
    //private EditText modifiedByEditText;

    //fields to appear only when a printer is selected
    private TextView printerTypeTextView;
    private Spinner printerTypeSpinner;

    //text labels of OS
    private TextView OSTextView;
    private TextView lastScannedTextView;

    //references to itemType and serial num passed in from previous intent
    String itemType;
    String serialNum;
    String building;
    String room;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_item_page);

        //grabbing item type and serial num from previous activity
        itemType = getIntent().getStringExtra("itemType");
        serialNum = getIntent().getStringExtra("serialNum");
        building = getIntent().getStringExtra("building");
        room = getIntent().getStringExtra("room");


        initUI();
        setComponentVisibility();

        initializeTextFields();

        //I know this is redundant now but will later need to call external method to store data
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isUpdated = updateDatabase();
                if(isUpdated) {
                    Intent intent = new Intent(getBaseContext(), InventorySearchResultsPage.class);
                    intent.putExtra("item", itemType);
                    intent.putExtra("building", buildingEditText.getText().toString());
                    intent.putExtra("room", roomEditText.getText().toString());
                    startActivity(intent);
                }
            }
        });

    }

    /**
     * Updates database with the new information.  New information will be entered into text fields
     *  by the user
     */
    public boolean updateDatabase(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(itemType).child(serialNum);
        GoogleSignInAccount acct    = GoogleSignIn.getLastSignedInAccount(this);
        String userName             = acct.getDisplayName();
        String userEmail            = acct.getEmail();
        User appUser                = new User(userName, userEmail);
        if(!emptyText(itemType, buildingEditText.getText().toString(), roomEditText.getText().toString(), brandEditText.getText().toString(),
                OSEditText.getText().toString(), serialNum)) {
            if (itemType.equals("Computer")) {
                Computer c;
                c = new Computer(
                        serialNum,
                        buildingEditText.getText().toString(),
                        Integer.parseInt(roomEditText.getText().toString()),
                        OSEditText.getText().toString(),
                        brandEditText.getText().toString(),
                        lastModifiedDisplayTextView.getText().toString(),
                        dateAddedDisplayTextView.getText().toString(),
                        appUser);
                ref.setValue(c);
            } else if (itemType.equals("Printer")) {
                Printer p;
                p = new Printer(
                        serialNum,
                        buildingEditText.getText().toString(),
                        Integer.parseInt(roomEditText.getText().toString()),
                        printerTypeSpinner.getSelectedItem().toString(),
                        brandEditText.getText().toString(),
                        dateAddedDisplayTextView.getText().toString(),
                        appUser);
                ref.setValue(p);
            }
            Toast.makeText(this, "Item modified successfully.", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            Toast.makeText(this, "Please fill out all fields.", Toast.LENGTH_SHORT).show();
            return false;
        }

    }

    /**
     * This method pulls data from Firebase and sets the text fields to the current data in the
     *  database.  Checks the object type and directs the data display to be a Computer or Printer
     */
    public void initializeTextFields(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(itemType).child(serialNum);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(itemType.equals("Computer")){
                    Computer c = dataSnapshot.getValue(Computer.class);
                    setSharedItemFields(c);
                    OSEditText.setText(c.getOs());
                    lastModifiedDisplayTextView.setText(c.getLastScanned());
                }
                else if (itemType.equals("Printer")){
                    Printer p = dataSnapshot.getValue(Printer.class);
                    setSharedItemFields(p);
                }
                else{
                    //could just be an if - else statement but leaving room for more items being added
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    /**
     * sets the data fields for the the item.
     * @param c - an item object to be referenced
     */
    public void setSharedItemFields(Item c){
        itemIDTextView.setText(c.getSerialNumber()+"");
        buildingEditText.setText(c.getBuilding());
        roomEditText.setText(c.getRoomNumber()+"");
        brandEditText.setText(c.getBrand());
        dateAddedDisplayTextView.setText(c.getDateAdded());
        //modifiedByEditText.setText(c.getModifiedBy().toString());
    }

    /**
     * OS and Last Scanned should only be visible when a Computer is selected
     */
    public void setComponentVisibility(){
        if(itemType.equals("Computer")){
            OSTextView.setVisibility(View.VISIBLE);
            OSEditText.setVisibility(View.VISIBLE);
            lastScannedTextView.setVisibility(View.VISIBLE);
            lastModifiedDisplayTextView.setVisibility(View.VISIBLE);
            printerTypeTextView.setVisibility(View.INVISIBLE);
            printerTypeSpinner.setVisibility(View.INVISIBLE);
        }
        else if(itemType.equals("Printer")){
            OSTextView.setVisibility(View.INVISIBLE);
            OSEditText.setVisibility(View.INVISIBLE);
            lastScannedTextView.setVisibility(View.INVISIBLE);
            lastModifiedDisplayTextView.setVisibility(View.INVISIBLE);
            printerTypeTextView.setVisibility(View.VISIBLE);
            printerTypeSpinner.setVisibility(View.VISIBLE);
        }
        else{
            //could just be an if - else statement but leaving room for more items being added
        }
    }


    public void initUI(){
        submitButton                = findViewById(R.id.submitModifyButton);
        buildingEditText            = findViewById(R.id.buildingEditText);
        roomEditText                = findViewById(R.id.roomEditText);
        OSEditText                  = findViewById(R.id.OSEditText);
        brandEditText               = findViewById(R.id.brandEditText);
        dateAddedDisplayTextView    = findViewById(R.id.dateAddedDisplayTextView);
        lastModifiedDisplayTextView = findViewById(R.id.lastScannedDisplayTextView);
        OSTextView                  = findViewById(R.id.OSTextView);
        itemIDTextView              = findViewById(R.id.itemIDTextView);
        lastScannedTextView         = findViewById(R.id.lastScannedTextView);
        printerTypeSpinner          = findViewById(R.id.printerTypeSpinner);
        printerTypeTextView         = findViewById(R.id.printerTypeTextView);
    }

    //helper method to check if any inputted text is empty;
    private boolean emptyText(String itemType, String building,String room,String brand,String os, String serialNumber){
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
}
