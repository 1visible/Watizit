package com.example.watizit.classes;

import android.app.Application;
import android.content.Context;

/**
 * This class represents the Application where we can access a static context.
 * It's useful to avoid carrying around a context parameter in each method that
 * needs a context, which reduces the number of possible errors on the persistence
 * of the context of an activity.
 */
public class App extends Application {
    private static Context context;

    /**
     * This method is used when the application is created.
     */
    public void onCreate() {
        super.onCreate();
        // Store the whole application context
        context = getApplicationContext();
    }

    /**
     * This method gets the application context.
     *
     * @return the context
     */
    public static Context getContext() {
        return context;
    }

}
