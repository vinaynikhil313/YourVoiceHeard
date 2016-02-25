package com.example.benjaminlize.yourvoiceheard.application;

import android.app.Application;

import com.example.benjaminlize.yourvoiceheard.utils.Constants;
import com.facebook.FacebookSdk;
import com.firebase.client.Firebase;

import timber.log.BuildConfig;
import timber.log.Timber;

/**
 * Created by Vinay Nikhil Pabba on 12-01-2016.
 * Initializes Facebook, Firebase and Timber SDKs
 */
public class YourVoiceHeardApplication extends Application {

    @Override
    public void onCreate () {
        super.onCreate ();
        FacebookSdk.sdkInitialize (getApplicationContext ());
        Firebase.setAndroidContext (this);
        Firebase.getDefaultConfig().setPersistenceEnabled (true);
        Firebase firebase = new Firebase(Constants.FIREBASE_REF);
        firebase.keepSynced (true);

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

}
