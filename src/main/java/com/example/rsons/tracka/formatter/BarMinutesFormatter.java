package com.example.rsons.tracka.formatter;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

/**
 * Created by rsons on 6/6/2017.
 */

public class BarMinutesFormatter implements IValueFormatter {

    @Override
    public String getFormattedValue(float v, Entry entry, int i, ViewPortHandler viewPortHandler) {
        return Math.round(v) + "m";
    }
}
