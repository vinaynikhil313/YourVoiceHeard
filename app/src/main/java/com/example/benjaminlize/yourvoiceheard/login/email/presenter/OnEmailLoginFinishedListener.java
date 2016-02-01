package com.example.benjaminlize.yourvoiceheard.login.email.presenter;

import com.example.benjaminlize.yourvoiceheard.user.User;

/**
 * Created by Vinay Nikhil Pabba on 21-01-2016.
 */
public interface OnEmailLoginFinishedListener {

    void onSuccess(User user);

    void onEmailError();

    void onPasswordError();

}
