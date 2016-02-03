package com.example.benjaminlize.yourvoiceheard.main.view;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcel;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.benjaminlize.yourvoiceheard.main.presenter.MainPresenter;
import com.example.benjaminlize.yourvoiceheard.main.presenter.MainPresenterImpl;
import com.example.benjaminlize.yourvoiceheard.petition.Petition;
import com.example.benjaminlize.yourvoiceheard.R;
import com.example.benjaminlize.yourvoiceheard.user.User;
import com.example.benjaminlize.yourvoiceheard.utils.Constants;
import com.example.benjaminlize.yourvoiceheard.utils.Utilities;
import com.google.gson.Gson;

import java.util.List;

/**
 * A placeholder for all the petitions.
 * Calls the PetitionsDisplayAdapter which displays the petitions on the screen
 */
public class MainActivityFragment extends Fragment implements MainActivityFragmentView {

    View viewHolder;

    MainPresenter presenter;

    ListView listView;
    PetitionsDisplayAdapter petitionsDisplayAdapter;

    NotificationManager notificationManager;

    public MainActivityFragment () {
    }

    String TAG = Utilities.getTag (this);

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        viewHolder = inflater.inflate (R.layout.fragment_main, container, false);

        notificationManager = (NotificationManager) getContext ().getSystemService(Context.NOTIFICATION_SERVICE);

        //int notifId = getActivity ().getIntent ().getIntExtra (Constants.NOTIF_ID, 0);
        Bundle extras = getActivity ().getIntent ().getExtras ();
        int notifId = 0;
        if(extras != null)
            notifId = getActivity ().getIntent ().getExtras ().getInt (Constants.NOTIF_ID);
        Log.i(TAG, "Notif id is " + notifId);
        if(notifId != 0){
            notificationManager.cancel (notifId);
        }

        listView = (ListView) viewHolder.findViewById (R.id.listView);

        presenter = new MainPresenterImpl (this);
        Log.i (TAG, "Presenter created and called");
        return viewHolder;
    }


    @Override
    public void onResume () {
        super.onResume ();
        presenter.generatePetitionList (getUser ());

    }

    @Override
    public void setDisplayAdapter (List<Petition> petitionList) {
        petitionsDisplayAdapter = new PetitionsDisplayAdapter (getContext (), petitionList);
        listView.setAdapter (petitionsDisplayAdapter);
    }

    private User getUser(){
        SharedPreferences sharedPreferences = getContext ().getSharedPreferences (Constants.MY_PREF, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String userJson = sharedPreferences.getString ("user", "");
        return gson.fromJson (userJson, User.class);
    }

    @Override
    public void showNotification (Petition petition) {

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(getContext ());
        stackBuilder.addParentStack(MainActivity.class);

        Intent i = new Intent(getContext (), MainActivity.class);
        //i.putExtra (Constants.NOTIF_ID, 243);
        Bundle extras = new Bundle ();
        extras.putInt (Constants.NOTIF_ID, 243);
        i.putExtras (extras);
        stackBuilder.addNextIntent (i);
        PendingIntent pendingIntent = stackBuilder.getPendingIntent (0, PendingIntent.FLAG_UPDATE_CURRENT); //PendingIntent.getActivity (getContext (), (int) System.currentTimeMillis(), i, 0);

        Notification notification = new NotificationCompat.Builder (getContext ())
                .setContentTitle ("New Petition added - " + petition.getmTitle ())
                .setContentText (petition.getmShortDescription ())
                .setTicker ("New Petition Added!")
                .setSmallIcon (R.mipmap.ic_launcher)
                .setContentIntent (pendingIntent)
                .addExtras (extras)
                .build ();

        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        notificationManager.notify (0, notification);
    }
}
