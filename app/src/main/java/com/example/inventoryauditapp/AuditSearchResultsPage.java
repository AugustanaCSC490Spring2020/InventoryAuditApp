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
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AuditSearchResultsPage extends AppCompatActivity {

    private Button submitButton;
    private Button cancelButton;

    //list views
    private ListView resultsListView;
    private ListView confirmedResultsListView;

    private ArrayAdapter<String> resultAdapter;
    private ArrayAdapter<String> confirmedResultAdapter;

    private ArrayList<String> resultsItems;
    private ArrayList<String> confirmedItems;

    private String building;
    private String room;
    private String item;

    private ArrayList<String> serialNums;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audit_search_results_page);

        initUI();

        serialNums = new ArrayList<>();

        //Submit Button
        //TODO: changeActivity(submit, ?.class);

        //Cancel Button
        changeActivity(cancelButton, AuditSearchPage.class);

        building = getIntent().getStringExtra("building");
        room     = getIntent().getStringExtra("room");
        item     = getIntent().getStringExtra("item");  //Todo: no item specific.  Need abstract class

        resultsItems = getIntent().getStringArrayListExtra("resultsList");
        confirmedItems = getIntent().getStringArrayListExtra("confirmedResultsList");

        handleListViews();

        resultsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(AuditSearchResultsPage.this, AuditItemConfirmationPage.class);
                i.putExtra("serialNum", serialNums.get(position));
                i.putExtra("itemType", item);
                i.putExtra("room", room);
                i.putExtra("building", building);
                i.putExtra("resultsList", resultsItems);
                i.putExtra("confirmedResultsList", confirmedItems);
                i.putExtra("position", position);
                startActivity(i);
            }
        });
    }

    private void initUI() {
        submitButton             = findViewById(R.id.submitButton);
        cancelButton             = findViewById(R.id.cancelButton);
        resultsListView          = findViewById(R.id.resultsListView);
        confirmedResultsListView = findViewById(R.id.confirmedResultsListView);
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
     * updates the values within the list views.  This method should only be called once when the
     *  activity is loaded.
     */
    private void handleListViews(){
        if(resultsItems.isEmpty() && confirmedItems.isEmpty()){ //initial case
            retrieveItems(); //initialize arraylists and displayed the information
        }
        else{
            resultAdapter = new ArrayAdapter<>(AuditSearchResultsPage.this, android.R.layout.simple_list_item_1, resultsItems);
            resultsListView.setAdapter(resultAdapter);
            resetSerialNums();

            confirmedResultAdapter = new ArrayAdapter<>(AuditSearchResultsPage.this, android.R.layout.simple_list_item_1, confirmedItems);
            confirmedResultsListView.setAdapter(confirmedResultAdapter);
        }
    }

    /**
     * every time we return from the Confirm page we must reset the Serial Num references so that
     *  when a new item is picked, it is pointing to the correct reference
     */
    private void resetSerialNums(){
        serialNums.clear();
        for (String printOut : resultsItems){
            String [] components = printOut.split("|");
            serialNums.add(components[0].trim());
        }
    }

    /**
     * Method used to get all valid computer IDs of the current item type (will work for printers sorta for now)
     * and calls getComputerInfo based on the current computerID
     */
    private void retrieveItems() {
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
                            resultsItems.add(c.toString());
                            serialNums.add(c.getSerialNumber());
                        }
                    }
                    if(dataSnapshot.getKey() == "Printer"){
                        Printer c = d.getValue(Printer.class);
                        if(c.getBuilding().equalsIgnoreCase(building) && String.valueOf(c.getRoomNumber()).equalsIgnoreCase(room)) {
                            resultsItems.add(c.toString());
                            serialNums.add(c.getSerialNumber());
                        }
                    }
                }
                resultAdapter = new ArrayAdapter<>(AuditSearchResultsPage.this, android.R.layout.simple_list_item_1, resultsItems);
                resultsListView.setAdapter(resultAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
