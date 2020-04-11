package com.example.watizit.utils;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;
import android.view.View;

import androidx.core.content.res.ResourcesCompat;

import com.example.watizit.R;
import com.example.watizit.classes.App;
import com.example.watizit.classes.CustomTypefaceSpan;

import java.util.Arrays;
import java.util.List;

import static android.text.Spanned.SPAN_INCLUSIVE_EXCLUSIVE;

public class DesignUtil {

    public static void setBgColor(View view, int color)
    {
        Context context = App.getContext();
        Drawable bg = view.getBackground();
        int c = context.getResources().getColor(color);

        bg.setColorFilter(c, PorterDuff.Mode.MULTIPLY);
    }

    public static SpannableStringBuilder applyIcons(CharSequence text, float fontSize)
    {
        Context context = App.getContext();
        SpannableStringBuilder string = new SpannableStringBuilder(text);
        Typeface typeface = ResourcesCompat.getFont(context, R.font.icons_font);
        Character[] icons = { '\uF04B', '\uF013', '\uF104', '\uf0e7' };
        List<Character> list = Arrays.asList(icons);

        for(int i = 0; i < string.length(); i++)
        {
            char c = string.charAt(i);
            if(list.contains(c))
            {
                string.setSpan(new CustomTypefaceSpan("", typeface), i, i+1, SPAN_INCLUSIVE_EXCLUSIVE);
                string.setSpan(new RelativeSizeSpan(fontSize), i, i+1, SPAN_INCLUSIVE_EXCLUSIVE);
            }
        }

        return string;
    }

}
