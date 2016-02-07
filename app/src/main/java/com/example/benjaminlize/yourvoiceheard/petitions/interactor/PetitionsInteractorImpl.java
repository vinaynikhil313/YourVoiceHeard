package com.example.benjaminlize.yourvoiceheard.petitions.interactor;

import android.util.Log;

import com.example.benjaminlize.yourvoiceheard.petitions.presenter.OnPetitionsListFinishedListener;
import com.example.benjaminlize.yourvoiceheard.petitiondetails.Petition;
import com.example.benjaminlize.yourvoiceheard.user.User;
import com.example.benjaminlize.yourvoiceheard.utils.Constants;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.FirebaseException;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Vinay Nikhil Pabba on 29-01-2016.
 */
public class PetitionsInteractorImpl implements PetitionsInteractor, ValueEventListener{

    OnPetitionsListFinishedListener listener;

    User user;

    static List<Petition> petitionList = new ArrayList<Petition> ();

    private static long petitionsCount = 0;
    private static long currentCount = 0;

    private static Map<String, String> userPreferences = new HashMap<> ();

    Firebase firebase = new Firebase (Constants.FIREBASE_REF);

    @Override
    public void onCancelled (FirebaseError firebaseError) {

    }

    @Override
    public void generatePetitionList (User user, OnPetitionsListFinishedListener listener) {
        this.user = user;
        this.listener = listener;
        currentCount = 0;
        firebase.child ("petitions").addListenerForSingleValueEvent (this);
    }

    @Override
    public void onDataChange (DataSnapshot dataSnapshot) {
        petitionsCount = dataSnapshot.getChildrenCount ();
        Log.i ("FIREBASE petitions : ", petitionsCount + "");
        userPreferences = user.getPreferences ();
        petitionList.clear ();
        for (DataSnapshot child : dataSnapshot.getChildren ()) {
            Petition p = getPetition (child);
            if (p != null && isUserPreferredPetition (p)) {
                Log.i ("PetitionsInteractor", "Title is " + p.getmTitle () + " category is " + p.getmCategory ());
                petitionList.add (p);
            }
        }
        Log.i ("MainInteractor", "Petition List Size = " + petitionList.size ());
        listener.onPetitionsListGenerated (petitionList);
        //firebase.child ("petitions").addChildEventListener (this);

    }

    private Petition getPetition (DataSnapshot dataSnapshot) {
        Petition p = null;
        try {
            p = dataSnapshot.getValue (Petition.class);
        } catch (FirebaseException e) {
            Log.e ("PetitionsInteractorImpl", e.getMessage ());
        }
        return p;
    }

    private boolean isUserPreferredPetition (Petition petition) {
        if (userPreferences.containsKey (petition.getmCategory ()))
            return true;
        else
            return false;
    }

}
