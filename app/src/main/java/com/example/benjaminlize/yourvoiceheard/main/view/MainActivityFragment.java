package com.example.benjaminlize.yourvoiceheard.main.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.benjaminlize.yourvoiceheard.main.presenter.MainPresenter;
import com.example.benjaminlize.yourvoiceheard.main.presenter.MainPresenterImpl;
import com.example.benjaminlize.yourvoiceheard.petition.Petition;
import com.example.benjaminlize.yourvoiceheard.R;
import com.example.benjaminlize.yourvoiceheard.user.User;
import com.example.benjaminlize.yourvoiceheard.utils.Constants;
import com.example.benjaminlize.yourvoiceheard.utils.Utilities;
import com.google.gson.Gson;

import java.util.List;

/**
 * A placeholder for all the petitions.
 * Calls the PetitionsDisplayAdapter which displays the petitions on the screen
 */
public class MainActivityFragment extends Fragment implements MainActivityFragmentView {

    View viewHolder;

    MainPresenter presenter;

    ListView listView;
    PetitionsDisplayAdapter petitionsDisplayAdapter;

    public MainActivityFragment () {
    }

    String TAG = Utilities.getTag (this);

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        viewHolder = inflater.inflate (R.layout.fragment_main, container, false);

        listView = (ListView) viewHolder.findViewById (R.id.listView);

        presenter = new MainPresenterImpl (this);
        Log.i(TAG, "Presenter created and called");
        return viewHolder;
    }


    @Override
    public void onResume () {
        super.onResume ();
        presenter.generatePetitionList (getUser ());

    }

    @Override
    public void setDisplayAdapter (List<Petition> petitionList) {
        petitionsDisplayAdapter = new PetitionsDisplayAdapter (getContext (), petitionList);
        listView.setAdapter (petitionsDisplayAdapter);
    }

    private User getUser(){
        SharedPreferences sharedPreferences = getContext ().getSharedPreferences (Constants.MY_PREF, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String userJson = sharedPreferences.getString ("user", "");
        return gson.fromJson (userJson, User.class);
    }

}
