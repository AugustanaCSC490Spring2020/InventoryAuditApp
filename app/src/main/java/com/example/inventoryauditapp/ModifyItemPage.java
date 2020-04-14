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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
                Intent intent = new Intent(getBaseContext(), InventorySearchResultsPage.class);
                startActivity(intent);
            }
        });

    }

    /**
     * Todo update database with the new information in the text fields entered by the user
     */
    public void updateDatebase(){

    }

    /**
     * Todo initialize text fields to the current data in the database
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
                    dateAddedEditText.setText(c.getDateAdded().toString());
                    modifiedByEditText.setText(c.getModifiedBy().toString());
                    OSEditText.setText(c.getOs());
                    lastScannedEditText.setText(c.getLastScanned().toString());
                }
                else if (itemType.equals("Printer")){
                    Printer p = dataSnapshot.getValue(Printer.class);
                    itemIDTextView.setText(p.getIdNumber());
                    buildingEditText.setText(p.getBuilding());
                    roomEditText.setText(p.getRoomNumber());
                    brandEditText.setText(p.getBrand());
                    dateAddedEditText.setText(p.getDateAdded().toString());
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
