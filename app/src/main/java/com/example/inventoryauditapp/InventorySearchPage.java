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
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

public class InventorySearchPage extends AppCompatActivity implements View.OnClickListener {

    private Spinner itemSpinner;
    private Spinner buildingSpinner;
    private Spinner roomSpinner;

    private Button searchButton;
    private Button resetButton;

    // ArrayLists for the spinner XML objects
    private ArrayList<String> items = new ArrayList<String>(Arrays.asList("Computer", "Printer"));
    private ArrayList<String> buildings = new ArrayList<>();
    private ArrayList<String> rooms = new ArrayList<>();

    // Strings to be passed to the results screen
    private String currentItem = "";
    private String currentBuilding = "";
    private String currentRoom = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_search_page);

        initUI();
        changeActivity(searchButton, InventorySearchResultsPage.class);
        handleItemSpinner();
        resetButton.setOnClickListener(this);

        /*DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Computer");
        Computer c = new Computer("O2D489", "Olin", 200, "Windows 10", "Dell",  Calendar.getInstance().getTime().toString(), Calendar.getInstance().getTime().toString(), new User("dylanhart16"));
        ref.child(c.getSerialNumber()).setValue(c);

        Computer d = new Computer("O2L654", "Olin", 200, "Windows 10 Pro", "Lenovo",  Calendar.getInstance().getTime().toString(), Calendar.getInstance().getTime().toString(), new User("kevinphoenix16"));
        ref.child(d.getSerialNumber()).setValue(d);

        Computer e = new Computer("O1D865", "Old Main", 100, "Windows 10", "Dell",  Calendar.getInstance().getTime().toString(), Calendar.getInstance().getTime().toString(), new User("maxmccomb16"));
        ref.child(e.getSerialNumber()).setValue(e);

        Computer f = new Computer("O1H893", "Old Main", 101, "Windows 10", "HP",  Calendar.getInstance().getTime().toString(), Calendar.getInstance().getTime().toString(), new User("scottdaluga16"));
        ref.child(f.getSerialNumber()).setValue(f);

        Computer g = new Computer("E2H168", "Evald", 200, "Windows 10", "HP",  Calendar.getInstance().getTime().toString(), Calendar.getInstance().getTime().toString(), new User("dylanhart16"));
        ref.child(g.getSerialNumber()).setValue(g);

        DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference("Printer");
        Printer p = new Printer("O2HL486", "Olin", 200, "Laser", "HP", Calendar.getInstance().getTime().toString(), new User("dylanhart16"));
        ref2.child(p.getSerialNumber()).setValue(p);

        Printer q = new Printer("O2HI354", "Olin", 200, "Ink", "HP", Calendar.getInstance().getTime().toString(), new User("kevinphoenix16"));
        ref2.child(q.getSerialNumber()).setValue(q);

        Printer r = new Printer("O1HL843", "Old Main", 100, "Laser", "HP", Calendar.getInstance().getTime().toString(), new User("maxmccomb16"));
        ref2.child(r.getSerialNumber()).setValue(r);

        Printer s = new Printer("O1HL287", "Old Main", 101, "Laser", "HP", Calendar.getInstance().getTime().toString(), new User("scottdaluga16"));
        ref2.child(s.getSerialNumber()).setValue(s);

        Printer t = new Printer("E2IL287", "Evald", 200, "Ink", "HP", Calendar.getInstance().getTime().toString(), new User("scottdaluga16"));
        ref2.child(t.getSerialNumber()).setValue(t);
        */


    }

    /**
     * @param button - button that is clicked on Main Page
     * @param page   - the new activity to change to
     */
    private void changeActivity(Button button, final Class<? extends Activity> page) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), page);
                intent.putExtra("item", currentItem);
                intent.putExtra("building", currentBuilding);
                intent.putExtra("room", currentRoom);
                startActivity(intent);
            }
        });
    }

    /**
     * Method used to reset spinners back to the default value of 0.
     */
    private void resetSpinners() {
        itemSpinner.setSelection(0);
        buildingSpinner.setSelection(0);
        roomSpinner.setSelection(0);
    }

    //Initializes Components
    private void initUI() {
        itemSpinner = findViewById(R.id.itemSpinner);
        buildingSpinner = findViewById(R.id.buildingSpinner);
        roomSpinner = findViewById(R.id.roomSpinner);

        searchButton = findViewById(R.id.searchButton);
        resetButton = findViewById(R.id.resetButton);
    }


    /**
     * Method used to handle selection of item. Will reset the lists and values in the buildings and
     * rooms list if a different item is selected. Also calls the getBuildings method to start gathering
     * information for the building spinner. Also will assign the value of currentItem for passing
     * to the result activity.
     */
    private void handleItemSpinner() {
        ArrayAdapter itemAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, items);
        itemSpinner.setAdapter(itemAdapter);
        itemSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentItem = items.get(position);
                buildings.clear();
                rooms.clear();
                getBuildings();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    /**
     * Method used to handle selection of building. Will reset the list for rooms if a different building
     * is selected and get the respective rooms. Also calls the getRooms method to start gathering
     * information for the room spinner. Also will assign the value of currentBuilding for passing to the
     * result activity.
     */
    private void handleBuildingSpinner() {
        ArrayAdapter buildingAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, buildings);
        buildingSpinner.setAdapter(buildingAdapter);
        buildingSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentBuilding = buildings.get(position);
                rooms.clear();
                getRooms();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    /**
     * Method used to handle selection of room. This method doesn't reset any other spinner lists as they are not
     * dependent on this value. Also will assign the value of currentRoom for passing to the result activity.
     */
    private void handleRoomSpinner() {
        ArrayAdapter roomAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, rooms);
        roomSpinner.setAdapter(roomAdapter);
        roomSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentRoom = rooms.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    /**
     * Method used to get all applicable buildings in the Firebase Database and adds
     * the data to a list called "buildings". Also calls handleBuildingSpinner once the
     * list has all buildings.
     */
    private void getBuildings() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(currentItem);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Set<String> s = new HashSet<>();
                buildings.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Computer c = ds.getValue(Computer.class);
                    if(!s.contains(c.getBuilding())) {
                        s.add(c.getBuilding());
                        buildings.add(c.getBuilding());
                    }
                }
                handleBuildingSpinner();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    /**
     * Method used to get all applicable rooms in the Firebase Database based on the building selection
     * and adds the data to a list called "rooms". Also calls handleRoomSpinner once the list has all
     * rooms.
     */
    private void getRooms() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(currentItem);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Set<Integer> s = new HashSet<>();
                rooms.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Computer c = ds.getValue(Computer.class);
                    if(c.getBuilding().equalsIgnoreCase(currentBuilding) && !s.contains(c.getRoomNumber())) {
                        s.add(c.getRoomNumber());
                        rooms.add(String.valueOf(c.getRoomNumber()));
                    }
                }
                handleRoomSpinner();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    /**
     * Supplying an onClick method for the reset button in order to call resetSpinners().
     * @param v - Reference to the XML object that is being used.
     */
    @Override
    public void onClick(View v) {
        if(v == resetButton) {
            resetSpinners();
        }
    }
}
