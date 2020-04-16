package com.example.watizit.classes;

import android.app.Application;
import android.content.Context;

/**
 * The type App.
 */
public class App extends Application {

    private static Context context;

    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    /**
     * Gets context.
     *
     * @return the context
     */
    public static Context getContext() {
        return context;
    }

}
