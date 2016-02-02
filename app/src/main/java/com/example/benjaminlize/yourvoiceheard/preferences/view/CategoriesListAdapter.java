package com.example.benjaminlize.yourvoiceheard.preferences.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.example.benjaminlize.yourvoiceheard.R;
import com.example.benjaminlize.yourvoiceheard.category.Category;
import com.example.benjaminlize.yourvoiceheard.preferences.presenter.PreferencesPresenter;
import com.example.benjaminlize.yourvoiceheard.user.User;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Vinay Nikhil Pabba on 02-02-2016.
 */
public class CategoriesListAdapter extends ArrayAdapter<Category> {

    private Context context;
    List<Category> categories;
    User user;

    PreferencesPresenter presenter;

    public CategoriesListAdapter (Context context, List<Category> categories, User user, PreferencesPresenter presenter) {

        super (context, 0, categories);

        this.context = context;
        this.categories = categories;
        this.user = user;
        this.presenter = presenter;

    }

    @Override
    public View getView (final int position, View convertView, ViewGroup parent) {

        final Category category = (Category) getItem (position);

        if (category == null) {
            return null;
        }
        if (convertView == null) {
            convertView = LayoutInflater.from (context).inflate (R.layout.preferences_item, parent, false);
        }

        CheckBox categoryCheckBox = (CheckBox) convertView.findViewById (R.id.checkBox);
        categoryCheckBox.setText (category.getName ());
        Map<String, String> userPreferences = user.getPreferences ();
        Log.i ("CategoriesAdapter", category.getName ());
        Log.i ("CategoriesAdapter", userPreferences.toString ());

        boolean initialCheck = false;

        if (userPreferences.containsKey (category.getCid ())) {
            initialCheck = true;
        }

        Log.i ("CategoriesAdapter", "Initial check " + initialCheck);
        categoryCheckBox.setChecked (initialCheck);
        categoryCheckBox.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                Log.i ("Category Adapter", "Check box state changed");
                Log.i ("Category Adapter", "Position = " + position);
                presenter.changePreferences (user, position, category, ((CheckBox) v).isChecked ());

            }
        });

        return convertView;
    }
}
