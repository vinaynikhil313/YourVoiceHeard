package com.example.benjaminlize.yourvoiceheard.main.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.benjaminlize.yourvoiceheard.settings.view.SettingsActivity;
import com.example.benjaminlize.yourvoiceheard.utils.AuthenticateUser;
import com.example.benjaminlize.yourvoiceheard.R;
import com.example.benjaminlize.yourvoiceheard.login.LoginActivity;
import com.example.benjaminlize.yourvoiceheard.utils.Constants;
import com.facebook.login.LoginManager;

/**
 * Main Activity for the petitions
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView (R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.logout :
                AuthenticateUser.unauth ();
                SharedPreferences sharedPreferences = getSharedPreferences (Constants.MY_PREF, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit ();
                editor.remove ("uid");
                editor.remove ("provider");
                editor.remove ("accessToken");
                editor.commit ();
                LoginManager.getInstance ().logOut ();
                Intent i = new Intent (MainActivity.this, LoginActivity.class);
                startActivity (i);
                finish ();
                break;

            case R.id.settings :
                startActivity (new Intent(MainActivity.this, SettingsActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

}
