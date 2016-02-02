package com.example.benjaminlize.yourvoiceheard.main.interactor;

import com.example.benjaminlize.yourvoiceheard.main.presenter.OnMainFinishedListener;
import com.example.benjaminlize.yourvoiceheard.user.User;

/**
 * Created by Vinay Nikhil Pabba on 29-01-2016.
 */
public interface MainInteractor {

    public void generatePetitionList(User user, OnMainFinishedListener listener);

}
