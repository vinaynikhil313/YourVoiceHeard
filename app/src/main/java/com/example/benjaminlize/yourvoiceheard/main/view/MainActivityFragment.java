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
        presenter.generatePetitionList ();
        /*if(petitionsDisplayAdapter != null){
            petitionsDisplayAdapter.notifyDataSetChanged ();
        }*/

    }

    @Override
    public void setDisplayAdapter (List<Petition> petitionList) {
        petitionsDisplayAdapter = new PetitionsDisplayAdapter (getContext (), petitionList);
        //petitionsDisplayAdapter.notifyDataSetChanged ();
        //listView.removeAllViewsInLayout ();
        //petitionsDisplayAdapter.clear();
        listView.setAdapter (petitionsDisplayAdapter);
    }

}
