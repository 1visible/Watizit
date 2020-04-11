package com.example.watizit.menus;


import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
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
import com.example.watizit.classes.LetterPicker;
import com.example.watizit.classes.Level;
import com.example.watizit.popups.HintsPopup;
import com.example.watizit.popups.WinPopup;
import com.example.watizit.utils.DatabaseUtil;
import com.example.watizit.utils.DesignUtil;

import java.util.Arrays;
import java.util.Random;

public class LevelMenu extends AppCompatActivity {

    private static final int MAX_LETTERS = 5;
    private Level level;
    private String word;

    ImageView levelImage;
    TextView levelText;
    LinearLayout letterPickerLayout;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.level_menu);

        int level_num = getIntent().getIntExtra("EXTRA_ID", -1);

        // If level can't be found, return to levels list
        if(level_num == -1) {
            finish();
            return;
        }

        Resources res = getResources();

        Button backButton = findViewById(R.id.backButton2);
        TextView levelNumberText = findViewById(R.id.levelNumberText);
        levelImage = findViewById(R.id.levelImage);
        levelText = findViewById(R.id.levelText);
        Button hints_button = findViewById(R.id.hintsButton);
        final HintsPopup hints_popup = new HintsPopup(this);

        hints_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hints_popup.show();
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        level = DatabaseUtil.getLevel(level_num);

        Drawable image = res.getDrawable(
                res.getIdentifier("img_"+level.getWord(), "drawable", getPackageName()));
        levelImage.setImageDrawable(image);

        /*
        // TODO : Pour l'indice
        ColorStateList csl = AppCompatResources.getColorStateList(this, R.color.COLOR_SEMIDARK);
        ImageViewCompat.setImageTintList(image_niveau, csl);

         */

        levelNumberText.setText(levelNumberText.getText().toString().replace("%d", String.valueOf(level.getID())));
        backButton.setText(DesignUtil.applyIcons(backButton.getText(), 1F));

        DesignUtil.setBgColor(backButton, R.color.COLOR_RED);
        DesignUtil.setBgColor(levelNumberText, R.color.COLOR_BLUE);
        DesignUtil.setBgColor(hints_button, R.color.COLOR_GRAY);

        letterPickerLayout = findViewById(R.id.letterPickerLayout);
        Random random = new Random();

        // Word to find
        word = res.getString(res.getIdentifier("word_"+level.getWord(), "string", getPackageName()));

        // Create one letter picker per char in the word
        for(int i = 0; i < word.length(); i++) {
            LetterPicker lpicker = createLetterPicker(letterPickerLayout, i, random);
            letterPickerLayout.addView(lpicker);
        }

        levelText.setText(getWord());

    }

    private LetterPicker createLetterPicker(LinearLayout layout, int id, Random random) {
        String[] values = new String[MAX_LETTERS];
        // Add word char to possible letters
        values[random.nextInt(MAX_LETTERS)] = Character.toString(word.charAt(id));

        // Fill the rest with random unique letters
        for(int j = 0; j < MAX_LETTERS; j++) {
            // This is to prevent word char overwrite
            if(values[j] != null) continue;

            String letter = Character.toString((char) (random.nextInt('Z' - 'A' + 1) + 'A'));

            while(Arrays.asList(values).contains(letter))
                letter = Character.toString((char) (random.nextInt('Z' - 'A' + 1) + 'A'));

            values[j] = letter;
        }

        LetterPicker lpicker = new LetterPicker(this, id, values);
        PickerListener listener = new PickerListener();
        // Adjust width to fit screen
        LinearLayout.LayoutParams lp =
                new LinearLayout.LayoutParams(layout.getLayoutParams().width/word.length(),
                        LinearLayout.LayoutParams.MATCH_PARENT);
        lpicker.setLayoutParams(lp);
        lpicker.setOnScrollListener(listener);

        return lpicker;
    }

    public void updateGame() {
        String text = getWord();
        levelText.setText(text);
        if(word.equals(text))
            triggerWin();
    }

    private String getWord() {
        StringBuilder text = new StringBuilder();
        for(int i = 0; i < word.length(); i++) {
            NumberPicker np = letterPickerLayout.findViewById(i);
            text.append(np.getDisplayedValues()[np.getValue()]);
        }
        return text.toString();
    }

    private void disableLetterPickers() {
        for(int i = 0; i < word.length(); i++) {
            NumberPicker np = letterPickerLayout.findViewById(i);
            np.setEnabled(false);
            String[] values = new String[MAX_LETTERS];

            for(int j = 0; j < MAX_LETTERS; j++)
                values[j] = Character.toString(word.charAt(i));

            np.setDisplayedValues(values);
        }
    }

    private void triggerWin() {
        final WinPopup win_popup = new WinPopup(this, level);

        disableLetterPickers();
        levelText.setTextColor(Color.GREEN);
        ImageViewCompat.setImageTintList(levelImage, null);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                win_popup.show();
            }
        }, 1000);
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
