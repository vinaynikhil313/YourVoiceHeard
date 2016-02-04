package com.example.benjaminlize.yourvoiceheard.petitions.interactor;

import com.example.benjaminlize.yourvoiceheard.petitions.presenter.OnPetitionsListFinishedListener;
import com.example.benjaminlize.yourvoiceheard.user.User;

/**
 * Created by Vinay Nikhil Pabba on 29-01-2016.
 */
public interface PetitionsInteractor {

    public void generatePetitionList(User user, OnPetitionsListFinishedListener listener);

}
