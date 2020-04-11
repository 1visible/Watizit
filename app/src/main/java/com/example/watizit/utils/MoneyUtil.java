package com.example.watizit.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.watizit.R;
import com.example.watizit.classes.App;

public class MoneyUtil {

    public static void addMoney(int money)
    {
        Context context = App.getContext();
        String sp_key = context.getString(R.string.sp_key);
        SharedPreferences pref = context.getSharedPreferences(sp_key, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        money += getMoney();
        editor.putInt("money", money);
        editor.apply();
    }

    public static int getMoney()
    {
        Context context = App.getContext();
        String sp_key = context.getString(R.string.sp_key);
        SharedPreferences pref = context.getSharedPreferences(sp_key, Context.MODE_PRIVATE);

        return pref.getInt("money", 3);
    }

}
