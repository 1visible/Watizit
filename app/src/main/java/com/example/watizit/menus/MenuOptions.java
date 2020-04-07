package com.example.watizit.menus;

import android.content.ContentResolver;
import android.content.Intent;
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
    SeekBar seekBar;
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

        seekBar = findViewById(R.id.light);
        seekBar.setMax(255);
        seekBar.setProgress(getLuminosite());
        //getPermission();

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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
                Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                intent.setData(Uri.parse("package :" + getApplicationContext().getPackageName()));
                startActivityForResult(intent, 1000);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultcode, Intent data){
        if(requestCode == 1000){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                boolean value = Settings.System.canWrite(getApplicationContext());
                if (value){
                    succes = true;
                }
                else {
                    toastMessage("Permission not granted");
                }
            }
        }
    }


    private  void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}
