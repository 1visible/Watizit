package com.example.watizit;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class WatizDBAdapter {

    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "levels_database.db";
    private static final String TABLE_LEVELS = "levels";
    public static final String COL_ID = "_id";
    public static final String COL_LEVEL = "level";

    private static final String CREATE_DB =
            "create table " + TABLE_LEVELS + " ("
                    + COL_ID + " integer primary key autoincrement, "
                    + COL_LEVEL + " integer not null);";

    private SQLiteDatabase mDB;
    private MyOpenHelper mOpenHelper;

    public WatizDBAdapter(Context context) {
        mOpenHelper = new MyOpenHelper(context, DB_NAME,
                null, DB_VERSION);
    }

    public void open() {
        mDB = mOpenHelper.getWritableDatabase();
    }

    public void close() {
        mDB.close();
    }

    private class MyOpenHelper extends SQLiteOpenHelper {
        public MyOpenHelper(Context context, String name,
                            SQLiteDatabase.CursorFactory cursorFactory, int version) {
            super(context, name, cursorFactory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_DB);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db,
                              int oldVersion, int newVersion) {
            db.execSQL("drop table " + TABLE_LEVELS + ";");
            onCreate(db);
        }

    }
}
