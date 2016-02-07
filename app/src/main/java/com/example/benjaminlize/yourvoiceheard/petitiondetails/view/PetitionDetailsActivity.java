package com.example.benjaminlize.yourvoiceheard.petitiondetails.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.benjaminlize.yourvoiceheard.R;
import com.example.benjaminlize.yourvoiceheard.petitiondetails.Petition;
import com.example.benjaminlize.yourvoiceheard.user.User;
import com.example.benjaminlize.yourvoiceheard.utils.Constants;
import com.example.benjaminlize.yourvoiceheard.petitiondetails.presenter.PetitionDetailsPresenter;
import com.example.benjaminlize.yourvoiceheard.petitiondetails.presenter.PetitionDetailsPresenterImpl;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

/**
 * Created by Vinay Nikhil Pabba on 18-01-2016.
 */
public class PetitionDetailsActivity extends AppCompatActivity
        implements PetitionDetailsView, View.OnClickListener {

    TextView title;
    ImageView image;
    TextView description;
    Button sign;
    Button unsign;
    TextView signsCount;
    TextView unsignsCount;
    Petition petition;

    PetitionDetailsPresenter presenter;

    ProgressDialog progressDialog;

    SharedPreferences sharedPreferences;

    private final String TAG = PetitionDetailsActivity.class.getSimpleName ();

    int buttonPressed = 0;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.petition_activity_main);

        Toolbar toolbar = (Toolbar) findViewById (R.id.petitionToolbar);
        setSupportActionBar (toolbar);

        getSupportActionBar ().setDisplayHomeAsUpEnabled (true);

        getSupportActionBar ().setTitle ("Petition");
        toolbar.setTitleTextColor (0xFFFFFFFF);

        Log.i (TAG, "Petition Activity created");

        sharedPreferences = getSharedPreferences (Constants.MY_PREF, Context.MODE_PRIVATE);

        User user = getUser ();

        petition = new Petition ();

        petition = (Petition) getIntent ().getSerializableExtra ("petition");

        presenter = new PetitionDetailsPresenterImpl (this, user, petition);

        Log.i (TAG, "Initializing views");

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
        if (petition.getmImageUrl () != null && ! petition.getmImageUrl ().equals ("") && ! petition.getmImageUrl ().isEmpty ())
            Picasso.with (PetitionDetailsActivity.this).load (petition.getmImageUrl ()).into (image);
        signsCount.setText ("Sign : " + petition.getmSigns ());
        unsignsCount.setText ("Disagree : " + petition.getmUnsigns ());
        Log.i (TAG, "Starting YouTube fragment");
        if (petition.getmVideoUrl () != null && ! petition.getmVideoUrl ().equals ("") && ! petition.getmVideoUrl ().isEmpty ()) {
            YouTubeFragment youTubeFragment = YouTubeFragment.newInstance (petition.getmVideoUrl ());
            getSupportFragmentManager ().beginTransaction ().replace (R.id.youtube_frame, youTubeFragment).commit ();
        }


        Log.i (TAG + " UID ", user.getUid ());

        sign.setOnClickListener (this);
        unsign.setOnClickListener (this);

    }

    private User getUser () {

        String userJson = sharedPreferences.getString ("user", "");
        Gson gson = new Gson ();
        User user = gson.fromJson (userJson, User.class);
        return user;

    }

    @Override
    public void onClick (View v) {
        Log.i (TAG, "Button clicked is " + v.getId ());
        switch (v.getId ()) {
            case R.id.button_sign:
                buttonPressed = Constants.SIGN;
                break;

            case R.id.button_unsign:
                buttonPressed = Constants.UNSIGN;
                break;
        }

        presenter.buttonClicked (buttonPressed);

    }

    @Override
    public void updateSignsUnsignsCount (int numSigns, int numUnsigns) {
        signsCount.setText ("Signs : " + numSigns);
        unsignsCount.setText ("Unsigns : " + numUnsigns);
    }

    @Override
    public void showMessage (String message) {
        Toast.makeText (PetitionDetailsActivity.this, message, Toast.LENGTH_SHORT).show ();
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
