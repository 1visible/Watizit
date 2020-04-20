package com.example.watizit.utils;

import com.example.watizit.classes.Level;
import com.example.watizit.database.DatabaseAccess;

import java.util.ArrayList;

/**
 * This util class makes it easier to manage database calls.
 */
public class DatabaseUtil {

    /**
     * This method gets a level by id from the database.
     *
     * @param id the level id
     * @return the level
     */
    public static Level getLevel(int id) {
        // Open the database
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance();
        databaseAccess.open();
        // Get the level by id
        Level level = databaseAccess.getLevel(id);
        // Close the database
        databaseAccess.close();
        // And return the level (return null if not found)
        return level;
    }

    /**
     * This method gets all levels from the database.
     *
     * @return the levels
     */
    public static ArrayList<Level> getLevels() {
        // Open the database
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance();
        databaseAccess.open();
        // Get a list of all levels
        ArrayList<Level> levels = databaseAccess.getLevels();
        // Close the database
        databaseAccess.close();
        // And return the list of levels
        return levels;
    }

    /**
     * This method updates the number of stars of a level in the database.
     *
     * @param id the id of the level
     * @param stars the number of stars set in the database
     */
    public static void updateStars(int id, int stars) {
        // Open the database
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance();
        databaseAccess.open();
        // Update the number of stars in the database
        databaseAccess.updateStars(id, stars);
        // Close the database
        databaseAccess.close();
    }

    /**
     * This method updates the hints used in a level in the database.
     *
     * @param id the id of the level
     * @param hints the hints to set in the database
     */
    public static void updateHints(int id, int hints) {
        // Open the database
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance();
        databaseAccess.open();
        // Update the hints in the database
        databaseAccess.updateHints(id, hints);
        // Close the database
        databaseAccess.close();
    }

}
