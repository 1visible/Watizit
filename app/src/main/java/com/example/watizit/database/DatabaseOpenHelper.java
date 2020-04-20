package com.example.watizit.database;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * This class is a database open helper that works with the database access class.
 */
class DatabaseOpenHelper extends SQLiteAssetHelper {
    private static final String DATABASE_NAME = "WatizDB.db"; // the database name
    private static final int DATABASE_VERSION = 1; // the database version

    /**
     * Instantiates a new Database open helper.
     *
     * @param context the context
     */
    DatabaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

}
