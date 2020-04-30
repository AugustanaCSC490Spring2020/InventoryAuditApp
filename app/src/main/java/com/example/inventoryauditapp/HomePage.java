package com.example.inventoryauditapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

        //Inventory Button
        inventoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), SearchPage.class);
                intent.putExtra(SearchPage.EXTRA_MODE, SearchPage.MODE_INVENTORY);
                startActivity(intent);
            }
        });

        //Audit Button
        auditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), SearchPage.class);
                intent.putExtra(SearchPage.EXTRA_MODE,SearchPage.MODE_AUDIT);
                startActivity(intent);

            }
        });

        //About Button
        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), AboutPage.class));
            }
        });
    }

    //Initializes Components
    private void initUI() {
        inventoryButton = findViewById(R.id.inventoryButton);
        auditButton     = findViewById(R.id.auditButton);
        aboutButton     = findViewById(R.id.aboutButton);
    }
}
