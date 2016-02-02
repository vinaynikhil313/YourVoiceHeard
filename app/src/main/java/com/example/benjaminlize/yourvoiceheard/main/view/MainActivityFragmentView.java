package com.example.benjaminlize.yourvoiceheard.main.view;

import com.example.benjaminlize.yourvoiceheard.petition.Petition;

import java.util.List;

/**
 * Created by Vinay Nikhil Pabba on 29-01-2016.
 */
public interface MainActivityFragmentView {

    void setDisplayAdapter(List<Petition> petitionList);

    void showNotification(Petition petition);

}
