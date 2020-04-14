package com.example.watizit.menus;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.watizit.R;
import com.example.watizit.utils.DesignUtil;
import com.example.watizit.utils.LocaleUtil;

public class OptionsMenu extends AppCompatActivity {


    SeekBar volumeseekBar;
    AudioManager audioManager;
    boolean succes = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.options_menu);

        final Context context = this;
        final Intent mainMenuIntent = new Intent(context, MainMenu.class);
        Button backButton = findViewById(R.id.backButton1);
        TextView optionsText = findViewById(R.id.optionsText);
        ImageView FRFlagImage = findViewById(R.id.FRFlagImage);
        ImageView ENFlagImage = findViewById(R.id.ENFlagImage);
        ImageView SPFlagImage = findViewById(R.id.SPFlagImage);
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
            finish(); startActivity(mainMenuIntent);
            }
        });
        ENFlagImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { LocaleUtil.setLocale(context, "en");
            finish(); startActivity(mainMenuIntent);
            }
        });
        SPFlagImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { LocaleUtil.setLocale(context, "sp");
            finish(); startActivity(mainMenuIntent);
            }
        });

        copyrightText.setMovementMethod(LinkMovementMethod.getInstance());

        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        changeVolume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultcode, Intent data) {
        super.onActivityResult(requestCode, resultcode, data);
        if (requestCode == 1000) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                boolean value = Settings.System.canWrite(getApplicationContext());
                if (value) {
                    succes = true;
                } else {
                    Toast.makeText(this, "Permission not granted", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private  void changeVolume(){
        try {
            volumeseekBar = findViewById(R.id.volumeSeekbar);
            audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            volumeseekBar.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
            volumeseekBar.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
            volumeseekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,progress,0);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });


        }catch (Exception e) { e.printStackTrace(); }

    }
}
