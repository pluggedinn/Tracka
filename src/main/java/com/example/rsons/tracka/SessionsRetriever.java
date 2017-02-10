package com.example.rsons.tracka;

import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by rsons on 2/9/2017.
 */

public class SessionsRetriever {

    HashMap<String, String> appsCodes;
    UsageStatsManager statsManager;
    Context context;

    public SessionsRetriever(Context c) {
        this.context = c;

        statsManager = (UsageStatsManager) c.getSystemService(c.USAGE_STATS_SERVICE);

        appsCodes = new HashMap<String, String>();
        appsCodes.put("fb", "com.facebook.katana");
        appsCodes.put("sc", "com.snapchat.android");
        appsCodes.put("yt", "com.google.android.youtube");
        appsCodes.put("ig", "com.instagram.android");
        appsCodes.put("tw", "com.twitter.android");

    }

    /**
     * Returns a list of sessions in the date period for that App in the form of UsageStats.
     *
     * returns List<UsageStats>
     * @param startDate
     * @param endDate
     * @param appCode
     * @return
     */
    public List<UsageStats> getSessionsApp(long startDate, long endDate, String appCode) {

        List<UsageStats> allSessions = statsManager.queryUsageStats(UsageStatsManager.INTERVAL_BEST, startDate, endDate);
        List<UsageStats> appSessions = new ArrayList<>();

        for (UsageStats i : allSessions) {
            if (i.getPackageName().contains(appsCodes.get(appCode))) {
                appSessions.add(i);
            }
        }

        return appSessions;
    }

    /**
     * Returns a list of sessions in the date period for all the Apps installed in form of UsageStats
     *
     * return List<UsageStats></UsageStats>
     * @param startDate
     * @param endDate
     * @return
     */
    public List<UsageStats> getSessionsAllApps(long startDate, long endDate) {

        List<UsageStats> allSessions = statsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, startDate, endDate);
        List<UsageStats> appSessions = new ArrayList<>();

        for (UsageStats i : allSessions) {
            if (appsCodes.containsValue(i.getPackageName()) && Utils.isPackageInstalled(i.getPackageName(), context.getPackageManager())) {
                appSessions.add(i);
            }
        }

        return appSessions;
    }


}

