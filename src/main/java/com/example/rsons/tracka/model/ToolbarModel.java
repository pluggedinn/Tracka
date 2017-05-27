package com.example.rsons.tracka.model;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.epoxy.EpoxyModel;
import com.example.rsons.tracka.R;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rsons on 4/6/2017.
 */

public class ToolbarModel extends EpoxyModel<LinearLayout> {
    private String score;
    private Context context;
    @BindView(R.id.toolbarText) TextView toolbarText;
    @BindView(R.id.toolbarImage)
    ImageView toolbarImage;

    public ToolbarModel(String score) {
        this.score = score;
    }

    @Override
    protected int getDefaultLayout() {
        return R.layout.cell_toolbar;
    }

    @Override
    public void bind(LinearLayout view) {
        ButterKnife.bind(this, view);
        context = toolbarText.getContext();

        setScore(score);
        toolbarImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity)context).finish();
            }
        });
    }

    public void setScore(String score) {
        toolbarText.setText(score);
    }
}
