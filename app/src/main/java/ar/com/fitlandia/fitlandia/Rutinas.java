package ar.com.fitlandia.fitlandia;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.android.youtube.player.YouTubePlayer.PlayerStateChangeListener;
import com.google.android.youtube.player.YouTubePlayer.PlaybackEventListener;

import ar.com.fitlandia.fitlandia.models.EjercicioModel;
import ar.com.fitlandia.fitlandia.utils.ApplicationGlobal;


public class Rutinas extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {
 public static final String API_KEY="AIzaSyCu4a_6MCExg3mUQvMr0CsgMLkoMhYNf38";
 //public static final String VIDEO_ID="R7JlVgWMsk8";
 public static String VIDEO_ID="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rutinas);
        YouTubePlayerView youTubePlayerView = (YouTubePlayerView) findViewById(R.id.v_youtube);
        youTubePlayerView.initialize(API_KEY, this);

        ApplicationGlobal applicationGlobal = ApplicationGlobal.getInstance();
        EjercicioModel ejercicioModel = applicationGlobal.getEjercicioSelected();
        VIDEO_ID = ejercicioModel.getVideoId();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean b) {
/** add listeners to YouTubePlayer instance **/
        player.setPlayerStateChangeListener(playerStateChangeListener);
        player.setPlaybackEventListener(playbackEventListener);
/** Start buffering **/
        if (!b) {
            player.cueVideo(VIDEO_ID);
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

    }
    private PlaybackEventListener playbackEventListener = new PlaybackEventListener() {
        @Override
        public void onBuffering(boolean arg0) {
        }
        @Override
        public void onPaused() {
        }
        @Override
        public void onPlaying() {
        }
        @Override
        public void onSeekTo(int arg0) {
        }
        @Override
        public void onStopped() {
            String jj = "";
            jj = jj.toLowerCase();
        }
    };

    private PlayerStateChangeListener playerStateChangeListener = new PlayerStateChangeListener() {
        @Override
        public void onAdStarted() {
        }
        @Override
        public void onError(YouTubePlayer.ErrorReason arg0) {
        }
        @Override
        public void onLoaded(String arg0) {
        }
        @Override
        public void onLoading() {
        }
        @Override
        public void onVideoEnded() {
            String jj = "";
            jj = jj.toLowerCase();
        }
        @Override
        public void onVideoStarted() {
        }
    };
}
