package com.example.rsons.tracka;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.rvalerio.fgchecker.AppChecker;

/**
 * Created by rsons on 3/20/2017.
 */

public class SessionService extends IntentService {

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     */
    public SessionService() {
        super("Sessions Recorder");
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {

        Log.d("TRACKASERV", "serv started");

        AppChecker appChecker = new AppChecker();
        appChecker
            .when("com.facebook.katana", new AppChecker.Listener() {
                @Override
                public void onForeground(String packageName) {
                    Log.d("TRACKASERV", "FACEBOOK started");
                }
            }
            ).when("com.instagram.android", new AppChecker.Listener() {
                @Override
                public void onForeground(String packageName) {
                    Log.d("TRACKASERV", "INSTAGRAM started");
                }
            }
        ).timeout(1000).start(this);

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }
}
