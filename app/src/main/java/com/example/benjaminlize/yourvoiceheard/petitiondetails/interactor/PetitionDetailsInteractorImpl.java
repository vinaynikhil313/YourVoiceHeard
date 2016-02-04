package com.example.benjaminlize.yourvoiceheard.petitiondetails.interactor;

import android.util.Log;

import com.example.benjaminlize.yourvoiceheard.petitiondetails.Petition;
import com.example.benjaminlize.yourvoiceheard.petitiondetails.presenter.OnPetitionDetailsFinishedListener;
import com.example.benjaminlize.yourvoiceheard.user.User;
import com.example.benjaminlize.yourvoiceheard.utils.Constants;
import com.example.benjaminlize.yourvoiceheard.utils.Utilities;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

/**
 * Created by Vinay Nikhil Pabba on 29-01-2016.
 */
public class PetitionDetailsInteractorImpl implements PetitionDetailsInteractor, ValueEventListener {

    OnPetitionDetailsFinishedListener listener;

    String uid;
    String petitionId;

    String TAG = Utilities.getTag (this);

    Firebase firebase;

    Petition petition;
    User user;

    public PetitionDetailsInteractorImpl (String uid, String petitionId){

        firebase = new Firebase (Constants.FIREBASE_REF);

        this.uid = uid;
        this.petitionId = petitionId;

        Log.i (TAG, "Interactor created");

        firebase.child ("petitions").child (petitionId).addValueEventListener (this);
        firebase.child ("users").child (uid).addValueEventListener (this);
    }

    private int checkPreviousSignUnsign(){
        int currentFlag = 0;
        if(user.getPetitions () != null && user.getPetitions ().get(petition.getmUniqueId ()) != null){
            return user.getPetitions ().get (petition.getmUniqueId ());
        }
        return currentFlag;
    }

    @Override
    public void checkAndUpdateFirebase(int flag, OnPetitionDetailsFinishedListener listener){
        Log.i(TAG, "Interactor checkupdate " + flag);

        this.listener = listener;

        int previousSignUnsign = checkPreviousSignUnsign();

        /**
         * previousSignUnsign == 0, means the user is signing/unsigning the petition for the first time
         * previousSignUnsign == flag, means that the user clicked on the same button again
         * previousSignUnsign != flag, means that the user has changed his mind
         */
        if(previousSignUnsign == 0 || previousSignUnsign != flag) {
            if(checkPreviousSignUnsign () == 0) {
                petition.newSignUnsign (flag);
                listener.onFirstTimeSignUnsign (petition.getmSigns (), petition.getmUnsigns ());
            }
            else {
                petition.changeOfMind (flag);
                listener.onChangeOfMind (petition.getmSigns (), petition.getmUnsigns ());
            }
            petition.getmUsers ().put (uid, flag);
            user.getPetitions ().put (petition.getmUniqueId (), flag);
            firebase.child ("petitions").child (petition.getmUniqueId () + "").setValue (petition);
            firebase.child ("users").child (uid).setValue (user);
        }
        else{
            listener.onReClick ();
        }
    }

    @Override
    public void onCancelled (FirebaseError firebaseError) {

    }

    @Override
    public void onDataChange (DataSnapshot dataSnapshot) {
        if(dataSnapshot.getKey ().equals (petitionId)) {
            Log.i(TAG + " Petition ", dataSnapshot.toString ());
            petition = dataSnapshot.getValue (Petition.class);
        }
        else if(dataSnapshot.getKey ().equals (uid)) {
            Log.i(TAG + " User ", dataSnapshot.toString ());
            user = dataSnapshot.getValue (User.class);
        }
    }
}
