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
import com.example.rsons.tracka.model.AppFormatter;
import com.example.rsons.tracka.model.Session;
import com.squareup.timessquare.CalendarPickerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DatePickerActivity extends AppCompatActivity {

    //TODO use MPAndroidChart to create a daily review. It's triggered by a notification that starts at the end of the day (once you go home)

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
        // eAdapter.addApps(AppFormatter.getListData());

        // creatin default dates for the Calendar picker
        Calendar lastYear = Calendar.getInstance();
        Calendar today = Calendar.getInstance();
        Calendar yesterday = Calendar.getInstance();
        Calendar previousDay = Calendar.getInstance();
        lastYear.add(Calendar.YEAR, -1);
        previousDay.add(Calendar.DATE, -2);
        yesterday.add(Calendar.DATE, -1);

        // adding them to a list
        List<Date> defaultDates = new ArrayList<>();
        defaultDates.add(previousDay.getTime());
        defaultDates.add(yesterday.getTime());


        calendar = (CalendarPickerView) findViewById(R.id.calendarView);
        calendar.init(lastYear.getTime(), today.getTime())
                .inMode(CalendarPickerView.SelectionMode.RANGE)
                .withSelectedDates(defaultDates);

        // initializing sessionretriever
        final SessionsRetriever dataRetriever = new SessionsRetriever(this);

        calendar.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {

                // getting selected dates
                List<Date> selectedDates = calendar.getSelectedDates();

                // clearing adapter
                eAdapter.clearApps();

                if (selectedDates.size() == 1) {
                    Calendar dayAfter = Calendar.getInstance();
                    dayAfter.setTime(date);
                    dayAfter.add(Calendar.DATE, 1);
                    List<Session> data = dataRetriever.getSessionsAllApps(date.getTime(), dayAfter.getTimeInMillis());
                    eAdapter.addApps(AppFormatter.createApps(data));

                    Log.d("DATA", data.size() + data.toString());
                    Log.d("START", date.toString());
                    Log.d("END", dayAfter.getTime()+"");
                } else {

                    Calendar lastDay = Calendar.getInstance();
                    lastDay.setTime(selectedDates.get(selectedDates.size() - 1));
                    lastDay.add(Calendar.DATE, 1);
                    List<Session> data = dataRetriever.getSessionsAllApps(selectedDates.get(0).getTime(), lastDay.getTimeInMillis());
                    eAdapter.addApps(AppFormatter.createApps(data));
                    showCalendar(null);

                    Log.d("START", selectedDates.get(0).toString()+"");
                    Log.d("END", lastDay.getTime()+"");
                    Log.d("DATA", data.size() + data.toString());
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
