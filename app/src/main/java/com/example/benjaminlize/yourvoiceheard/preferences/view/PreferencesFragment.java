package com.example.benjaminlize.yourvoiceheard.preferences.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.benjaminlize.yourvoiceheard.R;
import com.example.benjaminlize.yourvoiceheard.preferences.presenter.PreferencesPresenter;
import com.example.benjaminlize.yourvoiceheard.preferences.presenter.PreferencesPresenterImpl;
import com.example.benjaminlize.yourvoiceheard.user.User;
import com.example.benjaminlize.yourvoiceheard.utils.Constants;
import com.google.gson.Gson;

/**
 * Created by Vinay Nikhil Pabba on 31-01-2016.
 */
public class PreferencesFragment extends Fragment implements PreferencesView {

    PreferencesPresenter presenter;

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate (R.layout.fragment_main, container, false);

        presenter = new PreferencesPresenterImpl (this);

        generatePreferences ();

        return view;
    }

    private User getUser(){
        SharedPreferences sharedPreferences = getContext ().getSharedPreferences (Constants.MY_PREF, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String userJson = sharedPreferences.getString ("user", "");
        User user = gson.fromJson (userJson, User.class);
        return user;
    }

    @Override
    public void generatePreferences () {
        presenter.generateCategories ();
    }
}
