package com.example.rsons.tracka;

import android.app.AppOpsManager;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.rsons.tracka.service.SessionService;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    TextView text1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text1 = (TextView) findViewById(R.id.text);

        // Starting service that tracks all the apps
        Intent intent = new Intent(this, SessionService.class);
        startService(intent);
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
