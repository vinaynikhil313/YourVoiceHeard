package com.example.benjaminlize.yourvoiceheard.petitions.view;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.benjaminlize.yourvoiceheard.petitiondetails.Petition;
import com.example.benjaminlize.yourvoiceheard.R;
import com.example.benjaminlize.yourvoiceheard.petitiondetails.view.PetitionDetailsActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by benjamin.lize on 19/12/2015.
 * Opens the individual petition page when a petition is clicked
 */
public class PetitionsListDisplayAdapter extends ArrayAdapter<Petition> {

    public PetitionsListDisplayAdapter (Context context, List<Petition> petitionArray) {
        super (context, 0, petitionArray);
    }

    @Override
    public Petition getItem (int position) {
        return super.getItem (position);
    }

    @Override
    public View getView (int position, View convertView, ViewGroup parent) {

        final Petition petition = getItem (position);
        if (petition == null) {
            return null;
        }

        if (convertView == null) {
            convertView = LayoutInflater.from (getContext ()).inflate (R.layout.list_item, parent, false);
        }

        // Check if an existing view is being reused, otherwise inflate the view

        // Lookup view for data population
        TextView title = (TextView) convertView.findViewById (R.id.petitionTitle);
        TextView description = (TextView) convertView.findViewById (R.id.petitionShortDescription);
        ImageView imageView = (ImageView) convertView.findViewById (R.id.petitionImage);
        TextView numSigns = (TextView) convertView.findViewById (R.id.signsCount);
        TextView numUnsigns = (TextView) convertView.findViewById (R.id.unsignsCount);
        // Populate the data into the template view using the data object
        title.setText (petition.getmTitle ());
        description.setText (petition.getmShortDescription ());
        Log.i ("DisplayAdapter petition", petition.getmTitle ());
        if (! petition.getmImageUrl ().equals ("") && ! petition.getmImageUrl ().isEmpty ())
            Picasso.with (getContext ()).load (petition.getmImageUrl ()).into (imageView);
        numSigns.setText ("Sign : " + petition.getmSigns ());
        numUnsigns.setText ("Disagree : " + petition.getmUnsigns ());

        convertView.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                Intent i = new Intent (getContext (), PetitionDetailsActivity.class);
                i.putExtra ("petition", petition);
                Log.i ("DisplayAdapter", "Petition clicked");
                getContext ().startActivity (i);
            }
        });

        // Return the completed view to render on screen
        return convertView;
    }

}
