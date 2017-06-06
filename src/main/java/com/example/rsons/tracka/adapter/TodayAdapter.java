package com.example.rsons.tracka.adapter;

import com.airbnb.epoxy.EpoxyAdapter;
import com.example.rsons.tracka.model.PastHeaderModel;
import com.example.rsons.tracka.model.TodayHeaderModel;
import com.example.rsons.tracka.model.ToolbarModel;

/**
 * Created by rsons on 6/3/2017.
 */

public class TodayAdapter extends EpoxyAdapter {
    private ToolbarModel toolbarModel;
    private TodayHeaderModel todayHeaderModel;

    public TodayAdapter() {
        enableDiffing();

        toolbarModel = new ToolbarModel("+1");
        todayHeaderModel = new TodayHeaderModel();

        addModels(toolbarModel, todayHeaderModel);

        notifyModelsChanged();
    }

}
