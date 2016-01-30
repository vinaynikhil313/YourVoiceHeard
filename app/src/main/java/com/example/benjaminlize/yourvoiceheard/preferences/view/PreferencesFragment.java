package com.example.benjaminlize.yourvoiceheard.preferences.view;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.benjaminlize.yourvoiceheard.R;

/**
 * Created by Vinay Nikhil Pabba on 31-01-2016.
 */
public class PreferencesFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate (R.layout.fragment_main, container, false);

        return view;
    }
}
