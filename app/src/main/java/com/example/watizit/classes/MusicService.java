package com.example.watizit.classes;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

import com.example.watizit.R;

/**
 * The class represents the background music service that plays through the whole app.
 */
public class MusicService extends Service {
    private final IBinder binder = new ServiceBinder(); // binder for the service
    MediaPlayer mediaPlayer; // media player for the song
    private int length = 0; // length of media player
    private int volume; // volume of media player (needed to get the media player volume and sync it with the seekbar)

    /**
     * Instantiates a new music service.
     */
    public MusicService() { }

    /**
     * This class represents the binder for the music service.
     */
    public class ServiceBinder extends Binder {
        /**
         * This method is used to get the music service.
         *
         * @return the service
         */
        public MusicService getService() {
            return MusicService.this;
        }
    }

    /**
     * This method is called on service bind and returns the binder.
     *
     * @param intent the intent
     * @return the binder
     */
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    /**
     * This method is used when the service is created and launches the media player.
     */
    @Override
    public void onCreate() {
        super.onCreate();
        // Create the media player and retrieve the default/saved volume
        mediaPlayer = MediaPlayer.create(this, R.raw.theme_music);
        volume = getVolume();
        // If the media player has been successfully created
        if (mediaPlayer != null) {
            mediaPlayer.setLooping(true); // loop the music
            setVolume(volume); // and set its volume
        }
    }

    /**
     * This method is used when the service is started which starts the music.
     *
     * @param intent the intent
     * @param flags the flags
     * @param startId the id
     * @return the start state
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Start the media player
        if (mediaPlayer != null)
            mediaPlayer.start();

        return START_NOT_STICKY;
    }

    /**
     * This method pauses the music.
     */
    public void pauseMusic() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            // Save the current time-point of the music
            length = mediaPlayer.getCurrentPosition();
        }
    }

    /**
     * This method resumes the music.
     */
    public void resumeMusic() {
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            // Go back to the saved time-point where we paused the music
            mediaPlayer.seekTo(length);
            mediaPlayer.start();
        }
    }

    /**
     * This method sets the volume of the music.
     *
     * @param volume the volume to set
     */
    public void setVolume(int volume) {
        // Save the new volume
        this.volume = volume;
        // Use a logarithmic function to scale the volume for the media player (convert 0 - 100 to 0.0 - 1.0)
        float logVolume = (float) (1 - (Math.log(101 - volume) / Math.log(101)));
        // And set scaled log volume to the media player
        mediaPlayer.setVolume(logVolume, logVolume);
    }

    /**
     * This method gets the volume of the music from Shared Preferences.
     *
     * @return the volume of music
     */
    public int getVolume() {
        Context context = App.getContext(); // static context
        // Get UID key for the Shared Preferences
        String sp_key = context.getString(R.string.UID);
        // Open Shared Preferences
        SharedPreferences pref = context.getSharedPreferences(sp_key, Context.MODE_PRIVATE);
        // And return the volume, or 25 as a default value (used on the app first launch)
        return pref.getInt("volume", 25);
    }

    /**
     * This method saves the volume of the music in Shared Preferences.
     */
    public void saveVolume() {
        Context context = App.getContext(); // static context
        // Get UID key for the Shared Preferences
        String sp_key = context.getString(R.string.UID);
        // Open and edit Shared Preferences
        SharedPreferences pref = context.getSharedPreferences(sp_key, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        // And put the volume in, then save
        editor.putInt("volume", volume);
        editor.apply();
    }

    /**
     * This method is called when the service is destroyed (app finish)
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        // Stop and destroy the media player on service destroy (free memory)
        if (mediaPlayer != null)
            try {
                mediaPlayer.stop();
                mediaPlayer.release();
            } finally {
                mediaPlayer = null;
            }
    }
}
