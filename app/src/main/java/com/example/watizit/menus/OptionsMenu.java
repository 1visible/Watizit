package com.example.watizit.menus;

import android.content.Context;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.watizit.R;
import com.example.watizit.utils.DesignUtil;
import com.example.watizit.utils.LocaleUtil;

/**
 * This class represents the options menu where the user can configure the language and the music volume.
 */
public class OptionsMenu extends AppCompatActivity {

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



        setContentView(R.layout.options_menu); // apply options menu layout

        /*                  📌 VARIABLES

            • Retrieve views for design, click listener and/or gameplay
            • Retrieve other objects for gameplay purposes
        */

        Button backButton = findViewById(R.id.backButton1);
        TextView optionsText = findViewById(R.id.optionsText);
        ImageView FRFlagImage = findViewById(R.id.FRFlagImage);
        ImageView ENFlagImage = findViewById(R.id.ENFlagImage);
        ImageView SPFlagImage = findViewById(R.id.SPFlagImage);
        SeekBar volumeseekBar = findViewById(R.id.volumeSeekbar);
        TextView copyrightText = findViewById(R.id.copyrightText);

        final Context context = this;



        /*                  🎨 DESIGN & ACTIONS

            • Apply background color to buttons and text drawable
            • Apply icons to texts in views (if the text is made up of regular text + icons)
            • Make links in text views clickable
            • Apply click listeners to buttons
        */

        // Apply background color to buttons and text
        DesignUtil.setBgColor(backButton, R.color.COLOR_RED);
        DesignUtil.setBgColor(optionsText, R.color.COLOR_OVERLAY);
        DesignUtil.setBgColor(copyrightText, R.color.COLOR_OVERLAY);
        // Make the links in the copyright text clickable
        copyrightText.setMovementMethod(LinkMovementMethod.getInstance());
        // When the back button is clicked, finish the activity (and so go back to main menu)
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        // When the french flag image is clicked, switch the locale to french and finish activity
        FRFlagImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocaleUtil.setLocale(context, "fr");
                finish();
            }
        });
        // When the english flag image is clicked, switch the locale to english and finish activity
        ENFlagImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocaleUtil.setLocale(context, "en");
                finish();
            }
        });
        // When the spanish flag image is clicked, switch the locale to spanish and finish activity
        SPFlagImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocaleUtil.setLocale(context, "sp");
                finish();
            }
        });

        //                      END OF BLOCKS                      //

        // Set max value of seekbar (the volume can go from 0 to 100%)
        volumeseekBar.setMax(100);
        // If the music service exists
        if (MainMenu.musicService != null) {
            // Set the seekbar progress to the music volume
            volumeseekBar.setProgress(MainMenu.musicService.getVolume());
            // And set its progress change listener to set the volume of the music to the progress of the seekbar
            volumeseekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                /**
                 * This method is called when the seekbar progress changes. When it's the case, we
                 * set the music volume to the progress of the seekbar.
                 *
                 * @param seekBar the seekbar that changed
                 * @param progress the progress of the seekbar
                 * @param fromUser returns true if a user made this action
                 */
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    MainMenu.musicService.setVolume(progress);
                }
                @Override
                public void onStartTrackingTouch(SeekBar seekBar) { }
                @Override
                public void onStopTrackingTouch(SeekBar seekBar) { }
            });
        }
    }

    /**
     * This method is called when the activity is stopped. We save the music service volume to Shared
     * Preferences. We do that here instead of doing this on seekbar progress to improve performance.
     */
    @Override
    protected void onStop() {
        super.onStop();
        // If the music service exists, save its volume to Shared Preferences
        if (MainMenu.musicService != null)
            MainMenu.musicService.saveVolume();
    }

}
