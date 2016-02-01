package com.example.benjaminlize.yourvoiceheard.preferences.presenter;

import android.util.Log;

import com.example.benjaminlize.yourvoiceheard.preferences.interactor.PreferencesInteractor;
import com.example.benjaminlize.yourvoiceheard.preferences.interactor.PreferencesInteractorImpl;
import com.example.benjaminlize.yourvoiceheard.preferences.view.PreferencesFragment;
import com.example.benjaminlize.yourvoiceheard.user.User;

import java.util.List;
import java.util.Map;

/**
 * Created by Vinay Nikhil Pabba on 31-01-2016.
 */
public class PreferencesPresenterImpl implements PreferencesPresenter, OnPreferencesListGeneratedListener {

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
    public void onListGenerated (Map<String, String> categories) {
        Log.i ("PREFERENCES PRESENTER", categories.toString ());
    }
}
