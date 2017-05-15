package com.example.rsons.tracka.model;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.airbnb.epoxy.EpoxyModel;
import com.example.rsons.tracka.FactsRetriever;
import com.example.rsons.tracka.R;
import com.example.rsons.tracka.Utils;
import com.example.rsons.tracka.ui.UsageBarView;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rsons on 2/2/2017.
 */


public class AppModel extends EpoxyModel<RelativeLayout> {

    private final App app;
    private Date start;
    private Date end;

    // App bindings
    @BindView(R.id.hours) TextView hours;
    @BindView(R.id.accesses) TextView accesses;
    @BindView(R.id.usageBar) UsageBarView usageBar;
    @BindView(R.id.description) TextView description;
    @BindView(R.id.appIcon) ImageView appIcon;

    // Facts bindings
    @BindView(R.id.factImage1) ImageView factImage1;
    @BindView(R.id.factText1) TextView factText1;
    @BindView(R.id.factImage2) ImageView factImage2;
    @BindView(R.id.factText2) TextView factText2;
    @BindView(R.id.factImage3) ImageView factImage3;
    @BindView(R.id.factText3) TextView factText3;
    @BindView(R.id.factImage4) ImageView factImage4;
    @BindView(R.id.factText4) TextView factText4;

    public AppModel(App app, Date start, Date end) {
        this.app = app;
        this.start = start;
        this.end = end;
    }

    @Override
    protected int getDefaultLayout() {
        return R.layout.cell_app;
    }

    @Override
    public void bind(RelativeLayout view) {
        ButterKnife.bind(this, view);

        hours.setText(app.hoursString);
        accesses.setText(app.accessesString);
        description.setText("With this much time spent on "+ app.name +" you could have...");
        appIcon.setImageResource(app.backgroundIcon);
        view.setBackgroundColor(Color.parseColor(app.backgroundColor));
        usageBar.init(app.sessions, start.getTime(), end.getTime(), Color.parseColor("#E64A19"), Color.parseColor("#388E3C"));

        // Generating facts
        FactsRetriever rt = new FactsRetriever(view.getContext());
        rt.generateNewFact(app.hours);
        factImage1.setImageResource(rt.getFactDrawable());
        factText1.setText(rt.getFactString());
        rt.generateNewFact(app.hours);
        factImage2.setImageResource(rt.getFactDrawable());
        factText2.setText(rt.getFactString());
        rt.generateNewFact(app.hours);
        factImage3.setImageResource(rt.getFactDrawable());
        factText3.setText(rt.getFactString());
        rt.generateNewFact(app.hours);
        factImage4.setImageResource(rt.getFactDrawable());
        factText4.setText(rt.getFactString());
    }

    @Override
    public void unbind(RelativeLayout view) {
    }
}
