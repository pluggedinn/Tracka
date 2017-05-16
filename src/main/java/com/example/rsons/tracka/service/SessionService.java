package com.example.rsons.tracka.service;

import android.app.IntentService;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.os.Handler;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.example.rsons.tracka.R;
import com.rvalerio.fgchecker.AppChecker;

import java.util.Date;

/**
 * Created by rsons on 3/20/2017.
 */

// TODO: create color strings so that can be accessed from anywhere. here is hard coded
// TODO: implement the onClick to stop the service 9 (check PendingIntent)
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
    public void onCreate() {
        super.onCreate();


    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {

        context = this;

        Log.d("TrackaService", "Service started");

        // Initializing the database
        database = openOrCreateDatabase("TrackaDB",MODE_PRIVATE,null);
        database.execSQL("CREATE TABLE IF NOT EXISTS Sessions(package INTEGER, startTime LONG, endTime LONG);");

        // Starting the service in Foreground mode and turning on the notification
        Bitmap icon = BitmapFactory.decodeResource(getResources(),
                R.mipmap.ic_minimal);

        Notification notification = new NotificationCompat.Builder(this)
                .setContentText("Running, tap to stop")
                .setSmallIcon(R.mipmap.ic_minimal)
                .setColor(Color.parseColor("#0B486B"))
                .setPriority(Notification.PRIORITY_MIN)
                .setShowWhen(false)
                .setOngoing(true).build();

        startForeground(1192,
                notification);


        // Starting the loop that looks for apps opened and closed
        final Handler h = new Handler();
        final int delay = 1000; //milliseconds
        final PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);

        h.postDelayed(new Runnable(){
            public void run(){
                appChecker = new AppChecker();
                String packageName = appChecker.getForegroundApp(context);
                boolean isScreenOn = pm.isInteractive();

                if (packageName.contains("com.facebook.katana") && isScreenOn) {
                    if (lastAppOpened != 0) {
                        processFlags();
                    }
                    if (!katanaFlag) {
                        Log.d("TrackaService", "FACEBOOK started");
                        katanaFlag = true;
                        lastAppOpened = 0;
                        currAppStartTime = new Date().getTime();
                    }
                } else if (packageName.contains("com.instagram.android") && isScreenOn) {
                    if (lastAppOpened != 1) {
                        processFlags();
                    }
                    if (!instagramFlag) {
                        Log.d("TrackaService", "INSTAGRAM started");
                        instagramFlag = true;
                        lastAppOpened = 1;
                        currAppStartTime = new Date().getTime();
                    }
                } else if (packageName.contains("com.snapchat.android") && isScreenOn) {
                    if (lastAppOpened != 2) {
                        processFlags();
                    }
                    if (!snapchatFlag) {
                        Log.d("TrackaService", "SNAPCHAT started");
                        snapchatFlag = true;
                        lastAppOpened = 2;
                        currAppStartTime = new Date().getTime();
                    }
                } else if (packageName.contains("com.twitter.android") && isScreenOn) {
                    if (lastAppOpened != 3) {
                        processFlags();
                    }
                    if (!twitterFlag) {
                        Log.d("TrackaService", "TWITTER started");
                        twitterFlag = true;
                        lastAppOpened = 3;
                        currAppStartTime = new Date().getTime();
                    }
                } else if (packageName.contains("com.google.android.youtube") && isScreenOn) {
                    if (lastAppOpened != 4) {
                        processFlags();
                    }
                    if (!youtubeFlag) {
                        Log.d("TrackaService", "YOUTUBE started");
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

        return START_STICKY;
    }

    public void processFlags() {
        if (katanaFlag) {
            Log.d("TrackaService", "FACEBOOK stopped");
            katanaFlag = false;
            String query = "INSERT INTO Sessions VALUES('0','"+currAppStartTime+"','"+new Date().getTime()+"');";
            database.execSQL(query);
            Cursor result = database.rawQuery("Select * from Sessions",null);
            Log.d("TrackaService", result.getCount() + " sessions total");
        }
        if (instagramFlag) {
            Log.d("TrackaService", "INSTAGRAM stopped");
            instagramFlag = false;
            String query = "INSERT INTO Sessions VALUES('1','"+currAppStartTime+"','"+new Date().getTime()+"');";
            database.execSQL(query);
            Cursor result = database.rawQuery("Select * from Sessions",null);
            Log.d("TrackaService", result.getCount() + " sessions total");
        }
        if (snapchatFlag) {
            Log.d("TrackaService", "SNAPCHAT stopped");
            snapchatFlag = false;
            String query = "INSERT INTO Sessions VALUES('2','"+currAppStartTime+"','"+new Date().getTime()+"');";
            database.execSQL(query);
            Cursor result = database.rawQuery("Select * from Sessions",null);
            Log.d("TrackaService", result.getCount() + " sessions total");
        }
        if (twitterFlag) {
            Log.d("TrackaService", "TWITTER stopped");
            twitterFlag = false;
            String query = "INSERT INTO Sessions VALUES('3','"+currAppStartTime+"','"+new Date().getTime()+"');";
            database.execSQL(query);
            Cursor result = database.rawQuery("Select * from Sessions",null);
            Log.d("TrackaService", result.getCount() + " sessions total");
        }
        if (youtubeFlag) {
            Log.d("TrackaService", "YOUTUBE stopped");
            youtubeFlag = false;
            String query = "INSERT INTO Sessions VALUES('4','"+currAppStartTime+"','"+new Date().getTime()+"');";
            database.execSQL(query);
            Cursor result = database.rawQuery("Select * from Sessions",null);
            Log.d("TrackaService", result.getCount() + " sessions total");
        }


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("TrackaService", "Service stopped");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }



}
