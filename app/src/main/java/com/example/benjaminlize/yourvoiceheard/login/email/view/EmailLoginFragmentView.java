package com.example.benjaminlize.yourvoiceheard.login.email.view;

import com.example.benjaminlize.yourvoiceheard.user.User;

/**
 * Created by Vinay Nikhil Pabba on 22-01-2016.
 */
public interface EmailLoginFragmentView {

    void emailError();

    void passwordError();

    void openMainPage();

    void writeToSharedPreferences(User user);

    void showProgressDialog();

    void hideProgressDialog();

    void openPreferencesPage();

}