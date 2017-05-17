package com.example.rsons.tracka;

import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.example.rsons.tracka.model.Session;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by rsons on 2/9/2017.
 */

public class SessionsRetriever {

    Context context;
    SQLiteDatabase myDB;

    public SessionsRetriever(Context c) {
        this.context = c;

        // Initiliazing LOCAL database
        File dbFile = c.getDatabasePath("TrackaDB");
        myDB = SQLiteDatabase.openDatabase(dbFile.getAbsolutePath(), null, MODE_PRIVATE);

    }

    public List<Session> getSessionsApp(long startDate, long endDate, int appCode) {
        List<Session> sessionList = new ArrayList<Session>();

        Cursor result = myDB.rawQuery("SELECT * FROM Sessions WHERE package="+appCode+" AND startTime>"+startDate+" AND endTime<"+endDate+"",null);
        int rowsCount = result.getCount();
        result.moveToFirst();
        for(int i = 0; i < rowsCount; i++) {
            sessionList.add(new Session(result.getInt(0), result.getLong(1), result.getLong(2)));
        }

        result.close();
        return sessionList;
    }


    public List<Session> getSessionsAllApps(long startDate, long endDate) {
        List<Session> sessionList = new ArrayList<Session>();

        Cursor result = myDB.rawQuery("SELECT * FROM Sessions WHERE startTime>"+startDate+" AND endTime<"+endDate+"",null);
        int rowsCount = result.getCount();
        result.moveToFirst();
        for(int i = 0; i < rowsCount; i++) {
            sessionList.add(new Session(result.getInt(0), result.getLong(1), result.getLong(2)));
            result.moveToNext();
        }

        result.close();
        return sessionList;
    }


}

