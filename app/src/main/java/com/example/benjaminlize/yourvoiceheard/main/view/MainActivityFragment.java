package com.example.benjaminlize.yourvoiceheard.main.view;

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
import com.example.benjaminlize.yourvoiceheard.utils.Utilities;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;

import java.util.List;

/**
 * A placeholder for all the petitions.
 * Calls the PetitionsDisplayAdapter which displays the petitions on the screen
 */
public class MainActivityFragment extends Fragment implements YouTubePlayer.OnInitializedListener, MainActivityFragmentView {

    View viewHolder;

    MainPresenter presenter;

    public MainActivityFragment () {
    }

    String TAG = Utilities.getTag (this);

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        viewHolder = inflater.inflate (R.layout.fragment_main, container, false);

        presenter = new MainPresenterImpl (this);
        presenter.generatePetitionList ();
        Log.i(TAG, "Presenter created and called");
        return viewHolder;
    }

    @Override
    public void setDisplayAdapter (List<Petition> petitionList) {
        PetitionsDisplayAdapter petitionsDisplayAdapter = new PetitionsDisplayAdapter (getContext (), petitionList);

        ListView listView = (ListView) viewHolder.findViewById (R.id.mainListView);
        listView.setAdapter (petitionsDisplayAdapter);
    }

    @Override
    public void onInitializationSuccess (YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {

    }

    @Override
    public void onInitializationFailure (YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

    }

}
