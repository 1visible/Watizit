package com.example.watizit.menus;


import android.app.Dialog;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.ImageViewCompat;

import com.example.watizit.R;
import com.example.watizit.database.DatabaseAccess;
import com.example.watizit.other.Level;
import com.example.watizit.utils.WatizUtil;

import java.util.Random;

public class MenuJouer extends AppCompatActivity {

    private static final int MAX_LETTERS = 5;
    private int level_num = 1;
    private Level level;
    private String word;

    ImageView image_niveau;
    TextView texte_mot;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_jouer);

        Resources res = getResources();

        Button bouton_retour = findViewById(R.id.retour);
        TextView texte_niveau = findViewById(R.id.niveau);
        Button bouton_aide = findViewById(R.id.help);
        image_niveau = findViewById(R.id.level_image);
        texte_mot = findViewById(R.id.textView);

        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
        level = databaseAccess.getLevel(level_num);
        databaseAccess.close();

        image_niveau.setImageDrawable(res.getDrawable(res.getIdentifier("img_"+level.getWord(), "drawable", getPackageName())));

        texte_niveau.append(" "+level.getID());

        WatizUtil.setButtonIcon(this, bouton_retour, 1F, false);

        WatizUtil.setBackgroundColor(this, bouton_retour, R.color.COLOR_RED);
        WatizUtil.setBackgroundColor(this, texte_niveau, R.color.COLOR_BLUE);
        WatizUtil.setBackgroundColor(this, bouton_aide, R.color.COLOR_GRAY);

        LinearLayout layout = findViewById(R.id.layout);
        Random random = new Random();

        // Word to find
        word = res.getString(res.getIdentifier("word_"+level.getWord(), "string", getPackageName()));

        // Create one letter picker per char in the word
        for(int i = 0; i < word.length(); i++) {
            NumberPicker np = createLetterPicker(layout, i, random);
            layout.addView(np);
        }

        texte_mot.setText(getWord());

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
        PickerListener listener = new PickerListener();
        String[] values = new String[MAX_LETTERS];

        // Add word char to possible letters
        values[random.nextInt(MAX_LETTERS)] = Character.toString(word.charAt(id));

        np.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        np.setId(id);

        // Adjust width to fit screen
        LinearLayout.LayoutParams lp =
                new LinearLayout.LayoutParams(layout.getLayoutParams().width/word.length(), LinearLayout.LayoutParams.MATCH_PARENT);
        np.setLayoutParams(lp);


        // Fill the rest with random unique letters
        for(int j = 0; j < MAX_LETTERS; j++) {
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
        np.setOnScrollListener(listener);

        return np;
    }

    public void updateGame() {
        String text = getWord();
        texte_mot.setText(text);
        if(word.equals(text))
            triggerWin();
    }

    private String getWord() {
        String text = "";
        for(int i = 0; i < word.length(); i++) {
            NumberPicker np = findViewById(R.id.layout).findViewById(i);
            text += np.getDisplayedValues()[np.getValue()];
        }
        return text;
    }

    private void disableLetterPickers() {
        for(int i = 0; i < word.length(); i++) {
            NumberPicker np = findViewById(R.id.layout).findViewById(i);
            np.setEnabled(false);
            String[] values = new String[MAX_LETTERS];

            for(int j = 0; j < MAX_LETTERS; j++)
                values[j] = Character.toString(word.charAt(i));

            np.setDisplayedValues(values);
        }
    }

    private void triggerWin() {
        disableLetterPickers();
        texte_mot.setTextColor(Color.GREEN);
        ImageViewCompat.setImageTintList(image_niveau, null);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                openWinPopup();
            }
        }, 1000);
    }

    public void openWinPopup() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.popup);
        Button bouton_retour =  dialog.getWindow().findViewById(R.id.retour);
        Button bouton_suivant = dialog.getWindow().findViewById(R.id.next);
        WatizUtil.setBackgroundColor(this, bouton_retour, R.color.COLOR_RED);
        WatizUtil.setBackgroundColor(this, bouton_suivant, R.color.COLOR_BLUE);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

    }

    public void help(View v) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.popup_help);
        Button bouton_fermer =  dialog.getWindow().findViewById(R.id.close);
        Button bouton_clue1 =  dialog.getWindow().findViewById(R.id.clue1);
        Button bouton_clue2 =  dialog.getWindow().findViewById(R.id.clue2);
        Button bouton_clue3 =  dialog.getWindow().findViewById(R.id.clue3);
        WatizUtil.setBackgroundColor(this, bouton_fermer, R.color.COLOR_RED);
        WatizUtil.setBackgroundColor(this, bouton_clue1, R.color.COLOR_GOLD);
        WatizUtil.setBackgroundColor(this, bouton_clue2, R.color.COLOR_GOLD);
        WatizUtil.setBackgroundColor(this, bouton_clue3, R.color.COLOR_GOLD);
        WatizUtil.setButtonIcon(this, bouton_clue1, 0.8F, false);
        WatizUtil.setButtonIcon(this, bouton_clue2, 0.8F, false);
        WatizUtil.setButtonIcon(this, bouton_clue3, 0.8F, false);
        bouton_fermer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
    public void confirm(View v) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView((R.layout.confirm_help));
        Button bouton_non = dialog.getWindow().findViewById(R.id.no);
        Button bouton_oui = dialog.getWindow().findViewById(R.id.yes);
        WatizUtil.setBackgroundColor(this, bouton_non, R.color.COLOR_RED);
        WatizUtil.setBackgroundColor(this, bouton_oui, R.color.COLOR_BLUE);
        bouton_non.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

    }

    private class PickerListener implements NumberPicker.OnScrollListener {
        @Override
        public void onScrollStateChange(NumberPicker view, int scrollState) {
            if(scrollState == SCROLL_STATE_IDLE) {
                updateGame();
            }
        }
    }

}
