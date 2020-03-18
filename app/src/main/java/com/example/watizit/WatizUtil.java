package com.example.watizit;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

import androidx.core.content.res.ResourcesCompat;

import java.util.Locale;

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

    public static String getLocale(Context context) {
        Locale language = context.getResources().getConfiguration().locale;
        return language.getLanguage();
    }

    public static void setLocale(String locale_id, Context context) {
        Resources res = context.getResources();
        Locale locale = new Locale(locale_id);
        Locale.setDefault(locale);
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration config = res.getConfiguration();
        config.locale = locale;
        res.updateConfiguration(config, dm);

        SharedPreferences pref = context.getApplicationContext().getSharedPreferences("pref",0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("watizit_language",locale_id);
        editor.commit();

        Intent refresh = new Intent(context, MainActivity.class);
        refresh.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(refresh);
    }

    public static boolean isLocaleStored(Context context) {
        SharedPreferences pref = context.getApplicationContext().getSharedPreferences("pref",0);
        return pref.contains("watizit_language");
    }

    public static String getLocaleStored(Context context) {
        SharedPreferences pref = context.getApplicationContext().getSharedPreferences("pref",0);
        return pref.getString("watizit_language", null);
    }

}
