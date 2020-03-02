package com.example.watizit;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MenuJouer extends AppCompatActivity implements NumberPicker.OnValueChangeListener {

    private int maxLetters;
    private String word;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_jouer);

        Button bouton_retour = findViewById(R.id.retour);
        TextView texte_niveau = findViewById(R.id.niveau);
        Button bouton_aide = findViewById(R.id.help);

        WatizUtil.setButtonIcon(this, bouton_retour, 1F, false);

        WatizUtil.setBackgroundColor(this, bouton_retour, R.color.COLOR_RED);
        WatizUtil.setBackgroundColor(this, texte_niveau, R.color.COLOR_GREEN);
        WatizUtil.setBackgroundColor(this, bouton_aide, R.color.COLOR_GOLD);

        LinearLayout layout = findViewById(R.id.layout);
        Random random = new Random();
        maxLetters = 5; // Total of 5 when adding the word char
        word = "DOG"; // Word to find

        // Create one letter picker per char in the word
        for(int i = 0; i < word.length(); i++) {
            NumberPicker np = createLetterPicker(layout, i, random);
            layout.addView(np);
        }

        setText(getText());

    }

    public void retour(View view){
        finish();
    }

    private static boolean arrayContains(Object[] array, Object object) {
        for(Object o : array)
            if(object.equals(o)) return true;
        return false;
    }

    private NumberPicker createLetterPicker(LinearLayout layout, int id, Random random) {
        NumberPicker np = new NumberPicker(this);
        String[] values = new String[maxLetters];

        // Add word char to possible letters
        values[random.nextInt(maxLetters)] = Character.toString(word.charAt(id));

        np.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        np.setId(id);

        // Adjust width to fit screen
        LinearLayout.LayoutParams lp =
                new LinearLayout.LayoutParams(layout.getLayoutParams().width/word.length(), LinearLayout.LayoutParams.MATCH_PARENT);
        np.setLayoutParams(lp);


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
        String text = getText();
        setText(text);
        if(word.equals(text)) disableLetterPickers();
    }

    private String getText() {
        String text = "";
        for(int i = 0; i < word.length(); i++) {
            NumberPicker n = findViewById(R.id.layout).findViewById(i);
            text += n.getDisplayedValues()[n.getValue()];
        }
        return text;
    }

    private void setText(String text) {
        TextView textView = findViewById(R.id.textView);
        textView.setText(text);
        textView.setTextColor(word.equals(text) ? Color.GREEN : Color.BLACK);
    }

    private void disableLetterPickers() {
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
