package com.example.inventoryauditapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AuditSearchResultsPage extends AppCompatActivity {

    private Button submitButton;
    private Button cancelButton;

    private ListView resultsListView;

    private ArrayAdapter<String> resultAdapter;

    private ArrayList<String> items;

    private String building;
    private String room;
    private String item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audit_search_results_page);

        initUI();

        //Submit Button
        //TODO: changeActivity(submit, ?.class);

        //Cancel Button
        changeActivity(cancelButton, AuditSearchPage.class);

        building = getIntent().getStringExtra("building");
        room     = getIntent().getStringExtra("room");
        item     = getIntent().getStringExtra("item");

        items = new ArrayList<>();

        retrieveAndDisplayItems();
    }

    private void initUI() {
        submitButton     = findViewById(R.id.submitButton);
        cancelButton     = findViewById(R.id.cancelButton);
        resultsListView  = findViewById(R.id.resultsListView);

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
     * Method used to get all valid computer IDs of the current item type (will work for printers sorta for now)
     * and calls getComputerInfo based on the current computerID
     */
    private void retrieveAndDisplayItems() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(item);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Firebase
                for(DataSnapshot d: dataSnapshot.getChildren()) {
                    //Items are Computers
                    if(dataSnapshot.getKey() == "Computer"){
                        Computer c = d.getValue(Computer.class);
                        if(c.getBuilding().equalsIgnoreCase(building) && String.valueOf(c.getRoomNumber()).equalsIgnoreCase(room)) {
                            items.add(c.toString());
                        }
                        //Items are Printers
                    } else if(dataSnapshot.getKey() == "Printer"){
                        Printer c = d.getValue(Printer.class);
                        if(c.getBuilding().equalsIgnoreCase(building) && String.valueOf(c.getRoomNumber()).equalsIgnoreCase(room)) {
                            items.add(c.toString());
                        }
                    }
                }
                //ListView Setup
                resultAdapter = new ArrayAdapter<>(AuditSearchResultsPage.this, android.R.layout.simple_list_item_1, items);
                resultsListView.setAdapter(resultAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
