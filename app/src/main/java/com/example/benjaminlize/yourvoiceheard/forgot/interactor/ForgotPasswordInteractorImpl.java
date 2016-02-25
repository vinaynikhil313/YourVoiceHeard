package com.example.benjaminlize.yourvoiceheard.forgot.interactor;

import com.example.benjaminlize.yourvoiceheard.forgot.presenter.OnPasswordResetFinishedListener;
import com.example.benjaminlize.yourvoiceheard.utils.Constants;
import com.example.benjaminlize.yourvoiceheard.utils.Utilities;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import timber.log.Timber;

/**
 * Created by Vinay Nikhil Pabba on 30-01-2016.
 */
public class ForgotPasswordInteractorImpl implements ForgotPasswordInteractor {

    String TAG = Utilities.getTag (this);

    OnPasswordResetFinishedListener listener;

    Firebase firebase = new Firebase(Constants.FIREBASE_REF);

    @Override
    public void sendResetEmail (String email, OnPasswordResetFinishedListener listener) {

        Timber.tag (TAG);
        this.listener = listener;

        firebase.resetPassword (email, new ResetPasswordResultHandler (Constants.RESET));
    }

    @Override
    public void changePassword (String email, String oldPassword, String newPassword) {
        Timber.i(email+" "+oldPassword+" "+newPassword);
        firebase.changePassword (email, oldPassword, newPassword, new ResetPasswordResultHandler (Constants.CHANGE));
    }

    private class ResetPasswordResultHandler implements Firebase.ResultHandler{

        int flag;

        ResetPasswordResultHandler(int flag){
            this.flag = flag;
        }

        @Override
        public void onError (FirebaseError firebaseError) {
            listener.onFailure (firebaseError.getMessage ());
        }

        @Override
        public void onSuccess () {
            Timber.i ("OnPasswordResetSuccess");
            listener.onSuccess (flag);
        }
    }
}
