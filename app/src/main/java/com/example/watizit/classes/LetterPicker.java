package com.example.watizit.classes;

import android.content.Context;
import android.widget.NumberPicker;

public class LetterPicker extends NumberPicker {

    public LetterPicker(Context context)
    {
        super(context);
    }

    public LetterPicker(Context context, int id, String[] values)
    {
        super(context);
        setId(id);
        setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        setMinValue(0);
        setMaxValue(values.length - 1);
        setDisplayedValues(values);
    }

}
