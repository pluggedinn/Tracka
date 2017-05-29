package com.example.rsons.tracka.adapter;

import com.airbnb.epoxy.EpoxyAdapter;
import com.example.rsons.tracka.model.App;
import com.example.rsons.tracka.model.AppModel;
import com.example.rsons.tracka.model.PastHeaderModel;
import com.example.rsons.tracka.model.ToolbarModel;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by rsons on 4/6/2017.
 */

public class PastAdapter extends EpoxyAdapter {
    private ToolbarModel toolbarModel;
    private PastHeaderModel headerModel;

    public PastAdapter() {
        enableDiffing();

        toolbarModel = new ToolbarModel("-2");
        headerModel = new PastHeaderModel(this);

        addModels(toolbarModel, headerModel);

        notifyModelsChanged();
    }

    public void addApps(HashMap<Integer, App> apps, Date start, Date end) {

        for (Integer i : apps.keySet()) {
            addModel(new AppModel(apps.get(i), start, end));
        }
        notifyModelsChanged();
    }

    public void clearApps() {
        int currSize = models.size();

        for(int i = 2; i < currSize; i++) {
            models.remove(2);
        }
        notifyModelsChanged();
    }
}
