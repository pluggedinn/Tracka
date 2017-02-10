package com.example.rsons.tracka.adapter;

import android.app.usage.UsageStats;

import com.airbnb.epoxy.EpoxyAdapter;
import com.example.rsons.tracka.model.App;
import com.example.rsons.tracka.model.AppModel;

import java.util.List;

/**
 * Created by rsons on 2/2/2017.
 */

public class AppAdapter extends EpoxyAdapter {

    public void addApps(List<App> apps) {

        for (App app : apps) {
            addModel(new AppModel(app));
        }

    }

    public void clearApps() {
        models.clear();
    }
}
