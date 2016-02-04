package com.example.benjaminlize.yourvoiceheard.petitions.presenter;

import android.util.Log;

import com.example.benjaminlize.yourvoiceheard.petitions.interactor.PetitionsInteractor;
import com.example.benjaminlize.yourvoiceheard.petitions.interactor.PetitionsInteractorImpl;
import com.example.benjaminlize.yourvoiceheard.petitions.view.PetitionsActivityFragmentView;
import com.example.benjaminlize.yourvoiceheard.petitiondetails.Petition;
import com.example.benjaminlize.yourvoiceheard.user.User;
import com.example.benjaminlize.yourvoiceheard.utils.Utilities;

import java.util.List;

/**
 * Created by Vinay Nikhil Pabba on 29-01-2016.
 */
public class PetitionsPresenterImpl implements PetitionsPresenter, OnPetitionsListFinishedListener {

    PetitionsActivityFragmentView view;
    PetitionsInteractor interactor;

    String TAG = Utilities.getTag (this);

    public PetitionsPresenterImpl (PetitionsActivityFragmentView view){
        this.view = view;
        interactor = new PetitionsInteractorImpl ();
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
        view.showMessage ("New Petition added. Please scroll below");
    }

    @Override
    public void onPetitionRemoved (Petition petition) {

    }
}
