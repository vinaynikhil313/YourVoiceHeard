package com.example.benjaminlize.yourvoiceheard.start;

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

import com.example.benjaminlize.yourvoiceheard.utils.AuthenticateUser;
import com.example.benjaminlize.yourvoiceheard.login.LoginActivity;
import com.example.benjaminlize.yourvoiceheard.R;
import com.example.benjaminlize.yourvoiceheard.utils.Constants;
import com.facebook.AccessToken;
import com.facebook.FacebookSdk;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Vinay Nikhil Pabba on 16-01-2016.
 * Simple Start screen.
 * Waits for 5 seconds for the Facebook SDK to initialize.
 * Also validates the access token if already present and logs the user in directly without going
 * to the Login screen by using AuthenticateUser class
 */
public class StartPage extends Activity implements FacebookSdk.InitializeCallback{
    AccessTokenTracker accessTokenTracker = null;
    boolean openLoginPageFlag = true;
    boolean authenticateUser = true;
    ProgressBar progressBar;

    private static final String TAG = StartPage.class.getSimpleName ();

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);

        displayHashKey ();

        FacebookSdk.sdkInitialize (getApplicationContext (), this);
        accessTokenTracker = new AccessTokenTracker ();
        accessTokenTracker.startTracking ();
        //AuthenticateUser.unauth ();
        //LoginManager.getInstance ().logOut ();

        SharedPreferences sharedPreferences = getSharedPreferences (Constants.MY_PREF, Context.MODE_PRIVATE);
        if(sharedPreferences.getString ("provider", "").equals ("password")){
            String token = sharedPreferences.getString ("accessToken", "");
            Log.i(TAG + " Token ", token);
            if(!token.equals ("")) {
                AuthenticateUser.authWithToken (token, StartPage.this);
                openLoginPageFlag = false;
            }

        }

        setContentView (R.layout.start_page);

        progressBar = (ProgressBar) findViewById (R.id.progressBar);

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
                    if(accessTokenTracker != null && accessTokenTracker.isTracking ())
                        accessTokenTracker.stopTracking ();
                    authenticateUser = false;
                    openLoginPage ();
                }
            }

        }, 5000);
    }

    @Override
    protected void onDestroy () {
        super.onDestroy ();
        if(accessTokenTracker != null && accessTokenTracker.isTracking ())
            accessTokenTracker.stopTracking ();
    }

    @Override
    public void onInitialized () {
        if(AccessToken.getCurrentAccessToken () == null){
            Log.i(TAG, "SDK Initialized but AccessToken is null");
            if(accessTokenTracker == null)
                accessTokenTracker = new AccessTokenTracker ();
            if(!accessTokenTracker.isTracking ())
                accessTokenTracker.startTracking ();
        }
        else{
            Log.i(TAG, "SDK Initialized and AccessToken is not null");
            openLoginPageFlag = false;
            if(authenticateUser) {
                authenticateUser = false;
                AuthenticateUser.authWithFacebook (AccessToken.getCurrentAccessToken ().getToken (), StartPage.this);
                /*if(accessTokenTracker != null && accessTokenTracker.isTracking ())
                    accessTokenTracker.stopTracking ();*/
            }
        }
    }

    private void openLoginPage () {

        Log.i(TAG, "Opening Login Page");

        Intent i = new Intent(StartPage.this, LoginActivity.class);
        startActivity (i);
        finish();

    }

    private class AccessTokenTracker extends com.facebook.AccessTokenTracker{

        @Override
        protected void onCurrentAccessTokenChanged (AccessToken oldAccessToken, AccessToken currentAccessToken) {
            AccessToken accessToken = AccessToken.getCurrentAccessToken ();
            if(accessToken != null){
                Log.i(TAG, "inside TOKEN GRANTED!!!");
                openLoginPageFlag = false;
                if(authenticateUser) {
                    AuthenticateUser.authWithFacebook (accessToken.getToken (), StartPage.this);
                    authenticateUser = false;
                }
            }
        }
    }
}
