package com.example.rsons.tracka.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by rsons on 3/27/2017.
 */

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            Log.d("TRACKARECEIVER", "Boot completed. Starting service");
            Intent i = new Intent(context, SessionService.class);
            context.startService(i);
        }
        if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
            Log.d("TRACKARECEIVER", "Screen on. Starting service");
            Intent i = new Intent(context, SessionService.class);
            context.startService(i);
        }
    }
}
