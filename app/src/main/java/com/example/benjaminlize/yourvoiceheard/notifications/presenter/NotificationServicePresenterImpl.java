package com.example.benjaminlize.yourvoiceheard.notifications.presenter;

import com.example.benjaminlize.yourvoiceheard.notifications.interactor.NotificationServiceInteractor;
import com.example.benjaminlize.yourvoiceheard.notifications.interactor.NotificationServiceInteractorImpl;
import com.example.benjaminlize.yourvoiceheard.notifications.view.NotificationServiceView;
import com.example.benjaminlize.yourvoiceheard.petitiondetails.Petition;
import com.example.benjaminlize.yourvoiceheard.user.User;

/**
 * Created by Vinay Nikhil Pabba on 07-02-2016.
 */
public class NotificationServicePresenterImpl implements NotificationServicePresenter, OnNewPetitionListener {

    NotificationServiceView view;
    NotificationServiceInteractor interactor;

    public NotificationServicePresenterImpl (NotificationServiceView view) {

        this.view = view;
        interactor = new NotificationServiceInteractorImpl ();

    }

    @Override
    public void startNotifications (User user) {
        interactor.startNotifications (user, this);
    }

    @Override
    public void onNewPetition (Petition petition) {
        view.createNotification (petition);
    }

    @Override
    public void stopNotifications () {
        interactor.stopNotifications ();
    }
}
