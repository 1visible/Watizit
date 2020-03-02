package com.example.watizit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button bouton_jouer = findViewById(R.id.jouer);
        Button bouton_options = findViewById(R.id.options);

        WatizUtil.setButtonIcon(this, bouton_jouer, 0.75F, true);
        WatizUtil.setButtonIcon(this, bouton_options, 0.75F, true);

        WatizUtil.setBackgroundColor(this, bouton_jouer, R.color.COLOR_BLUE);
        WatizUtil.setBackgroundColor(this, bouton_options, R.color.COLOR_GRAY);

    }


    public void option(View view){
        startActivity(new Intent(this, MenuOptions.class));
    }

    public void jouer(View view){
        startActivity(new Intent(this, MenuJouer.class));
    }

}
