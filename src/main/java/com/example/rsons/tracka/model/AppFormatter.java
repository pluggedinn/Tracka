package com.example.rsons.tracka.model;

import android.app.usage.UsageStats;
import android.content.Intent;

import com.example.rsons.tracka.FactsRetriever;
import com.example.rsons.tracka.R;
import com.example.rsons.tracka.Utils;

import java.util.ArrayList;
import java.util.HashMap;
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

    public static HashMap<Integer, App> createApps(List<Session> sessions) {

        HashMap<Integer, App> map = new HashMap<Integer, App>();

        for(int i = 0; i < sessions.size(); i++) {

            if (sessions.get(i).appCode == 0) {

                if (map.get(0) != null) {
                    App app = map.get(0);
                    app.accesses++;
                    app.hours += sessions.get(i).endTime - sessions.get(i).startTime;
                    app.sessions.add(sessions.get(i));
                    map.put(0, app);

                } else {
                    App newApp = new App();
                    newApp.name = titles[0];
                    newApp.accesses = 1;
                    newApp.backgroundColor = backgroundColors[0];
                    newApp.backgroundIcon = icons[0];
                    newApp.textColor = "#ffffff";
                    newApp.hours = sessions.get(i).endTime - sessions.get(i).startTime;
                    newApp.sessions = new ArrayList<Session>();
                    newApp.sessions.add(sessions.get(i));
                    map.put(0, newApp);
                }

            } else if (sessions.get(i).appCode == 1) {

                if (map.get(1) != null) {
                    App app = map.get(1);
                    app.accesses++;
                    app.hours += sessions.get(i).endTime - sessions.get(i).startTime;
                    app.sessions.add(sessions.get(i));
                    map.put(1, app);

                } else {
                    App newApp = new App();
                    newApp.name = titles[1];
                    newApp.accesses = 1;
                    newApp.backgroundColor = backgroundColors[1];
                    newApp.backgroundIcon = icons[1];
                    newApp.textColor = "#ffffff";
                    newApp.hours = sessions.get(i).endTime - sessions.get(i).startTime;
                    newApp.sessions = new ArrayList<Session>();
                    newApp.sessions.add(sessions.get(i));
                    map.put(1, newApp);
                }

            } else if (sessions.get(i).appCode == 2) {

                if (map.get(2) != null) {
                    App app = map.get(2);
                    app.accesses++;
                    app.hours += sessions.get(i).endTime - sessions.get(i).startTime;
                    app.sessions.add(sessions.get(i));
                    map.put(2, app);


                } else {
                    App newApp = new App();
                    newApp.name = titles[2];
                    newApp.accesses = 1;
                    newApp.backgroundColor = backgroundColors[2];
                    newApp.backgroundIcon = icons[2];
                    newApp.textColor = "#000000";
                    newApp.hours = sessions.get(i).endTime - sessions.get(i).startTime;
                    newApp.sessions = new ArrayList<Session>();
                    newApp.sessions.add(sessions.get(i));
                    map.put(2, newApp);
                }

            } else if (sessions.get(i).appCode == 3) {

                if (map.get(3) != null) {
                    App app = map.get(3);
                    app.accesses++;
                    app.hours += sessions.get(i).endTime - sessions.get(i).startTime;
                    app.sessions.add(sessions.get(i));
                    map.put(3, app);

                } else {
                    App newApp = new App();
                    newApp.name = titles[3];
                    newApp.accesses = 1;
                    newApp.backgroundColor = backgroundColors[3];
                    newApp.backgroundIcon = icons[3];
                    newApp.textColor = "#000000";
                    newApp.hours = sessions.get(i).endTime - sessions.get(i).startTime;
                    newApp.sessions = new ArrayList<Session>();
                    newApp.sessions.add(sessions.get(i));
                    map.put(3, newApp);
                }

            } else {

                if (map.get(4) != null) {
                    App app = map.get(4);
                    app.accesses++;
                    app.hours += sessions.get(i).endTime - sessions.get(i).startTime;
                    app.sessions.add(sessions.get(i));
                    map.put(4, app);

                } else {
                    App newApp = new App();
                    newApp.name = titles[4];
                    newApp.accesses = 1;
                    newApp.backgroundColor = backgroundColors[4];
                    newApp.backgroundIcon = icons[4];
                    newApp.textColor = "#ffffff";
                    newApp.hours = sessions.get(i).endTime - sessions.get(i).startTime;
                    newApp.sessions = new ArrayList<Session>();
                    newApp.sessions.add(sessions.get(i));
                    map.put(3, newApp);
                }
            }

        }

        for (Integer i : map.keySet()) {
            App app = map.get(i);
            app.hoursString = Utils.convertMillisToDuration(app.hours);
            app.accessesString = "in " + app.accesses + " accesses";
            map.put(i, app);
        }

        return map;
    }
}
