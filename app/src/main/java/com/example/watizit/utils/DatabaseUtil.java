package com.example.watizit.utils;

import com.example.watizit.database.DatabaseAccess;
import com.example.watizit.classes.Level;

import java.util.ArrayList;

/**
 * The type Database util.
 */
public class DatabaseUtil {

    /**
     * Gets level.
     *
     * @param id the id
     * @return the level
     */
    public static Level getLevel(int id)
    {
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance();
        databaseAccess.open();
        Level level = databaseAccess.getLevel(id);
        databaseAccess.close();

        return level;
    }

    /**
     * Gets levels.
     *
     * @return the levels
     */
    public static ArrayList<Level> getLevels()
    {
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance();
        databaseAccess.open();
        ArrayList<Level> levels = databaseAccess.getLevels();
        databaseAccess.close();

        return levels;
    }

    /**
     * Update stars.
     *
     * @param id    the id
     * @param stars the stars
     */
    public static void updateStars(int id, int stars)
    {
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance();
        databaseAccess.open();
        databaseAccess.updateStars(id, stars);
        databaseAccess.close();
    }

    /**
     * Update hints.
     *
     * @param id    the id
     * @param hints the hints
     */
    public static void updateHints(int id, int hints)
    {
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance();
        databaseAccess.open();
        databaseAccess.updateHints(id, hints);
        databaseAccess.close();
    }

}
