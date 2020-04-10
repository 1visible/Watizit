package com.example.watizit.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.watizit.objects.Level;

import java.util.ArrayList;

public class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess instance;

    private DatabaseAccess(Context context) {
        this.openHelper = new DatabaseOpenHelper(context);
    }

    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    public void close() {
        if (database != null) {
            this.database.close();
        }
    }

    public Level getLevel(int id) {
        Level level = null;
        Cursor cursor = database.rawQuery("SELECT * FROM levels WHERE id = ?", new String[] { String.valueOf(id) });
        cursor.moveToFirst();
        if(cursor.getCount() > 0)
            level = new Level(cursor.getInt(0), cursor.getString(1),
                    cursor.getInt(2), cursor.getInt(3));
        return level;
    }

    public ArrayList<Level> getLevels() {
        ArrayList<Level> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM levels", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Level level = new Level(cursor.getInt(0), cursor.getString(1),
                    cursor.getInt(2), cursor.getInt(3));
            list.add(level);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }
}

