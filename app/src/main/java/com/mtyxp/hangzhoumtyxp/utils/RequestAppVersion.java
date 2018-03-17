package com.mtyxp.hangzhoumtyxp.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by CaoZhF on 2018-03-15.
 */

public class RequestAppVersion {

    public static Map<String,String> getAPpVersion(Context mContext){
        PackageManager packageManager = mContext.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(mContext.getPackageName(), 0);
            int versionCode = packageInfo.versionCode;
            String versionName = packageInfo.versionName;
            Map<String,String> data_map = new HashMap<>();
            data_map.put("versionName",versionName);
            data_map.put("versionCode", String.valueOf(versionCode));
            return data_map;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}
