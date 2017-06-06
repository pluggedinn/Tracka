package com.example.rsons.tracka.model;

import android.content.Context;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.airbnb.epoxy.EpoxyModel;
import com.example.rsons.tracka.R;
import com.example.rsons.tracka.SessionsRetriever;
import com.example.rsons.tracka.Utils;
import com.example.rsons.tracka.formatter.BarMinutesFormatter;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rsons on 6/3/2017.
 */

public class TodayHeaderModel extends EpoxyModel<RelativeLayout> {

    @BindView(R.id.hoursChart) HorizontalBarChart hoursChart;
    @BindView(R.id.accessesChart) BarChart accessesChart;
    @BindView(R.id.timeAccessChart) LineChart timeAccessChart;
    private SessionsRetriever retriever;
    private Context context;

    @Override
    protected int getDefaultLayout() {
        return R.layout.cell_header_today;
    }

    @Override
    public void bind(RelativeLayout view) {
        super.bind(view);
        ButterKnife.bind(this, view);
        context = view.getContext();
        retriever = new SessionsRetriever(context);

        // Midnight of today
        Calendar beginToday = Calendar.getInstance();
        beginToday.set(Calendar.HOUR_OF_DAY, 0);
        beginToday.set(Calendar.MINUTE, 0);
        beginToday.set(Calendar.SECOND, 0);
        beginToday.set(Calendar.MILLISECOND, 0);
        // Right now
        Date now = new Date();

        List<Session> facebookData = retriever.getSessionsApp(beginToday.getTimeInMillis(), now.getTime(), 0);
        List<Session> instagramData = retriever.getSessionsApp(beginToday.getTimeInMillis(), now.getTime(), 1);
        List<Session> snapchatData = retriever.getSessionsApp(beginToday.getTimeInMillis(), now.getTime(), 2);
        List<Session> twitterData = retriever.getSessionsApp(beginToday.getTimeInMillis(), now.getTime(), 3);
        List<Session> youtubeData = retriever.getSessionsApp(beginToday.getTimeInMillis(), now.getTime(), 4);

        // Setting up data for hoursChart
        List<BarEntry> entries = new ArrayList<BarEntry>();
        entries.add(new BarEntry(0f, Utils.convertSessionsToMinutes(facebookData)));
        entries.add(new BarEntry(1f, Utils.convertSessionsToMinutes(instagramData)));
        entries.add(new BarEntry(2f, Utils.convertSessionsToMinutes(snapchatData)));
        entries.add(new BarEntry(3f, Utils.convertSessionsToMinutes(twitterData)));
        entries.add(new BarEntry(4f, Utils.convertSessionsToMinutes(youtubeData)));

        BarDataSet set = new BarDataSet(entries, "");
        set.setColors(new int[] {R.color.facebook, R.color.instagram, R.color.snapchat, R.color.twitter, R.color.youtube}, context);
        set.setValueTextSize(14f);
        set.setValueFormatter(new BarMinutesFormatter());
        BarData data = new BarData(set);
        data.setBarWidth(0.8f);
        hoursChart.setData(data);
        hoursChart.getXAxis().setEnabled(false);
        hoursChart.getAxisLeft().setEnabled(false);
        hoursChart.getAxisRight().setEnabled(false);
        Description emptyDescription = new Description();
        emptyDescription.setText("");
        hoursChart.setDescription(emptyDescription);
        hoursChart.getLegend().setEnabled(false);
        hoursChart.invalidate();

    }
}
