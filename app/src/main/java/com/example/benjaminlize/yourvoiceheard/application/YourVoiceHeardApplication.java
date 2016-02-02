package com.example.benjaminlize.yourvoiceheard.application;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.benjaminlize.yourvoiceheard.utils.Constants;
import com.facebook.FacebookSdk;
import com.firebase.client.Firebase;

/**
 * Created by Vinay Nikhil Pabba on 12-01-2016.
 * Initializes Facebook and Firebase SDKs
 */
public class YourVoiceHeardApplication extends Application {

    @Override
    public void onCreate () {
        super.onCreate ();
        FacebookSdk.sdkInitialize (getApplicationContext ());
        Firebase.setAndroidContext (this);
        Firebase.getDefaultConfig().setPersistenceEnabled (true);
        Firebase scoresRef = new Firebase(Constants.FIREBASE_REF);
        scoresRef.keepSynced (true);
    }

}
