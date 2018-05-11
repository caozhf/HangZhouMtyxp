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
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Intent intent = new Intent();
                intent.setAction("this_device_num");
                intent.putExtra("data_d_n",device_num[position]);
                lbm.sendBroadcast(intent);

                Toast.makeText(RemainCheckActivity.this,"已选择"+device_num[position],Toast.LENGTH_SHORT).show();

                remain_check_drawerlayout.closeDrawer(remain_check_left_drawer);
            }
        });
    }

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
