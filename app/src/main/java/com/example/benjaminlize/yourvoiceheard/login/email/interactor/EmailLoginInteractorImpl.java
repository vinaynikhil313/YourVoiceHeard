package com.example.benjaminlize.yourvoiceheard.login.email.interactor;

import android.util.Log;

import com.example.benjaminlize.yourvoiceheard.login.email.presenter.OnEmailLoginFinishedListener;
import com.example.benjaminlize.yourvoiceheard.utils.Constants;
import com.example.benjaminlize.yourvoiceheard.utils.UpdateFirebaseLogin;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

/**
 * Created by Vinay Nikhil Pabba on 21-01-2016.
 */
public class EmailLoginInteractorImpl implements EmailLoginInteractor, Firebase.AuthResultHandler{

    Firebase firebase = new Firebase(Constants.FIREBASE_REF);

    private final String TAG = EmailLoginInteractorImpl.class.getSimpleName ();

    private OnEmailLoginFinishedListener listener;

    @Override
    public void authenticateWithEmail (String email, String password, OnEmailLoginFinishedListener listener) {
        this.listener = listener;
        Log.i (TAG, "Login with email called");
        firebase.authWithPassword (email, password, EmailLoginInteractorImpl.this);

    }


    @Override
    public void onAuthenticated (AuthData authData) {

        listener.onSuccess (authData.getUid (), authData.getToken ());

        Log.i(TAG, "Login with email successful");

        UpdateFirebaseLogin.updateFirebase (authData);

    }

    @Override
    public void onAuthenticationError (FirebaseError firebaseError) {

        switch (firebaseError.getCode ()){

            case FirebaseError.USER_DOES_NOT_EXIST:
                listener.onEmailError ();
                break;

            case FirebaseError.INVALID_PASSWORD:
                listener.onPasswordError ();
                break;

            case FirebaseError.INVALID_EMAIL:
                listener.onEmailError ();
                break;

        }

    }
}
