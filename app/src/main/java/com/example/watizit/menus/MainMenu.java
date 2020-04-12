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
import com.example.watizit.classes.JouerMusique;
import com.example.watizit.utils.DesignUtil;
import com.example.watizit.utils.LocaleUtil;

public class MainMenu extends AppCompatActivity {

    HomeWatcher mHomeWatcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(LocaleUtil.isLocaleStored()
                && !LocaleUtil.getLocaleStored().equals(LocaleUtil.getLocale()))
        {
            LocaleUtil.setLocale(LocaleUtil.getLocaleStored());
        }

        setContentView(R.layout.activity_main);

        Button playButton = findViewById(R.id.playButton);
        Button optionsButton = findViewById(R.id.optionsButton);
        final Intent levelsListMenuIntent = new Intent(this, LevelsListMenu.class);
        final Intent optionsMenuIntent = new Intent(this, OptionsMenu.class);

        DesignUtil.setBgColor(playButton, R.color.COLOR_BLUE);
        DesignUtil.setBgColor(optionsButton, R.color.COLOR_GRAY);

        playButton.setText(DesignUtil.applyIcons(playButton.getText(), 0.75F));
        optionsButton.setText(DesignUtil.applyIcons(optionsButton.getText(), 0.75F));

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { startActivity(levelsListMenuIntent);
            }
        });
        optionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { startActivity(optionsMenuIntent);
            }
        });

        doBindService();
        Intent music = new Intent();
        music.setClass(this, JouerMusique.class);
        startService(music);

        mHomeWatcher = new HomeWatcher(this);
        mHomeWatcher.setOnHomePressedListener(new HomeWatcher.OnHomePressedListener() {
            @Override
            public void onHomePressed() {
                if (mServ != null) {
                    mServ.pauseMusic();
                }
            }
            @Override
            public void onHomeLongPressed() {
                if (mServ != null) {
                    mServ.pauseMusic();
                }
            }
        });
        mHomeWatcher.startWatch();

    }

    private boolean mIsBound = false;
    private JouerMusique mServ;
    private ServiceConnection Scon =new ServiceConnection(){

        public void onServiceConnected(ComponentName name, IBinder
                binder) {
            mServ = ((JouerMusique.ServiceBinder)binder).getService();
        }

        public void onServiceDisconnected(ComponentName name) {
            mServ = null;
        }
    };

    void doBindService(){
        bindService(new Intent(this,JouerMusique.class),
                Scon, Context.BIND_AUTO_CREATE);
        mIsBound = true;
    }

    void doUnbindService()
    {
        if(mIsBound)
        {
            unbindService(Scon);
            mIsBound = false;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mServ != null) {
            mServ.resumeMusic();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();

        PowerManager pm = (PowerManager)
                getSystemService(Context.POWER_SERVICE);
        boolean isScreenOn = false;
        if (pm != null) {
            isScreenOn = pm.isScreenOn();
        }

        if (!isScreenOn) {
            if (mServ != null) {
                mServ.pauseMusic();
            }
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        doUnbindService();
        Intent music = new Intent();
        music.setClass(this,JouerMusique.class);
        stopService(music);

    }

}
