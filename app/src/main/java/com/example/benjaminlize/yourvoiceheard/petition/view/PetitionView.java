package com.example.benjaminlize.yourvoiceheard.petition.view;

/**
 * Created by Vinay Nikhil Pabba on 29-01-2016.
 */
public interface PetitionView {

    void showMessage(String message);

    void updateSignsUnsignsCount(int numSigns, int numUnsigns);

    void showProgressDialog();

    void hideProgressDialog();

}
