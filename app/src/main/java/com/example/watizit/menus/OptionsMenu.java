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
 * The type Options menu.
 */
public class OptionsMenu extends AppCompatActivity {

    @Override
    protected void onResume()
    {
        super.onResume();

        if(LocaleUtil.isLocaleStored(this)
                && !LocaleUtil.getLocaleStored(this).equals(LocaleUtil.getLocale(this)))
            LocaleUtil.setLocale(this, LocaleUtil.getLocaleStored(this));

        setContentView(R.layout.options_menu);

        final Context context = this;
        Button backButton = findViewById(R.id.backButton1);
        TextView optionsText = findViewById(R.id.optionsText);
        ImageView FRFlagImage = findViewById(R.id.FRFlagImage);
        ImageView ENFlagImage = findViewById(R.id.ENFlagImage);
        ImageView SPFlagImage = findViewById(R.id.SPFlagImage);
        SeekBar volumeseekBar = findViewById(R.id.volumeSeekbar);
        TextView copyrightText = findViewById(R.id.copyrightText);

        DesignUtil.setBgColor(backButton, R.color.COLOR_RED);
        DesignUtil.setBgColor(optionsText, R.color.COLOR_OVERLAY);
        DesignUtil.setBgColor(copyrightText, R.color.COLOR_OVERLAY);

        backButton.setText(DesignUtil.applyIcons(backButton.getText(), 1F));

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { finish();
            }
        });
        FRFlagImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { LocaleUtil.setLocale(context, "fr");
            finish();
            }
        });
        ENFlagImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { LocaleUtil.setLocale(context, "en");
            finish();
            }
        });
        SPFlagImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { LocaleUtil.setLocale(context, "sp");
            finish();
            }
        });

        copyrightText.setMovementMethod(LinkMovementMethod.getInstance());

        volumeseekBar.setMax(100);
        if(MainMenu.musicService != null)
        {
            volumeseekBar.setProgress(MainMenu.musicService.getVolume());
            volumeseekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
                {
                    MainMenu.musicService.setVolume(progress);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) { }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) { }
            });
        }

    }

    @Override
    protected void onStop()
    {
        super.onStop();
        if(MainMenu.musicService != null)
            MainMenu.musicService.saveVolume();
    }

}
