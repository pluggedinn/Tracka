package com.example.rsons.tracka.model;


import android.content.Context;
import android.util.Log;
import android.widget.CalendarView;
import android.widget.RelativeLayout;

import com.airbnb.epoxy.EpoxyAdapter;
import com.airbnb.epoxy.EpoxyModel;
import com.example.rsons.tracka.R;
import com.example.rsons.tracka.SessionsRetriever;
import com.example.rsons.tracka.adapter.PastAdapter;
import com.squareup.timessquare.CalendarPickerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rsons on 4/6/2017.
 */

public class PastHeaderModel extends EpoxyModel<RelativeLayout> {
    private PastAdapter eAdapter;
    private Context context;
    private List<Date> selectedDates;

    @BindView(R.id.calendarView) CalendarPickerView calendar;

    public PastHeaderModel(PastAdapter eAdapter) {
        this.eAdapter = eAdapter;
    }

    @Override
    protected int getDefaultLayout() {
        return R.layout.cell_header_past;
    }

    @Override
    public void bind(RelativeLayout view) {
        ButterKnife.bind(this, view);
        context = calendar.getContext();

        // Creating default dates for the Calendar picker
        Calendar lastYear = Calendar.getInstance();
        Calendar today = Calendar.getInstance();
        Calendar yesterday = Calendar.getInstance();
        Calendar previousDay = Calendar.getInstance();
        lastYear.add(Calendar.YEAR, -1);
        previousDay.add(Calendar.DATE, -2);
        yesterday.add(Calendar.DATE, -1);

        // Adding default dates to the calendar if not already set by the user
        if (selectedDates == null) {
            selectedDates = new ArrayList<>();
            selectedDates.add(previousDay.getTime());
            selectedDates.add(yesterday.getTime());
        }

        calendar.init(lastYear.getTime(), today.getTime())
                .inMode(CalendarPickerView.SelectionMode.RANGE)
                .withSelectedDates(selectedDates);

        // Initializing session retriever
        final SessionsRetriever dataRetriever = new SessionsRetriever(context);

        calendar.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {

                // getting selected dates
                selectedDates = calendar.getSelectedDates();

                // clearing adapter
                eAdapter.clearApps();

                if (selectedDates.size() == 1) {
                    Calendar dayAfter = Calendar.getInstance();
                    dayAfter.setTime(date);
                    dayAfter.add(Calendar.DATE, 1);
                    List<Session> data = dataRetriever.getSessionsAllApps(date.getTime(), dayAfter.getTimeInMillis());

                    // Adding apps to the adapter
                    eAdapter.addApps(AppFormatter.createApps(data), date, dayAfter.getTime());

                    Log.d("DATA", data.size() + data.toString());
                    Log.d("START", date.toString());
                    Log.d("END", dayAfter.getTime()+"");
                } else {
                    Calendar lastDay = Calendar.getInstance();
                    lastDay.setTime(selectedDates.get(selectedDates.size() - 1));
                    lastDay.add(Calendar.DATE, 1);
                    List<Session> data = dataRetriever.getSessionsAllApps(selectedDates.get(0).getTime(), lastDay.getTimeInMillis());

                    // Adding apps to the adapter
                    eAdapter.addApps(AppFormatter.createApps(data), selectedDates.get(0), lastDay.getTime());

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

    public void passAdapter(PastAdapter pastAdapter) {
    }
}
