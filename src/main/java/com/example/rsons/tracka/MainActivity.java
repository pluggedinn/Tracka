package com.example.rsons.tracka;

import android.annotation.TargetApi;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.rsons.tracka.service.MyReceiver;
import com.example.rsons.tracka.service.SessionService;

import java.io.File;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


// TODO: 4/25/2017 setting up custom font  
public class MainActivity extends AppCompatActivity {

    TextView text1;
    MyReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text1 = (TextView) findViewById(R.id.text);

        // Checking & asking if the user has access to settings
        requestUsageStatsPermission();

        // Starting service that tracks all the apps
        Intent intent = new Intent(this, SessionService.class);
        startService(intent);

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    void requestUsageStatsPermission() {
        if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
                && !hasUsageStatsPermission(this)) {
            startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    boolean hasUsageStatsPermission(Context context) {
        AppOpsManager appOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
        int mode = appOps.checkOpNoThrow("android:get_usage_stats",
                android.os.Process.myUid(), context.getPackageName());
        boolean granted = mode == AppOpsManager.MODE_ALLOWED;
        return granted;
    }

    public void clickShowSessions(View v) {

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


    public void clickPastActivity(View v) {
        Intent intent = new Intent(this, PastActivity.class);
        startActivity(intent);
    }

    public void clickHomeActivity(View v) {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}
