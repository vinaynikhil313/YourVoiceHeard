package com.example.benjaminlize.yourvoiceheard.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.benjaminlize.yourvoiceheard.application.YourVoiceHeardApplication;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Vinay Nikhil Pabba on 21-01-2016.
 */
public class UpdateFirebaseLogin {

    private static final Firebase firebase = new Firebase("https://yourvoiceheard.firebaseio.com");


    public static void updateFirebase(AuthData authData){
        Map<String, Object> map = new HashMap<String, Object> ();
        map.put ("provider", authData.getProvider ());
        map.put ("accessToken", authData.getToken ());
        map.put ("uid", authData.getUid ());
        map.putAll (authData.getProviderData ());
        //SharedPreferences sharedPreferences = .getSharedPreferences ("MyPref", Context.MODE_PRIVATE);

        //Log.i (TAG + " Token ", authData.getToken ());
        //Log.i (TAG + " Expires in ", authData.getExpires () + "");
        /*SharedPreferences sharedPreferences = YourVoiceHeardApplication.getSharedPreferences ();
        SharedPreferences.Editor editor = sharedPreferences.edit ();
        editor.putString ("provider", authData.getProvider ());
        editor.putString ("uid", authData.getUid ());
        editor.putString ("accessToken", authData.getToken ());*/
        if(map.containsKey ("isTemporaryPassword"))
            map.remove ("isTemporaryPassword");
        if(map.containsKey ("temporaryPassword"))
            map.remove ("temporaryPassword");
        if(map.containsKey ("cachedUserProfile"))
            map.remove ("cachedUserProfile");
        /*
        if(authData.getProvider ().equals ("password")){
            Log.i (TAG, map.keySet ().toString ());
            Log.i (TAG, map.values ().toString ());
            editor.putString ("email", (String) map.get ("email"));
            editor.putString ("accessToken", (String) map.get ("accessToken"));
        }

        editor.commit ();*/
        firebase.child ("users").child (authData.getUid ()).updateChildren (map);
        //Log.i (TAG, "Logged in with provider - " + authData.getProvider ());
    }

}
