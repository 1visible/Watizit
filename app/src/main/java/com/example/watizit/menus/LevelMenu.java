package com.example.watizit.menus;


import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
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

/**
 * This class represents the level menu where the game happens.
 */
public class LevelMenu extends AppCompatActivity implements HintsPopup.HintsListener, WinPopup.WinListener {
    private static final int MAX_LETTERS = 5; // the max letters in a letter picker
    private Level level; // the currently played level
    private String word; // the word to find
    private long startTime; // the start time of the level
    private LinearLayout letterPickerLayout; // the layout containing all the letter pickers
    private ImageView levelImage; // the level image
    private TextView levelText; // the level text

    /**
     * This method is used when the activity is created. It applies the level menu content to the
     * activity and store the start play time.
     *
     * @param savedInstanceState the state of the saved instance
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.level_menu); // apply level menu layout

        // Save the start time of the game, used to calculate the final score
        startTime = System.currentTimeMillis();
    }

    /**
     * This method is used when the activity start, restart or gain focus. It applies the main
     * components to the activity such as the language, the design and the logic.
     */
    @Override
    protected void onResume() {
        super.onResume();

        // Get level ID sent by the levels list activity
        int levelId = getIntent().getIntExtra("EXTRA_ID", 0); // Default ID = 0 if ID not found

        // If ID < 1, it's not part of database so we have nothing to show and we finish the activity
        if (levelId < 1) {
            finish();
            return;
        }

        /*                  🌍 LANGUAGE

            • Compare current locale to stored locale and updating it if needed
        */

        if (LocaleUtil.isLocaleStored(this)
                && !LocaleUtil.getLocaleStored(this).equals(LocaleUtil.getLocale(this)))
            LocaleUtil.setLocale(this, LocaleUtil.getLocaleStored(this));



        /*                  📌 VARIABLES

            • Retrieve views for design, click listener and/or gameplay
            • Retrieve other objects for gameplay purposes
        */

        Button backButton = findViewById(R.id.backButton2);
        TextView levelNumberText = findViewById(R.id.levelNumberText);
        Button hintsButton = findViewById(R.id.hintsButton);
        levelImage = findViewById(R.id.levelImage);
        levelText = findViewById(R.id.levelText);
        letterPickerLayout = findViewById(R.id.letterPickerLayout);

        Resources res = getResources();
        // Used to place letters at random index in letter picker
        Random random = new Random();
        // Create the hints popup
        final HintsPopup hints_popup = new HintsPopup(this);
        // Get the level from the retrieved ID
        level = DatabaseUtil.getLevel(levelId);
        // Get menu title from current locale resources
        String strlevelNumberText = res.getString(R.string.levelMenu_title);
        // Get current locale word associated to the level
        int wordIdentifier = res.getIdentifier("word_" + level.getWord(), "string", getPackageName());
        // Retrieve drawable identifier associated to current level
        int imageIdentifier = res.getIdentifier("img_" + level.getWord(), "drawable", getPackageName());
        word = res.getString(wordIdentifier); // this is the word the player needs to find



        /*                  🎨 DESIGN & ACTIONS

            • Apply background color to buttons drawable
            • Apply icons to texts in views (if the text is made up of regular text + icons)
            • Apply click listeners to buttons
        */

        // Apply background color to buttons
        DesignUtil.setBgColor(backButton, R.color.COLOR_RED);
        DesignUtil.setBgColor(levelNumberText, R.color.COLOR_PRIMARY);
        DesignUtil.setBgColor(hintsButton, R.color.COLOR_OVERLAY);
        // Apply background color to layout
        DesignUtil.setBgColor(letterPickerLayout, R.color.COLOR_OVERLAY);

        // When the back button is clicked, finish the activity (and so go back to levels list)
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        // When the hints button is clicked, show the hints popup (dialog) with the current level as data entry
        hintsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hints_popup.showWith(level);
            }
        });

        //                      END OF BLOCKS                      //

        // Update menu title with the level ID (i.e "Level 1" for example)
        levelNumberText.setText(strlevelNumberText.replace("%d", String.valueOf(level.getID())));
        // Try to set drawable to level image view from identifier
        try {
            Drawable image = res.getDrawable(imageIdentifier);
            levelImage.setImageDrawable(image);
        } catch (Exception e) {
            // Should never happen, but just in case the drawable can't be found, finish activity
            finish();
            return;
        }

        // Create one letter picker per char in the word
        for (int i = 0; i < word.length(); i++) {
            LetterPicker lpicker = createLetterPicker(i, random);
            // Add letter picker to layout
            letterPickerLayout.addView(lpicker);
        }

        // Used to update the displayed level text according to letter pickers values
        updateText();

        // Apply hints if the player has already bought them (and returns to the level later)
        for (int i = 1; i < 3; i++)
            if (level.isHintBought(i))
                applyHint(i);
    }

    /**
     * This methods create a letter picker with one of the letters of the word plus random
     * unique values.
     *
     * @param id the id to assign to the letter picker in the letter picker layout
     * @param random a Random object for the index randomizer in the values array
     * @return the created letter picker
     */
    private LetterPicker createLetterPicker(int id, Random random) {
        String[] values = new String[MAX_LETTERS]; // Array of values for letter picker
        int min = id % 2; // Min range for the array index (explained below)
        int max = MAX_LETTERS - 1; // Max range for the array index
        PickerListener listener = new PickerListener(); // Scroll listener for letter picker
        /*
        To avoid all letters of the word to find to be the default values of the letter pickers
        we implemented a variation in the randomizer :
            - if the index of the letter is even, the possible interval for the letter is [0, MAX_LETTERS-1]
            - else the possible interval for the letter is [1, MAX_LETTERS-1]
        This method make sure that the word can't have all its letters in the 0-index (default displayed value)
        */
        // Insert a character of the word in a random index of the array
        values[random.nextInt(max - min + 1) + min] = Character.toString(word.charAt(id));
        // Fill the rest of the array with random unique letters
        for (int j = 0; j < MAX_LETTERS; j++) {
            // This is to prevent a letter overwrite
            if (values[j] != null)
                continue;

            String letter = Character.toString((char) (random.nextInt('Z' - 'A' + 1) + 'A'));
            while (Arrays.asList(values).contains(letter))
                letter = Character.toString((char) (random.nextInt('Z' - 'A' + 1) + 'A'));

            values[j] = letter;
        }
        // Create the letter picker and assign it the letters array
        LetterPicker lpicker = new LetterPicker(this, id, values);
        // Adjust its width to fit in the layout
        LinearLayout.LayoutParams lp =
                new LinearLayout.LayoutParams(letterPickerLayout.getLayoutParams().width / word.length(),
                        LinearLayout.LayoutParams.MATCH_PARENT);
        lpicker.setLayoutParams(lp);
        // Give it the scroll listener to update text when the player stops scrolling
        lpicker.setOnScrollListener(listener);

        return lpicker;
    }

    /**
     * This method updates the level text with the values of the letter pickers.
     */
    private void updateText() {
        String text = getWord(); // We get the word from letter pickers values
        levelText.setText(text); // then update the level text
        if (word.equals(text)) // and check if the player has found the solution
            triggerWin(); // in order to trigger the 'Win' part
    }

    /**
     * This method returns the word obtained by concatenating the values of all letter pickers.
     *
     * @return the word obtained by concatenating the values of all letter pickers
     */
    private String getWord() {
        // Append text from letter picker values in a StringBuilder (for better performance)
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < word.length(); i++) {
            NumberPicker lPicker = letterPickerLayout.findViewById(i);
            text.append(lPicker.getDisplayedValues()[lPicker.getValue()]); // get letter from letter picker int index
        }
        return text.toString();
    }

    /**
     * This method disable the letter picker of ID id in the letter picker layout, making it
     * unscrollable and replacing all its values with the solution letter.
     *
     * @param id the id of the letter picker (to disable) in the layout
     */
    private void disableLetterPicker(int id) {
        String[] values = new String[MAX_LETTERS];
        // Retrieve the letter picker we want to disable
        LetterPicker letterPicker = letterPickerLayout.findViewById(id);
        // Set all its values to the corresponding solution letter of the word
        for (int i = 0; i < MAX_LETTERS; i++)
            values[i] = Character.toString(word.charAt(id));
        letterPicker.setDisplayedValues(values);
        letterPicker.setEnabled(false); // and disable it
    }

    /**
     * This method triggers the win system by revealing the solution, disabling all letter pickers,
     * making the phone vibrate and opening the Win popup.
     */
    private void triggerWin() {
        // Calculate the time it took for the player to finish the level
        long totalTime = System.currentTimeMillis() - startTime;
        // Disable all letter pickers
        for (int i = 0; i < word.length(); i++)
            disableLetterPicker(i);
        // Update the level text one last time
        levelText.setText(getWord());
        // Color it
        levelText.setTextColor(getResources().getColor(R.color.COLOR_PRIMARY));
        // Remove white tint from image to reveal it
        ImageViewCompat.setImageTintList(levelImage, null);
        // Set level stars according to total time
        level.setStars(totalTime);
        // Give the player a monetary reward based on his score
        MoneyUtil.addMoney(level.getStars());
        // Make the phone vibrate
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        // Vibration pattern
        long[] pattern = {0, 100, 50, 100};
        if(v != null)
            v.vibrate(pattern, -1);
        // Create the win popup
        final WinPopup win_popup = new WinPopup(this, level);
        // And show it after a 1s delay (to allow the player to have time to see the solution)
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                win_popup.show();
            }
        }, 1000);
    }

    /**
     * This method overrides the one in the Hints popup. It serves as a link between
     * this activity and the dialog, allowing the dialog to finish this activity.
     *
     * @param hintNumber the hint number to apply to the level
     */
    @Override
    public void applyHint(int hintNumber) {
        switch (hintNumber) {
            case 1: // Hint: See a letter
                /* Always get the letter in the middle of the word because it gives more chance
                of not finding a letter like the first/last letter or an obvious vowel */
                int letterPickerId = (int) Math.ceil((float) word.length() / 2);
                LetterPicker letterPicker = letterPickerLayout.findViewById(letterPickerId);
                // Disable the letter picker
                disableLetterPicker(letterPickerId);
                // Color it to make it pop out
                letterPicker.setColor(R.color.COLOR_GOLD);
                // And update the level text
                levelText.setText(getWord());
                break;
            case 2: // Hint: Brighten the image
                // Set image tint to a more transparent color
                ColorStateList csl = AppCompatResources.getColorStateList(this, R.color.COLOR_BRIGHT);
                ImageViewCompat.setImageTintList(levelImage, csl);
                break;
            case 3: // Hint: Skip the level
                // Just call triggerWin() and the magic happens!
                triggerWin();
                break;
        }
    }

    /**
     * This method overrides the one in the Win popup. It serves as a link between
     * this activity and the dialog, allowing the dialog to finish this activity.
     */
    @Override
    public void finishActivity() {
        finish();
    }

    /**
     * Class that implements the scroll listener for letter pickers
     */
    private class PickerListener implements NumberPicker.OnScrollListener {
        /**
         * Event that triggers on a letter picker scroll state change
         *
         * @param view a LetterPicker object
         * @param scrollState the scroll state of the letter picker
         */
        @Override
        public void onScrollStateChange(NumberPicker view, int scrollState) {
            // Update level text if the letter picker stops scrolling
            if (scrollState == SCROLL_STATE_IDLE)
                updateText();
        }
    }

}
