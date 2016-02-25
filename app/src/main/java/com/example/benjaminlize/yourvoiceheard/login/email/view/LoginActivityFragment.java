package com.example.benjaminlize.yourvoiceheard.login.email.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.benjaminlize.yourvoiceheard.R;
import com.example.benjaminlize.yourvoiceheard.login.email.presenter.EmailLoginPresenter;
import com.example.benjaminlize.yourvoiceheard.login.email.presenter.EmailLoginPresenterImpl;
import com.example.benjaminlize.yourvoiceheard.petitions.view.PetitionsActivity;
import com.example.benjaminlize.yourvoiceheard.preferences.view.PreferencesActivity;
import com.example.benjaminlize.yourvoiceheard.user.User;
import com.example.benjaminlize.yourvoiceheard.utils.Constants;
import com.google.gson.Gson;

import timber.log.Timber;

/**
 * Created by Vinay Nikhil Pabba on 14-01-2016.
 * Fragment containing the fields on the Login Screen.
 * Check the credentials entered for logging in using the AuthenticateUser Class and logs in the user.
 * Can also open the Register page when clicked on the text
 */
public class LoginActivityFragment extends Fragment implements EmailLoginFragmentView {

    Button loginButton;
    EditText email;
    EditText password;
    ProgressDialog progressDialog;

    private EmailLoginPresenter emailLoginPresenter;

    private static final String TAG = LoginActivityFragment.class.getSimpleName ();

    View viewGroup;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public LoginActivityFragment(){

    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        viewGroup = inflater.inflate (R.layout.login_fragment_main, container, false);

        Timber.tag (TAG);

        sharedPreferences = getContext ().getSharedPreferences (Constants.MY_PREF, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit ();

        emailLoginPresenter = new EmailLoginPresenterImpl (this);

        email = (EditText) viewGroup.findViewById (R.id.emailText);
        password = (EditText) viewGroup.findViewById (R.id.passwordText);

        progressDialog = new ProgressDialog (getContext ());
        progressDialog.setMessage ("Logging you in");
        progressDialog.setProgressStyle (ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate (true);

        loginButton = (Button) viewGroup.findViewById (R.id.loginButton);
        loginButton.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                Timber.i (TAG, "Login button Clicked");
                emailLoginPresenter.authenticateCredentials (email.getText ().toString (), password.getText ().toString ());
            }
        });
        return viewGroup;

    }

    @Override
    public void emailError(){
        Toast.makeText (getContext (), "Email Error!", Toast.LENGTH_SHORT).show ();
    }

    @Override
    public void passwordError(){
        Toast.makeText (getContext (), "Password Error!", Toast.LENGTH_SHORT).show ();
    }

    @Override
    public void openMainPage () {
        Toast.makeText (getContext (), "Login Successful", Toast.LENGTH_SHORT).show ();
        startActivity (new Intent(getContext (), PetitionsActivity.class));
        ((Activity) getContext ()).finish ();
    }

    @Override
    public void openPreferencesPage () {
        Toast.makeText (getContext (), "Please select at least 1 category", Toast.LENGTH_SHORT).show ();
        Intent intent = new Intent(getContext (), PreferencesActivity.class);
        intent.putExtra ("caller",
                getContext ().getClass ().getSimpleName ());
        startActivity (intent);
        getActivity ().finish ();
    }

    @Override
    public void writeToSharedPreferences (User user) {
        Timber.i ("EMAIL VIEW", "UID = " + user.getUid ());
        Gson userGson = new Gson ();
        String userJson = userGson.toJson (user);
        editor.putString ("user", userJson);
        editor.commit ();
        Timber.i("Login UID", "The uid is - " + sharedPreferences.getString ("user", ""));
    }

    @Override
    public void hideProgressDialog () {
        progressDialog.hide ();
    }

    @Override
    public void showProgressDialog () {
        progressDialog.show ();
    }
}
