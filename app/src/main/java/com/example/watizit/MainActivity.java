package com.example.watizit;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button bouton_jouer = findViewById(R.id.jouer);

        SpannableStringBuilder string = new SpannableStringBuilder(bouton_jouer.getText());
        Typeface typeface = ResourcesCompat.getFont(this, R.font.icons_font);

        string.setSpan(new CustomTypefaceSpan("", typeface), 0, 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        string.setSpan(new RelativeSizeSpan(0.75f), 0, 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        bouton_jouer.setText(string);

    }


    public void option(View view){
        startActivity(new Intent(this, MenuOptions.class));
    }

    public void jouer(View view){
        startActivity(new Intent(this, MenuJouer.class));
    }
}
