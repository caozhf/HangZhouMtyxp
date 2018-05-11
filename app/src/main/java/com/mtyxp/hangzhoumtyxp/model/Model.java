package com.mtyxp.hangzhoumtyxp.model;

import android.app.Activity;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.LocalBroadcastManager;

import com.mtyxp.hangzhoumtyxp.utils.DeviceNumBroadCast;
import com.mtyxp.hangzhoumtyxp.utils.GetVersionBroadcast;
import com.mtyxp.hangzhoumtyxp.utils.LocalBroadCast;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by CaoZhF on 2017-10-24.
 */

public class Model {

    private static Model model;
    private ExecutorService executorService = Executors.newCachedThreadPool();
    private Context context;
    private LocalBroadcastManager lbm;
    private IntentFilter filter;
    private LocalBroadCast localBroadCast;
    private GetVersionBroadcast get_version_bd;

    private DeviceNumBroadCast device_num_bc;

    public Model() {
    }

    public void initContext(Context context){
        this.context = context;
    }

    public static Model getInstance(){
        if (model == null){
            model = new Model();
        }
        return model;
    }

    public boolean isNetWorkAvailable(Activity activity){
        Context context = activity.getApplicationContext();
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager == null){
            return false;
        }else {
            NetworkInfo networkInfo = manager.getActiveNetworkInfo();
            if (networkInfo!=null && networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                return true;
            }
//            }else {
//                Toast.makeText(context,"请检查是否开启wifi或移动网络！",Toast.LENGTH_SHORT).show();
//            }
        }
        return false;
    }

    public void initBroadCast(){
        lbm = LocalBroadcastManager.getInstance(context);
        filter = new IntentFilter();
        localBroadCast = new LocalBroadCast();
        filter.addAction("device_num");
        lbm.registerReceiver(localBroadCast,filter);
    }

    public void initGetDeviceNumBroadCast(){
        lbm = LocalBroadcastManager.getInstance(context);
        filter = new IntentFilter();
        device_num_bc = new DeviceNumBroadCast();
        filter.addAction("this_device_num");
        lbm.registerReceiver(device_num_bc,filter);
    }

    public void GetVersionNum(){
        lbm = LocalBroadcastManager.getInstance(context);
        filter = new IntentFilter();
        get_version_bd = new GetVersionBroadcast();
        filter.addAction("get_version_number");
        lbm.registerReceiver(get_version_bd,filter);
    }

    public ExecutorService getGlobalThreadPool(){
        return executorService;
    }

}
