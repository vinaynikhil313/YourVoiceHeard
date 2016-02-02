package com.example.benjaminlize.yourvoiceheard.main.interactor;

import android.util.Log;

import com.example.benjaminlize.yourvoiceheard.main.presenter.OnMainFinishedListener;
import com.example.benjaminlize.yourvoiceheard.petition.Petition;
import com.example.benjaminlize.yourvoiceheard.user.User;
import com.example.benjaminlize.yourvoiceheard.utils.Constants;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Vinay Nikhil Pabba on 29-01-2016.
 */
public class MainInteractorImpl implements MainInteractor, ValueEventListener, ChildEventListener {

    OnMainFinishedListener listener;

    User user;

    List<Petition> petitionList = new ArrayList<Petition> ();

    static long petitionsCount = 0;

    Firebase firebase = new Firebase(Constants.FIREBASE_REF);

    @Override
    public void onCancelled (FirebaseError firebaseError) {

    }

    @Override
    public void generatePetitionList(User user, OnMainFinishedListener listener){
        this.user = user;
        this.listener = listener;
        firebase.child ("petitions").addListenerForSingleValueEvent (this);
    }

    @Override
    public void onDataChange (DataSnapshot dataSnapshot) {
        petitionsCount = dataSnapshot.getChildrenCount ();
        firebase.child ("petitions").addChildEventListener (this);
        Log.i ("FIREBASE petitions : ", petitionsCount + "");
        Map<String, String> userPreferences = user.getPreferences ();
        petitionList.clear ();
        for (DataSnapshot child : dataSnapshot.getChildren ()) {
            Petition p = child.getValue (Petition.class);
            if(userPreferences.containsKey (p.getmCategory ()))
                petitionList.add (p);
        }
        listener.onPetitionsListGenerated (petitionList);

    }

    private static long currentCount = 0;

    @Override
    public void onChildAdded (DataSnapshot dataSnapshot, String s) {
        Log.i("MainInteractor", "Unknown String = " + s);

        Petition newPetition = dataSnapshot.getValue (Petition.class);
        Log.i("MainInteractor", "New PetitionID " + newPetition.getmUniqueId ());
        currentCount++;
        if(currentCount > petitionsCount){
            //listener.onNewPetitionAdded(newPetition);
            petitionsCount++;
            listener.onNewPetitionAdded (newPetition);
        }
        Log.i("MainInteractor", "New currentCount =  " + currentCount + " " + petitionsCount);

    }

    @Override
    public void onChildChanged (DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onChildMoved (DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onChildRemoved (DataSnapshot dataSnapshot) {
        Petition removedPetition = dataSnapshot.getValue (Petition.class);
        Log.i("MainInteractor", "Removed PetitionID " + removedPetition.getmUniqueId ());
        if(currentCount == petitionsCount)
            petitionsCount--;
        currentCount--;
    }
}
