package com.example.benjaminlize.yourvoiceheard.preferences.interactor;

import com.example.benjaminlize.yourvoiceheard.category.Category;
import com.example.benjaminlize.yourvoiceheard.preferences.presenter.OnPreferencesFinishedListener;
import com.example.benjaminlize.yourvoiceheard.user.User;

/**
 * Created by Vinay Nikhil Pabba on 31-01-2016.
 */
public interface PreferencesInteractor {

    void generateList(OnPreferencesFinishedListener listener);

    void checkAndUpdatePreferences(User user, Category category, boolean isChecked, OnPreferencesFinishedListener listener);

}
