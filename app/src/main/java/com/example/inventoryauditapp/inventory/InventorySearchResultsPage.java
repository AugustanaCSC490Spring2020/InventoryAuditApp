package com.example.inventoryauditapp.inventory;

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
import android.widget.TextView;

import com.example.inventoryauditapp.HomePage;
import com.example.inventoryauditapp.classes.Computer;
import com.example.inventoryauditapp.classes.Printer;
import com.example.inventoryauditapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class InventorySearchResultsPage extends AppCompatActivity {

    private Button addItemButton;
    private Button homePageButton;

    private ListView resultsListView;

    private TextView title;

    private ArrayAdapter<String> resultAdapter;

    private ArrayList<String> items;

    ArrayList<String> serialNums;

    private String building;
    private String room;
    private String item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_search_results_page);

        initIU();

        //Add Item Button
        changeActivityWithButton(addItemButton, AddItemPage.class);
        changeActivityWithButton(homePageButton, HomePage.class);

        building = getIntent().getStringExtra("building");
        room = getIntent().getStringExtra("room");
        item = getIntent().getStringExtra("item");

        title.setText(item + " Results");

        items = new ArrayList<>();
        serialNums = new ArrayList<>();
        retrieveAndDisplayItems();

        //ListView
        resultsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getBaseContext(), ModifyItemPage.class);
                i.putExtra("itemType", item);
                i.putExtra("serialNum", serialNums.get(position));
                startActivity(i);
            }
        });
    }

    public void initIU(){
        addItemButton    = findViewById(R.id.addItemButton);
        homePageButton   = findViewById(R.id.returnToHomeButton);
        resultsListView  = findViewById(R.id.resultsListView);
        title            = findViewById(R.id.inventorySearchResults);
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
                intent.putExtra("item", item);
                intent.putExtra("building", building);
                intent.putExtra("room", room);
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
                items.clear();
                for(DataSnapshot d: dataSnapshot.getChildren()) {
                    //Items are Computers
                    if(dataSnapshot.getKey() == "Computer"){
                        Computer c = d.getValue(Computer.class);
                        if(c.getBuilding().equalsIgnoreCase(building) && String.valueOf(c.getRoomNumber()).equalsIgnoreCase(room)) {
                            items.add(c.toString());
                            serialNums.add(c.getSerialNumber());
                        }
                    //Items are Printers
                    } else if(dataSnapshot.getKey() == "Printer"){
                        Printer c = d.getValue(Printer.class);
                        if(c.getBuilding().equalsIgnoreCase(building) && String.valueOf(c.getRoomNumber()).equalsIgnoreCase(room)) {
                            items.add(c.toString());
                            serialNums.add(c.getSerialNumber());
                        }
                    }
                }
                //ListView Setup
                resultAdapter = new ArrayAdapter<>(InventorySearchResultsPage.this, android.R.layout.simple_list_item_1, items);
                resultsListView.setAdapter(resultAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
