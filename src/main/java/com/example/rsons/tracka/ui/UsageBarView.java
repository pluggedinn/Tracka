package com.example.rsons.tracka.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.example.rsons.tracka.model.Session;

import java.util.List;

/**
 * Created by rsons on 4/1/2017.
 */

// TODO: to fix. The bar appears to be always red for everything
public class UsageBarView extends View {

    private int w;
    private int h;
    private List<Session> sessions;
    private int sessionColor;
    private int backgroundColor;
    private long startPeriod;
    private long endPeriod;

    public UsageBarView(Context context) {
        super(context);
    }

    public UsageBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public UsageBarView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void init(List<Session> sessions, long startPeriod, long endPeriod, int sessionColor, int backgroundColor) {
        this.sessions = sessions;
        this.sessionColor = sessionColor;
        this.backgroundColor = backgroundColor;
        this.startPeriod = startPeriod;
        this.endPeriod = endPeriod;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        this.w = w;
        this.h = h;
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        Paint seshPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        backgroundPaint.setColor(backgroundColor);
        seshPaint.setColor(sessionColor);

        // Drawing background of the bar
        canvas.drawRect((float)0, (float)0, (float)w, (float)h, backgroundPaint);

        // Drawing all the session bars if they exist
        if (sessions != null) {

            for (Session s : sessions) {
                long startPixel = w * (s.startTime - startPeriod) / (endPeriod - startPeriod);
                long endPixel = w * (s.endTime = startPeriod) / (endPeriod - startPeriod);
                canvas.drawRect((float) startPixel, (float) 0, (float) endPixel, (float) h, seshPaint);
            }
        }

        super.onDraw(canvas);
    }
}
