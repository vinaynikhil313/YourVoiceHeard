package com.example.benjaminlize.yourvoiceheard.preferences.presenter;

import com.example.benjaminlize.yourvoiceheard.category.Category;
import com.example.benjaminlize.yourvoiceheard.user.User;

/**
 * Created by Vinay Nikhil Pabba on 31-01-2016.
 */
public interface PreferencesPresenter {

    void generateCategories();

    void changePreferences(User user, int position, Category category, boolean isChecked);
    
}
