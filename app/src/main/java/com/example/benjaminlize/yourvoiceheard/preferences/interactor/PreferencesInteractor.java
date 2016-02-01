package com.example.benjaminlize.yourvoiceheard.preferences.interactor;

import com.example.benjaminlize.yourvoiceheard.preferences.presenter.OnPreferencesListGeneratedListener;
import com.example.benjaminlize.yourvoiceheard.user.User;

/**
 * Created by Vinay Nikhil Pabba on 31-01-2016.
 */
public interface PreferencesInteractor {

    void generateList(OnPreferencesListGeneratedListener listener);

}
