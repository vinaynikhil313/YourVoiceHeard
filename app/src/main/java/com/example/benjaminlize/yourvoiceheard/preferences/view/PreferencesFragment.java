package com.example.benjaminlize.yourvoiceheard.preferences.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import com.example.benjaminlize.yourvoiceheard.R;
import com.example.benjaminlize.yourvoiceheard.category.Category;
import com.example.benjaminlize.yourvoiceheard.preferences.presenter.PreferencesPresenter;
import com.example.benjaminlize.yourvoiceheard.preferences.presenter.PreferencesPresenterImpl;
import com.example.benjaminlize.yourvoiceheard.user.User;
import com.example.benjaminlize.yourvoiceheard.utils.Constants;
import com.google.gson.Gson;

import java.util.List;
import java.util.Map;

/**
 * Created by Vinay Nikhil Pabba on 31-01-2016.
 */
public class PreferencesFragment extends Fragment implements PreferencesView {

    PreferencesPresenter presenter;

    ListView listView;

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate (R.layout.fragment_main, container, false);

        presenter = new PreferencesPresenterImpl (this);

        listView = (ListView) view.findViewById (R.id.listView);

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

        /*listView.setOnItemClickListener (new AdapterView.OnItemClickListener () {
            @Override
            public void onItemClick (AdapterView<?> parent, View view, int position, long id) {
                String main = listView.getSelectedItem().toString();
                Log.i ("Preferences Fragment" , main);
                presenter.changePreferences (getUser (), position, categories.get (position), ((CheckBox) view).isChecked ());
            }
        });*/
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

    private View getViewByPosition(int pos) {
        final int firstListItemPosition = listView.getFirstVisiblePosition ();
        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

        Log.i("PreferencesFragment", firstListItemPosition + " " + lastListItemPosition + " " + pos);

        if (pos < firstListItemPosition || pos > lastListItemPosition ) {
            return listView.getAdapter().getView(pos, null, listView);
        } else {
            final int childIndex = pos - firstListItemPosition;
            Log.i("PreferencesFragment", ((Category)listView.getAdapter ().getItem (childIndex)).getName ());
            return listView.getChildAt(childIndex);
        }
    }

    @Override
    public void unCheck (int position, String message) {
        //((CheckBox)getViewByPosition (position)).setChecked (false);
        ((CheckBox) getViewByPosition (position).findViewById (R.id.checkBox)).setChecked (false);

        showMessage (message);
    }

    private void showMessage (String message) {
        Toast.makeText (getContext (), message, Toast.LENGTH_SHORT).show ();
    }
}
