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
import com.example.watizit.utils.HomeWatcher;
import com.example.watizit.utils.JouerMusique;
import com.example.watizit.utils.WatizUtil;

public class MainActivity extends AppCompatActivity {

    HomeWatcher mHomeWatcher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(WatizUtil.isLocaleStored(this)
                && !WatizUtil.getLocaleStored(this).equals(WatizUtil.getLocale(this)))
        {
            WatizUtil.setLocale(WatizUtil.getLocaleStored(this), this);
        }

        setContentView(R.layout.activity_main);

        Button bouton_jouer = findViewById(R.id.jouer);
        Button bouton_options = findViewById(R.id.options);

        WatizUtil.setButtonIcon(this, bouton_jouer, 0.75F, true);
        WatizUtil.setButtonIcon(this, bouton_options, 0.75F, true);

        WatizUtil.setBackgroundColor(this, bouton_jouer, R.color.COLOR_BLUE);
        WatizUtil.setBackgroundColor(this, bouton_options, R.color.COLOR_GRAY);

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

    public void option(View view){
        startActivity(new Intent(this, MenuOptions.class));
    }

    public void jouer(View view){
        startActivity(new Intent(this, MenuNiveaux.class));
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
