package com.example.benjaminlize.yourvoiceheard.login.facebook.interactor;

import com.example.benjaminlize.yourvoiceheard.login.facebook.presenter.OnFacebookLoginFinishedListener;

/**
 * Created by Vinay Nikhil Pabba on 27-01-2016.
 */
public interface FacebookLoginInteractor {

    void requestData(OnFacebookLoginFinishedListener listener);

}
