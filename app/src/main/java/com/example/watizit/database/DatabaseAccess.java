package com.example.watizit.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.watizit.classes.App;
import com.example.watizit.classes.Level;

import java.util.ArrayList;

public class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess instance;

    private DatabaseAccess()
    {
        Context context = App.getContext();
        openHelper = new DatabaseOpenHelper(context);
    }

    public static DatabaseAccess getInstance()
    {
        if (instance == null)
        {
            instance = new DatabaseAccess();
        }

        return instance;
    }

    public void open()
    {
        database = openHelper.getWritableDatabase();
    }

    public void close()
    {
        if (database != null)
        {
            database.close();
        }
    }

    public Level getLevel(int id)
    {
        Level level = null;
        Cursor cursor = database.rawQuery("SELECT * FROM levels WHERE id = ?", new String[] { String.valueOf(id) });

        cursor.moveToFirst();
        if(cursor.getCount() > 0)
            level = new Level(cursor.getInt(0), cursor.getString(1),
                    cursor.getInt(2), cursor.getInt(3));
        cursor.close();

        return level;
    }

    public ArrayList<Level> getLevels()
    {
        ArrayList<Level> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM levels", null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            Level level = new Level(cursor.getInt(0), cursor.getString(1),
                    cursor.getInt(2), cursor.getInt(3));
            list.add(level);
            cursor.moveToNext();
        }
        cursor.close();

        return list;
    }

    public void updateStars(int id, int stars)
    {
        ContentValues values = new ContentValues();
        values.put("stars", stars);
        database.update("levels", values, "id =" + id, null);
    }

    public void updateHints(int id, int hints)
    {
        ContentValues values = new ContentValues();
        values.put("hints", hints);
        database.update("levels", values, "id =" + id, null);
    }
}

