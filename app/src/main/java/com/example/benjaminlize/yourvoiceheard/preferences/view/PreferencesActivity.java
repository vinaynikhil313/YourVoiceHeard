package com.example.benjaminlize.yourvoiceheard.preferences.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.benjaminlize.yourvoiceheard.R;
import com.example.benjaminlize.yourvoiceheard.petitions.view.PetitionsActivity;
import com.example.benjaminlize.yourvoiceheard.user.User;
import com.example.benjaminlize.yourvoiceheard.utils.Constants;
import com.google.gson.Gson;

/**
 * Created by Vinay Nikhil Pabba on 31-01-2016.
 */
public class PreferencesActivity extends AppCompatActivity {

    TextView selectText;
    Button proceedButton;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.preferences_main);

        Toolbar toolbar = (Toolbar) findViewById (R.id.my_toolbar);
        setSupportActionBar (toolbar);
        Log.i ("PreferenceActivity", "Checkpoint1");
        Intent intent = getIntent ();
        String caller = intent.getStringExtra ("caller");
        Log.i ("PreferenceActivity", "Caller is " + caller);

        Log.i ("PreferenceActivity", "Checkpoint2");

        selectText = (TextView) findViewById (R.id.chooseText);
        proceedButton = (Button) findViewById (R.id.proceedButton);

        proceedButton.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                if (getUser ().getPreferences ().size () > 0)
                    openMainPage ();
                else
                    showMessage ("Please select at least 1 preference");
            }
        });

        if (caller.equals ("SettingsActivity")) {
            getSupportActionBar ().setDisplayHomeAsUpEnabled (true);
            selectText.setVisibility (View.GONE);
            proceedButton.setVisibility (View.GONE);
        }


    }

    private void openMainPage () {
        startActivity (new Intent (PreferencesActivity.this, PetitionsActivity.class));
        finish ();
    }

    private User getUser () {
        SharedPreferences sharedPreferences = getSharedPreferences (Constants.MY_PREF, Context.MODE_PRIVATE);
        Gson gson = new Gson ();
        String userJson = sharedPreferences.getString ("user", "");
        User user = gson.fromJson (userJson, User.class);
        return user;
    }

    private void showMessage (String message) {
        Toast.makeText (PreferencesActivity.this, message, Toast.LENGTH_LONG).show ();
    }

}
