package com.example.benjaminlize.yourvoiceheard.start.view;

/**
 * Created by Vinay Nikhil Pabba on 30-01-2016.
 */
public interface StartPageView {

    void writeToSharedPreferences(String provider, String uid, String accessToken);

    void showMessage(String message);

    void openMainPage();

    void openLoginPage();

    void disableLoginPage();

}
