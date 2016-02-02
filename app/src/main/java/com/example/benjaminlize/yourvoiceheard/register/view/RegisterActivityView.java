package com.example.benjaminlize.yourvoiceheard.register.view;

import com.example.benjaminlize.yourvoiceheard.user.User;

/**
 * Created by Vinay Nikhil Pabba on 27-01-2016.
 */
public interface RegisterActivityView {

    void openHomePage();

    void registrationError(String message);

    void showProgressBar();

    void hideProgressBar();

    void writeToSharedPreferences(User user);

}
