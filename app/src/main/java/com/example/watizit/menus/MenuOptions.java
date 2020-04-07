package com.example.watizit.menus;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.watizit.R;
import com.example.watizit.utils.WatizUtil;

public class MenuOptions extends AppCompatActivity {


    TextView textView;
    SeekBar luminosityseekBar;
    SeekBar volumeseekBar;
    AudioManager audioManager;
    boolean succes = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.menu_options);

        Button bouton_retour = findViewById(R.id.retour);
        TextView texte_options = findViewById(R.id.options);


        WatizUtil.setButtonIcon(this, bouton_retour, 1F, false);

        WatizUtil.setBackgroundColor(this, bouton_retour, R.color.COLOR_RED);
        WatizUtil.setBackgroundColor(this, texte_options, R.color.COLOR_GRAY);

        textView = findViewById(R.id.copyright);
        textView.setMovementMethod(LinkMovementMethod.getInstance());

        luminosityseekBar = findViewById(R.id.light);
        luminosityseekBar.setMax(255);
        luminosityseekBar.setProgress(getLuminosite());
        getPermission();

        luminosityseekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser && succes){
                    setLuminosite(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if(!succes){
                    toastMessage("Permission not granted!");
                }
            }
        });

        /*int cLuminosite = Settings.System.getInt(getContentResolver(),Settings.System.SCREEN_BRIGHTNESS,0);
        luminosityseekBar.setProgress(cLuminosite);

        luminosityseekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Context context = getApplicationContext();
                boolean canWrite = Settings.System.canWrite(context);
                if(canWrite){
                    int sLuminosite = progress*255/255;
                    Settings.System.putInt(context.getContentResolver(),
                            Settings.System.SCREEN_BRIGHTNESS_MODE,
                            Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
                            Settings.System.putInt(context.getContentResolver(),
                                    Settings.System.SCREEN_BRIGHTNESS,sLuminosite);
                }
                else{
                    Intent intent = new Intent((Settings.ACTION_MANAGE_WRITE_SETTINGS));
                    context.startActivity(intent);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });*/
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        changeVolume();


    }

    public void retour(View view){
        finish();
    }

    public void fr2en(View view) {
        WatizUtil.setLocale("en", this);
    }
    public void en2fr(View view) {
        WatizUtil.setLocale("fr", this);
    }

    private void setLuminosite(int luminosity){
        if (luminosity < 0){
            luminosity = 0;
        }
        else if (luminosity > 255){
            luminosity = 255;
        }
        ContentResolver contentResolver = getApplicationContext().getContentResolver();
        Settings.System.putInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS, luminosity);
    }

    private int getLuminosite(){
        int luminosity = 100;
        try{
            ContentResolver contentResolver = getApplicationContext().getContentResolver();
            luminosity = Settings.System.getInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS);
        }catch (Settings.SettingNotFoundException e){
            e.printStackTrace();
        }
        return  luminosity;
    }

    private void getPermission(){
        boolean value;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            value = Settings.System.canWrite(getApplicationContext());
            if(value){
                succes = true;
            }
            else{
                succes = true;
                Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                intent.setData(Uri.parse("package :" + getApplicationContext().getPackageName()));
                startActivityForResult(intent, 1000);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultcode, Intent data) {
        if (requestCode == 1000) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                boolean value = Settings.System.canWrite(getApplicationContext());
                if (value) {
                    succes = true;
                } else {
                    toastMessage("Permission not granted");
                }
            }
        }
    }

    private  void changeVolume(){
        try {
            volumeseekBar = findViewById(R.id.volume);
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


        }catch (Exception e) {
        }

    }

    private  void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}
