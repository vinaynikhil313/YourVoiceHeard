package com.example.benjaminlize.yourvoiceheard.start.presenter;

import com.example.benjaminlize.yourvoiceheard.user.User;

/**
 * Created by Vinay Nikhil Pabba on 30-01-2016.
 */
public interface OnTokenLoginFinishedListener {

    void onLoginSuccessful(String provider, User user);

    void onLoginUnsuccessful();

}
