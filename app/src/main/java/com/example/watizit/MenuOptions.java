package com.example.watizit;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MenuOptions extends AppCompatActivity {
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.menu_options);

        Button bouton_retour = findViewById(R.id.retour);
        TextView texte_options = findViewById(R.id.options);

        WatizUtil.setButtonIcon(this, bouton_retour, 1F, false);

        WatizUtil.setBackgroundColor(this, bouton_retour, R.color.COLOR_RED);
        WatizUtil.setBackgroundColor(this, texte_options, R.color.COLOR_GRAY);

        textView = findViewById(R.id.copyright);
        textView.setMovementMethod(LinkMovementMethod.getInstance());

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

}
