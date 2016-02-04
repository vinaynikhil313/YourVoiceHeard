package com.example.benjaminlize.yourvoiceheard.petitiondetails.view;

/**
 * Created by Vinay Nikhil Pabba on 01-02-2016.
 */
import android.os.Bundle;

import com.example.benjaminlize.yourvoiceheard.utils.Constants;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.OnInitializedListener;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
public class YouTubeFragment extends YouTubePlayerSupportFragment {

    private YouTubePlayer activePlayer;

    public static YouTubeFragment newInstance(String url) {

        YouTubeFragment youTubeFragment = new YouTubeFragment ();

        Bundle bundle = new Bundle();
        bundle.putString("url", url);

        youTubeFragment.setArguments (bundle);

        youTubeFragment.init ();

        return youTubeFragment;
    }

    private void init() {

        initialize(Constants.DEVELOPER_KEY, new OnInitializedListener () {

            @Override
            public void onInitializationFailure (Provider arg0, YouTubeInitializationResult arg1) {
            }

            @Override
            public void onInitializationSuccess (YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
                activePlayer = player;
                activePlayer.setPlayerStyle (YouTubePlayer.PlayerStyle.DEFAULT);
                if (! wasRestored) {
                    activePlayer.loadVideo (getArguments ().getString ("url"), 0);

                }
            }
        });
    }

}