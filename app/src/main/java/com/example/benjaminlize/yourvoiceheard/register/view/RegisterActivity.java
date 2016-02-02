package com.example.benjaminlize.yourvoiceheard.register.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.app.Fragment;
import android.widget.Toast;

import com.example.benjaminlize.yourvoiceheard.main.view.MainActivity;
import com.example.benjaminlize.yourvoiceheard.register.presenter.RegisterPresenter;
import com.example.benjaminlize.yourvoiceheard.register.presenter.RegisterPresenterImpl;
import com.example.benjaminlize.yourvoiceheard.R;
import com.example.benjaminlize.yourvoiceheard.user.User;
import com.example.benjaminlize.yourvoiceheard.utils.Constants;
import com.google.gson.Gson;

/**
 * Created by Vinay Nikhil Pabba on 15-01-2016.
 * Main Register Screen
 * Contains the DetailsFragment and a button to create user using the AuthenticateUser Class.
 */
public class RegisterActivity extends AppCompatActivity implements RegisterActivityView {

    Fragment details;

    RegisterPresenter presenter;

    EditText email;
    EditText password;

    ProgressDialog progressDialog;

    Button register;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.register_main);

        presenter = new RegisterPresenterImpl (this);

        details = getFragmentManager ().findFragmentById (R.id.registerDetailsFragment);

        email = (EditText) details.getView ().findViewById (R.id.emailText);
        password = (EditText) details.getView ().findViewById (R.id.passwordText);

        progressDialog = new ProgressDialog (this);
        progressDialog.setMessage ("Registering User");
        progressDialog.setProgressStyle (ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate (true);

        register = (Button) findViewById (R.id.registerButton);
        register.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                presenter.createUser (email.getText ().toString (), password.getText ().toString ());
            }
        });

    }

    @Override
    public void hideProgressBar () {
        progressDialog.hide ();
    }

    @Override
    public void showProgressBar () {
        progressDialog.show ();
    }

    @Override
    public void openHomePage () {
        Toast.makeText (RegisterActivity.this, "Welcome to YourVoiceHeard", Toast.LENGTH_SHORT).show ();
        startActivity (new Intent (RegisterActivity.this, MainActivity.class));
        finish ();
    }

    @Override
    public void registrationError (String message) {
        Toast.makeText (RegisterActivity.this, message, Toast.LENGTH_SHORT).show ();
    }

    @Override
    public void writeToSharedPreferences (User user) {
        SharedPreferences sharedPreferences = getSharedPreferences (Constants.MY_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit ();
        //editor.putString ("provider", "password");
        Log.i ("REGISTER VIEW", user.getUid ());
        Gson gson = new Gson ();
        String userJson = gson.toJson (user);
        editor.putString ("user", userJson);
        //editor.putString ("uid", uid);
        //editor.putString ("accessToken", token);
        editor.commit ();
        //Log.i ("Login UID", "The uid is - " + sharedPreferences.getString ("uid", ""));
    }
}
