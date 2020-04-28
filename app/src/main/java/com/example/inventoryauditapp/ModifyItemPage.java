package com.example.inventoryauditapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

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
    private EditText modifiedByEditText;

    //fields to appear only when a printer is selected
    private TextView printerTypeTextView;
    private Spinner printerTypeSpinner;

    //text labels of OS
    private TextView OSTextView;
    private TextView lastScannedTextView;

    //references to itemType and serial num passed in from previous intent
    String itemType;
    String serialNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_item_page);

        //grabbing item type and serial num from previous activity
        itemType = getIntent().getStringExtra("itemType");
        serialNum = getIntent().getStringExtra("serialNum");

        initUI();
        setComponentVisibility();

        initializeTextFields();



        //I know this is redundant now but will later need to call external method to store data
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDatabase();
            }
        });

    }

    /**
     * Updates database with the new information.  New information will be entered into text fields
     *  by the user
     */
    public void updateDatabase(){
        // TODO: Update this to reflect DB changes
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(itemType).child(serialNum);
        if(itemType.equals("Computer")){
            Computer c;
            c = new Computer(
                    serialNum,
                    buildingEditText.getText().toString(),
                    Integer.parseInt(roomEditText.getText().toString()),
                    OSEditText.getText().toString(),
                    brandEditText.getText().toString(),
                    lastModifiedDisplayTextView.getText().toString(),
                    dateAddedDisplayTextView.getText().toString(),
                    new User(modifiedByEditText.getText().toString()));
            ref.setValue(c);
        }
        else if(itemType.equals("Printer")){
            Printer p;
            p = new Printer(
                    serialNum,
                    buildingEditText.getText().toString(),
                    Integer.parseInt(roomEditText.getText().toString()),
                    printerTypeSpinner.getSelectedItem().toString(),
                    brandEditText.getText().toString(),
                    dateAddedDisplayTextView.getText().toString(),
                    new User(modifiedByEditText.getText().toString()));

            ref.setValue(p);
        }
        else{
            //could just be an if - else statement but leaving room for more items being added
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
                    itemIDTextView.setText(c.getSerialNumber()+"");
                    buildingEditText.setText(c.getBuilding());
                    roomEditText.setText(c.getRoomNumber()+"");
                    brandEditText.setText(c.getBrand());
                    dateAddedDisplayTextView.setText(c.getDateAdded());
                    modifiedByEditText.setText(c.getModifiedBy().toString());
                    OSEditText.setText(c.getOs());
                    lastModifiedDisplayTextView.setText(c.getLastScanned());
                }
                else if (itemType.equals("Printer")){
                    Printer p = dataSnapshot.getValue(Printer.class);
                    itemIDTextView.setText(p.getSerialNumber()+"");
                    buildingEditText.setText(p.getBuilding());
                    roomEditText.setText(p.getRoomNumber()+"");
                    brandEditText.setText(p.getBrand());
                    dateAddedDisplayTextView.setText(p.getDateAdded());
                    modifiedByEditText.setText(p.getModifiedBy().toString());
                    //lastModifiedDisplayTextView.setText(p.getLastScanned()) Todo: printer should have a last scanned field
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


    public void initUI(){
        submitButton                = findViewById(R.id.submitModifyButton);
        buildingEditText            = findViewById(R.id.buildingEditText);
        roomEditText                = findViewById(R.id.roomEditText);
        OSEditText                  = findViewById(R.id.OSEditText);
        brandEditText               = findViewById(R.id.brandEditText);
        dateAddedDisplayTextView    = findViewById(R.id.dateAddedDisplayTextView);
        lastModifiedDisplayTextView = findViewById(R.id.lastScannedDisplayTextView);
        modifiedByEditText          = findViewById(R.id.modifiedByEditText);
        OSTextView                  = findViewById(R.id.OSTextView);
        itemIDTextView              = findViewById(R.id.itemIDTextView);
        lastScannedTextView         = findViewById(R.id.lastScannedTextView);
        printerTypeSpinner          = findViewById(R.id.printerTypeSpinner);
        printerTypeTextView         = findViewById(R.id.printerTypeTextView);
    }
}
