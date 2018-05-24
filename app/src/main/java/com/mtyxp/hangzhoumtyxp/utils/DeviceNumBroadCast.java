package com.mtyxp.hangzhoumtyxp.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.mtyxp.hangzhoumtyxp.model.bean.GoodsTitleInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CaoZhF on 2018-05-10.
 */

public class DeviceNumBroadCast extends BroadcastReceiver {

    public static String tem_un = "";
    public static List<GoodsTitleInfo> data = null;
    @Override
    public void onReceive(Context context, Intent intent) {
        String data_d_n = intent.getStringExtra("data_d_n");
        data = new ArrayList<>();
        List<GoodsTitleInfo> myData = (List<GoodsTitleInfo>) intent.getSerializableExtra("goods_info");
        tem_un = data_d_n;
        data = myData;
        System.out.println(myData);
    }
}
