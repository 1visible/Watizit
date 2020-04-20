package com.example.watizit.classes;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

/**
 * This class represents the Home watcher which watch for a home button press.
 * It's useful for pausing the background music when the home button is pressed.
 */
public class HomeWatcher {

    private Context context;
    private IntentFilter intentFilter;
    private OnHomePressedListener listener;
    private InnerRecevier recevier;

    /**
     * Instantiates a new Home watcher.
     *
     * @param context the context
     */
    public HomeWatcher(Context context) {
        this.context = context;
        intentFilter = new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
    }

    /**
     * This method sets the OnHomePressed listener and the receiver for the home button press
     *
     * @param listener the listener to set
     */
    public void setOnHomePressedListener(OnHomePressedListener listener) {
        this.listener = listener;
        recevier = new InnerRecevier();
    }

    /**
     * This method start watching for home button press.
     */
    public void startWatch() {
        if (recevier != null)
            context.registerReceiver(recevier, intentFilter);
    }

    /**
     * The class represents the receiver for the home button press.
     */
    class InnerRecevier extends BroadcastReceiver {
        /**
         * This method is triggered when the home button is pressed and call the listener in consequence.
         *
         * @param context the context
         * @param intent the intent
         */
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action != null && action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {
                String reason = intent.getStringExtra("reason");
                if (reason != null && listener != null) {
                    if (reason.equals("homekey"))
                        listener.onHomePressed();
                    else if (reason.equals("recentapps"))
                        listener.onHomeLongPressed();
                }
            }
        }
    }

    /**
     * The interface OnHomePressed listener which serves as a link between the home watcher
     * and the main menu.
     */
    public interface OnHomePressedListener {
        /**
         * This method is triggered when the user press on Home button.
         * This method will be overridden in the LevelMenu class.
         */
        void onHomePressed();

        /**
         * This method is triggered when the user long press on Home button.
         * This method will be overridden in the LevelMenu class.
         */
        void onHomeLongPressed();
    }
}