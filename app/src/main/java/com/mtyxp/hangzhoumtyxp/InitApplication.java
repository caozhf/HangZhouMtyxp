package com.mtyxp.hangzhoumtyxp;

import android.app.Application;

import com.mtyxp.hangzhoumtyxp.model.Model;

import cn.bmob.v3.Bmob;

/**
 * Created by CaoZhF on 2017-10-24.
 */

public class InitApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Bmob.initialize(this,"9a81ba906cb12cb467432f2cf1146de0");
        Model.getInstance().initContext(this);
        Model.getInstance().initBroadCast();
    }
}
