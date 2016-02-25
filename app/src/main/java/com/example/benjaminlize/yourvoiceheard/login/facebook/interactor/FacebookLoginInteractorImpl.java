package com.example.benjaminlize.yourvoiceheard.login.facebook.interactor;

import android.os.Bundle;

import com.example.benjaminlize.yourvoiceheard.login.facebook.presenter.OnFacebookLoginFinishedListener;
import com.example.benjaminlize.yourvoiceheard.user.User;
import com.example.benjaminlize.yourvoiceheard.utils.Constants;
import com.example.benjaminlize.yourvoiceheard.utils.UpdateFirebaseLogin;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import org.json.JSONObject;

/**
 * Created by Vinay Nikhil Pabba on 27-01-2016.
 */
public class FacebookLoginInteractorImpl implements FacebookLoginInteractor,
        Firebase.AuthResultHandler, ValueEventListener {

    Firebase firebase = new Firebase(Constants.FIREBASE_REF);
    OnFacebookLoginFinishedListener listener;

    @Override
    public void requestData (OnFacebookLoginFinishedListener listener) {
        //Log.i(TAG + " inside requestData ", AccessToken.getCurrentAccessToken ().getToken ());
        this.listener = listener;
        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object,GraphResponse response) {

                        JSONObject json = response.getJSONObject();
                        //Log.i("JSON ", json.toString ());

                        if (json != null) {
                            firebase.authWithOAuthToken ("facebook", AccessToken.getCurrentAccessToken ().getToken (), FacebookLoginInteractorImpl.this);
                        }

                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString ("fields", "id,name,link,email,picture");
        request.setParameters (parameters);
        request.executeAsync ();
    }

    @Override
    public void onAuthenticated (AuthData authData) {
        //listener.onFirebaseLoginSuccess (authData.getUid (), authData.getToken ());
        UpdateFirebaseLogin.updateFirebase (authData);

        firebase.child ("users").child (authData.getUid ()).addListenerForSingleValueEvent (this);
    }

    @Override
    public void onAuthenticationError (FirebaseError firebaseError) {
        listener.onFirebaseLoginFailure ();
    }

    @Override
    public void onDataChange (DataSnapshot dataSnapshot) {
        User user = dataSnapshot.getValue (User.class);
        listener.onFirebaseLoginSuccess (user);
    }

    @Override
    public void onCancelled (FirebaseError firebaseError) {

    }
}
