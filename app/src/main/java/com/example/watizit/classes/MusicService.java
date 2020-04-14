package com.example.watizit.classes;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

import com.example.watizit.R;

public class MusicService extends Service {

    private final IBinder binder = new ServiceBinder();
    MediaPlayer mediaPlayer;
    private int length = 0;

    public MusicService() { }

    public class ServiceBinder extends Binder
    {
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

        if (mediaPlayer != null)
        {
            mediaPlayer.setLooping(true);
            mediaPlayer.setVolume(25, 25);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        if (mediaPlayer != null)
            mediaPlayer.start();

        return START_NOT_STICKY;
    }

    public void pauseMusic()
    {
        if (mediaPlayer != null && mediaPlayer.isPlaying())
        {
            mediaPlayer.pause();
            length = mediaPlayer.getCurrentPosition();
        }
    }

    public void resumeMusic()
    {
        if (mediaPlayer != null && !mediaPlayer.isPlaying())
        {
            mediaPlayer.seekTo(length);
            mediaPlayer.start();
        }
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
