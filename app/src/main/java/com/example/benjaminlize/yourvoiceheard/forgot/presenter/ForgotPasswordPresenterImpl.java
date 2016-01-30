package com.example.benjaminlize.yourvoiceheard.forgot.presenter;

import android.util.Log;

import com.example.benjaminlize.yourvoiceheard.forgot.interactor.ForgotPasswordInteractor;
import com.example.benjaminlize.yourvoiceheard.forgot.interactor.ForgotPasswordInteractorImpl;
import com.example.benjaminlize.yourvoiceheard.forgot.view.ForgotPasswordView;
import com.example.benjaminlize.yourvoiceheard.utils.Constants;
import com.example.benjaminlize.yourvoiceheard.utils.Utilities;

/**
 * Created by Vinay Nikhil Pabba on 30-01-2016.
 */
public class ForgotPasswordPresenterImpl implements
        ForgotPasswordPresenter, OnPasswordResetFinishedListener {

    String TAG = Utilities.getTag (this);

    ForgotPasswordView view;
    ForgotPasswordInteractor interactor;

    public ForgotPasswordPresenterImpl(ForgotPasswordView view){
        this.view = view;
        interactor = new ForgotPasswordInteractorImpl ();
    }

    @Override
    public void resetPassword (String email) {
        view.showProgressDialog ("Sending Reset Email...");
        interactor.sendResetEmail (email, this);
    }

    @Override
    public void changePassword (String email, String oldPassword, String newPassword) {
        view.hideChangePasswordDialog ();
        Log.i (TAG, email + " " + oldPassword + " " + newPassword);
        interactor.changePassword (email, oldPassword, newPassword);
        view.showProgressDialog ("Updating your new password...");
    }

    @Override
    public void onFailure (String message) {
        view.hideProgressDialog ();
        view.showMessage (message);
    }

    @Override
    public void onSuccess (int flag) {
        Log.i (TAG + flag, "OnSuccess");
        view.hideProgressDialog ();
        switch (flag) {
            case Constants.RESET:
                view.showMessage ("New Password has been mailed to your email");
                view.showChangePasswordDialog ();
                break;
            case Constants.CHANGE:
                view.showMessage ("Password Changed Successfully!");
                view.openLoginPage ();
                break;
        }

    }
}
