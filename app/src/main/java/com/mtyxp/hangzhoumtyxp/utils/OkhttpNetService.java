package com.mtyxp.hangzhoumtyxp.utils;

import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by CaoZhF on 2017-11-27.
 */

public class OkhttpNetService {

    public static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("multipart/form-data; charset=utf-8");

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

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

    public static Call RemainSendServePost(String get_url,String device_num,String device_remain){
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10000L,TimeUnit.MILLISECONDS)
                .readTimeout(10000L,TimeUnit.MILLISECONDS)
                .build();
        Request.Builder builder = new Request.Builder();

//        RequestBody.create(MEDIA_TYPE_MARKDOWN,device_num);

//        MultipartBody multipartBody = new MultipartBody.Builder()
//                .setType(MEDIA_TYPE_MARKDOWN)
//                .addFormDataPart("device_num",device_num)
//                .addFormDataPart("device_remain",device_remain)
//                .build();

        FormBody body = new FormBody.Builder()
                .add("pk",device_num)
                .add("road_info",device_remain)
                .build();
        Request request = builder.get().url(get_url).post(body).build();
        Call call = client.newCall(request);
        return call;
    }

    public static Call DefaultDataInput(String get_url){
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
