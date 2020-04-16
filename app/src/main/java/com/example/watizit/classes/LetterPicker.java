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
 * The type Letter picker.
 */
public class LetterPicker extends NumberPicker {

    /**
     * The Typeface.
     */
    Typeface typeface;

    /**
     * Instantiates a new Letter picker.
     *
     * @param context the context
     */
    public LetterPicker(Context context)
    {
        super(context);
    }

    /**
     * Instantiates a new Letter picker.
     *
     * @param context the context
     * @param id      the id
     * @param values  the values
     */
    public LetterPicker(Context context, int id, String[] values)
    {
        super(context);
        setId(id);
        setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        setBackgroundColor(Color.TRANSPARENT);
        setMinValue(0);
        setMaxValue(values.length - 1);
        setDisplayedValues(values);
    }

    @Override
    public void addView(View child)
    {
        super.addView(child);
        typeface = ResourcesCompat.getFont(App.getContext(), R.font.app_font);
        updateView(child);
    }

    @Override
    public void addView(View child, int index, android.view.ViewGroup.LayoutParams params)
    {
        super.addView(child, index, params);
        typeface = ResourcesCompat.getFont(App.getContext(), R.font.app_font);
        updateView(child);
    }

    @Override
    public void addView(View child, android.view.ViewGroup.LayoutParams params)
    {
        super.addView(child, params);
        typeface = ResourcesCompat.getFont(App.getContext(), R.font.app_font);
        updateView(child);
    }

    private void updateView(View view)
    {
        if (view instanceof EditText)
        {
            ((EditText) view).setTypeface(typeface);
            ((EditText) view).setTextSize(20);
            ((EditText) view).setTextColor(getResources().getColor(R.color.COLOR_TEXT));
        }
    }

    /**
     * Sets color.
     *
     * @param color the color
     */
    public void setColor(int color)
    {
        int count = this.getChildCount();
        for(int i = 0; i < count; i++)
        {
            View child = this.getChildAt(i);
            if (child instanceof EditText)
                ((EditText) child).setTextColor(getResources().getColor(color));
        }
    }

}
