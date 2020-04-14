package com.example.watizit.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.example.watizit.R;

import java.util.Locale;

public class LocaleUtil {

    public static String getLocale(Context context)
    {
        Locale language = context.getResources().getConfiguration().locale;

        return language.getLanguage();
    }

    public static void setLocale(Context context, String locale_id)
    {
        Resources res = context.getResources();
        Locale locale = new Locale(locale_id);
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration config = res.getConfiguration();

        Locale.setDefault(locale);
        config.locale = locale;
        res.updateConfiguration(config, dm);

        String UID = context.getString(R.string.UID);
        SharedPreferences pref = context.getSharedPreferences(UID, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("lang", locale_id);
        editor.apply();
    }

    public static boolean isLocaleStored(Context context)
    {
        String UID = context.getString(R.string.UID);
        SharedPreferences pref = context.getSharedPreferences(UID, Context.MODE_PRIVATE);

        return pref.contains("lang");
    }

    public static String getLocaleStored(Context context)
    {
        String UID = context.getString(R.string.UID);
        SharedPreferences pref = context.getSharedPreferences(UID, Context.MODE_PRIVATE);

        return pref.getString("lang", null);
    }

}
