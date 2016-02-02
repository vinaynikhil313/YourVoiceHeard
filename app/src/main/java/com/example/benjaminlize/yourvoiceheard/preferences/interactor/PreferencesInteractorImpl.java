package com.example.benjaminlize.yourvoiceheard.preferences.interactor;

import android.util.Log;

import com.example.benjaminlize.yourvoiceheard.category.Category;
import com.example.benjaminlize.yourvoiceheard.preferences.presenter.OnPreferencesFinishedListener;
import com.example.benjaminlize.yourvoiceheard.user.User;
import com.example.benjaminlize.yourvoiceheard.utils.Constants;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Vinay Nikhil Pabba on 31-01-2016.
 */
public class PreferencesInteractorImpl implements PreferencesInteractor, ValueEventListener {

    Firebase firebase = new Firebase (Constants.FIREBASE_REF);

    OnPreferencesFinishedListener listener;

    List<Category> categories = new ArrayList<> ();

    @Override
    public void generateList (OnPreferencesFinishedListener listener) {
        this.listener = listener;

        firebase.child ("categories").addListenerForSingleValueEvent (this);
    }

    @Override
    public void onCancelled (FirebaseError firebaseError) {

    }

    @Override
    public void onDataChange (DataSnapshot dataSnapshot) {

        for (DataSnapshot child : dataSnapshot.getChildren ()) {

            categories.add (child.getValue (Category.class));
        }
        Log.i ("Preferences Interactor", categories.get (0).getCid ());
        listener.onListGenerated (categories);
    }

    @Override
    public void checkAndUpdatePreferences (User user, Category category, boolean isChecked, OnPreferencesFinishedListener listener) {
        this.listener = listener;
        String cid = category.getCid ();
        Map<String, String> userPreferences = user.getPreferences ();
        if (isChecked && userPreferences.size () == Constants.MAX_PREFERENCES) {
            Log.i("PREF CHANGED INTERACTOR", isChecked + " " + userPreferences.size ());
            listener.onPrefChangedFailure ();
        }
        else {
            if(isChecked)
                userPreferences.put (cid, "preferred");
            else
                userPreferences.remove (cid);
            user.setPreferences (userPreferences);
            firebase.child ("users").child (user.getUid ()).setValue (user);
            Log.i ("PREF CHANGED INTERACTOR", isChecked + " " + userPreferences.size ());
            listener.onPrefChangedSuccess (user);
        }
    }
}
