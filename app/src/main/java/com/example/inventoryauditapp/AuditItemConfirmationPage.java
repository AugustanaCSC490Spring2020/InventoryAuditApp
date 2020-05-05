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
    private EditText commentBox;

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

    private void retrieveAndDisplayData(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(itemType).child(serialNum);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(itemType.equals("Computer")){
                    Computer c = dataSnapshot.getValue(Computer.class);
                    dataList.add("Serial Number: " + c.getSerialNumber());
                    dataList.add("Building     : " + c.getBuilding());
                    dataList.add("Room Number  : " + c.getRoomNumber());
                    dataList.add("OS           : "  + c.getOs());
                    dataList.add("Brand        : "  + c.getBrand());
                    dataList.add("Last Scanned : "  + c.getLastScanned());
                    dataList.add("Date Added   : "  + c.getDateAdded());
                    dataList.add("Modified By  : " + c.getModifiedBy());
                }
                else if (itemType.equals("Printer")){
                    Printer c = dataSnapshot.getValue(Printer.class);
                    dataList.add("Serial Number: " + c.getSerialNumber());
                    dataList.add("Building     : " + c.getBuilding());
                    dataList.add("Room Number  : " + c.getRoomNumber());
                    dataList.add("Brand        : "  + c.getBrand());
                    dataList.add("Date Added   : "  + c.getDateAdded());
                    dataList.add("Modified By  : " + c.getModifiedBy());
                    //dataList.add("Last Scanned : "  + c.getLastScanned()); Todo: printer should have a last scanned field
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

    private void initUI(){
        confirmButton = findViewById(R.id.confirmDataButton);
        cancelButton  = findViewById(R.id.confirmAuditCancelButton);
        itemDisplay   = findViewById(R.id.dataDisplayListView);
        commentBox    = findViewById(R.id.auditConfirmCommentsSection);
    }
}