package com.example.inventoryauditapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class HomePage extends AppCompatActivity {

    private Button inventoryButton;
    private Button auditButton;
    private Button aboutButton;
    private GoogleSignInClient client;
    int RC_SIGN_IN = 0;
    private Button signOutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

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

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        client = GoogleSignIn.getClient(this, gso);

        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });
    }

    //Initializes Components
    private void initUI() {
        inventoryButton = findViewById(R.id.inventoryButton);
        auditButton     = findViewById(R.id.auditButton);
        aboutButton     = findViewById(R.id.aboutButton);
        signOutButton   = findViewById(R.id.signOutButton);
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

    private void signOut() {
        client.signOut().addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(HomePage.this, "Signed out Successfully!", Toast.LENGTH_SHORT).show();
                //finish();
                startActivity(new Intent(getBaseContext(), LoginStartPage.class));
            }
        });
    }


}
