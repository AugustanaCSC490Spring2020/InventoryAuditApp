package com.example.inventoryauditapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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
