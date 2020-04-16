package com.example.watizit.classes;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

/**
 * The type Home watcher.
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
    public HomeWatcher(Context context)
    {
        this.context = context;
        intentFilter = new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
    }

    /**
     * Sets on home pressed listener.
     *
     * @param listener the listener
     */
    public void setOnHomePressedListener(OnHomePressedListener listener)
    {
        this.listener = listener;
        recevier = new InnerRecevier();
    }

    /**
     * Start watch.
     */
    public void startWatch()
    {
        if (recevier != null)
            context.registerReceiver(recevier, intentFilter);
    }

    /**
     * The type Inner recevier.
     */
    class InnerRecevier extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            String action = intent.getAction();
            if (action != null && action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS))
            {
                String reason = intent.getStringExtra("reason");
                if (reason != null && listener != null)
                {
                    if (reason.equals("homekey"))
                        listener.onHomePressed();
                    else if (reason.equals("recentapps"))
                        listener.onHomeLongPressed();
                }
            }
        }
    }

    /**
     * The interface On home pressed listener.
     */
    public interface OnHomePressedListener
    {
        /**
         * On home pressed.
         */
        void onHomePressed();

        /**
         * On home long pressed.
         */
        void onHomeLongPressed();
    }
}