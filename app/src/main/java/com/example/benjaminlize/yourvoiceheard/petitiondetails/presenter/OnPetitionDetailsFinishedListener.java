package com.example.benjaminlize.yourvoiceheard.petitiondetails.presenter;

/**
 * Created by Vinay Nikhil Pabba on 29-01-2016.
 */
public interface OnPetitionDetailsFinishedListener {

    void onFirstTimeSignUnsign(int numSigns, int numUnsigns);

    void onChangeOfMind(int numSigns, int numUnsigns);

    void onReClick();

}
