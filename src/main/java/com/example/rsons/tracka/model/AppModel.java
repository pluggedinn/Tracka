package com.example.rsons.tracka.model;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.epoxy.EpoxyModel;
import com.example.rsons.tracka.R;
import com.example.rsons.tracka.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rsons on 2/2/2017.
 */

// TODO: 4/24/2017 fixare tutto qua. e aggiungere aggiungere il factsRetriever 

public class AppModel extends EpoxyModel<CardView> {
    private final App app;
    @BindView(R.id.cell_title) TextView title;
    @BindView(R.id.cell_subtitle) TextView subtitle;
    @BindView(R.id.cell_icon) ImageView icon;
    @BindView(R.id.cell_value) TextView value;

    public AppModel(App app) {
        this.app = app;
    }

    @Override
    protected int getDefaultLayout() {
        return R.layout.old_cell_app;
    }

    @Override
    public void bind(CardView view) {
        ButterKnife.bind(this, view);
        title.setText(app.getTitle());
        value.setText(app.getValue());
        icon.setImageResource(app.getImageResId());
        view.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(app.getBackgroundColor())));
        subtitle.setText("On " + Utils.convertMillisToTime(Long.parseLong(app.getStartTime())));

        if (app.getTitle().contains("Snapchat")) {
            title.setTextColor(Color.BLACK);
            subtitle.setTextColor(Color.BLACK);
            value.setTextColor(Color.BLACK);
        }
    }

    @Override
    public void unbind(CardView view) {
    }
}
