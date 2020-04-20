package com.example.watizit.classes;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;

import androidx.core.content.res.ResourcesCompat;

import com.example.watizit.R;

/**
 * This class represents a letter picker, derived from the number picker.
 * It's used by player, to scroll through letters in order to find the word.
 */
public class LetterPicker extends NumberPicker {
    private Typeface typeface;

    /**
     * Instantiates a new Letter picker.
     *
     * @param context the context
     */
    public LetterPicker(Context context) {
        super(context);
    }

    /**
     * Instantiates a new Letter picker.
     *
     * @param context the context
     * @param id the id of the letter picker
     * @param values the values array (letters)
     */
    public LetterPicker(Context context, int id, String[] values) {
        super(context);
        setId(id);
        // Remove the up and down arrows in the letter picker
        setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        // Remove the background
        setBackgroundColor(Color.TRANSPARENT);
        // Set the number of values to the size of the array
        setMinValue(0);
        setMaxValue(values.length - 1);
        // And set the displayed values to be the letters instead of numbers
        setDisplayedValues(values);
    }

    /**
     * This method add an EditText (letter) in the letter picker
     *
     * @param child the view to add, in our case a EditText (letter)
     */
    @Override
    public void addView(View child) {
        super.addView(child);
        typeface = ResourcesCompat.getFont(App.getContext(), R.font.app_font);
        updateView(child);
    }

    /**
     * This method add an EditText (letter) in the letter picker at a given index
     *
     * @param child the view to add, in our case a EditText (letter)
     * @param index the index where the child should be added
     * @param params the params for the view
     */
    @Override
    public void addView(View child, int index, android.view.ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        typeface = ResourcesCompat.getFont(App.getContext(), R.font.app_font);
        updateView(child);
    }

    /**
     * This method add an EditText (letter) in the letter picker
     *
     * @param child the view to add, in our case a EditText (letter)
     * @param params the params for the view
     */
    @Override
    public void addView(View child, android.view.ViewGroup.LayoutParams params) {
        super.addView(child, params);
        typeface = ResourcesCompat.getFont(App.getContext(), R.font.app_font);
        updateView(child);
    }

    /**
     * This method update an EditText (letter) in the letter picker by setting its font,
     * size and color.
     *
     * @param view the view to update
     */
    private void updateView(View view) {
        // If the view is an EditText (letter), update its font, size and color
        if (view instanceof EditText) {
            ((EditText) view).setTypeface(typeface);
            ((EditText) view).setTextSize(20);
            ((EditText) view).setTextColor(getResources().getColor(R.color.COLOR_TEXT));
        }
    }

    /**
     * This method sets the color of a letter picker text.
     *
     * @param color the color
     */
    public void setColor(int color) {
        int count = this.getChildCount();
        // Get all letters in letter picker
        for (int i = 0; i < count; i++) {
            View child = this.getChildAt(i);
            if (child instanceof EditText)
                // And edit their text color
                ((EditText) child).setTextColor(getResources().getColor(color));
        }
    }

}
