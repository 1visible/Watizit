package com.example.watizit;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MenuOptions extends AppCompatActivity {
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_options);
        textView = findViewById(R.id.copyright);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
