package com.example.benjaminlize.yourvoiceheard.preferences.presenter;

import com.example.benjaminlize.yourvoiceheard.category.Category;
import com.example.benjaminlize.yourvoiceheard.user.User;

import java.util.List;
import java.util.Map;

/**
 * Created by Vinay Nikhil Pabba on 31-01-2016.
 */
public interface OnPreferencesFinishedListener {

    void onListGenerated(List<Category> categories);

    void onPrefChangedSuccess(User user);

    void onPrefChangedFailure();

}
