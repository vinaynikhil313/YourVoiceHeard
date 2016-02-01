package com.example.benjaminlize.yourvoiceheard.start.presenter;

import android.util.Log;

import com.example.benjaminlize.yourvoiceheard.start.interactor.StartPageInteractor;
import com.example.benjaminlize.yourvoiceheard.start.interactor.StartPageInteractorImpl;
import com.example.benjaminlize.yourvoiceheard.start.view.StartPageView;
import com.example.benjaminlize.yourvoiceheard.user.User;
import com.example.benjaminlize.yourvoiceheard.utils.Constants;
import com.example.benjaminlize.yourvoiceheard.utils.Utilities;
import com.facebook.AccessToken;
import com.facebook.FacebookSdk;

/**
 * Created by Vinay Nikhil Pabba on 30-01-2016.
 */
public class StartPagePreviousLoginChecker implements
        StartPagePresenter, OnTokenLoginFinishedListener {

    String TAG = Utilities.getTag (this);

    StartPageView view;
    StartPageInteractor interactor;

    public StartPagePreviousLoginChecker (StartPageView view){
        this.view = view;
        interactor = new StartPageInteractorImpl ();
        Log.i (TAG, "PreviousLoginChecker created");
    }

    @Override
    public void loginWithPassword (String accessToken) {
        Log.i(TAG, "Logging in with Password");
        interactor.loginWithToken (Constants.PROVIDER_PASSWORD, accessToken, this);
    }

    @Override
    public void onInitialized () {

        if(AccessToken.getCurrentAccessToken () != null){
            Log.i(TAG, "Logging in with Facebook");
            interactor.loginWithToken (Constants.PROVIDER_FACEBOOK, AccessToken.getCurrentAccessToken ().getToken (), this);
        }

    }

    @Override
    public void onLoginSuccessful (String provider, User user) {
        view.disableLoginPage ();
        Log.i(TAG, "UID = " + user.getUid ());
        view.writeToSharedPreferences (provider, user);
        view.openMainPage ();
    }

    @Override
    public void onLoginUnsuccessful () {
        view.showMessage ("Your access token has expired\nPlease re-login");
        view.openLoginPage ();
    }
}
