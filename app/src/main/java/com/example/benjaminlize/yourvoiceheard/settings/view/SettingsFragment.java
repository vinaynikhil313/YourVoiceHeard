package com.example.benjaminlize.yourvoiceheard.settings.view;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.benjaminlize.yourvoiceheard.R;
import com.example.benjaminlize.yourvoiceheard.preferences.view.PreferencesActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vinay Nikhil Pabba on 31-01-2016.
 */
public class SettingsFragment extends Fragment {

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate (R.layout.fragment_main, container, false);
        String[] settings = new String[] {"Preferences",
                "Help",
                "About Us"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext (),
                android.R.layout.simple_list_item_2, android.R.id.text2, settings);

        ListView listView = (ListView) view.findViewById (R.id.listView);
        listView.setAdapter (adapter);

        listView.setOnItemClickListener (new AdapterView.OnItemClickListener () {
            @Override
            public void onItemClick (AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    openPreferences();
                }
            }
        });

        //listView.setAdapter ();

        return view;
    }

    private void openPreferences () {

        Intent intent = new Intent(getContext (), PreferencesActivity.class);
        intent.putExtra("caller",
                getContext ().getClass ().getSimpleName ());
        startActivity(intent);
        //startActivity (new Intent(getContext (), PreferencesActivity.class));

    }
}
