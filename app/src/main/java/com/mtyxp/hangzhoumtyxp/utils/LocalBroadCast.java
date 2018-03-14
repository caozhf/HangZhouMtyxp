package com.mtyxp.hangzhoumtyxp.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by CaoZhF on 2017-12-07.
 */

public class LocalBroadCast extends BroadcastReceiver {

    public static String NUM = "";

    @Override
    public void onReceive(Context context, Intent intent) {

        String num = intent.getStringExtra("num");
        NUM = num;

    }
}
