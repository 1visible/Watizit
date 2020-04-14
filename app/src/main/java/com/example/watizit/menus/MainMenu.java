package com.example.watizit.menus;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PowerManager;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.watizit.R;
import com.example.watizit.classes.HomeWatcher;
import com.example.watizit.classes.MusicService;
import com.example.watizit.utils.DesignUtil;
import com.example.watizit.utils.LocaleUtil;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Button playButton = findViewById(R.id.playButton);
        Button optionsButton = findViewById(R.id.optionsButton);

        DesignUtil.startBounceIn(playButton, 0.15F);
        DesignUtil.startBounceIn(optionsButton, 0.3F);

        doBindService();
        Intent music = new Intent();
        music.setClass(this, MusicService.class);
        startService(music);

        HomeWatcher homeWatcher = new HomeWatcher(this);

        homeWatcher.setOnHomePressedListener(new HomeWatcher.OnHomePressedListener() {
            @Override
            public void onHomePressed()
            {
                if (musicService != null)
                    musicService.pauseMusic();
            }
            @Override
            public void onHomeLongPressed()
            {
                if (musicService != null)
                    musicService.pauseMusic();
            }
        });
        homeWatcher.startWatch();
    }

    private boolean isBound = false;
    private MusicService musicService;

    private ServiceConnection serviceConnection = new ServiceConnection()
    {
        public void onServiceConnected(ComponentName name, IBinder binder)
        {
            musicService = ((MusicService.ServiceBinder)binder).getService();
        }
        public void onServiceDisconnected(ComponentName name)
        {
            musicService = null;
        }
    };

    void doBindService()
    {
        bindService(new Intent(this, MusicService.class),
                serviceConnection, Context.BIND_AUTO_CREATE);
        isBound = true;
    }

    void doUnbindService()
    {
        if(isBound)
        {
            unbindService(serviceConnection);
            isBound = false;
        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        if(LocaleUtil.isLocaleStored(this)
                && !LocaleUtil.getLocaleStored(this).equals(LocaleUtil.getLocale(this)))
            LocaleUtil.setLocale(this, LocaleUtil.getLocaleStored(this));

        Button playButton = findViewById(R.id.playButton);
        Button optionsButton = findViewById(R.id.optionsButton);
        final Intent levelsListMenuIntent = new Intent(this, LevelsListMenu.class);
        final Intent optionsMenuIntent = new Intent(this, OptionsMenu.class);

        String playButtonText = getResources().getString(R.string.mainMenu_playButton);
        String optionsButtonText = getResources().getString(R.string.mainMenu_optionsButton);

        playButton.setText(DesignUtil.applyIcons(playButtonText, 0.75F));
        optionsButton.setText(DesignUtil.applyIcons(optionsButtonText, 0.75F));

        DesignUtil.setBgColor(playButton, R.color.COLOR_PRIMARY);
        DesignUtil.setBgColor(optionsButton, R.color.COLOR_OVERLAY);

        playButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(levelsListMenuIntent);
            }
        });
        optionsButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(optionsMenuIntent);
            }
        });

        if (musicService != null)
            musicService.resumeMusic();
    }

    @Override
    protected void onPause()
    {
        super.onPause();

        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        boolean isScreenOn = false;
        
        if(pm != null)
            isScreenOn = pm.isScreenOn();
        
        if (!isScreenOn && musicService != null)
            musicService.pauseMusic();

    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();

        doUnbindService();
        Intent music = new Intent();
        music.setClass(this, MusicService.class);
        stopService(music);
    }

}
