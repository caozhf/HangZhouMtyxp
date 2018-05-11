package com.mtyxp.hangzhoumtyxp.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by CaoZhF on 2018-05-10.
 */

public class DeviceNumBroadCast extends BroadcastReceiver {

    public static String tem_un = "";

    @Override
    public void onReceive(Context context, Intent intent) {
        String data_d_n = intent.getStringExtra("data_d_n");
        tem_un = data_d_n;
//        System.out.println(tem_un);
    }
}
