package com.example.watizit.database;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * The type Database open helper.
 */
class DatabaseOpenHelper extends SQLiteAssetHelper {
    private static final String DATABASE_NAME = "WatizDB.db";
    private static final int DATABASE_VERSION = 1;

    /**
     * Instantiates a new Database open helper.
     *
     * @param context the context
     */
    DatabaseOpenHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

}
