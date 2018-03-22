package com.mtyxp.hangzhoumtyxp.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by CaoZhF on 2018-03-22.
 */

public class GetVersionBroadcast extends BroadcastReceiver {

    public static int local_version_num=0;
    public static int serve_version_num=0;

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle extras = intent.getExtras();
        local_version_num = extras.getInt("local_version_num");
        serve_version_num = extras.getInt("serve_version_num");

    }
}
