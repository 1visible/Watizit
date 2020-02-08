package com.example.watizit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }


    public void option(View view){
        startActivity(new Intent(this, MenuOptions.class));
    }

    public void jouer(View view){
        startActivity(new Intent(this, MenuJouer.class));
    }
}
