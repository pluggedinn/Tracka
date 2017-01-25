package com.example.rsons.tracka;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    TextView text1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text1 = (TextView) findViewById(R.id.text);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 2:
                if (hasPermission()){
                }else{
                    requestPermission();
                }
                break;
        }
    }

    public void checkProcesses(View v) {
        if (!hasPermission()) {
            requestPermission();
        } else {
            getStats();
        }

    }

    private void requestPermission() {
        startActivityForResult(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS), 2);
    }

    private boolean hasPermission() {
        AppOpsManager appOps = (AppOpsManager)
                getSystemService(this.APP_OPS_SERVICE);
        int mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS,
                android.os.Process.myUid(), getPackageName());
        return mode == AppOpsManager.MODE_ALLOWED;
    }

    private void getStats() {
        UsageStatsManager statsManager = (UsageStatsManager) this.getSystemService(this.USAGE_STATS_SERVICE);
        List<UsageStats> listy =
                statsManager.queryUsageStats(
                        UsageStatsManager.INTERVAL_BEST,
                        System.currentTimeMillis()- TimeUnit.DAYS.toMillis(3),
                        System.currentTimeMillis());

        StringBuilder builder = new StringBuilder();

        for (UsageStats i : listy) {

            if (i.getPackageName().contains("katana")) {
                long timeInForeground = i.getTotalTimeInForeground();

                builder.append(i.getPackageName() + " " + convertToHours(timeInForeground) + "\n");
//                Log.d("snippet", i.getPackageName() + " " + convertToHours(timeInForeground));
            }
        }

        text1.setText(builder.toString());
    }

    private String convertToHours(long millis) {
        String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));

        return hms;
    }
}
