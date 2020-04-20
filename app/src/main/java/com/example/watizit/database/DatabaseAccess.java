package com.example.watizit.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.watizit.classes.App;
import com.example.watizit.classes.Level;

import java.util.ArrayList;

/**
 * This class represents the database access with all the selection and update
 * methods associated to it.
 */
public class DatabaseAccess {
    private SQLiteOpenHelper openHelper; // the database open helper
    private SQLiteDatabase database; // the database object
    private static DatabaseAccess instance; // the database access instance

    /**
     * The constructor in which we instantiate the database open helper.
     */
    private DatabaseAccess() {
        Context context = App.getContext();
        openHelper = new DatabaseOpenHelper(context);
    }

    /**
     * This methods gets the database access instance.
     *
     * @return the instance
     */
    public static DatabaseAccess getInstance() {
        if (instance == null)
            instance = new DatabaseAccess();

        return instance;
    }

    /**
     * This method opens the database.
     */
    public void open() {
        database = openHelper.getWritableDatabase();
    }

    /**
     * This method closes the database.
     */
    public void close() {
        if (database != null)
            database.close();
    }

    /**
     * This method gets a level by id from the database.
     *
     * @param id the level id
     * @return the level
     */
    public Level getLevel(int id) {
        Level level = null;
        // Database query
        Cursor cursor = database.rawQuery("SELECT * FROM levels WHERE id = ?", new String[]{String.valueOf(id)});

        cursor.moveToFirst();
        // Get the level if it exists
        if (cursor.getCount() > 0)
            level = new Level(cursor.getInt(0), cursor.getString(1),
                    cursor.getInt(2), cursor.getInt(3));
        cursor.close();

        return level;
    }

    /**
     * This method gets all levels from the database.
     *
     * @return the levels
     */
    public ArrayList<Level> getLevels() {
        ArrayList<Level> list = new ArrayList<>();
        // Database query
        Cursor cursor = database.rawQuery("SELECT * FROM levels", null);

        cursor.moveToFirst();
        // Add levels to list if they exist
        while (!cursor.isAfterLast()) {
            Level level = new Level(cursor.getInt(0), cursor.getString(1),
                    cursor.getInt(2), cursor.getInt(3));
            list.add(level);
            cursor.moveToNext();
        }
        cursor.close();

        return list;
    }

    /**
     * This method updates the number of stars of a level in the database.
     *
     * @param id the id of the level
     * @param stars the number of stars set in the database
     */
    public void updateStars(int id, int stars) {
        ContentValues values = new ContentValues();
        values.put("stars", stars);
        database.update("levels", values, "id =" + id, null);
    }

    /**
     * This method updates the hints used in a level in the database.
     *
     * @param id the id of the level
     * @param hints the hints to set in the database
     */
    public void updateHints(int id, int hints) {
        ContentValues values = new ContentValues();
        values.put("hints", hints);
        database.update("levels", values, "id =" + id, null);
    }
}

