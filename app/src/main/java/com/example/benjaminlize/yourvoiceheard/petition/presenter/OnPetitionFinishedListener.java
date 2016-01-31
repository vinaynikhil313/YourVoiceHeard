package com.example.benjaminlize.yourvoiceheard.petition.presenter;

import com.example.benjaminlize.yourvoiceheard.petition.Petition;

/**
 * Created by Vinay Nikhil Pabba on 29-01-2016.
 */
public interface OnPetitionFinishedListener {

    void onFirstTimeSignUnsign(int numSigns, int numUnsigns);

    void onChangeOfMind(int numSigns, int numUnsigns);

    void onReClick();

}
