package com.example.rsons.tracka.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
    Handler handler;
    Runnable runnable;
    MyReceiver receiver;
    private String ACTION_STOP_SERVICE = "0";
    private String ACTION_START_HANDLER = "1";
    private String ACTION_STOP_HANDLER = "2";


    public SessionService() {
        super("Sessions Recorder");
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // Registering receiver that tracks if the screen is on
        receiver = new MyReceiver();
        IntentFilter lockFilter = new IntentFilter();
        lockFilter.addAction(Intent.ACTION_SCREEN_ON);
        lockFilter.addAction(Intent.ACTION_SCREEN_OFF);
        registerReceiver(receiver, lockFilter);

        // Initializing the database
        database = openOrCreateDatabase("TrackaDB",MODE_PRIVATE,null);
        database.execSQL("CREATE TABLE IF NOT EXISTS Sessions(package INTEGER, startTime LONG, endTime LONG);");

        // Initializing the handler that will contain the Runnable AppCheckerThread
        handler = new Handler();

        Log.d("TrackaService", "Service created...");
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {

        // Checking if ACTION_STOP_SERVICE event is triggered
        if (ACTION_STOP_SERVICE.equals(intent.getAction())) {
            Log.d("TrackaService","...called to cancel service...");

            // Deleting foreground notification
            NotificationManager mNotifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            mNotifyMgr.cancel(1192);

            // Stopping service and calling onDestroy()
            stopSelf();
            Log.d("TrackaService", "...returning start_not_sticky");
            return START_NOT_STICKY;
        }

        // ... continuing with the execution of the Service
        context = this;
        Log.d("TrackaService", "Service started...");

        // Setting the action when the user taps on the notification
        Intent stopSelf = new Intent(this, SessionService.class);
        stopSelf.setAction(this.ACTION_STOP_SERVICE);
        PendingIntent pStopSelf = PendingIntent.getService(this, 0, stopSelf, PendingIntent.FLAG_CANCEL_CURRENT);

        // Creating notification and starting service in Foreground
        Notification notification = new NotificationCompat.Builder(this)
                .setContentText("Running, tap to stop")
                .setSmallIcon(R.mipmap.ic_minimal)
                .setColor(Color.parseColor("#0B486B"))
                .setPriority(Notification.PRIORITY_MIN)
                //.setShowWhen(false)
                .setContentIntent(pStopSelf)
                .setOngoing(true).build();

        startForeground(1192, notification);

        // Starting the loop that looks for apps opened and closed
        if (ACTION_START_HANDLER.equals(intent.getAction())) {
            startAppCheckerThread();
        } else if (ACTION_STOP_HANDLER.equals(intent.getAction())) {
            stopAppCheckerThread();
        }


        Log.d("TrackaService", "...returning start_sticky");
        return START_STICKY;
    }

    /**
     * Starts the thread loop that checks the apps.
     */
    public void startAppCheckerThread() {
        final int delay = 1000; // milliseconds
        final PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);

        runnable = new Runnable(){
            public void run(){
                appChecker = new AppChecker();
                String packageName = appChecker.getForegroundApp(context);
                boolean isScreenOn = pm.isInteractive();

                // Checking if the packagename string is not null
                if (packageName != null && !packageName.isEmpty()) {

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
                }

                // Restarting this runnable at the delay
                handler.postDelayed(runnable, delay);
            }
        };

        handler.post(runnable);
        Log.d("TrackaService", "...handler started...");
    }

    /**
     * Kills the thread loop that checks all the apps.
     */
    public void stopAppCheckerThread() {
        processFlags();
        if (runnable != null) {
            handler.removeCallbacks(runnable);
        }
        Log.d("TrackaService", "...handler stopped...");
    }

    /**
     * Call this method if you want to stop recording the foreground app want to register it to the databse.
     *
     * When any of the flags are on it means that the one of those apps have been open.
     * When you run this method you notify Tracka that the app have been closed (or the foreground app has been changed)
     * and then you write into the database the Session of the app just closed.
     */
    public void processFlags() {

        if (katanaFlag) {
            Log.d("TrackaService", "FACEBOOK stopped");
            katanaFlag = false;
            String query = "INSERT INTO Sessions VALUES('0','"+currAppStartTime+"','"+new Date().getTime()+"');";
            database.execSQL(query);

            Cursor cursor = database.rawQuery("Select * from Sessions",null);
            Log.d("TrackaService", cursor.getCount() + " sessions total");
            cursor.close();
        }
        if (instagramFlag) {
            Log.d("TrackaService", "INSTAGRAM stopped");
            instagramFlag = false;
            String query = "INSERT INTO Sessions VALUES('1','"+currAppStartTime+"','"+new Date().getTime()+"');";
            database.execSQL(query);

            Cursor cursor = database.rawQuery("Select * from Sessions",null);
            Log.d("TrackaService", cursor.getCount() + " sessions total");
            cursor.close();
        }
        if (snapchatFlag) {
            Log.d("TrackaService", "SNAPCHAT stopped");
            snapchatFlag = false;
            String query = "INSERT INTO Sessions VALUES('2','"+currAppStartTime+"','"+new Date().getTime()+"');";
            database.execSQL(query);

            Cursor cursor = database.rawQuery("Select * from Sessions",null);
            Log.d("TrackaService", cursor.getCount() + " sessions total");
            cursor.close();
        }
        if (twitterFlag) {
            Log.d("TrackaService", "TWITTER stopped");
            twitterFlag = false;
            String query = "INSERT INTO Sessions VALUES('3','"+currAppStartTime+"','"+new Date().getTime()+"');";
            database.execSQL(query);

            Cursor cursor = database.rawQuery("Select * from Sessions",null);
            Log.d("TrackaService", cursor.getCount() + " sessions total");
            cursor.close();
        }
        if (youtubeFlag) {
            Log.d("TrackaService", "YOUTUBE stopped");
            youtubeFlag = false;
            String query = "INSERT INTO Sessions VALUES('4','"+currAppStartTime+"','"+new Date().getTime()+"');";
            database.execSQL(query);

            Cursor cursor = database.rawQuery("Select * from Sessions",null);
            Log.d("TrackaService", cursor.getCount() + " sessions total");
            cursor.close();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopAppCheckerThread();
        unregisterReceiver(receiver);
        Log.d("TrackaService", "...Service destroyed");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }



}
