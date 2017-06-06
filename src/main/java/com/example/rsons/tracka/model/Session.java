package com.example.rsons.tracka.model;

import com.example.rsons.tracka.Utils;

/**
 * Created by rsons on 3/28/2017.
 */

public class Session {

    public int appCode;
    public long startTime;
    public long endTime;

    public Session (int _appCode, long _startTime, long _endTime) {
        appCode = _appCode;
        startTime = _startTime;
        endTime = _endTime;
    }

    public String getDuration(){
        return Utils.convertMillisToDuration(endTime - startTime);
    }
}
