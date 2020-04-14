package com.example.watizit.utils;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.core.content.res.ResourcesCompat;

import com.example.watizit.R;
import com.example.watizit.classes.App;
import com.example.watizit.classes.BounceInInterpolator;
import com.example.watizit.classes.CustomTypefaceSpan;

import java.util.Arrays;
import java.util.List;

import static android.text.Spanned.SPAN_INCLUSIVE_EXCLUSIVE;

public class DesignUtil {

    public static void startBounceIn(View view, float startOffset)
    {
        Animation animation = AnimationUtils.loadAnimation(App.getContext(), R.anim.bounce_in);
        animation.setInterpolator(new BounceInInterpolator(0.06, 18));
        animation.setStartOffset((long) (startOffset*1000));
        view.startAnimation(animation);
    }

    public static void setBgColor(View view, int color)
    {
        Drawable bg = view.getBackground();
        int c = App.getContext().getResources().getColor(color);

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

    public static SpannableStringBuilder applyIcons(CharSequence cs, float fontSize, int color)
    {
        Context context = App.getContext();
        String text = cs.toString();
        int startBracket = text.indexOf('{');
        int endBracket = text.indexOf('}');
        if(startBracket > -1 && endBracket > -1)
        {
            text = text.replace("{", "");
            text = text.replace("}", "");
        }
        SpannableStringBuilder string = new SpannableStringBuilder(text);
        Typeface typeface = ResourcesCompat.getFont(context, R.font.icons_font);
        Character[] icons = { '\uF04B', '\uF013', '\uF104', '\uf0e7' };
        List<Character> list = Arrays.asList(icons);

        if(startBracket > -1 && endBracket > -1)
        {
            int c = context.getResources().getColor(color);
            try
            {
                string.setSpan(new ForegroundColorSpan(c), startBracket, endBracket - 1, SPAN_INCLUSIVE_EXCLUSIVE);
                string.setSpan(new RelativeSizeSpan(0.85f), startBracket, endBracket - 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            }
            catch(Exception e) {}
        }

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
