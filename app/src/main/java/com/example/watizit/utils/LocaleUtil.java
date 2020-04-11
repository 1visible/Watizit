package com.example.watizit.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.example.watizit.R;
import com.example.watizit.menus.MainMenu;
import com.example.watizit.classes.App;

import java.util.Locale;

public class LocaleUtil {

    public static String getLocale()
    {
        Context context = App.getContext();
        Locale language = context.getResources().getConfiguration().locale;

        return language.getLanguage();
    }

    public static void setLocale(String locale_id)
    {
        Context context = App.getContext();
        Resources res = context.getResources();
        Locale locale = new Locale(locale_id);
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration config = res.getConfiguration();

        Locale.setDefault(locale);
        config.locale = locale;
        res.updateConfiguration(config, dm);

        String sp_key = context.getString(R.string.sp_key);
        SharedPreferences pref = context.getSharedPreferences(sp_key, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("language", locale_id);
        editor.apply();

        Intent refresh = new Intent(context, MainMenu.class);

        refresh.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(refresh);
    }

    public static boolean isLocaleStored()
    {
        Context context = App.getContext();
        String sp_key = context.getString(R.string.sp_key);
        SharedPreferences pref = context.getSharedPreferences(sp_key, Context.MODE_PRIVATE);

        return pref.contains("language");
    }

    public static String getLocaleStored()
    {
        Context context = App.getContext();
        String sp_key = context.getString(R.string.sp_key);
        SharedPreferences pref = context.getSharedPreferences(sp_key, Context.MODE_PRIVATE);

        return pref.getString("language", null);
    }

}
