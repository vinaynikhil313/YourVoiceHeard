package com.example.benjaminlize.yourvoiceheard.login.email.presenter;

/**
 * Created by Vinay Nikhil Pabba on 21-01-2016.
 */
public interface OnEmailLoginFinishedListener {

    void onSuccess(String uid, String token);

    void onEmailError();

    void onPasswordError();

}
