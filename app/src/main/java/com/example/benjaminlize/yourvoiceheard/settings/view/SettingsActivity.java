package com.example.benjaminlize.yourvoiceheard.settings.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.benjaminlize.yourvoiceheard.R;

/**
 * Created by Vinay Nikhil Pabba on 31-01-2016.
 */
public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.settings_main);

        Toolbar toolbar = (Toolbar) findViewById (R.id.settingsToolbar);
        setSupportActionBar (toolbar);

        getSupportActionBar ().setDisplayHomeAsUpEnabled (true);
    }
}
