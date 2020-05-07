package com.example.inventoryauditapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AuditItemConfirmationPage extends AppCompatActivity {

    //widgets
    private Button confirmButton;
    private Button cancelButton;
    private ListView itemDisplay;
    private EditText commentBox; //ToDo: implement comment box in email

    //to display data in the ListView
    private ArrayList<String> dataList;
    private ArrayAdapter<String> dataAdapter;

    //data to be passed back to previous activity
    private ArrayList<String> resultsItems;
    private ArrayList<String> confirmedItems;
    private String serialNum;
    private String building;
    private String roomNum;
    private String itemType;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audit_item_confirmation_page);

        initUI();

        dataList = new ArrayList<>();

        resultsItems   = getIntent().getStringArrayListExtra("resultsList");
        confirmedItems = getIntent().getStringArrayListExtra("confirmedResultsList");
        serialNum      = getIntent().getStringExtra("serialNum");
        itemType       = getIntent().getStringExtra("itemType");
        building       = getIntent().getStringExtra("building");
        roomNum        = getIntent().getStringExtra("room");
        position       = getIntent().getExtras().getInt("position");

        retrieveAndDisplayData();

        //cancel button.  Passes all data back to previous screen
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AuditItemConfirmationPage.this, AuditSearchResultsPage.class);
                i.putExtra("building", building);
                i.putExtra("room", roomNum);
                i.putExtra("item", itemType);
                i.putExtra("resultsList", resultsItems);
                i.putExtra("confirmedResultsList", confirmedItems);
                startActivity(i);
            }
        });

        //confirm button.  moves referenced item from results list to confirmed list
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String confirmedItem = resultsItems.get(position);
                resultsItems.remove(position);
                confirmedItems.add(confirmedItem);
                Intent i = new Intent(AuditItemConfirmationPage.this, AuditSearchResultsPage.class);
                i.putExtra("building", building);
                i.putExtra("room", roomNum);
                i.putExtra("item", itemType);
                i.putExtra("resultsList", resultsItems);
                i.putExtra("confirmedResultsList", confirmedItems);
                startActivity(i);
            }
        });

    }

    /**
     * this method grabs all the data on the referenced object and displays it so the user can
     *  confirm that the data is correct.
     */
    private void retrieveAndDisplayData(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(itemType).child(serialNum);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(itemType.equals("Computer")){
                    Computer c = dataSnapshot.getValue(Computer.class);
                    setSharedData(c);
                    dataList.add("OS           : "  + c.getOs());
                    dataList.add("Last Scanned : "  + c.getLastScanned());
                }
                else if (itemType.equals("Printer")){
                    Printer c = dataSnapshot.getValue(Printer.class);
                    setSharedData(c);
                    dataList.add("Type:        : " + c.getType());
                }
                else{
                    //could just be an if - else statement but leaving room for more items being added
                }
                dataAdapter = new ArrayAdapter<>(AuditItemConfirmationPage.this, android.R.layout.simple_list_item_1, dataList);
                itemDisplay.setAdapter(dataAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    /**
     * sets the data for the the item being referenced.
     * @param c - an item object to be referenced
     */
    private void setSharedData(Item c){
        dataList.add("Serial Number: " + c.getSerialNumber());
        dataList.add("Building     : " + c.getBuilding());
        dataList.add("Room Number  : " + c.getRoomNumber());
        dataList.add("Brand        : "  + c.getBrand());
        dataList.add("Date Added   : "  + c.getDateAdded());
        dataList.add("Modified By  : " + c.getModifiedBy());
    }

    private void initUI(){
        confirmButton = findViewById(R.id.confirmDataButton);
        cancelButton  = findViewById(R.id.confirmAuditCancelButton);
        itemDisplay   = findViewById(R.id.dataDisplayListView);
        commentBox    = findViewById(R.id.auditConfirmCommentsSection);
    }
}
