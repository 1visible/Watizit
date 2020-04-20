package com.example.watizit.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.example.watizit.R;

import java.util.Locale;

/**
 * This util class makes it easier to manage the app locale.
 */
public class LocaleUtil {

    /**
     * This method gets the app locale.
     *
     * @param context the context
     * @return the locale
     */
    public static String getLocale(Context context) {
        Locale language = context.getResources().getConfiguration().locale;
        return language.getLanguage();
    }

    /**
     * This method sets the app locale.
     *
     * @param context the context
     * @param localeId the locale id as a string ("en", "fr" or "sp")
     */
    public static void setLocale(Context context, String localeId) {
        Resources res = context.getResources();
        Locale locale = new Locale(localeId);
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration config = res.getConfiguration();
        // Set the default locale and the configuration locale to the new locale
        Locale.setDefault(locale);
        config.locale = locale;
        // Update the configuration with the new locale
        res.updateConfiguration(config, dm);
        // Get UID key for the Shared Preferences
        String UID = context.getString(R.string.UID);
        // Open and edit Shared Preferences
        SharedPreferences pref = context.getSharedPreferences(UID, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        // And put the locale in Shared Preferences, then save
        editor.putString("lang", localeId);
        editor.apply();
    }

    /**
     * This method tells if there is a locale stored in Shared Preferences.
     *
     * @param context the context
     * @return a boolean telling if there is a locale stored in Shared Preferences
     */
    public static boolean isLocaleStored(Context context) {
        // Get UID key for the Shared Preferences
        String UID = context.getString(R.string.UID);
        // Open Shared Preferences
        SharedPreferences pref = context.getSharedPreferences(UID, Context.MODE_PRIVATE);
        // Return true if there is a locale store, false otherwise
        return pref.contains("lang");
    }

    /**
     * This method gets the locale stored in Shared Preferences.
     *
     * @param context the context
     * @return the locale stored
     */
    public static String getLocaleStored(Context context) {
        // Get UID key for the Shared Preferences
        String UID = context.getString(R.string.UID);
        // Open Shared Preferences
        SharedPreferences pref = context.getSharedPreferences(UID, Context.MODE_PRIVATE);
        // Return the locale stored in Shared Preferences
        return pref.getString("lang", null);
    }

}
