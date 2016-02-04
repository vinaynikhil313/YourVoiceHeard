package com.example.benjaminlize.yourvoiceheard.petitions.presenter;

import com.example.benjaminlize.yourvoiceheard.petitiondetails.Petition;

import java.util.List;

/**
 * Created by Vinay Nikhil Pabba on 29-01-2016.
 */
public interface OnPetitionsListFinishedListener {

    void onPetitionsListGenerated(List<Petition> petitionList);

    void onNewPetitionAdded(Petition petition);

    void onPetitionRemoved(Petition petition);

}
