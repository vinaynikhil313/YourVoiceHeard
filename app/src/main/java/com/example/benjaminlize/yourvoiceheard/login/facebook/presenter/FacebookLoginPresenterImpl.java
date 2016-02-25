package com.example.benjaminlize.yourvoiceheard.login.facebook.presenter;

import com.example.benjaminlize.yourvoiceheard.login.facebook.interactor.FacebookLoginInteractor;
import com.example.benjaminlize.yourvoiceheard.login.facebook.interactor.FacebookLoginInteractorImpl;
import com.example.benjaminlize.yourvoiceheard.login.facebook.view.FacebookLoginFragment;
import com.example.benjaminlize.yourvoiceheard.login.facebook.view.FacebookLoginFragmentView;
import com.example.benjaminlize.yourvoiceheard.user.User;
import com.facebook.AccessToken;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;

import timber.log.Timber;

/**
 * Created by Vinay Nikhil Pabba on 27-01-2016.
 */
public class FacebookLoginPresenterImpl implements FacebookLoginPresenter,
        OnFacebookLoginFinishedListener, FacebookCallback<LoginResult> {

    FacebookLoginFragmentView view;
    FacebookLoginInteractor interactor;

    private static final String TAG = FacebookLoginPresenterImpl.class.getSimpleName ();

    public FacebookLoginPresenterImpl(FacebookLoginFragment view){

        this.view = view;
        interactor = new FacebookLoginInteractorImpl ();
        Timber.tag (TAG);
        Timber.i(TAG, "FacebookPresenter created");

    }

    @Override
    public void onCancel () {

    }

    @Override
    public void onError (FacebookException error) {

    }

    @Override
    public void onSuccess (LoginResult loginResult) {
        view.showProgressDialog ();
        AccessToken accessToken = loginResult.getAccessToken ();
            Timber.i (TAG, accessToken.getToken ());
        AccessToken.setCurrentAccessToken (accessToken);
        if (accessToken != null) {
            Timber.i (TAG, "Facebook Login Successful");
            interactor.requestData (this);
        }
    }

    @Override
    public void onFirebaseLoginFailure () {
        view.hideProgressDialog ();
        view.onError ();
    }

    @Override
    public void onFirebaseLoginSuccess (User user) {
        Timber.i (TAG, "Firebase Facebook Login successful");
        view.writeToSharedPrefernces (user);
        view.hideProgressDialog ();

        if(user.getPreferences ().size () > 0)
            view.openMainPage ();
        else
            view.openPreferencesPage ();
    }

}
