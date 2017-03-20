package com.example.rsons.tracka;

import android.app.IntentService;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;

import com.rvalerio.fgchecker.AppChecker;

/**
 * Created by rsons on 3/20/2017.
 */

public class SessionService extends IntentService {

    boolean katanaFlag;
    boolean instagramFlag;
    boolean snapchatFlag;
    boolean twitterFlag;
    boolean youtubeFlag;

    public SessionService() {
        super("Sessions Recorder");
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {

        Log.d("TRACKASERV", "serv started");

        SQLiteDatabase database = openOrCreateDatabase("your database name",MODE_PRIVATE,null);
        database.execSQL("CREATE TABLE IF NOT EXISTS Sessions(package INTEGER, startTime INTEGER, endTime INTEGER);");

        final Handler h = new Handler();
        final int delay = 1000; //milliseconds
        final AppChecker appChecker = new AppChecker();

        //// TODO: 3/21/2017 when a user switch between an app to another both app result to be open at the same time 

        h.postDelayed(new Runnable(){
            public void run(){
                String packageName = appChecker.getForegroundApp(getApplicationContext());
                if (packageName.contains("com.facebook.katana")) {
                    if (!katanaFlag) {
                        Log.d("TRACKASERV", "FACEBOOK started");
                        katanaFlag = true;
                    }
                } else if (packageName.contains("com.instagram.android")) {
                    if (!instagramFlag) {
                        Log.d("TRACKASERV", "INSTAGRAM started");
                        instagramFlag = true;
                    }
                } else if (packageName.contains("com.snapchat.android")) {
                    if (!snapchatFlag) {
                        Log.d("TRACKASERV", "SNAPCHAT started");
                        snapchatFlag = true;
                    }
                } else if (packageName.contains("com.twitter.android")) {
                    if (!twitterFlag) {
                        Log.d("TRACKASERV", "TWITTER started");
                        twitterFlag = true;
                    }
                } else if (packageName.contains("com.google.android.youtube")) {
                    if (!youtubeFlag) {
                        Log.d("TRACKASERV", "YOUTUBE started");
                        youtubeFlag = true;
                    }
                } else {
                    processFlags();
                }
                h.postDelayed(this, delay);
            }
        }, delay);

        return super.onStartCommand(intent, flags, startId);
    }

    public void processFlags() {
        if (katanaFlag) {
            Log.d("TRACKASERV", "FACEBOOK stopped");
            katanaFlag = false;
        }
        if (instagramFlag) {
            Log.d("TRACKASERV", "INSTAGRAM stopped");
            instagramFlag = false;
        }
        if (snapchatFlag) {
            Log.d("TRACKASERV", "SNAPCHAT stopped");
            snapchatFlag = false;
        }
        if (twitterFlag) {
            Log.d("TRACKASERV", "TWITTER stopped");
            twitterFlag = false;
        }
        if (youtubeFlag) {
            Log.d("TRACKASERV", "YOUTUBE stopped");
            youtubeFlag = false;
        }
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }

}
