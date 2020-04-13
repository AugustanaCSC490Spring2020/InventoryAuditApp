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

public class ModifyItemPage extends AppCompatActivity {

    //buttons at the bottom of the screen
    private Button submitButton;
    private Button cancelButton;

    //Spinner that lets the use select the item type ie Computer or Printer
    private Spinner itemTypeSpinner;

    //all text fields that the user can modify
    private EditText buildingEditText;
    private EditText roomEditText;
    private EditText OSEditText;
    private EditText brandEditText;
    private EditText lastScannedEditText;
    private EditText dateAddedEditText;
    private EditText modifiedByEditText;

    //text labels of OS and lastScanned
    private TextView OSTextView;
    private TextView lastScannedTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_item_page);

        initUI();
        initializeTextFields();


        changeActivity(cancelButton, InventorySearchResultsPage.class);

        //when a new item is selected on the spinner
        itemTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                handleItemSpinnerChange();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //left intentionally blank
            }
        });

        //I know this is redundant now but will later need to call external method to store data
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDatebase();
                Intent intent = new Intent(getBaseContext(), InventorySearchResultsPage.class);
                startActivity(intent);
            }
        });

    }

    /**
     * Todo update database with the new information in the text fields entered by the user
     */
    public void updateDatebase(){

    }

    /**
     * Todo initialize text fields to the current data in the database
     */
    public void initializeTextFields(){

    }

    /**
     * OS and Last Scanned should only be visible when a Computer is selected
     */
    public void handleItemSpinnerChange(){
        String itemType = itemTypeSpinner.getSelectedItem().toString();
        if(itemType.equals("Computer")){
            OSTextView.setVisibility(View.VISIBLE);
            OSEditText.setVisibility(View.VISIBLE);
            lastScannedTextView.setVisibility(View.VISIBLE);
            lastScannedEditText.setVisibility(View.VISIBLE);
        }
        else if(itemType.equals("Printer")){
            OSTextView.setVisibility(View.INVISIBLE);
            OSEditText.setVisibility(View.INVISIBLE);
            lastScannedTextView.setVisibility(View.INVISIBLE);
            lastScannedEditText.setVisibility(View.INVISIBLE);
        }
        else{
            //could just be an if - else statement but leaving room for more items being added
        }
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


    public void initUI(){
        submitButton        = findViewById(R.id.submitModifyButton);
        cancelButton        = findViewById(R.id.cancelModifyButton);
        itemTypeSpinner     = findViewById(R.id.itemTypeSpinner);
        buildingEditText    = findViewById(R.id.buildingEditText);
        roomEditText        = findViewById(R.id.roomEditText);
        OSEditText          = findViewById(R.id.OSEditText);
        brandEditText       = findViewById(R.id.brandEditText);
        lastScannedEditText = findViewById(R.id.lastScannedEditText);
        modifiedByEditText  = findViewById(R.id.modifiedByEditText);
        OSTextView          = findViewById(R.id.OSTextView);
        lastScannedTextView = findViewById(R.id.lastScannedTextView);
    }
}
