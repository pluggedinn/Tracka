package com.example.rsons.tracka;

import android.app.usage.UsageStats;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.rsons.tracka.adapter.AppAdapter;
import com.example.rsons.tracka.model.AppDataFormatter;
import com.squareup.timessquare.CalendarPickerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DatePickerActivity extends AppCompatActivity {

    CalendarPickerView calendar;
    RecyclerView recView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_picker);

        recView = (RecyclerView) findViewById(R.id.AppList);
        StaggeredGridLayoutManager staggeredManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recView.setLayoutManager(staggeredManager);

        final AppAdapter eAdapter = new AppAdapter();
        recView.setAdapter(eAdapter);
        // eAdapter.addApps(AppDataFormatter.getListData());

        Calendar lastYear = Calendar.getInstance();
        lastYear.add(Calendar.YEAR, -1);

        Calendar today = Calendar.getInstance();
        Calendar previousDay = Calendar.getInstance();
        previousDay.add(Calendar.DATE, -4);

        Calendar yesterday = Calendar.getInstance();
        yesterday.add(Calendar.DATE, -1);

        List<Date> defaultDates = new ArrayList<>();
        defaultDates.add(previousDay.getTime());
        defaultDates.add(yesterday.getTime());

        final SessionsRetriever dataRetriever = new SessionsRetriever(this);

        calendar = (CalendarPickerView) findViewById(R.id.calendarView);
        calendar.init(lastYear.getTime(), today.getTime())
                .inMode(CalendarPickerView.SelectionMode.RANGE)
                .withSelectedDates(defaultDates);

        calendar.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {
                List<Date> selectedDates = calendar.getSelectedDates();

                // eAdapter.clearApps();

                if (selectedDates.size() == 1) {
                    Calendar dayBefore = Calendar.getInstance();
                    dayBefore.setTime(date);
                    dayBefore.add(Calendar.DATE, -1);
                    Log.d("START", dayBefore.getTime()+"");
                    Log.d("END", date.toString());
                    List<UsageStats> data = dataRetriever.getSessionsAllApps(dayBefore.getTimeInMillis(), date.getTime());
                    Log.d("DATA", data.size() + data.toString());
                    eAdapter.addApps(AppDataFormatter.createApps(data));

                } else {
                    Log.d("START", selectedDates.get(0).toString()+"");
                    Log.d("END", selectedDates.get(selectedDates.size() - 1).toString());
                    List<UsageStats> data = dataRetriever.getSessionsAllApps(selectedDates.get(0).getTime(), selectedDates.get(selectedDates.size() - 1).getTime());
                    Log.d("DATA", data.size() + data.toString());
                    eAdapter.addApps(AppDataFormatter.createApps(data));
                }
            }

            @Override
            public void onDateUnselected(Date date) {

            }
        });
    }

    public void showCalendar(View v) {
        Handler handler = new Handler();

        if (calendar.getVisibility() == View.VISIBLE) {
            YoYo.with(Techniques.FadeOut)
                    .duration(500)
                    .playOn(calendar);
            handler.postDelayed(new Runnable(){
                @Override
                public void run(){
                    calendar.setVisibility(View.GONE);
                }
            }, 500);
        } else {
            YoYo.with(Techniques.FadeIn)
                .duration(500)
                .playOn(calendar);
            calendar.setVisibility(View.VISIBLE);
        }
    }
}
