package com.example.rsons.tracka.model;

import android.app.usage.UsageStats;

import com.example.rsons.tracka.R;
import com.example.rsons.tracka.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rsons on 2/2/2017.
 */

public class AppFormatter {

    private static String[] titles = {
            "Facebook",
            "Instagram",
            "Snapchat",
            "Twitter",
            "YouTube"};
    private static int[] icons = {
            R.drawable.ic_facebook_icon,
            R.drawable.ic_instagram_icon,
            R.drawable.ic_snapchat_icon,
            R.drawable.ic_twitter_icon,
            R.drawable.ic_youtube_icon};
    private static String[] backgroundColors = {
            "#3B5998",
            "#6B463C",
            "#FFFC00",
            "#00ACED",
            "#DC482F"};

    public static List<App> createApps(List<Session> sessionList) {
        List<App> data = new ArrayList<>();

        for(int i = 0; i < sessionList.size(); i++) {
            App item = new App();
            if (sessionList.get(i).appCode == 0) {
                item.setTitle(titles[0]);
                item.setImageResId(icons[0]);
                item.setBackgroundColor(backgroundColors[0]);
            } else if (sessionList.get(i).appCode == 1) {
                item.setTitle(titles[1]);
                item.setImageResId(icons[1]);
                item.setBackgroundColor(backgroundColors[1]);
            } else if (sessionList.get(i).appCode == 2) {
                item.setTitle(titles[2]);
                item.setImageResId(icons[2]);
                item.setBackgroundColor(backgroundColors[2]);
            } else if (sessionList.get(i).appCode == 3) {
                item.setTitle(titles[3]);
                item.setImageResId(icons[3]);
                item.setBackgroundColor(backgroundColors[3]);
            } else {
                item.setTitle(titles[4]);
                item.setImageResId(icons[4]);
                item.setBackgroundColor(backgroundColors[4]);
            }

            item.setValue(sessionList.get(i).getDuration());
            item.setStartTime(sessionList.get(i).startTime+"");
            item.setEndTime(sessionList.get(i).endTime+"");
            data.add(item);
        }

        return data;
    }
}
