package com.example.rsons.tracka;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.util.Calendar;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;

import com.rvalerio.fgchecker.AppChecker;

import java.util.Date;

/**
 * Created by rsons on 3/20/2017.
 */

public class SessionService extends IntentService {

    /*
        - 1: none
        0: katana
        1: instagram
        2: snapchat
        3: twitter
        4: youtube
     */
    int lastAppOpened = -1;
    long currAppStartTime;
    boolean katanaFlag;
    boolean instagramFlag;
    boolean snapchatFlag;
    boolean twitterFlag;
    boolean youtubeFlag;

    SQLiteDatabase database;
    Context context;
    AppChecker appChecker;


    public SessionService() {
        super("Sessions Recorder");
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {

        context = this;

        Log.d("TRACKASERV", "serv started");

        database = openOrCreateDatabase("TrackaDB",MODE_PRIVATE,null);
        database.execSQL("CREATE TABLE IF NOT EXISTS Sessions(package INTEGER, startTime LONG, endTime LONG);");

        final Handler h = new Handler();
        final int delay = 1000; //milliseconds


        h.postDelayed(new Runnable(){
            public void run(){
                appChecker = new AppChecker();
                String packageName = appChecker.getForegroundApp(context);

                if (packageName.contains("com.facebook.katana")) {
                    if (lastAppOpened != 0) {
                        processFlags();
                    }
                    if (!katanaFlag) {
                        Log.d("TRACKASERV", "FACEBOOK started");
                        katanaFlag = true;
                        lastAppOpened = 0;
                        currAppStartTime = new Date().getTime();
                    }
                } else if (packageName.contains("com.instagram.android")) {
                    if (lastAppOpened != 1) {
                        processFlags();
                    }
                    if (!instagramFlag) {
                        Log.d("TRACKASERV", "INSTAGRAM started");
                        instagramFlag = true;
                        lastAppOpened = 1;
                        currAppStartTime = new Date().getTime();
                    }
                } else if (packageName.contains("com.snapchat.android")) {
                    if (lastAppOpened != 2) {
                        processFlags();
                    }
                    if (!snapchatFlag) {
                        Log.d("TRACKASERV", "SNAPCHAT started");
                        snapchatFlag = true;
                        lastAppOpened = 2;
                        currAppStartTime = new Date().getTime();
                    }
                } else if (packageName.contains("com.twitter.android")) {
                    if (lastAppOpened != 3) {
                        processFlags();
                    }
                    if (!twitterFlag) {
                        Log.d("TRACKASERV", "TWITTER started");
                        twitterFlag = true;
                        lastAppOpened = 3;
                        currAppStartTime = new Date().getTime();
                    }
                } else if (packageName.contains("com.google.android.youtube")) {
                    if (lastAppOpened != 4) {
                        processFlags();
                    }
                    if (!youtubeFlag) {
                        Log.d("TRACKASERV", "YOUTUBE started");
                        youtubeFlag = true;
                        lastAppOpened = 4;
                        currAppStartTime = new Date().getTime();
                    }
                } else {
                    processFlags();
                    lastAppOpened = -1;
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
            String query = "INSERT INTO Sessions VALUES('0','"+currAppStartTime+"','"+new Date().getTime()+"');";
            database.execSQL(query);
        }
        if (instagramFlag) {
            Log.d("TRACKASERV", "INSTAGRAM stopped");
            instagramFlag = false;
            String query = "INSERT INTO Sessions VALUES('1','"+currAppStartTime+"','"+new Date().getTime()+"');";
            database.execSQL(query);
        }
        if (snapchatFlag) {
            Log.d("TRACKASERV", "SNAPCHAT stopped");
            snapchatFlag = false;
            String query = "INSERT INTO Sessions VALUES('2','"+currAppStartTime+"','"+new Date().getTime()+"');";
            database.execSQL(query);
        }
        if (twitterFlag) {
            Log.d("TRACKASERV", "TWITTER stopped");
            twitterFlag = false;
            String query = "INSERT INTO Sessions VALUES('3','"+currAppStartTime+"','"+new Date().getTime()+"');";
            database.execSQL(query);
        }
        if (youtubeFlag) {
            Log.d("TRACKASERV", "YOUTUBE stopped");
            youtubeFlag = false;
            String query = "INSERT INTO Sessions VALUES('4','"+currAppStartTime+"','"+new Date().getTime()+"');";
            database.execSQL(query);
        }

        Cursor result = database.rawQuery("Select * from Sessions",null);
        Log.d("TRACKASERV", result.getCount() + "");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }

}
