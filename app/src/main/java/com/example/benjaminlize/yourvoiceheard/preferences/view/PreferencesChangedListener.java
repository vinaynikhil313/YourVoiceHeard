package com.example.benjaminlize.yourvoiceheard.preferences.view;


import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.example.benjaminlize.yourvoiceheard.preferences.presenter.PreferencesPresenter;
import com.example.benjaminlize.yourvoiceheard.user.User;
import com.example.benjaminlize.yourvoiceheard.utils.Constants;

/**
 * Created by Vinay Nikhil Pabba on 02-02-2016.
 */
public class PreferencesChangedListener implements View.OnClickListener {

    private boolean originalCheck;
    private PreferencesPresenter presenter;
    private User user;
    private String cid;

    public PreferencesChangedListener(String cid, User user, boolean originalCheck, PreferencesPresenter presenter){
        this.originalCheck = originalCheck;
        this.user = user;
        this.presenter = presenter;
        this.cid = cid;
    }

    /*@Override
    public void onCheckedChanged (CompoundButton buttonView, boolean isChecked) {

        Log.i ("CHECK LISTENER", "onCheckedChanged");

        if(isChecked == true) {
            if (user.getPreferences ().size () == Constants.MAX_PREFERENCES) {
                buttonView.setChecked (false);
            }
            else{
                user.getPreferences ().put (cid, "preferred");
                presenter.changePreferences (user);
            }
        }
        else{
            user.getPreferences ().remove (cid);
            presenter.changePreferences (user);
        }

    }*/

    @Override
    public void onClick (View v) {
        Log.i ("CHECK LISTENER", "onCheckedChanged");

        if(((CheckBox)v).isChecked ()) {
            if (user.getPreferences ().size () == Constants.MAX_PREFERENCES) {
                ((CheckBox) v).setChecked (false);
            }
            else{
                user.getPreferences ().put (cid, "preferred");
                //presenter.changePreferences (user, cid);
            }
        }
        else{
            user.getPreferences ().remove (cid);
            //presenter.changePreferences (user, cid);
        }
    }
}
