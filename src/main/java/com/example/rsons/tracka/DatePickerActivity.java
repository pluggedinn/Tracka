package com.example.rsons.tracka;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.CalendarView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.rsons.tracka.adapter.AppAdapter;
import com.example.rsons.tracka.model.AppData;
import com.orhanobut.logger.Logger;

public class DatePickerActivity extends AppCompatActivity {

    CalendarView calendar;
    Handler handler;

    RecyclerView recView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_picker);

        recView = (RecyclerView) findViewById(R.id.SAppList);
        StaggeredGridLayoutManager staggeredManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recView.setLayoutManager(staggeredManager);

        AppAdapter eAdapter = new AppAdapter();
        recView.setAdapter(eAdapter);
        eAdapter.addApps(AppData.getListData());


        handler = new Handler();
        calendar = (CalendarView) findViewById(R.id.calendarView);
        calendar.setDate(System.currentTimeMillis());

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                Logger.d("finally found the listener, the date is: year " + year + ", month "  + month + ", dayOfMonth " + dayOfMonth);
            }
        });

    }

    public void showCalendar(View v) {
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
