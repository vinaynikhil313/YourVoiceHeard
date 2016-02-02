package com.example.benjaminlize.yourvoiceheard.main.presenter;

import com.example.benjaminlize.yourvoiceheard.petition.Petition;

import java.util.List;

/**
 * Created by Vinay Nikhil Pabba on 29-01-2016.
 */
public interface OnMainFinishedListener {

    void onPetitionsListGenerated(List<Petition> petitionList);

    void onNewPetitionAdded(Petition petition);

    void onPetitionRemoved(Petition petition);

}
