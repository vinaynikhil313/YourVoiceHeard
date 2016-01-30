package com.example.benjaminlize.yourvoiceheard.login.facebook.interactor;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.benjaminlize.yourvoiceheard.login.facebook.presenter.OnFacebookLoginFinishedListener;
import com.example.benjaminlize.yourvoiceheard.utils.AuthenticateUser;
import com.example.benjaminlize.yourvoiceheard.utils.Constants;
import com.example.benjaminlize.yourvoiceheard.utils.UpdateFirebaseLogin;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Vinay Nikhil Pabba on 27-01-2016.
 */
public class FacebookLoginInteractorImpl implements FacebookLoginInteractor, Firebase.AuthResultHandler{

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
                        Log.i("JSON ", json.toString ());

                        if (json != null) {
                            firebase.authWithOAuthToken ("facebook", AccessToken.getCurrentAccessToken ().getToken (), FacebookLoginInteractorImpl.this);
                        }

                        /*try {
                            if(json != null){
                                //Toast.makeText (getActivity (), "Welcome " + json.getString ("name"), Toast.LENGTH_LONG).show ();
                                //AuthenticateUser.authWithFacebook (AccessToken.getCurrentAccessToken ().getToken (), getContext ());
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }*/
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString ("fields", "id,name,link,email,picture");
        request.setParameters (parameters);
        request.executeAsync ();
    }

    @Override
    public void onAuthenticated (AuthData authData) {
        listener.onFirebaseLoginSuccess (authData.getUid (), authData.getToken ());
        UpdateFirebaseLogin.updateFirebase (authData);
    }

    @Override
    public void onAuthenticationError (FirebaseError firebaseError) {
        listener.onFirebaseLoginFailure ();
    }
}
