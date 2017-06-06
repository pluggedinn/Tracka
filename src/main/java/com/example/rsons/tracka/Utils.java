package com.example.rsons.tracka;

import android.app.AppOpsManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import com.example.rsons.tracka.model.Session;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by rsons on 2/9/2017.
 */

public class Utils {

    public static String convertMillisToDuration(long millis) {
        if (TimeUnit.MILLISECONDS.toHours(millis) == 0) {
            return TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)) + "m";
        } else {
            return TimeUnit.MILLISECONDS.toHours(millis) + "h " + (TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis))) + "m";
        }
    }

    public static boolean isPackageInstalled(String packagename, PackageManager packageManager) {
        try {
            packageManager.getPackageInfo(packagename, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public static String convertMillisToTime(long timeStamp) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeStamp);

        int mYear = calendar.get(Calendar.YEAR);
        int mMonth = calendar.get(Calendar.MONTH);
        int mDay = calendar.get(Calendar.DAY_OF_MONTH);
        int mHour = calendar.get(Calendar.HOUR_OF_DAY);
        int mMinute = calendar.get(Calendar.MINUTE);

        return String.format("%02d:%02d:%02d %02d:%02d", mMonth, mDay, mYear, mHour, mMinute);
    }

    public static long convertSessionsToMinutes(List<Session> sessions) {
        long total = 0;
        for(int i = 0; i < sessions.size(); i++) {
            total += sessions.get(i).endTime - sessions.get(i).startTime;
        }

        return TimeUnit.MILLISECONDS.toMinutes(total);
    }
}
