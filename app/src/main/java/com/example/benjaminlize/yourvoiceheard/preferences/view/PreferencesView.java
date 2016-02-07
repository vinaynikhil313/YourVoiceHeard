package com.example.benjaminlize.yourvoiceheard.preferences.view;

import com.example.benjaminlize.yourvoiceheard.category.Category;
import com.example.benjaminlize.yourvoiceheard.user.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Vinay Nikhil Pabba on 01-02-2016.
 */
public interface PreferencesView {

    void generatePreferences();

    void onListGenerated(List<Category> categories);

    void unCheck(int position, String message);

    void writeToSharedPreferences(User user);

    void changeService(User user);

}
