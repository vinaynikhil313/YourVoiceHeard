package com.example.benjaminlize.yourvoiceheard.petition.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
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
import com.squareup.picasso.Picasso;

/**
 * Created by Vinay Nikhil Pabba on 18-01-2016.
 */
public class PetitionActivity extends AppCompatActivity implements PetitionView, View.OnClickListener {

    TextView title;
    ImageView image;
    TextView description;
    Button sign;
    Button unsign;
    Petition petition;

    PetitionPresenter presenter;

    ProgressDialog progressDialog;

    private final String TAG = PetitionActivity.class.getSimpleName ();

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.petition_activity_main);
        //getActionBar().setDisplayHomeAsUpEnabled(true);
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

        progressDialog = new ProgressDialog (this);
        progressDialog.setMessage ("Please Wait...");
        progressDialog.setProgressStyle (ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate (true);

        title.setText (petition.getmTitle ());
        description.setText (petition.getmLongDescription ());
        Picasso.with (PetitionActivity.this).load ("http://www.butterflyhomehelp.com/images/BUTTERFLY-ORANGE-969x1024.jpg").into (image);

        Log.i (TAG + " UID ", uid);

        //sign.setOnClickListener (new SignUnsignPetition (SignUnsignPetition.SIGN, petition, uid, this));
        //unsign.setOnClickListener (new SignUnsignPetition (SignUnsignPetition.UNSIGN, petition, uid, this));

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
}
