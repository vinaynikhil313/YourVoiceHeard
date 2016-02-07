package com.example.benjaminlize.yourvoiceheard.notifications.presenter;

import com.example.benjaminlize.yourvoiceheard.user.User;

/**
 * Created by Vinay Nikhil Pabba on 07-02-2016.
 */
public interface NotificationServicePresenter {

    void startNotifications(User user);

    void stopNotifications();

}
