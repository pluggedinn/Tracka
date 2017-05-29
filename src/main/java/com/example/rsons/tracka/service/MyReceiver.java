package com.example.rsons.tracka.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

/**
 * Created by rsons on 3/27/2017.
 */

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        // Detecting the end of the boot
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            Log.d("TrackaRecevier", "Boot completed. Starting service");
            Intent i = new Intent(context, SessionService.class);

            context.startService(i);
        }

        // Detecting screen ON
        else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
            Log.d("TrackaRecevier", "Screen ON. Starting service with handler on");
            Intent i = new Intent(context, SessionService.class);
            i.setAction("1"); // 1 represents ACTION_START_HANDLER

            context.startService(i);
        }

        // Detecting screen OFF
        else if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
            Log.d("TrackaRecevier", "Screen OFF. Starting service with handler off");
            Intent i = new Intent(context, SessionService.class);
            i.setAction("2"); // 2 represents ACTION_STOP_HANDLER

            context.startService(i);
        }
    }
}
