package com.example.benjaminlize.yourvoiceheard.petitions.view;

import com.example.benjaminlize.yourvoiceheard.petitiondetails.Petition;

import java.util.List;

/**
 * Created by Vinay Nikhil Pabba on 29-01-2016.
 */
public interface PetitionsActivityFragmentView {

    void setDisplayAdapter(List<Petition> petitionList);

    void showNotification(Petition petition);

    void showMessage(String message);

}
