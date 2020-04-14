package com.example.inventoryauditapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AddItemPage extends AppCompatActivity {

    Button submit;
    Button cancel;
    EditText itemInput;
    EditText buildingInput;
    EditText roomInput;
    EditText osInput;
    EditText brandInput;
    EditText lastScannedInput;
    EditText dateAddedInput;
    EditText modifiedByInput;
    EditText typeInput;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item_page);

        initUI();

        //Submit Button
        changeActivity(submit, InventorySearchResultsPage.class);

        //Cancel Button
        changeActivity(cancel, InventorySearchResultsPage.class);

    }

    //Initializes Components
    private void initUI() {
        submit              = findViewById(R.id.submitButton);
        cancel              = findViewById(R.id.cancelButton);
        itemInput           = findViewById(R.id.itemInput);
        buildingInput       = findViewById(R.id.buildingInput);
        roomInput           = findViewById(R.id.roomInput);
        osInput             = findViewById(R.id.osInput);
        brandInput          = findViewById(R.id.brandInput);
        lastScannedInput    = findViewById(R.id.lastScannedInput);
        dateAddedInput      = findViewById(R.id.dateAddedInput);
        modifiedByInput     = findViewById(R.id.modifiedInput);
        typeInput           = findViewById(R.id.typeInput);
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
