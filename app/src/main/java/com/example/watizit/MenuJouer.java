package com.example.watizit;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.Random;

public class MenuJouer extends AppCompatActivity implements NumberPicker.OnValueChangeListener {

    private int maxLetters;
    private String word;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_jouer);

        LinearLayout layout = findViewById(R.id.layout);
        Random random = new Random();
        maxLetters = 5; // Total of 5 when adding the word char
        word = "CONFUS"; // Word to find

        // Create one letter picker per char in the word
        for(int i = 0; i < word.length(); i++) {
            NumberPicker np = createLetterPicker(i, random);
            layout.addView(np);
        }

    }

    public void retour(View view){
        finish();
    }

    private static boolean arrayContains(Object[] array, Object object) {
        for(Object o : array)
            if(object.equals(o)) return true;
        return false;
    }

    private NumberPicker createLetterPicker(int id, Random random) {
        NumberPicker np = new NumberPicker(this);
        String[] values = new String[maxLetters];

        // Add word char to possible letters
        values[random.nextInt(maxLetters)] = Character.toString(word.charAt(id));

        np.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        np.setId(id);
        // TODO: A changer
        np.setBackground(ContextCompat.getDrawable(this, R.drawable.app_button));

        // Fill the rest with random unique letters
        for(int j = 0; j < maxLetters; j++) {
            // This is to prevent word char overwrite
            if(values[j] != null) continue;

            String letter = Character.toString((char) (random.nextInt('Z' - 'A' + 1) + 'A'));

            while(arrayContains(values, letter))
                letter = Character.toString((char) (random.nextInt('Z' - 'A' + 1) + 'A'));

            values[j] = letter;
        }

        np.setMinValue(0);
        np.setMaxValue(values.length - 1);
        np.setDisplayedValues(values);
        np.setOnValueChangedListener(this);

        return np;
    }

    public void onValueChange(NumberPicker np, int oldVal, int newVal) {
        TextView textView = findViewById(R.id.textView);
        String text = "";
        for(int i = 0; i < word.length(); i++) {
            NumberPicker n = findViewById(R.id.layout).findViewById(i);
            text += n.getDisplayedValues()[n.getValue()];
        }
        textView.setText(text);
        textView.setTextColor(word.equals(text) ? Color.GREEN : Color.BLACK);
        if(word.equals(text))
            for(int i = 0; i < word.length(); i++) {
                NumberPicker n = findViewById(R.id.layout).findViewById(i);

                n.setEnabled(false);
                n.setValue(n.getValue());
                openDialog();
            }
    }

    public void openDialog()  {
        final Dialog dialog = new Dialog(this); // Context, this, etc.
        dialog.setContentView(R.layout.popup);
        dialog.setTitle("test");
        dialog.show();
    }
}
