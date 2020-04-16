package com.example.watizit.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.example.watizit.R;

import java.util.Locale;

/**
 * The type Locale util.
 */
public class LocaleUtil {

    /**
     * Gets locale.
     *
     * @param context the context
     * @return the locale
     */
    public static String getLocale(Context context)
    {
        Locale language = context.getResources().getConfiguration().locale;

        return language.getLanguage();
    }

    /**
     * Sets locale.
     *
     * @param context   the context
     * @param locale_id the locale id
     */
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

    /**
     * Is locale stored boolean.
     *
     * @param context the context
     * @return the boolean
     */
    public static boolean isLocaleStored(Context context)
    {
        String UID = context.getString(R.string.UID);
        SharedPreferences pref = context.getSharedPreferences(UID, Context.MODE_PRIVATE);

        return pref.contains("lang");
    }

    /**
     * Gets locale stored.
     *
     * @param context the context
     * @return the locale stored
     */
    public static String getLocaleStored(Context context)
    {
        String UID = context.getString(R.string.UID);
        SharedPreferences pref = context.getSharedPreferences(UID, Context.MODE_PRIVATE);

        return pref.getString("lang", null);
    }

}
