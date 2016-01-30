package com.example.benjaminlize.yourvoiceheard.main.view;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.benjaminlize.yourvoiceheard.petition.Petition;
import com.example.benjaminlize.yourvoiceheard.R;
import com.example.benjaminlize.yourvoiceheard.petition.view.PetitionActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by benjamin.lize on 19/12/2015.
 * Opens the individual petition page when a petition is clicked
 */
public class PetitionsDisplayAdapter extends ArrayAdapter<Petition> {

    public PetitionsDisplayAdapter (Context context, List<Petition> petitionArray) {
        super(context, 0, petitionArray);
    }

    @Override
    public Petition getItem (int position) {
        return super.getItem (position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Petition petition = getItem(position);
        if(petition == null){
            return null;
        }

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        // Check if an existing view is being reused, otherwise inflate the view

        // Lookup view for data population
        TextView title       = (TextView) convertView.findViewById(R.id.textViewTitle);
        TextView description = (TextView) convertView.findViewById(R.id.textViewDescription);
        ImageView imageView  = (ImageView) convertView.findViewById(R.id.imageView);
        TextView numSigns    = (TextView) convertView.findViewById(R.id.signsCount);
        TextView numUnsigns  = (TextView) convertView.findViewById(R.id.unsignsCount);
        // Populate the data into the template view using the data object
        title.setText(petition.getmTitle());
        description.setText(petition.getmShortDescription ());
        Log.i ("DisplayAdapter petition", petition.getmTitle ());
        Picasso.with(getContext()).load("http://www.butterflyhomehelp.com/images/BUTTERFLY-ORANGE-969x1024.jpg").into (imageView);
        numSigns.setText ("Signs : " + petition.getmSigns ());
        numUnsigns.setText ("UnSigns : " + petition.getmUnsigns ());

        convertView.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                Intent i = new Intent(getContext (), PetitionActivity.class);
                i.putExtra ("petition", petition);
                Log.i("DisplayAdapter", "Petition clicked");
                getContext ().startActivity (i);
            }
        });

        // Return the completed view to render on screen
        return convertView;
    }
}
