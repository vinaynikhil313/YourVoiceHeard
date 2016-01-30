package com.example.benjaminlize.yourvoiceheard.petition.presenter;

import android.util.Log;

import com.example.benjaminlize.yourvoiceheard.petition.Petition;
import com.example.benjaminlize.yourvoiceheard.petition.interactor.PetitionInteractor;
import com.example.benjaminlize.yourvoiceheard.petition.interactor.PetitionInteractorImpl;
import com.example.benjaminlize.yourvoiceheard.petition.view.PetitionView;
import com.example.benjaminlize.yourvoiceheard.utils.Utilities;

/**
 * Created by Vinay Nikhil Pabba on 30-01-2016.
 */
public class PetitionPresenterImpl implements PetitionPresenter, OnPetitionFinishedListener {

    PetitionView view;
    PetitionInteractor interactor;

    String TAG = Utilities.getTag (this);

    public PetitionPresenterImpl(PetitionView view, String uid, String petitionId){
        this.view = view;
        interactor = new PetitionInteractorImpl (uid, petitionId);
    }

    @Override
    public void buttonClicked (int flag) {
        Log.i (TAG, "Button " + flag + " clicked");
        view.showProgressDialog ();
        interactor.checkAndUpdateFirebase (flag, this);
    }

    @Override
    public void onChangeOfMind () {
        view.hideProgressDialog ();
        view.showMessage ("You have changed your mind");
    }

    @Override
    public void onFirstTimeSignUnsign () {
        view.hideProgressDialog ();
        view.showMessage ("Thank you for your response");
    }

    @Override
    public void onReClick () {
        view.hideProgressDialog ();
        view.showMessage ("You have already expressed your opinion");
    }
}
