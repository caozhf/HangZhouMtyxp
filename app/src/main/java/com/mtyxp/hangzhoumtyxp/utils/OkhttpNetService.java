package com.mtyxp.hangzhoumtyxp.utils;

import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by CaoZhF on 2017-11-27.
 */

public class OkhttpNetService {

    public static Call GetOpenDoorImgAndPwd(String get_url) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                .build();
        Request.Builder builder = new Request.Builder();
        Request request = builder.get().url(get_url).build();
        Call call = client.newCall(request);
        return call;
    }

}
