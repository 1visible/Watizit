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
 * The type Music service.
 */
public class MusicService extends Service {

    private final IBinder binder = new ServiceBinder();
    /**
     * The Media player.
     */
    MediaPlayer mediaPlayer;
    private int length = 0;
    private int volume;

    /**
     * Instantiates a new Music service.
     */
    public MusicService() { }

    /**
     * The type Service binder.
     */
    public class ServiceBinder extends Binder
    {
        /**
         * Gets service.
         *
         * @return the service
         */
        public MusicService getService()
        {
            return MusicService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        return binder;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();

        mediaPlayer = MediaPlayer.create(this, R.raw.theme_music);
        volume = getVolume();

        if (mediaPlayer != null)
        {
            mediaPlayer.setLooping(true);
            setVolume(volume);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        if (mediaPlayer != null)
            mediaPlayer.start();

        return START_NOT_STICKY;
    }

    /**
     * Pause music.
     */
    public void pauseMusic()
    {
        if (mediaPlayer != null && mediaPlayer.isPlaying())
        {
            mediaPlayer.pause();
            length = mediaPlayer.getCurrentPosition();
        }
    }

    /**
     * Resume music.
     */
    public void resumeMusic()
    {
        if (mediaPlayer != null && !mediaPlayer.isPlaying())
        {
            mediaPlayer.seekTo(length);
            mediaPlayer.start();
        }
    }

    /**
     * Sets volume.
     *
     * @param volume the volume
     */
    public void setVolume(int volume)
    {
        this.volume = volume;
        float logVolume = (float) (1 - (Math.log(101 - volume) / Math.log(101)));
        mediaPlayer.setVolume(logVolume, logVolume);
    }

    /**
     * Gets volume.
     *
     * @return the volume
     */
    public int getVolume()
    {
        Context context = App.getContext();
        String sp_key = context.getString(R.string.UID);
        SharedPreferences pref = context.getSharedPreferences(sp_key, Context.MODE_PRIVATE);

        return pref.getInt("volume", 25);
    }

    /**
     * Save volume.
     */
    public void saveVolume()
    {
        Context context = App.getContext();
        String sp_key = context.getString(R.string.UID);
        SharedPreferences pref = context.getSharedPreferences(sp_key, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        editor.putInt("volume", volume);
        editor.apply();
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();

        if (mediaPlayer != null)
            try
            {
                mediaPlayer.stop();
                mediaPlayer.release();
            }
            finally
            {
                mediaPlayer = null;
            }
    }
}
