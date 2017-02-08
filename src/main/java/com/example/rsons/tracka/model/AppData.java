package com.example.rsons.tracka.model;

import com.example.rsons.tracka.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rsons on 2/2/2017.
 */

public class AppData {

    private static String[] titles = {"Facebook", "Instagram", "Snapchat", "Twitter", "YouTube"};
    private static int[] icons = {R.drawable.ic_facebook_icon, R.drawable.ic_instagram_icon, R.drawable.ic_snapchat_icon, R.drawable.ic_twitter_icon, R.drawable.ic_youtube_icon};
    private static String[] backgroundColors = {"#3B5998", "#6B463C", "#FFFC00", "#00ACED", "#DC482F"};
    private static String[] values = {"52m", "3h 6m", "1h 39m", "16m", "5h 41m"};

    public static List<App> getListData() {
        List<App> data = new ArrayList<>();

        for(int i = 0; i < titles.length; i++) {
            App item = new App();
            item.setTitle(titles[i]);
            item.setImageResId(icons[i]);
            item.setBackgroundColor(backgroundColors[i]);
            item.setValue(values[i]);
            data.add(item);
        }

        return data;
    }
}
