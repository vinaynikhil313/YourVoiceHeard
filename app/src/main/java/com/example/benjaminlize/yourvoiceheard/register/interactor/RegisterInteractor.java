package com.example.benjaminlize.yourvoiceheard.register.interactor;

import com.example.benjaminlize.yourvoiceheard.register.presenter.OnRegisterFinishedListener;

/**
 * Created by Vinay Nikhil Pabba on 27-01-2016.
 */
public interface RegisterInteractor {

    void registerUser(String email, String password, OnRegisterFinishedListener listener);

}
