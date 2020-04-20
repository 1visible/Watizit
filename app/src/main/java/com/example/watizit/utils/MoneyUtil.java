package com.example.watizit.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.watizit.R;
import com.example.watizit.classes.App;

/**
 * This util class makes it easier to manage the player's money.
 */
public class MoneyUtil {

    /**
     * This method adds or subtracts money to the player's account.
     *
     * @param money the money to add (put negative number to subtract)
     */
    public static void addMoney(int money) {
        Context context = App.getContext(); // static context
        // Get UID key for the Shared Preferences
        String sp_key = context.getString(R.string.UID);
        // Open and edit Shared Preferences
        SharedPreferences pref = context.getSharedPreferences(sp_key, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        // Calculate total money
        money += getMoney();
        // And put it in Shared Preferences, then save
        editor.putInt("money", money);
        editor.apply();
    }

    /**
     * This method gets the player's money.
     *
     * @return the player's money
     */
    public static int getMoney() {
        Context context = App.getContext(); // static context
        // Get UID key for the Shared Preferences
        String sp_key = context.getString(R.string.UID);
        // Open Shared Preferences
        SharedPreferences pref = context.getSharedPreferences(sp_key, Context.MODE_PRIVATE);
        // And return the total money, or 3 as a default value (used when this is the first time the user plays)
        return pref.getInt("money", 3);
    }

}
