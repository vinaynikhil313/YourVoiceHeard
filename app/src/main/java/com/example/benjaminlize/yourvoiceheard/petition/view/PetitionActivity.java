package com.example.benjaminlize.yourvoiceheard.petition.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
public class PetitionActivity extends YouTubeBaseActivity
        implements PetitionView, View.OnClickListener, YouTubePlayer.OnInitializedListener {

    TextView title;
    ImageView image;
    TextView description;
    Button sign;
    Button unsign;
    Petition petition;

    YouTubePlayerView youTubeView;
    private static final int RECOVERY_DIALOG_REQUEST = 1;

    PetitionPresenter presenter;

    ProgressDialog progressDialog;

    private final String TAG = PetitionActivity.class.getSimpleName ();

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.petition_activity_main);
        Log.i (TAG, "Petition Activity created");

        SharedPreferences sharedPreferences = getSharedPreferences (Constants.MY_PREF, Context.MODE_PRIVATE);
        String uid = sharedPreferences.getString ("uid", "");

        petition = (Petition) getIntent ().getSerializableExtra ("petition");

        presenter = new PetitionPresenterImpl (this, uid, petition.getmUniqueId ());

        title = (TextView) findViewById (R.id.petition_title);
        image = (ImageView) findViewById (R.id.petition_image);
        description = (TextView) findViewById (R.id.petition_long_description);
        sign = (Button) findViewById (R.id.button_sign);
        unsign = (Button) findViewById (R.id.button_unsign);

        youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);
        youTubeView.initialize(Constants.DEVELOPER_KEY, this);

        progressDialog = new ProgressDialog (this);
        progressDialog.setMessage ("Please Wait...");
        progressDialog.setProgressStyle (ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate (true);

        title.setText (petition.getmTitle ());
        description.setText (petition.getmLongDescription ());
        Picasso.with (PetitionActivity.this).load ("http://www.butterflyhomehelp.com/images/BUTTERFLY-ORANGE-969x1024.jpg").into (image);

        Log.i (TAG + " UID ", uid);

        sign.setOnClickListener (this);
        unsign.setOnClickListener (this);

    }

    @Override
    public void onClick (View v) {
        Log.i(TAG, "Button clicked is " + v.getId ());
        int flag = 0;
        switch (v.getId ()){
            case R.id.button_sign:
                flag = Constants.SIGN;
                break;

            case R.id.button_unsign:
                flag = Constants.UNSIGN;
                break;
        }

        presenter.buttonClicked(flag);

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

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                        YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_DIALOG_REQUEST).show();
        } else {
            String errorMessage = "Youtube Player Culd not be initialized";
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                        YouTubePlayer player, boolean wasRestored) {
        if (!wasRestored) {

            // loadVideo() will auto play video
            // Use cueVideo() method, if you don't want to play it automatically
            player.loadVideo(Constants.YOUTUBE_VIDEO_CODE);

            // Hiding player controls
            //player.setPlayerStyle(PlayerStyle.CHROMELESS);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_DIALOG_REQUEST) {
            // Retry initialization if user performed a recovery action
            getYouTubePlayerProvider().initialize(Constants.DEVELOPER_KEY, this);
        }
    }

    private YouTubePlayer.Provider getYouTubePlayerProvider() {
        return (YouTubePlayerView) findViewById(R.id.youtube_view);
    }

}
