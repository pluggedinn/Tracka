package com.example.rsons.tracka.adapter;

import com.airbnb.epoxy.EpoxyAdapter;
import com.example.rsons.tracka.model.App;
import com.example.rsons.tracka.model.AppModel;
import com.example.rsons.tracka.model.PastHeaderModel;
import com.example.rsons.tracka.model.ToolbarModel;

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

        headerModel = new PastHeaderModel(this);
        toolbarModel = new ToolbarModel("86");

        addModels(toolbarModel, headerModel);

        notifyModelsChanged();
    }

    public void addApps(HashMap<Integer, App> apps) {

        for (Integer i : apps.keySet()) {
            addModel(new AppModel(apps.get(i)));
        }
        notifyModelsChanged();
    }

    public void clearApps() {
        for(int i = 2; i < models.size(); i++) {
            models.remove(i);
        }
        notifyModelsChanged();
    }
}
