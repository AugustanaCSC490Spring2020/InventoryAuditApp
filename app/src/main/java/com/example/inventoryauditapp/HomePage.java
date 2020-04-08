package com.example.inventoryauditapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
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
        changeActivity(inventoryButton, InventorySearchPage.class);

        //Audit Button
        changeActivity(auditButton, AuditSearchPage.class);

        //About Button
        changeActivity(aboutButton, AboutPage.class);
    }

    //Initializes Components
    private void initUI() {
        inventoryButton = findViewById(R.id.inventoryButton);
        auditButton     = findViewById(R.id.auditButton);
        aboutButton     = findViewById(R.id.aboutButton);
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
}
