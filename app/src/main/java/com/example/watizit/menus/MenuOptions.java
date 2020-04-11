package com.example.watizit.menus;

import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
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
    SeekBar volumeseekBar;
    AudioManager audioManager;

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
