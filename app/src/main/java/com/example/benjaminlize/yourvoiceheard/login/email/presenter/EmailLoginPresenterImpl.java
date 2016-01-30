package com.example.benjaminlize.yourvoiceheard.login.email.presenter;

import android.util.Log;

import com.example.benjaminlize.yourvoiceheard.login.email.view.EmailLoginFragmentView;
import com.example.benjaminlize.yourvoiceheard.login.email.view.LoginActivityFragment;
import com.example.benjaminlize.yourvoiceheard.login.email.interactor.EmailLoginInteractor;
import com.example.benjaminlize.yourvoiceheard.login.email.interactor.EmailLoginInteractorImpl;

/**
 * Created by Vinay Nikhil Pabba on 21-01-2016.
 */
public class EmailLoginPresenterImpl implements EmailLoginPresenter, OnEmailLoginFinishedListener {

    private EmailLoginFragmentView loginActivityFragment;
    private EmailLoginInteractor emailLoginInteractor;

    public EmailLoginPresenterImpl (LoginActivityFragment loginActivityFragment){
        this.loginActivityFragment = loginActivityFragment;
        emailLoginInteractor = new EmailLoginInteractorImpl ();
    }

    @Override
    public void authenticateCredentials (String email, String password) {

        loginActivityFragment.showProgressDialog ();
        if(loginActivityFragment != null)
            emailLoginInteractor.authenticateWithEmail (email, password, this);
        //AuthenticateUser.authWithEmailPassword (email, password, loginActivityFragment.getFragment ().getContext ());
    }

    @Override
    public void onEmailError () {
        loginActivityFragment.hideProgressDialog ();
        loginActivityFragment.emailError ();
    }

    @Override
    public void onPasswordError () {
        loginActivityFragment.hideProgressDialog ();
        loginActivityFragment.passwordError ();
    }

    @Override
    public void onSuccess (String uid, String token) {
        loginActivityFragment.writeToSharedPreferences (uid, token);
        Log.i ("EMAIL PRESENTER", uid + " " + token);
        loginActivityFragment.hideProgressDialog ();
        loginActivityFragment.openMainPage ();
    }
}