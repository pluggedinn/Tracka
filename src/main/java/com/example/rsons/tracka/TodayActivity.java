package com.example.rsons.tracka;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.example.rsons.tracka.adapter.TodayAdapter;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class TodayActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today);

        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        StaggeredGridLayoutManager staggeredManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredManager);

        final TodayAdapter eAdapter = new TodayAdapter();
        recyclerView.setAdapter(eAdapter);
    }

    // pass context to Calligraphy
    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
    }
}
