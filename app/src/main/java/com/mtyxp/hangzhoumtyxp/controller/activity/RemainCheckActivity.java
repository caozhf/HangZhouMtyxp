package com.mtyxp.hangzhoumtyxp.controller.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.mtyxp.hangzhoumtyxp.R;
import com.mtyxp.hangzhoumtyxp.controller.fragment.remain_check_fragment.RemainCheckFragment;
import com.mtyxp.hangzhoumtyxp.model.bean.Bashinai;
import com.mtyxp.hangzhoumtyxp.model.bean.GoodsTitleInfo;
import com.mtyxp.hangzhoumtyxp.utils.Constant;
import com.mtyxp.hangzhoumtyxp.utils.OkhttpNetService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class RemainCheckActivity extends AppCompatActivity {

    private DrawerLayout remain_check_drawerlayout;
    private FrameLayout remain_check_frame;
    private ListView remain_check_left_drawer;

    private String[] device_num = {"LANZHOU0001","LANZHOU0002","LANZHOU0003","LANZHOU0004","LANZHOU0005","LANZHOU0006","LANZHOU0007","LANZHOU0008",
                                   "LANZHOU0009","LANZHOU0010","LANZHOU0011","LANZHOU0012","LANZHOU0013","LANZHOU0014","LANZHOU0015","LANZHOU0016",
                                   "HANGZHOU0001","HANGZHOU0002","HANGZHOU0003","HANGZHOU0004","HANGZHOU0005","HANGZHOU0006","HANGZHOU0007","HANGZHOU0008",
                                   "HANGZHOU0009","HANGZHOU0010","HANGZHOU0011","HANGZHOU0012","HANGZHOU0013","HANGZHOU0014","HANGZHOU0015","HANGZHOU0016"};

    private ArrayAdapter adapter;

    private Fragment remain_check_fragment;

    private LocalBroadcastManager lbm;

    private List<Bashinai> mlist = new ArrayList<Bashinai>();

//    private List spinner_item = new ArrayList();
    private List<GoodsTitleInfo> mlist_goods_info = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remain_check);
        initView();
        initlistener();


    }

    private void initlistener() {

        remain_check_left_drawer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int position, long l) {

                System.out.println(device_num[position]);

                Call call = OkhttpNetService.DefaultDataInput(Constant.TEMP_QUANTITY_CHECK +device_num[position]);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Toast.makeText(RemainCheckActivity.this, "error+" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (!response.isSuccessful()) {

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(RemainCheckActivity.this, "请求数据不存在！", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }else {
                            String string = response.body().string();
                            if (string.length()<40){
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(RemainCheckActivity.this, "请求数据不存在！", Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }else {
                                try {
                                    mlist.clear();
                                    mlist_goods_info.clear();
                                    JSONObject object = new JSONObject(string);
                                    for (int i = 1; i < 13; i++) {
                                        Bashinai bashinai = new Bashinai();

                                        JSONArray jsonArray = object.getJSONArray(i + "");
                                        bashinai.setBa_id(jsonArray.getInt(0));
                                        bashinai.setPass_num(i);
                                        bashinai.setBa_title(jsonArray.getString(1));
                                        bashinai.setBa_num(jsonArray.getInt(2));
                                        mlist.add(bashinai);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                for (int i=0;i<12;i++){
                                    GoodsTitleInfo info = new GoodsTitleInfo(mlist.get(i).getBa_id(),mlist.get(i).getBa_title());
                                    mlist_goods_info.add(info);
                                }

                                Intent intent = new Intent();
                                intent.setAction("this_device_num");
                                intent.putExtra("data_d_n",device_num[position]);
                                intent.putExtra("goods_info", (Serializable) mlist_goods_info);
                                lbm.sendBroadcast(intent);

                                System.out.println(mlist_goods_info);

                            }

                        }
                    }
                });


                Toast.makeText(RemainCheckActivity.this,"已选择"+device_num[position],Toast.LENGTH_SHORT).show();

                remain_check_drawerlayout.closeDrawer(remain_check_left_drawer);
            }
        });
    }

//    public class SpinnerItem{
//        private String Key = "";
//        private String Value = "";
//
//        public SpinnerItem() {
//            Key = "";
//            Value = "";
//        }
//
//        public SpinnerItem(String _Key, String _Value) {
//            Key = _Key;
//            Value = _Value;
//        }
//
//        @Override
//        public String toString() {
//            return Value;
//        }
//
//        public String GetKey() {
//            return Key;
//        }
//
//        public String GetValue() {
//            return Value;
//        }
//
//    }

//    private void DefaultData() {
//        Call call = OkhttpNetService.DefaultDataInput(Constant.TEMP_QUANTITY_CHECK + DeviceNumBroadCast.tem_un);
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Toast.makeText(getActivity(), "error+" + e.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                if (!response.isSuccessful()) {
//
//                    getActivity().runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(getContext(), "请求数据不存在！", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                } else {
//
//                    String string = response.body().string();
//                    try {
//                        JSONObject object = new JSONObject(string);
//                        for (int i = 1; i < 13; i++) {
//                            Bashinai bashinai = new Bashinai();
//
//                            JSONArray jsonArray = object.getJSONArray(i + "");
//                            bashinai.setBa_id(jsonArray.getInt(0));
//                            bashinai.setPass_num(i);
//                            bashinai.setBa_title(jsonArray.getString(1));
//                            bashinai.setBa_num(jsonArray.getInt(2));
//                            mlist.add(bashinai);
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//                    System.out.println("kk"+mlist.toString());
//
//                    for (int i=0;i<12;i++){
//                        SpinnerItem item = new SpinnerItem(String.valueOf(mlist.get(i).getBa_id()),mlist.get(i).getBa_title());
//                        spinner_item.add(item);
//                    }
//
//                }
//            }
//        });
//    }


    private void initView() {

        lbm = LocalBroadcastManager.getInstance(this);

        remain_check_drawerlayout = (DrawerLayout) findViewById(R.id.remain_check_drawerlayout);
        remain_check_frame = (FrameLayout) findViewById(R.id.remain_check_frame);
        remain_check_left_drawer = (ListView) findViewById(R.id.remain_check_left_drawer);

        adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,device_num);
        remain_check_left_drawer.setAdapter(adapter);

        RefreshFragment();

    }

    private void RefreshFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        hideFragment(transaction);

        if (remain_check_fragment == null){
            remain_check_fragment = new RemainCheckFragment();
            transaction.add(R.id.remain_check_frame,remain_check_fragment);
        }
        else {
            transaction.show(remain_check_fragment);
        }
        transaction.commit();
    }

    private void hideFragment(FragmentTransaction transaction) {
        if (remain_check_fragment !=null){
            transaction.hide(remain_check_fragment);
        }
    }
}
