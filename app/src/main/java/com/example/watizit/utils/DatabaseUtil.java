package com.example.watizit.utils;

import com.example.watizit.database.DatabaseAccess;
import com.example.watizit.classes.Level;

import java.util.ArrayList;

public class DatabaseUtil {

    public static Level getLevel(int level_num)
    {
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance();
        databaseAccess.open();
        Level level = databaseAccess.getLevel(level_num);
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

}
