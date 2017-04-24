package com.example.rsons.tracka;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.rsons.tracka.adapter.PastAdapter;
import com.squareup.timessquare.CalendarPickerView;


public class PastActivity extends AppCompatActivity {

    //TODO use MPAndroidChart to create a daily review. It's triggered by a notification that starts at the end of the day (once you go home)

    CalendarPickerView calendar;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past);

        recyclerView = (RecyclerView) findViewById(R.id.appList);
        StaggeredGridLayoutManager staggeredManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredManager);

        final PastAdapter eAdapter = new PastAdapter();
        recyclerView.setAdapter(eAdapter);
    }

//    public void showCalendar(View v) {
//        Handler handler = new Handler();
//
//        if (calendar.getVisibility() == View.VISIBLE) {
//            YoYo.with(Techniques.FadeOut)
//                    .duration(500)
//                    .playOn(calendar);
//            handler.postDelayed(new Runnable(){
//                @Override
//                public void run(){
//                    calendar.setVisibility(View.GONE);
//                }
//            }, 500);
//        } else {
//            YoYo.with(Techniques.FadeIn)
//                .duration(500)
//                .playOn(calendar);
//            calendar.setVisibility(View.VISIBLE);
//        }
//    }
}
