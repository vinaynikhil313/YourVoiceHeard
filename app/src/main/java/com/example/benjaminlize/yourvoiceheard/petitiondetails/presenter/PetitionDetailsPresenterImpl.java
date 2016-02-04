package com.example.benjaminlize.yourvoiceheard.petitiondetails.presenter;

import android.util.Log;

import com.example.benjaminlize.yourvoiceheard.petitiondetails.Petition;
import com.example.benjaminlize.yourvoiceheard.petitiondetails.interactor.PetitionDetailsInteractor;
import com.example.benjaminlize.yourvoiceheard.petitiondetails.interactor.PetitionDetailsInteractorImpl;
import com.example.benjaminlize.yourvoiceheard.petitiondetails.view.PetitionDetailsView;
import com.example.benjaminlize.yourvoiceheard.user.User;
import com.example.benjaminlize.yourvoiceheard.utils.Utilities;

/**
 * Created by Vinay Nikhil Pabba on 30-01-2016.
 */
public class PetitionDetailsPresenterImpl implements PetitionDetailsPresenter, OnPetitionDetailsFinishedListener {

    PetitionDetailsView view;
    PetitionDetailsInteractor interactor;

    String TAG = Utilities.getTag (this);

    public PetitionDetailsPresenterImpl (PetitionDetailsView view, User user, Petition petition){
        this.view = view;
        interactor = new PetitionDetailsInteractorImpl (user.getUid (), petition.getmUniqueId ());
    }

    @Override
    public void buttonClicked (int flag) {
        Log.i (TAG, "Button " + flag + " clicked");
        view.showProgressDialog ();
        interactor.checkAndUpdateFirebase (flag, this);
    }

    @Override
    public void onChangeOfMind (int numSigns, int numUnsigns) {
        view.updateSignsUnsignsCount (numSigns, numUnsigns);
        view.hideProgressDialog ();
        view.showMessage ("You have changed your mind");
    }

    @Override
    public void onFirstTimeSignUnsign (int numSigns, int numUnsigns) {
        view.updateSignsUnsignsCount (numSigns, numUnsigns);
        view.hideProgressDialog ();
        view.showMessage ("Thank you for your response");
    }

    @Override
    public void onReClick () {
        view.hideProgressDialog ();
        view.showMessage ("You have already expressed your opinion");
    }
}
