package com.example.benjaminlize.yourvoiceheard.petition.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.benjaminlize.yourvoiceheard.R;
import com.example.benjaminlize.yourvoiceheard.petition.Petition;
import com.example.benjaminlize.yourvoiceheard.utils.Constants;
import com.example.benjaminlize.yourvoiceheard.utils.SignUnsignPetition;
import com.example.benjaminlize.yourvoiceheard.petition.presenter.PetitionPresenter;
import com.example.benjaminlize.yourvoiceheard.petition.presenter.PetitionPresenterImpl;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.squareup.picasso.Picasso;

/**
 * Created by Vinay Nikhil Pabba on 18-01-2016.
 */
public class PetitionActivity extends AppCompatActivity
        implements PetitionView, View.OnClickListener {

    TextView title;
    ImageView image;
    TextView description;
    Button sign;
    Button unsign;
    TextView signsCount;
    TextView unsignsCount;
    Petition petition;

    PetitionPresenter presenter;

    ProgressDialog progressDialog;

    private final String TAG = PetitionActivity.class.getSimpleName ();

    int buttonPressed = 0;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.petition_activity_main);
        Log.i (TAG, "Petition Activity created");

        SharedPreferences sharedPreferences = getSharedPreferences (Constants.MY_PREF, Context.MODE_PRIVATE);
        String uid = sharedPreferences.getString ("uid", "");

        petition = (Petition) getIntent ().getSerializableExtra ("petition");

        presenter = new PetitionPresenterImpl (this, uid, petition.getmUniqueId ());

        title = (TextView) findViewById (R.id.petitionTitle);
        image = (ImageView) findViewById (R.id.petition_image);
        description = (TextView) findViewById (R.id.petition_long_description);
        sign = (Button) findViewById (R.id.button_sign);
        unsign = (Button) findViewById (R.id.button_unsign);
        signsCount = (TextView) findViewById (R.id.signsCount);
        unsignsCount = (TextView) findViewById (R.id.unsignsCount);

        progressDialog = new ProgressDialog (this);
        progressDialog.setMessage ("Please Wait...");
        progressDialog.setProgressStyle (ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate (true);

        title.setText (petition.getmTitle ());
        description.setText (petition.getmLongDescription ());
        Picasso.with (PetitionActivity.this).load ("http://www.butterflyhomehelp.com/images/BUTTERFLY-ORANGE-969x1024.jpg").into (image);
        signsCount.setText ("Signs : " + petition.getmSigns ());
        unsignsCount.setText ("UnSigns : " + petition.getmUnsigns ());


        Log.i (TAG + " UID ", uid);

        sign.setOnClickListener (this);
        unsign.setOnClickListener (this);

    }

    @Override
    public void onClick (View v) {
        Log.i(TAG, "Button clicked is " + v.getId ());
        switch (v.getId ()){
            case R.id.button_sign:
                buttonPressed = Constants.SIGN;
                break;

            case R.id.button_unsign:
                buttonPressed = Constants.UNSIGN;
                break;
        }

        presenter.buttonClicked(buttonPressed);

    }

    @Override
    public void updateSignsUnsignsCount (int numSigns, int numUnsigns) {
        signsCount.setText("Signs : " + numSigns);
        unsignsCount.setText ("Unsigns : " + numUnsigns);
    }

    @Override
    public void showMessage (String message) {
        Toast.makeText (PetitionActivity.this, message, Toast.LENGTH_SHORT).show ();
    }

    @Override
    public void showProgressDialog () {
        progressDialog.show ();
    }

    @Override
    public void hideProgressDialog () {
        progressDialog.hide ();
    }

}
