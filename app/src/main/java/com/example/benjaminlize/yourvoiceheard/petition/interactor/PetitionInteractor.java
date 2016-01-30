package com.example.benjaminlize.yourvoiceheard.petition.interactor;

import com.example.benjaminlize.yourvoiceheard.petition.presenter.OnPetitionFinishedListener;

/**
 * Created by Vinay Nikhil Pabba on 29-01-2016.
 */
public interface PetitionInteractor {

    void checkAndUpdateFirebase(int flag, OnPetitionFinishedListener listener);
    
}
