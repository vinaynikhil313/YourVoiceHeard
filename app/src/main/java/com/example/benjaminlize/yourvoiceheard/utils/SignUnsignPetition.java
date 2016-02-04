package com.example.benjaminlize.yourvoiceheard.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.benjaminlize.yourvoiceheard.petitiondetails.Petition;
import com.example.benjaminlize.yourvoiceheard.user.User;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

/**
 * Created by Vinay Nikhil Pabba on 16-01-2016.
 * Listener for Sign/Unsign Button clicks.
 * Once the user clicks on a button, first we check whether he clicked the same button before
 * or a different button or if he is a first time clicker.
 * Then based on the above condition we modify our firebase
 * The Firebase data is read in the Async Handler below
 */
public class SignUnsignPetition implements View.OnClickListener{

    private int flag;
    //public static final int SIGN = 1;
    //public static final int UNSIGN = -1;
    private Petition petition;
    private static final String TAG = SignUnsignPetition.class.getSimpleName ();
    private Context context;
    private String uid;
    User user;

    Firebase firebasePetitions;
    Firebase firebaseUsers;

    public SignUnsignPetition(int flag, Petition petition, String uid, Context context){
        this.flag = flag;
        this.petition = petition;
        this.uid = uid;
        Log.i(TAG, uid);
        this.context = context;
        firebasePetitions = new Firebase("https://yourvoiceheard.firebaseio.com/petitions");
        firebaseUsers = new Firebase("https://yourvoiceheard.firebaseio.com/users");
        new PetitionValueEventListener ().execute ();
    }

    private int checkPreviousSignUnsign(){
        int currentFlag = 0;
        if(user.getPetitions () != null && user.getPetitions ().get(petition.getmUniqueId ()) != null){
            return user.getPetitions ().get (petition.getmUniqueId ());
        }
        return currentFlag;
    }

    @Override
    public void onClick (View v) {

        /**
         * checkPreviousSignUnsign == 0, means the user is signing/unsigning the petition for the first time
         * checkPreviousSignUnsign == flag, means that the user clicked on the same button again
         * checkPreviousSignUnsign != flag, means that the user has changed his mind
         */
        if(checkPreviousSignUnsign () == 0 || checkPreviousSignUnsign () != flag) {
            if(checkPreviousSignUnsign () == 0) {
                Toast.makeText (context, "Thank you for your interest", Toast.LENGTH_SHORT).show ();
                petition.newSignUnsign (flag);
            }
            else {
                Toast.makeText (context, "You have changed your mind", Toast.LENGTH_SHORT).show ();
                petition.changeOfMind (flag);
            }
            petition.getmUsers ().put (uid, flag);
            user.getPetitions ().put (petition.getmUniqueId (), flag);
            firebasePetitions.child (petition.getmUniqueId () + "").setValue (petition);
            firebaseUsers.child (uid).setValue (user);
        }
        else{
            String text = "You have already ";
            if(flag == 1)
                text += "signed";
            else
                text += "unsigned";
            text += " this petition";
            Toast.makeText (context, text, Toast.LENGTH_SHORT).show ();
        }
    }

    private class PetitionValueEventListener
            extends AsyncTask<Void, Void, Void>
            implements ValueEventListener{

        @Override
        protected Void doInBackground (Void... params) {
            firebasePetitions.child (petition.getmUniqueId ()).addValueEventListener (this);
            firebaseUsers.child (uid).addValueEventListener (this);
            return null;
        }

        @Override
        public void onDataChange (DataSnapshot dataSnapshot) {
            //Log.i(TAG, dataSnapshot.toString ());
            if(dataSnapshot.getKey ().equals (petition.getmUniqueId ())) {
                //Log.i(TAG + " Petition ", dataSnapshot.toString ());
                petition = dataSnapshot.getValue (Petition.class);
            }
            else if(dataSnapshot.getKey ().equals (uid)) {
                //Log.i(TAG + " User ", dataSnapshot.toString ());
                user = dataSnapshot.getValue (User.class);
            }
            //Log.i(TAG + " petition - ", petition.getmTitle ());
        }

        @Override
        public void onCancelled (FirebaseError firebaseError) {
            Log.e(TAG, "FIREBASE ERROR!!!");
        }
    }
}
