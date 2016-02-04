package com.example.benjaminlize.yourvoiceheard.petitiondetails.interactor;

import com.example.benjaminlize.yourvoiceheard.petitiondetails.presenter.OnPetitionDetailsFinishedListener;

/**
 * Created by Vinay Nikhil Pabba on 29-01-2016.
 */
public interface PetitionDetailsInteractor {

    void checkAndUpdateFirebase(int flag, OnPetitionDetailsFinishedListener listener);
    
}
