package com.example.watizit;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.widget.Button;

import androidx.core.content.res.ResourcesCompat;

import static android.text.Spanned.SPAN_INCLUSIVE_EXCLUSIVE;

public class WatizUtil {

    public static void setBackgroundColor(Context context, View view, int color) {
        view.getBackground().setColorFilter(context.getResources().getColor(color), PorterDuff.Mode.MULTIPLY);
    }

    public static void setButtonIcon(Context context, Button button, float size, boolean addGap) {
        SpannableStringBuilder string = new SpannableStringBuilder(button.getText());
        Typeface typeface = ResourcesCompat.getFont(context, R.font.icons_font);

        string.setSpan(new CustomTypefaceSpan("", typeface), 0, 1, SPAN_INCLUSIVE_EXCLUSIVE);
        string.setSpan(new RelativeSizeSpan(size), 0, 1, SPAN_INCLUSIVE_EXCLUSIVE);

        if(addGap)
            string.insert(1, " ");

        button.setText(string);
    }

}
