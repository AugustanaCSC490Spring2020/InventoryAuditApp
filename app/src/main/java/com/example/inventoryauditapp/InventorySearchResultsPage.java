package com.example.inventoryauditapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class InventorySearchResultsPage extends AppCompatActivity {

    private Button addItemButton;
    private Button modifyItemButton;

    //todo : implement a checkbox widget

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_search_results_page);

        initIU();

        //Add Item Button
        //changeActivity(addItemButton, );  will implement when Scott creates Add Item Page

        //Modify Item Button
        changeActivity(modifyItemButton,ModifyItemPage.class);
    }

    public void initIU(){
        addItemButton    = findViewById(R.id.addItemButton);
        modifyItemButton = findViewById(R.id.modifyItemButton);
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
