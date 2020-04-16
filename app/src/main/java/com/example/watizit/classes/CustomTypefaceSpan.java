package com.example.watizit.classes;

import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.text.style.TypefaceSpan;

import androidx.annotation.NonNull;

/**
 * The type Custom typeface span.
 */
public class CustomTypefaceSpan extends TypefaceSpan {

    private final Typeface newType;

    /**
     * Instantiates a new Custom typeface span.
     *
     * @param family the family
     * @param type   the type
     */
    public CustomTypefaceSpan(String family, Typeface type)
    {
        super(family);
        newType = type;
    }

    @Override
    public void updateDrawState(@NonNull TextPaint paint)
    {
        applyCustomTypeFace(paint, newType);
    }

    @Override
    public void updateMeasureState(@NonNull TextPaint paint)
    {
        applyCustomTypeFace(paint, newType);
    }

    private static void applyCustomTypeFace(Paint paint, Typeface tf)
    {
        int oldStyle;
        Typeface old = paint.getTypeface();
        if (old == null)
        {
            oldStyle = 0;
        }
        else
        {
            oldStyle = old.getStyle();
        }

        int fake = oldStyle & ~tf.getStyle();
        if ((fake & Typeface.BOLD) != 0)
        {
            paint.setFakeBoldText(true);
        }

        if ((fake & Typeface.ITALIC) != 0)
        {
            paint.setTextSkewX(-0.25f);
        }

        paint.setTypeface(tf);
    }
}
