package com.example.benjaminlize.yourvoiceheard.petition.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.benjaminlize.yourvoiceheard.R;
import com.example.benjaminlize.yourvoiceheard.utils.Constants;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayerView;

/**
 * Created by Vinay Nikhil Pabba on 31-01-2016.
 */
public class YouTubeFragment extends YouTubePlayerFragment implements YouTubePlayer.OnInitializedListener {

    View view;

    YouTubePlayerView youTubeView;

    private static final int RECOVERY_DIALOG_REQUEST = 1;

    @Override
    public View onCreateView (LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        view = layoutInflater.inflate (R.layout.youtube_fragment, viewGroup, false);

        youTubeView = (YouTubePlayerView) view.findViewById (R.id.youtube_view);
        youTubeView.initialize (Constants.DEVELOPER_KEY, this);

        return view;
    }

    @Override
    public void onInitializationFailure (YouTubePlayer.Provider provider,
                                         YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError ()) {
            errorReason.getErrorDialog ((Activity) getContext (), RECOVERY_DIALOG_REQUEST).show ();
        } else {
            String errorMessage = "Youtube Player Culd not be initialized";
            Toast.makeText (getContext (), errorMessage, Toast.LENGTH_LONG).show ();
        }
    }

    @Override
    public void onInitializationSuccess (YouTubePlayer.Provider provider,
                                         YouTubePlayer player, boolean wasRestored) {
        if (! wasRestored) {

            // loadVideo() will auto play video
            // Use cueVideo() method, if you don't want to play it automatically
            player.loadVideo (Constants.YOUTUBE_VIDEO_CODE);

            // Hiding player controls
            //player.setPlayerStyle(PlayerStyle.CHROMELESS);
        }
    }

    @Override
    public void onActivityResult (int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_DIALOG_REQUEST) {
            // Retry initialization if user performed a recovery action
            getYouTubePlayerProvider ().initialize (Constants.DEVELOPER_KEY, this);
        }
        super.onActivityResult (requestCode, resultCode, data);
    }

    private YouTubePlayer.Provider getYouTubePlayerProvider () {
        return (YouTubePlayerView) view.findViewById (R.id.youtube_view);
    }
}
