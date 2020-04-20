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

/**
 * This class represents the main menu of the application where we can navigate to other
 * menus. This class also launch the background music service and the home watcher.
 */
public class MainMenu extends AppCompatActivity {
    private boolean isBound = false; // boolean to check if the music service is bound or not
    public static MusicService musicService; // background music service

    /**
     * This method is used when the activity is created. It applies the main menu content to the activity
     * and apply some animations. It also creates and bind the music service, and launches the home watcher.
     *
     * @param savedInstanceState the state of the saved instance
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // apply main menu layout

        // Apply animations to menu buttons (only on create, not on resume)
        Button playButton = findViewById(R.id.playButton);
        Button optionsButton = findViewById(R.id.optionsButton);
        DesignUtil.startBounceIn(playButton, 0.15F);
        DesignUtil.startBounceIn(optionsButton, 0.3F);

        // Preparing music service and home watcher
        Intent musicItent = new Intent();
        HomeWatcher homeWatcher = new HomeWatcher(this);
        // Bind music service and start it
        doBindService();
        musicItent.setClass(this, MusicService.class);
        startService(musicItent);
        // Override home button press listener methods to pause the music when the app is not focused
        homeWatcher.setOnHomePressedListener(new HomeWatcher.OnHomePressedListener() {
            @Override
            public void onHomePressed() {
                if (musicService != null)
                    musicService.pauseMusic();
            }
            @Override
            public void onHomeLongPressed() {
                if (musicService != null)
                    musicService.pauseMusic();
            }
        });
        // Start watching for a home button press
        homeWatcher.startWatch();
    }
    // Link bound music service to main menu
    private ServiceConnection serviceConnection = new ServiceConnection() {
        // Update music service in main menu on service connect (after binding)
        public void onServiceConnected(ComponentName name, IBinder binder) {
            musicService = ((MusicService.ServiceBinder) binder).getService();
        }
        // Remove the music service on service disconnect (free memory)
        public void onServiceDisconnected(ComponentName name) {
            musicService = null;
        }
    };

    /**
     * This method is used to bind the music service.
     */
    void doBindService() {
        bindService(new Intent(this, MusicService.class),
                serviceConnection, Context.BIND_AUTO_CREATE);
        isBound = true;
    }

    /**
     * This method is used to unbind the music service.
     */
    void doUnbindService() {
        if (isBound) {
            unbindService(serviceConnection);
            isBound = false;
        }
    }

    /**
     * This method is used when the activity start, restart or gain focus. It applies the main
     * components to the activity such as the language, the design and the logic.
     */
    @Override
    protected void onResume() {
        super.onResume();

        /*                  🌍 LANGUAGE

            • Compare current locale to stored locale and updating it if needed
        */

        if (LocaleUtil.isLocaleStored(this)
                && !LocaleUtil.getLocaleStored(this).equals(LocaleUtil.getLocale(this)))
            LocaleUtil.setLocale(this, LocaleUtil.getLocaleStored(this));



        /*                  📌 VARIABLES

            • Retrieve views for design, click listener and/or gameplay
            • Retrieve other objects for gameplay purposes
        */

        Button playButton = findViewById(R.id.playButton);
        Button optionsButton = findViewById(R.id.optionsButton);

        // Intents that will be applied to 'Play' and 'Options' buttons to go to other activities
        final Intent levelsListMenuIntent = new Intent(this, LevelsListMenu.class);
        final Intent optionsMenuIntent = new Intent(this, OptionsMenu.class);
        // Retrieve texts from current locale resources
        String playButtonText = getResources().getString(R.string.mainMenu_playButton);
        String optionsButtonText = getResources().getString(R.string.mainMenu_optionsButton);



        /*                  🎨 DESIGN & ACTIONS

            • Apply background color to buttons drawable
            • Apply icons to texts in views (if the text is made up of regular text + icons)
            • Apply click listeners to buttons
        */

        // Apply background color to buttons
        DesignUtil.setBgColor(playButton, R.color.COLOR_PRIMARY);
        DesignUtil.setBgColor(optionsButton, R.color.COLOR_OVERLAY);
        // Apply icons to texts in buttons
        playButton.setText(DesignUtil.applyIcons(playButtonText, 0.75F));
        optionsButton.setText(DesignUtil.applyIcons(optionsButtonText, 0.75F));
        // When the play button is clicked, go to levels list menu
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(levelsListMenuIntent);
            }
        });
        // When the options button is clicked, go to options menu
        optionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(optionsMenuIntent);
            }
        });

        //                      END OF BLOCKS                      //

        // Play/Resume music when the app gain focus
        if (musicService != null)
            musicService.resumeMusic();
    }

    /**
     * This method is called when the activity is paused (app unfocused or app on another activity).
     * If the activity is paused and the app is unfocused (not on screen) we pause the music.
     */
    @Override
    protected void onPause() {
        super.onPause();
        boolean isScreenOn = false; // boolean that checks if the app is on screen
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        // Set isOnScreen value
        if (pm != null)
            isScreenOn = pm.isScreenOn();
        // If the app isn't focused and the music service exists, pause it
        if (!isScreenOn && musicService != null)
            musicService.pauseMusic();
    }

    /**
     * This method is called when the menu is destroyed (app finish).
     * We unbind and stop the music service to free memory.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Unbind the music service
        doUnbindService();
        Intent music = new Intent();
        music.setClass(this, MusicService.class);
        stopService(music); // and stop it
    }
}
