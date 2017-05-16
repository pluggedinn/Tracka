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

            // Registering SCREEN ON event to this receiver
            Intent i = new Intent(context, SessionService.class);
            IntentFilter lockFilter = new IntentFilter();
            lockFilter.addAction(Intent.ACTION_SCREEN_ON);
            lockFilter.addAction(Intent.ACTION_SCREEN_OFF);
            context.getApplicationContext().registerReceiver(this, lockFilter);

            // Starting SessionService
            context.startService(i);
        } else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
            Log.d("TrackaRecevier", "Screen on. Starting service");
            Intent i = new Intent(context, SessionService.class);

            // Starting SessionService
            context.startService(i);
        }
    }
}
