package com.example.watizit.utils;

import com.example.watizit.database.DatabaseAccess;
import com.example.watizit.classes.Level;

import java.util.ArrayList;

public class DatabaseUtil {

    public static Level getLevel(int id)
    {
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance();
        databaseAccess.open();
        Level level = databaseAccess.getLevel(id);
        databaseAccess.close();

        return level;
    }

    public static ArrayList<Level> getLevels()
    {
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance();
        databaseAccess.open();
        ArrayList<Level> levels = databaseAccess.getLevels();
        databaseAccess.close();

        return levels;
    }

    public static int updateStars(int id, int stars)
    {
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance();
        databaseAccess.open();
        int state = databaseAccess.updateStars(id, stars);
        databaseAccess.close();

        return state;
    }

    public static int updateHints(int id, int hints)
    {
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance();
        databaseAccess.open();
        int state = databaseAccess.updateHints(id, hints);
        databaseAccess.close();

        return state;
    }

}
