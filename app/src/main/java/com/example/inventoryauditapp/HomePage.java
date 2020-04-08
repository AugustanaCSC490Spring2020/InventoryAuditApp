package com.example.inventoryauditapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

public class HomePage extends AppCompatActivity {

    private Button inventoryButton;
    private Button auditButton;
    private Button aboutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();
    }

    //Initializes Components
    private void initUI() {
        inventoryButton = findViewById(R.id.inventoryButton);
        auditButton     = findViewById(R.id.auditButton);
        aboutButton     = findViewById(R.id.aboutButton);
    }
}
