package com.example.benjaminlize.yourvoiceheard.login.facebook.view;

import com.example.benjaminlize.yourvoiceheard.user.User;

/**
 * Created by Vinay Nikhil Pabba on 22-01-2016.
 */
public interface FacebookLoginFragmentView {

    void openMainPage();

    void openPreferencesPage();

    void onError();

    void writeToSharedPrefernces(User user);

    void showProgressDialog();

    void hideProgressDialog();

}
