package com.example.inventoryauditapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

public class InventorySearchPage extends AppCompatActivity {

    private Spinner itemSpinner;
    private Spinner buildingSpinner;
    private Spinner roomSpinner;

    private Button searchButton;
    private Button resetButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_search_page);

        initUI();

        changeActivity(searchButton, InventorySearchResultsPage.class);
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

    //Initializes Components
    private void initUI() {
        itemSpinner     = findViewById(R.id.itemSpinner);
        buildingSpinner = findViewById(R.id.buildingSpinner);
        roomSpinner     = findViewById(R.id.roomSpinner);

        searchButton    = findViewById(R.id.searchButton);
        resetButton     = findViewById(R.id.resetButton);
    }
}