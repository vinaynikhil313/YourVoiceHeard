package com.example.benjaminlize.yourvoiceheard.start.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.benjaminlize.yourvoiceheard.main.view.MainActivity;
import com.example.benjaminlize.yourvoiceheard.start.presenter.StartPagePreviousLoginChecker;
import com.example.benjaminlize.yourvoiceheard.start.presenter.StartPagePresenter;
import com.example.benjaminlize.yourvoiceheard.login.LoginActivity;
import com.example.benjaminlize.yourvoiceheard.R;
import com.example.benjaminlize.yourvoiceheard.user.User;
import com.example.benjaminlize.yourvoiceheard.utils.Constants;
import com.example.benjaminlize.yourvoiceheard.utils.Utilities;
import com.facebook.FacebookSdk;
import com.google.gson.Gson;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Vinay Nikhil Pabba on 16-01-2016.
 * Simple Start screen.
 * Waits for 5 seconds for the Facebook SDK to initialize.
 * Also validates the access token if already present and logs the user in directly without going
 * to the Login screen by using AuthenticateUser class
 */
public class StartPage extends Activity implements StartPageView{

    boolean openLoginPageFlag = true;
    ProgressBar progressBar;

    StartPagePresenter presenter;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    private static final String TAG = StartPage.class.getSimpleName ();

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);

        displayHashKey ();

        presenter = new StartPagePreviousLoginChecker (this);

        FacebookSdk.sdkInitialize (getApplicationContext (), presenter);

        sharedPreferences = getSharedPreferences (Constants.MY_PREF, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit ();

        checkPreviousPasswordLogin ();

        setContentView (R.layout.start_page);

        progressBar = (ProgressBar) findViewById (R.id.progressBar);

    }

    void checkPreviousPasswordLogin(){
        Log.i (TAG, "Checking previous password login");
        if(sharedPreferences.getString ("provider", "").equals ("password")){
            Log.i (TAG, "Provider is password");
            Gson gson = new Gson ();
            String userJson = sharedPreferences.getString ("user", "");
            User user = gson.fromJson (userJson, User.class);
            if(user == null)
                return;
            String token = user.getAccessToken (); //sharedPreferences.getString ("accessToken", "");
            Log.i(TAG + " Token ", token);
            if(!token.equals ("")) {
                presenter.loginWithPassword (token);
                openLoginPageFlag = false;
            }

        }
    }

    @Override
    public void writeToSharedPreferences (String provider, User user) {
        SharedPreferences.Editor editor = sharedPreferences.edit ();

        Log.i(TAG, provider + " " + user.getUid ());
        editor.putString ("provider", provider);
        Gson gson = new Gson ();
        String userJson = gson.toJson (user);
        editor.putString ("user", userJson);
        //editor.putString ("uid", uid);
        //editor.putString ("accessToken", accessToken);
        editor.commit ();
    }

    @Override
    public void showMessage (String message) {
        Toast.makeText (StartPage.this, message, Toast.LENGTH_SHORT).show ();
    }

    private void displayHashKey () {

        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.example.benjaminlize.yourvoiceheard",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray ());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }

    }


    @Override
    protected void onResume () {
        super.onResume ();

        new Handler ().postDelayed (new Runnable () {

            @Override
            public void run () {
                if(openLoginPageFlag) {
                    openLoginPage ();
                }
            }

        }, 5000);
    }

    @Override
    public void openLoginPage () {
        Log.i(TAG, "Opening Login Page");
        startActivity (new Intent(StartPage.this, LoginActivity.class));
        finish();

    }

    @Override
    public void openMainPage () {
        Log.i(TAG, "Opening Main Page");
        startActivity (new Intent(StartPage.this, MainActivity.class));
        finish();
    }

    @Override
    public void disableLoginPage () {
        openLoginPageFlag = false;
    }

}
