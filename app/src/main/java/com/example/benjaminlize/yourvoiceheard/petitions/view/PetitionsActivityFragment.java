package com.example.benjaminlize.yourvoiceheard.petitions.view;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.benjaminlize.yourvoiceheard.petitions.presenter.PetitionsPresenter;
import com.example.benjaminlize.yourvoiceheard.petitions.presenter.PetitionsPresenterImpl;
import com.example.benjaminlize.yourvoiceheard.petitiondetails.Petition;
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
public class PetitionsActivityFragment extends Fragment implements PetitionsActivityFragmentView {

    View viewHolder;

    PetitionsPresenter presenter;

    ListView listView;
    PetitionsListDisplayAdapter petitionsListDisplayAdapter;

    public PetitionsActivityFragment () {
    }

    String TAG = Utilities.getTag (this);

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        viewHolder = inflater.inflate (R.layout.fragment_main, container, false);

        listView = (ListView) viewHolder.findViewById (R.id.listView);

        presenter = new PetitionsPresenterImpl (this);
        Log.i (TAG, "Presenter created and called");
        return viewHolder;
    }


    @Override
    public void onResume () {
        super.onResume ();
        presenter.generatePetitionList (getUser ());

    }

    @Override
    public void setDisplayAdapter (List<Petition> petitionList) {
        if (getActivity () != null) {
            petitionsListDisplayAdapter = new PetitionsListDisplayAdapter (getContext (), petitionList);
            listView.setAdapter (petitionsListDisplayAdapter);
        }
    }

    private User getUser () {
        SharedPreferences sharedPreferences = getContext ().getSharedPreferences (Constants.MY_PREF, Context.MODE_PRIVATE);
        Gson gson = new Gson ();
        String userJson = sharedPreferences.getString ("user", "");
        return gson.fromJson (userJson, User.class);
    }

    @Override
    public void showMessage (String message) {
        Toast.makeText (getContext (), message, Toast.LENGTH_LONG).show ();
    }
}
