package com.example.benjaminlize.yourvoiceheard.preferences.interactor;

import android.util.Log;

import com.example.benjaminlize.yourvoiceheard.preferences.presenter.OnPreferencesListGeneratedListener;
import com.example.benjaminlize.yourvoiceheard.utils.Constants;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Vinay Nikhil Pabba on 31-01-2016.
 */
public class PreferencesInteractorImpl implements PreferencesInteractor, ValueEventListener {

    Firebase firebase = new Firebase(Constants.FIREBASE_REF);

    OnPreferencesListGeneratedListener listener;

    Map<String, String> categories = new HashMap<> ();

    @Override
    public void generateList (OnPreferencesListGeneratedListener listener) {
        this.listener = listener;

        firebase.child ("categories").addListenerForSingleValueEvent (this);
    }

    @Override
    public void onCancelled (FirebaseError firebaseError) {

    }

    @Override
    public void onDataChange (DataSnapshot dataSnapshot) {

        for(DataSnapshot child : dataSnapshot.getChildren ()){

            categories.put (child.getKey (), (String)child.getValue ());
        }
        Log.i ("Preferences Interactor", categories.toString ());
        listener.onListGenerated (categories);
    }
}
