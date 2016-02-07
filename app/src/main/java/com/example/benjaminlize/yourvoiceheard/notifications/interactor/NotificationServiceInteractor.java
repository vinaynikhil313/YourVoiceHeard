package com.example.benjaminlize.yourvoiceheard.notifications.interactor;

import com.example.benjaminlize.yourvoiceheard.notifications.presenter.OnNewPetitionListener;
import com.example.benjaminlize.yourvoiceheard.user.User;

/**
 * Created by Vinay Nikhil Pabba on 07-02-2016.
 */
public interface NotificationServiceInteractor {

    void startNotifications(User user, OnNewPetitionListener listener);

    void stopNotifications();

}
