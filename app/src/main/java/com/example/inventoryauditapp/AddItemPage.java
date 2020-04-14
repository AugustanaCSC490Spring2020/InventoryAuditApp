package com.example.inventoryauditapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class AddItemPage extends AppCompatActivity {

    //Initialize Buttons
    Button submit;
    Button cancel;

    //Initialize Spinner
    Spinner itemType;

    //Initialize EditText
    EditText buildingInput;
    EditText roomInput;
    EditText osInput;
    EditText brandInput;
    EditText lastScannedInput;
    EditText dateAddedInput;
    EditText modifiedByInput;

    //Initialize TextView
    TextView osTextView;
    TextView lastScannedTextView;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item_page);

        initUI();

        //Submit Button
        changeActivity(submit, InventorySearchResultsPage.class);

        //Cancel Button
        changeActivity(cancel, InventorySearchResultsPage.class);

        //sets a listener to see if the spinner is changed
        itemType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                handleItemSpinnerChange();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //left intentionally blank
            }
        });

    }

    //Initializes Components
    private void initUI() {
        submit              = findViewById(R.id.submitButton);
        cancel              = findViewById(R.id.cancelButton);
        itemType            = findViewById(R.id.itemTypeSpinner);
        buildingInput       = findViewById(R.id.buildingInput);
        roomInput           = findViewById(R.id.roomInput);
        osInput             = findViewById(R.id.osInput);
        brandInput          = findViewById(R.id.brandInput);
        lastScannedInput    = findViewById(R.id.lastScannedInput);
        dateAddedInput      = findViewById(R.id.dateAddedInput);
        modifiedByInput     = findViewById(R.id.modifiedInput);
        osTextView          = findViewById(R.id.OSText);
        lastScannedTextView = findViewById(R.id.lastScannedText);
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
     * OS and Last Scanned should only be visible when a Computer is selected
     */
    public void handleItemSpinnerChange(){
        String itemTypeText = itemType.getSelectedItem().toString();
        if(itemTypeText.equals("Computer")){
            osTextView.setVisibility(View.VISIBLE);
            osInput.setVisibility(View.VISIBLE);
            lastScannedTextView.setVisibility(View.VISIBLE);
            lastScannedInput.setVisibility(View.VISIBLE);
        }
        else if(itemTypeText.equals("Printer")){
            osTextView.setVisibility(View.INVISIBLE);
            osInput.setVisibility(View.INVISIBLE);
            lastScannedTextView.setVisibility(View.INVISIBLE);
            lastScannedInput.setVisibility(View.INVISIBLE);
        }
        else{
            //could just be an if - else statement but leaving room for more items being added
        }
    }
}
