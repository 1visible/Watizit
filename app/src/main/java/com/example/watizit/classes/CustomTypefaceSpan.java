package com.example.watizit.classes;

import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.text.style.TypefaceSpan;

import androidx.annotation.NonNull;

/**
 * This class represents a custom typeface span.
 */
public class CustomTypefaceSpan extends TypefaceSpan {

    private final Typeface newType;

    /**
     * Instantiates a new Custom typeface span.
     *
     * @param family the font family as a string
     * @param type the typeface
     */
    public CustomTypefaceSpan(String family, Typeface type) {
        super(family);
        newType = type;
    }

    /**
     * @param paint a TextPaint object
     */
    @Override
    public void updateDrawState(@NonNull TextPaint paint) {
        applyCustomTypeFace(paint, newType);
    }

    /**
     * @param paint a TextPaint object
     */
    @Override
    public void updateMeasureState(@NonNull TextPaint paint) {
        applyCustomTypeFace(paint, newType);
    }

    /**
     * @param paint a Paint object
     * @param type the typeface
     */
    private static void applyCustomTypeFace(Paint paint, Typeface type) {
        int oldStyle;
        Typeface old = paint.getTypeface();
        if (old == null) {
            oldStyle = 0;
        } else {
            oldStyle = old.getStyle();
        }

        int fake = oldStyle & ~type.getStyle();
        if ((fake & Typeface.BOLD) != 0) {
            paint.setFakeBoldText(true);
        }

        if ((fake & Typeface.ITALIC) != 0) {
            paint.setTextSkewX(-0.25f);
        }

        paint.setTypeface(type);
    }
}
