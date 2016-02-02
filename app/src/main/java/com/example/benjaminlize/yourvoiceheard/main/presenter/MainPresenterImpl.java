package com.example.benjaminlize.yourvoiceheard.main.presenter;

import android.util.Log;

import com.example.benjaminlize.yourvoiceheard.main.interactor.MainInteractor;
import com.example.benjaminlize.yourvoiceheard.main.interactor.MainInteractorImpl;
import com.example.benjaminlize.yourvoiceheard.main.view.MainActivityFragmentView;
import com.example.benjaminlize.yourvoiceheard.petition.Petition;
import com.example.benjaminlize.yourvoiceheard.user.User;
import com.example.benjaminlize.yourvoiceheard.utils.Utilities;

import java.util.List;

/**
 * Created by Vinay Nikhil Pabba on 29-01-2016.
 */
public class MainPresenterImpl implements MainPresenter, OnMainFinishedListener {

    MainActivityFragmentView view;
    MainInteractor interactor;

    String TAG = Utilities.getTag (this);

    public MainPresenterImpl(MainActivityFragmentView view){
        this.view = view;
        interactor = new MainInteractorImpl ();
    }

    @Override
    public void generatePetitionList(User user){
        interactor.generatePetitionList (user, this);
        Log.i (TAG, "Interactor called");
    }

    @Override
    public void onPetitionsListGenerated (List<Petition> petitionList) {
        view.setDisplayAdapter (petitionList);
        Log.i(TAG, "List Created");
    }

    @Override
    public void onNewPetitionAdded (Petition petition) {
        view.showNotification (petition);
    }

    @Override
    public void onPetitionRemoved (Petition petition) {

    }
}
