package com.example.benjaminlize.yourvoiceheard.preferences.presenter;

import android.util.Log;

import com.example.benjaminlize.yourvoiceheard.category.Category;
import com.example.benjaminlize.yourvoiceheard.preferences.interactor.PreferencesInteractor;
import com.example.benjaminlize.yourvoiceheard.preferences.interactor.PreferencesInteractorImpl;
import com.example.benjaminlize.yourvoiceheard.preferences.view.PreferencesFragment;
import com.example.benjaminlize.yourvoiceheard.user.User;
import com.example.benjaminlize.yourvoiceheard.utils.Constants;

import java.util.List;

/**
 * Created by Vinay Nikhil Pabba on 31-01-2016.
 */
public class PreferencesPresenterImpl implements PreferencesPresenter,
        OnPreferencesFinishedListener {

    int position;

    PreferencesFragment view;

    PreferencesInteractor interactor;

    public PreferencesPresenterImpl (PreferencesFragment view) {
        this.view = view;

        interactor = new PreferencesInteractorImpl ();
    }

    @Override
    public void generateCategories () {
        interactor.generateList (this);
    }

    @Override
    public void onListGenerated (List<Category> categories) {
        view.onListGenerated (categories);
    }

    @Override
    public void changePreferences (User user, int position, Category category, boolean isChecked) {
        this.position = position;
        interactor.checkAndUpdatePreferences (user, category, isChecked, this);
        Log.i ("PreferencesPresenter1", "Position = " + position);
        Log.i ("PreferencesPresenter2", "Position = " + this.position);
    }

    @Override
    public void onPrefChangedFailure () {
        String message = "You can select " + Constants.MAX_PREFERENCES + " categories";
        Log.i ("PreferencesPresenter3", "Position = " + position);
        view.unCheck (position, message);

    }

    @Override
    public void onPrefChangedSuccess (User user) {
        view.writeToSharedPreferences (user);
    }
}
