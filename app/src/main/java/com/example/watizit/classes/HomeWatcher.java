package com.example.watizit.classes;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

public class HomeWatcher {

    private Context context;
    private IntentFilter intentFilter;
    private OnHomePressedListener listener;
    private InnerRecevier recevier;

    public HomeWatcher(Context context)
    {
        this.context = context;
        intentFilter = new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
    }

    public void setOnHomePressedListener(OnHomePressedListener listener)
    {
        this.listener = listener;
        recevier = new InnerRecevier();
    }

    public void startWatch()
    {
        if (recevier != null)
            context.registerReceiver(recevier, intentFilter);
    }

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

    public interface OnHomePressedListener
    {
        void onHomePressed();
        void onHomeLongPressed();
    }
}