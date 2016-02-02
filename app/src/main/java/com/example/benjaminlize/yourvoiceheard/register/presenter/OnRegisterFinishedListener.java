package com.example.benjaminlize.yourvoiceheard.register.presenter;

import com.example.benjaminlize.yourvoiceheard.user.User;

/**
 * Created by Vinay Nikhil Pabba on 27-01-2016.
 */
public interface OnRegisterFinishedListener {

    void onSuccess(User user);

    void onFailure(int errorCode);

}
