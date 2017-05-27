package com.example.rsons.tracka.ui;

import android.app.Application;

import com.example.rsons.tracka.R;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by rsons on 5/17/2017.
 */

public class CustomFontApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // initalize Calligraphy
        CalligraphyConfig.initDefault(
                new CalligraphyConfig.Builder()
                        .setDefaultFontPath("fonts/AvenirNextLTPro-Regular.otf")
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );
    }
}
