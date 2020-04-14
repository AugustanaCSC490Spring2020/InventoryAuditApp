package com.example.inventoryauditapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ModifyItemPage extends AppCompatActivity {

    //buttons at the bottom of the screen
    private Button submitButton;
    private Button cancelButton;

    //id of the item being edited
    private TextView itemIDTextView;

    //all text fields that the user can modify
    private EditText buildingEditText;
    private EditText roomEditText;
    private EditText OSEditText;
    private EditText brandEditText;
    private EditText lastScannedEditText;
    private EditText dateAddedEditText;
    private EditText modifiedByEditText;

    //text labels of OS and lastScanned
    private TextView OSTextView;
    private TextView lastScannedTextView;

    //some hard coded data references for testing
    String buildingNum = "Old Main";
    String roomNum = "1";
    String itemType = "Computer"; //hard coded for now.  Will be dynamic later
    String id = "111004";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_item_page);

        initUI();
        setComponentVisibility(); //Todo grab item type from other screen... ie Computer or Printer
        initializeTextFields();

        changeActivity(cancelButton, InventorySearchResultsPage.class);

        //I know this is redundant now but will later need to call external method to store data
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDatebase();
            }
        });

    }

    /**
     * Updates database with the new information in the text fields entered by the user
     */
    public void updateDatebase(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(buildingNum).child(roomNum).child(itemType).child(id);
        User user = new User(modifiedByEditText.getText().toString());
        if(itemType.equals("Computer")){
            Computer c;
            c = new Computer(
                    Integer.parseInt(id),
                    buildingEditText.getText().toString(),
                    Integer.parseInt(roomEditText.getText().toString()),
                    OSEditText.getText().toString(),
                    brandEditText.getText().toString(),
                    stringToDate(lastScannedEditText.getText().toString()),
                    stringToDate(dateAddedEditText.getText().toString()),
                    user);

            ref.setValue(c);
        }
        else if(itemType.equals("Printer")){
            Printer p;
            p = new Printer(
                    Integer.parseInt(id),
                    buildingEditText.getText().toString(),
                    Integer.parseInt(roomEditText.getText().toString()),
                    brandEditText.getText().toString(),
                    stringToDate(dateAddedEditText.getText().toString()),
                    user);

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
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(buildingNum).child(roomNum).child(itemType).child(id);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(itemType.equals("Computer")){
                    Computer c = dataSnapshot.getValue(Computer.class);
                    itemIDTextView.setText(c.getIdNumber()+"");
                    buildingEditText.setText(c.getBuilding());
                    roomEditText.setText(c.getRoomNumber()+"");
                    brandEditText.setText(c.getBrand());
                    dateAddedEditText.setText(c.getDateAdded().getMonth() + "/" + c.getDateAdded().getDate() + "/" + c.getDateAdded().getYear());
                    modifiedByEditText.setText(c.getModifiedBy().toString());
                    OSEditText.setText(c.getOs());
                    lastScannedEditText.setText(c.getLastScanned().getMonth() + "/" + c.getLastScanned().getDate() + "/" + c.getLastScanned().getYear());
                }
                else if (itemType.equals("Printer")){
                    Printer p = dataSnapshot.getValue(Printer.class);
                    itemIDTextView.setText(p.getIdNumber());
                    buildingEditText.setText(p.getBuilding());
                    roomEditText.setText(p.getRoomNumber());
                    brandEditText.setText(p.getBrand());
                    dateAddedEditText.setText(p.getDateAdded().getMonth() + "/" + p.getDateAdded().getDate() + "/" + p.getDateAdded().getYear());
                    modifiedByEditText.setText(p.getModifiedBy().toString());
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
     * This method takes in a date as a string and converts it to a Date object
     *
     * @param s the date as a String formatted "mm/dd/yyyy"
     * @return a Date object that has been converted from a String
     */
    public Date stringToDate(String s){
        String[] a = s.split("/");
        int month = Integer.parseInt(a[0]);
        int day = Integer.parseInt(a[1]);
        int year = Integer.parseInt(a[2]);
        Date date = new Date(year, month, day);
        return date;
    }

    /**
     * OS and Last Scanned should only be visible when a Computer is selected
     */
    public void setComponentVisibility(){
        if(itemType.equals("Computer")){
            OSTextView.setVisibility(View.VISIBLE);
            OSEditText.setVisibility(View.VISIBLE);
            lastScannedTextView.setVisibility(View.VISIBLE);
            lastScannedEditText.setVisibility(View.VISIBLE);
        }
        else if(itemType.equals("Printer")){
            OSTextView.setVisibility(View.INVISIBLE);
            OSEditText.setVisibility(View.INVISIBLE);
            lastScannedTextView.setVisibility(View.INVISIBLE);
            lastScannedEditText.setVisibility(View.INVISIBLE);
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
        submitButton        = findViewById(R.id.submitModifyButton);
        cancelButton        = findViewById(R.id.cancelModifyButton);
        buildingEditText    = findViewById(R.id.buildingEditText);
        roomEditText        = findViewById(R.id.roomEditText);
        OSEditText          = findViewById(R.id.OSEditText);
        brandEditText       = findViewById(R.id.brandEditText);
        dateAddedEditText   = findViewById(R.id.dateAddedEditText);
        lastScannedEditText = findViewById(R.id.lastScannedEditText);
        modifiedByEditText  = findViewById(R.id.modifiedByEditText);
        OSTextView          = findViewById(R.id.OSTextView);
        lastScannedTextView = findViewById(R.id.lastScannedTextView);
        itemIDTextView      = findViewById(R.id.itemIDTextView);
    }
}
