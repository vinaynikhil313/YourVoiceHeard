package com.example.benjaminlize.yourvoiceheard.notifications.view;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import com.example.benjaminlize.yourvoiceheard.R;
import com.example.benjaminlize.yourvoiceheard.notifications.presenter.NotificationServicePresenter;
import com.example.benjaminlize.yourvoiceheard.notifications.presenter.NotificationServicePresenterImpl;
import com.example.benjaminlize.yourvoiceheard.petitiondetails.Petition;
import com.example.benjaminlize.yourvoiceheard.petitiondetails.view.PetitionDetailsActivity;
import com.example.benjaminlize.yourvoiceheard.petitions.view.PetitionsActivity;
import com.example.benjaminlize.yourvoiceheard.user.User;
import com.example.benjaminlize.yourvoiceheard.utils.Constants;
import com.example.benjaminlize.yourvoiceheard.utils.Utilities;
import com.google.gson.Gson;

/**
 * Created by Vinay Nikhil Pabba on 07-02-2016.
 */
public class NotificationService extends Service implements NotificationServiceView {

    NotificationManager notificationManager;
    NotificationServicePresenter presenter;

    @Override
    public int onStartCommand (Intent intent, int flags, int startId) {
        Log.i (Utilities.getTag (this), "Notifications Serviice Created");
        notificationManager = (NotificationManager) getSystemService (Context.NOTIFICATION_SERVICE);
        presenter = new NotificationServicePresenterImpl (this);
        presenter.startNotifications (getUser ());
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind (Intent intent) {
        return null;
    }

    @Override
    public void createNotification (Petition petition) {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder (this)
                        .setSmallIcon (R.mipmap.ic_launcher)
                        .setContentTitle ("New petition added")
                        .setContentText (petition.getmTitle ())
                        .setAutoCancel (true);
// Creates an explicit intent for an Activity in your app
        //Intent resultIntent = new Intent (this, PetitionsActivity.class);
        Intent resultIntent = new Intent (this, PetitionDetailsActivity.class);
        resultIntent.putExtra ("petition", petition);

// The stack builder object will contain an artificial back stack for the
// started Activity.
// This ensures that navigating backward from the Activity leads out of
// your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create (this);
// Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack (PetitionsActivity.class);
// Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent (resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent (
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent (resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService (Context.NOTIFICATION_SERVICE);
// mId allows you to update the notification later on.
        mNotificationManager.notify (0, mBuilder.build ());
    }

    private User getUser(){
        SharedPreferences sharedPreferences = getSharedPreferences (Constants.MY_PREF, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String userJson = sharedPreferences.getString ("user", "");
        User user = gson.fromJson (userJson, User.class);
        return user;
    }

    @Override
    public void onDestroy () {
        presenter.stopNotifications ();
        Log.i(Utilities.getTag (this), "Notif Service destroyed");
    }
}
