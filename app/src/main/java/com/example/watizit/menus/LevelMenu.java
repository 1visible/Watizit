package com.example.watizit.menus;


import android.content.res.ColorStateList;
import android.content.res.Resources;
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
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.widget.ImageViewCompat;

import com.example.watizit.R;
import com.example.watizit.classes.LetterPicker;
import com.example.watizit.classes.Level;
import com.example.watizit.popups.HintsPopup;
import com.example.watizit.popups.WinPopup;
import com.example.watizit.utils.DatabaseUtil;
import com.example.watizit.utils.DesignUtil;
import com.example.watizit.utils.LocaleUtil;
import com.example.watizit.utils.MoneyUtil;

import java.util.Arrays;
import java.util.Random;

public class LevelMenu extends AppCompatActivity implements HintsPopup.HintsListener, WinPopup.WinListener {

    static final int MAX_LETTERS = 5;

    Level level;
    String word;
    long startTime;

    LinearLayout letterPickerLayout;
    ImageView levelImage;
    TextView levelText;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.level_menu);

        startTime = System.currentTimeMillis();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(LocaleUtil.isLocaleStored(this)
                && !LocaleUtil.getLocaleStored(this).equals(LocaleUtil.getLocale(this)))
            LocaleUtil.setLocale(this, LocaleUtil.getLocaleStored(this));

        int level_num = getIntent().getIntExtra("EXTRA_ID", -1);

        // If level can't be found, return to levels list
        if(level_num < 1) {
            finish();
            return;
        }

        Resources res = getResources();

        Button backButton = findViewById(R.id.backButton2);
        TextView levelNumberText = findViewById(R.id.levelNumberText);
        levelImage = findViewById(R.id.levelImage);
        levelText = findViewById(R.id.levelText);
        letterPickerLayout = findViewById(R.id.letterPickerLayout);
        Button hintsButton = findViewById(R.id.hintsButton);
        level = DatabaseUtil.getLevel(level_num);

        final HintsPopup hints_popup = new HintsPopup(this);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        hintsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hints_popup.showWith(level);
            }
        });

        Drawable image = res.getDrawable(
                res.getIdentifier("img_"+level.getWord(), "drawable", getPackageName()));
        levelImage.setImageDrawable(image);
        String strlevelNumberText = getResources().getString(R.string.levelMenu_title);
        levelNumberText.setText(strlevelNumberText.replace("%d", String.valueOf(level.getID())));
        backButton.setText(DesignUtil.applyIcons(backButton.getText(), 1F));

        DesignUtil.setBgColor(backButton, R.color.COLOR_RED);
        DesignUtil.setBgColor(levelNumberText, R.color.COLOR_PRIMARY);
        DesignUtil.setBgColor(hintsButton, R.color.COLOR_OVERLAY);
        DesignUtil.setBgColor(letterPickerLayout, R.color.COLOR_OVERLAY);

        Random random = new Random();

        // Word to find
        word = res.getString(res.getIdentifier("word_"+level.getWord(), "string", getPackageName()));

        // Create one letter picker per char in the word
        for(int i = 0; i < word.length(); i++) {
            LetterPicker lpicker = createLetterPicker(i, random);
            letterPickerLayout.addView(lpicker);
        }

        levelText.setText(getWord());

        for(int i = 1; i < 3; i++)
        {
            if(level.isHintBought(i))
                applyHint(i);
        }

    }

    private LetterPicker createLetterPicker(int id, Random random) {
        String[] values = new String[MAX_LETTERS];
        // Add word char to possible letters
        int min = id % 2;
        int max = MAX_LETTERS - 1;
        values[random.nextInt(max - min + 1) + min] = Character.toString(word.charAt(id));

        // Fill the rest with random unique letters
        for(int j = 0; j < MAX_LETTERS; j++)
        {
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
                new LinearLayout.LayoutParams(letterPickerLayout.getLayoutParams().width/word.length(),
                        LinearLayout.LayoutParams.MATCH_PARENT);
        lpicker.setLayoutParams(lp);
        lpicker.setOnScrollListener(listener);

        return lpicker;
    }

    private void updateGame()
    {
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

    private void disableLetterPicker(int id)
    {
        String[] values = new String[MAX_LETTERS];
        LetterPicker letterPicker = letterPickerLayout.findViewById(id);

        for(int i = 0; i < MAX_LETTERS; i++)
            values[i] = Character.toString(word.charAt(id));

        letterPicker.setEnabled(false);
        letterPicker.setDisplayedValues(values);
    }

    private void triggerWin() {
        long totalTime = System.currentTimeMillis() - startTime;

        for(int i = 0; i < word.length(); i++)
            disableLetterPicker(i);

        levelText.setText(getWord());
        levelText.setTextColor(getResources().getColor(R.color.COLOR_PRIMARY));
        ImageViewCompat.setImageTintList(levelImage, null);
        level.setStars(totalTime);
        MoneyUtil.addMoney(level.getStars());

        final WinPopup win_popup = new WinPopup(this, level);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                win_popup.show();
            }
        }, 1000);
    }

    @Override
    public void applyHint(int hintNumber)
    {
        switch(hintNumber)
        {
            case 1:
                int letterPickerId = (int) Math.ceil((float) word.length()/2);
                disableLetterPicker(letterPickerId);
                LetterPicker letterPicker = letterPickerLayout.findViewById(letterPickerId);
                letterPicker.setColor(R.color.COLOR_GOLD);
                levelText.setText(getWord());
                break;
            case 2:
                ColorStateList csl = AppCompatResources.getColorStateList(this, R.color.COLOR_BRIGHT);
                ImageViewCompat.setImageTintList(levelImage, csl);
                break;
            case 3:
                triggerWin();
                break;
        }
    }

    @Override
    public void finishActivity() { finish(); }

    private class PickerListener implements NumberPicker.OnScrollListener {
        @Override
        public void onScrollStateChange(NumberPicker view, int scrollState) {
            if(scrollState == SCROLL_STATE_IDLE)
                updateGame();
        }
    }

}
