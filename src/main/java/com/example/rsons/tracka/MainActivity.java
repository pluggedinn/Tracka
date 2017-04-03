package com.example.rsons.tracka;

import android.app.AppOpsManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    TextView text1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, SessionService.class);
        startService(intent);
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

        File dbFile = this.getDatabasePath("TrackaDB");
        SQLiteDatabase myDB = SQLiteDatabase.openDatabase(dbFile.getAbsolutePath(), null, MODE_PRIVATE);
        Cursor result = myDB.rawQuery("Select * from Sessions",null);
        StringBuilder builder = new StringBuilder();

        int rowsCount = result.getCount();
        builder.append("sessions: " + rowsCount + "\n");

        result.moveToFirst();
        for(int i = 0; i < rowsCount; i++) {
            builder.append(result.getLong(0) + " " + result.getLong(1) + " " + result.getLong(2) + " " + Utils.convertMillisToTime(result.getLong(1)) + "\n");
            result.moveToNext();
        }

        text1.setText(builder.toString());
    }


    public void startOtherActivity(View v) {
        Intent intent = new Intent(this, DatePickerActivity.class);
        startActivity(intent);
    }
}
