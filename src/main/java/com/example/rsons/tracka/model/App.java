package com.example.rsons.tracka.model;

import com.mikepenz.fastadapter.items.AbstractItem;
import android.support.v7.widget.RecyclerView.ViewHolder;

/**
 * Created by rsons on 2/2/2017.
 */

public class App {

    private String name;
    private int imageResId;
    private String backgroundColor;
    private String value;

    public String getTitle() {
        return name;
    }

    public void setTitle(String name) {
        this.name = name;
    }

    public int getImageResId() {
        return imageResId;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
