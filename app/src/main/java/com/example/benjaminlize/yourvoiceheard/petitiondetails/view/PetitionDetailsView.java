package com.example.benjaminlize.yourvoiceheard.petitiondetails.view;

/**
 * Created by Vinay Nikhil Pabba on 29-01-2016.
 */
public interface PetitionDetailsView {

    void showMessage(String message);

    void updateSignsUnsignsCount(int numSigns, int numUnsigns);

    void showProgressDialog();

    void hideProgressDialog();

}
