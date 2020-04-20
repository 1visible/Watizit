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

/**
 * This util class makes it easier to manage the animations and the design of views.
 */
public class DesignUtil {

    /**
     * This method start a bounce-in animation for a view (which can be delayed with the start offset).
     *
     * @param view the view to animate
     * @param startOffset the start offset
     */
    public static void startBounceIn(View view, float startOffset) {
        // Load the bounce-in animation
        Animation animation = AnimationUtils.loadAnimation(App.getContext(), R.anim.bounce_in);
        // Apply the bounce-in interpolator
        animation.setInterpolator(new BounceInInterpolator(0.06, 18));
        // Set the animation start offset
        animation.setStartOffset((long) (startOffset * 1000));
        // And start the animation
        view.startAnimation(animation);
    }

    /**
     * This method sets the background color of a view drawable.
     *
     * @param view the view
     * @param color the color
     */
    public static void setBgColor(View view, int color) {
        Drawable bg = view.getBackground();
        int c = App.getContext().getResources().getColor(color);
        // Apply the color filter to the view background
        bg.setColorFilter(c, PorterDuff.Mode.MULTIPLY);
    }

    /**
     * This method applies icons to a text, with a font size that is a percentage of the default one.
     *
     * @param text the text to apply icons to
     * @param fontSize the font size in percentage
     * @return a spannable string builder with icons in
     */
    public static SpannableStringBuilder applyIcons(CharSequence text, float fontSize) {
        Context context = App.getContext();
        SpannableStringBuilder string = new SpannableStringBuilder(text);
        // Get the icons font
        Typeface typeface = ResourcesCompat.getFont(context, R.font.icons_font);
        // List of icons to search for in the text
        List<Character> list = Arrays.asList('\uF04B', '\uF013', '\uF104', '\uf0e7');
        // Search for those icons in the text
        for (int i = 0; i < string.length(); i++) {
            char c = string.charAt(i);
            // If icon found
            if (list.contains(c)) {
                // Change font family to icons font and font size for this character
                string.setSpan(new CustomTypefaceSpan("", typeface), i, i + 1, SPAN_INCLUSIVE_EXCLUSIVE);
                string.setSpan(new RelativeSizeSpan(fontSize), i, i + 1, SPAN_INCLUSIVE_EXCLUSIVE);
            }
        }
        return string;
    }

    /**
     * This method applies icons to a text, with a font size that is a percentage of the default one.
     * It also search a placeholder at the same time to change the color and size of a part of the text.
     *
     * @param cs the text to apply icons and color to
     * @param fontSize the font size in percentage
     * @param color the color to apply
     * @return a spannable string builder with icons and color in
     */
    public static SpannableStringBuilder applyIcons(CharSequence cs, float fontSize, int color) {
        Context context = App.getContext();
        String text = cs.toString();
        // Get start and end index to apply color and size to that substring
        int startBracket = text.indexOf('{');
        int endBracket = text.indexOf('}');
        // Remove placeholders if they exist
        if (startBracket > -1 && endBracket > -1) {
            text = text.replace("{", "");
            text = text.replace("}", "");
        }
        SpannableStringBuilder string = new SpannableStringBuilder(text);
        // Get the icons font
        Typeface typeface = ResourcesCompat.getFont(context, R.font.icons_font);
        // List of icons to search for in the text
        List<Character> list = Arrays.asList('\uF04B', '\uF013', '\uF104', '\uf0e7');
        // If a placeholder has been found
        if (startBracket > -1 && endBracket > -1) {
            int c = context.getResources().getColor(color);
            // Try to set the new color and size percentage to this substring
            try {
                string.setSpan(new ForegroundColorSpan(c), startBracket, endBracket - 1, SPAN_INCLUSIVE_EXCLUSIVE);
                string.setSpan(new RelativeSizeSpan(0.85f), startBracket, endBracket - 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            } catch (Exception e) {
                // Should never happen, but here in case the placeholder is malformed
                e.printStackTrace();
            }
        }
        // Search for those icons in the text
        for (int i = 0; i < string.length(); i++) {
            char c = string.charAt(i);
            // If icon found
            if (list.contains(c)) {
                // Change font family to icons font and font size for this character
                string.setSpan(new CustomTypefaceSpan("", typeface), i, i + 1, SPAN_INCLUSIVE_EXCLUSIVE);
                string.setSpan(new RelativeSizeSpan(fontSize), i, i + 1, SPAN_INCLUSIVE_EXCLUSIVE);
            }
        }
        return string;
    }

}
