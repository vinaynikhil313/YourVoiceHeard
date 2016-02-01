package com.example.benjaminlize.yourvoiceheard.login.facebook.presenter;

import com.example.benjaminlize.yourvoiceheard.user.User;

/**
 * Created by Vinay Nikhil Pabba on 27-01-2016.
 */
public interface OnFacebookLoginFinishedListener {

    void onFirebaseLoginSuccess(User user);

    void onFirebaseLoginFailure();

}
