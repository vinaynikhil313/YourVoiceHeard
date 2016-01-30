package com.example.benjaminlize.yourvoiceheard.main.interactor;

import android.util.Log;

import com.example.benjaminlize.yourvoiceheard.main.presenter.OnMainFinishedListener;
import com.example.benjaminlize.yourvoiceheard.petition.Petition;
import com.example.benjaminlize.yourvoiceheard.utils.Constants;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vinay Nikhil Pabba on 29-01-2016.
 */
public class MainInteractorImpl implements MainInteractor, ValueEventListener {

    OnMainFinishedListener listener;

    List<Petition> petitionList = new ArrayList<Petition> ();

    Firebase firebase = new Firebase(Constants.FIREBASE_REF);

    @Override
    public void onCancelled (FirebaseError firebaseError) {

    }

    @Override
    public void generatePetitionList(OnMainFinishedListener listener){
        this.listener = listener;
        firebase.child ("petitions").addListenerForSingleValueEvent (this);
    }

    @Override
    public void onDataChange (DataSnapshot dataSnapshot) {
        Log.i ("FIREBASE petitions : ", dataSnapshot.getChildrenCount () + "");
        for (DataSnapshot child : dataSnapshot.getChildren ()) {
            Petition p = child.getValue (Petition.class);
            petitionList.add (p);
        }

        listener.onPetitionsListGenerated (petitionList);

        /*PetitionsDisplayAdapter petitionsDisplayAdapter = new PetitionsDisplayAdapter (getContext (), petitionsArray);

        ListView listView = (ListView) viewHolder.findViewById (R.id.mainListView);
        listView.setAdapter (petitionsDisplayAdapter);*/
    }
}
