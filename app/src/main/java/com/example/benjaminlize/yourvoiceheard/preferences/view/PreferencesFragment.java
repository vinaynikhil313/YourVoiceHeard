package com.example.benjaminlize.yourvoiceheard.preferences.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import com.example.benjaminlize.yourvoiceheard.R;
import com.example.benjaminlize.yourvoiceheard.category.Category;
import com.example.benjaminlize.yourvoiceheard.notifications.view.NotificationService;
import com.example.benjaminlize.yourvoiceheard.preferences.presenter.PreferencesPresenter;
import com.example.benjaminlize.yourvoiceheard.preferences.presenter.PreferencesPresenterImpl;
import com.example.benjaminlize.yourvoiceheard.user.User;
import com.example.benjaminlize.yourvoiceheard.utils.Constants;
import com.google.gson.Gson;

import java.util.List;

/**
 * Created by Vinay Nikhil Pabba on 31-01-2016.
 */
public class PreferencesFragment extends Fragment implements PreferencesView {

    PreferencesPresenter presenter;

    ListView listView;

    Intent i;

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate (R.layout.fragment_main, container, false);

        presenter = new PreferencesPresenterImpl (this);

        listView = (ListView) view.findViewById (R.id.listView);
        i = new Intent (getContext (), NotificationService.class);

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

    @Override
    public void onListGenerated (final List<Category> categories) {
        CategoriesListAdapter adapter = new CategoriesListAdapter (getContext (), categories, getUser (), presenter);
        listView.setAdapter (adapter);
    }

    @Override
    public void writeToSharedPreferences (User user) {
        SharedPreferences sharedPreferences = getContext ().getSharedPreferences (Constants.MY_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit ();

        Gson gson = new Gson();
        String userJson = gson.toJson (user);
        editor.putString ("user", userJson);
        editor.commit ();

    }

    @Override
    public void unCheck (int position, String message) {
        ((CheckBox) listView.getChildAt (position).findViewById (R.id.checkBox)).setChecked (false);
        showMessage (message);
    }

    private void showMessage (String message) {
        Toast.makeText (getContext (), message, Toast.LENGTH_SHORT).show ();
    }

    @Override
    public void changeService (User user) {
        getContext ().stopService (i);
        getContext ().startService (i);
    }
}
