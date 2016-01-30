package com.example.benjaminlize.yourvoiceheard;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.util.Log;

import com.example.benjaminlize.yourvoiceheard.petition.Petition;
import com.example.benjaminlize.yourvoiceheard.utils.Utilities;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {

    String mFirebaseLink = "https://localpetitions.firebaseio.com/petitions";

    public ApplicationTest() {
        super(Application.class);


    }

    public void testWriteDummyPetitionToFirebase() {

        Firebase.setAndroidContext(getContext());
        Firebase myFirebaseRef = new Firebase(mFirebaseLink);

        // Attach an listener to read the data at our posts reference

        myFirebaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Log.d("BenDebugs", "There are " + snapshot.getChildrenCount() + " blog posts");
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Petition petition = postSnapshot.getValue(Petition.class);
                    Log.d("BenDebugs", petition.getmTitle());
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

        Petition petition0 = Utilities.createPetition2 ();
        Petition petition1 = Utilities.createPetition3();
        myFirebaseRef.push().setValue(petition0);
        myFirebaseRef.push().setValue(petition1);
        myFirebaseRef.push().setValue(petition0);
        myFirebaseRef.push().setValue(petition1);



    }


}