package com.mtyxp.hangzhoumtyxp.controller.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mtyxp.hangzhoumtyxp.R;
import com.mtyxp.hangzhoumtyxp.controller.fragment.statistic_fragment.StatisticDeviceFragment;
import com.mtyxp.hangzhoumtyxp.controller.fragment.statistic_fragment.StatisticInputFragment;
import com.mtyxp.hangzhoumtyxp.controller.fragment.statistic_fragment.StatisticOrderFragment;

import java.util.ArrayList;
import java.util.List;

public class DataStatisticActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewPager statistic_viewpager;

    private LinearLayout statistic_detail_order,statistic_detail_input,statistic_detail_device;

    private ImageView statistic_detail_order_img,statistic_detail_input_img,statistic_detail_device_img;
    private TextView statistic_detail_order_text,statistic_detail_input_text,statistic_detail_device_text;

    private Fragment statistic_order_fragment,statistic_input_fragment,statistic_device_fragment;

    private List<Fragment> mlist_fragment;

    private FragmentPagerAdapter StatisticAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_statistic);

        initView();
        initListener();
        setSelect(0);
    }

    private void initListener() {

        statistic_detail_order.setOnClickListener(this);
        statistic_detail_input.setOnClickListener(this);
        statistic_detail_device.setOnClickListener(this);

    }

    private void initView() {
        statistic_viewpager = (ViewPager) findViewById(R.id.statistic_viewpager);

        statistic_detail_order = (LinearLayout) findViewById(R.id.statistic_detail_order);
        statistic_detail_input = (LinearLayout) findViewById(R.id.statistic_detail_input);
        statistic_detail_device = (LinearLayout) findViewById(R.id.statistic_detail_device);

        statistic_detail_order_img = (ImageView) findViewById(R.id.statistic_detail_order_img);
        statistic_detail_input_img = (ImageView) findViewById(R.id.statistic_detail_input_img);
        statistic_detail_device_img = (ImageView) findViewById(R.id.statistic_detail_device_img);

        statistic_detail_order_text = (TextView) findViewById(R.id.statistic_detail_order_text);
        statistic_detail_input_text = (TextView) findViewById(R.id.statistic_detail_input_text);
        statistic_detail_device_text = (TextView) findViewById(R.id.statistic_detail_device_text);

        statistic_order_fragment = new StatisticOrderFragment();
        statistic_input_fragment = new StatisticInputFragment();
        statistic_device_fragment = new StatisticDeviceFragment();

        mlist_fragment = new ArrayList<>();

        mlist_fragment.add(statistic_order_fragment);
        mlist_fragment.add(statistic_input_fragment);
        mlist_fragment.add(statistic_device_fragment);

        StatisticAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mlist_fragment.get(position);
            }

            @Override
            public int getCount() {
                return mlist_fragment.size();
            }
        };
        statistic_viewpager.setAdapter(StatisticAdapter);
        statistic_viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                int currentItem = statistic_viewpager.getCurrentItem();

                ResetImg();
                switch (currentItem){
                    case 0:
                        statistic_detail_order_img.setImageResource(R.drawable.statistic_check_order);
                        statistic_detail_order_text.setTextColor(getResources().getColor(R.color.statistic_detail_text));
                        break;
                    case 1:
                        statistic_detail_input_img.setImageResource(R.drawable.statistic_input);
                        statistic_detail_input_text.setTextColor(getResources().getColor(R.color.statistic_detail_text));
                        break;
                    case 2:
                        statistic_detail_device_img.setImageResource(R.drawable.statistic_device_num);
                        statistic_detail_device_text.setTextColor(getResources().getColor(R.color.statistic_detail_text));
                        break;
                }


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.statistic_detail_order:
                setSelect(0);
                break;
            case R.id.statistic_detail_input:
                setSelect(1);
                break;
            case R.id.statistic_detail_device:
                setSelect(2);
                break;
        }
    }

    private void setSelect(int i) {
        ResetImg();
        switch (i){
            case 0:
                statistic_detail_order_img.setImageResource(R.drawable.statistic_check_order);
                statistic_detail_order_text.setTextColor(getResources().getColor(R.color.statistic_detail_text));
                break;
            case 1:
                statistic_detail_input_img.setImageResource(R.drawable.statistic_input);
                statistic_detail_input_text.setTextColor(getResources().getColor(R.color.statistic_detail_text));
                break;
            case 2:
                statistic_detail_device_img.setImageResource(R.drawable.statistic_device_num);
                statistic_detail_device_text.setTextColor(getResources().getColor(R.color.statistic_detail_text));
        }
        statistic_viewpager.setCurrentItem(i);

    }

    private void ResetImg(){
        statistic_detail_order_img.setImageResource(R.drawable.statistic_no_check_order);
        statistic_detail_input_img.setImageResource(R.drawable.statistic_no_input);
        statistic_detail_device_img.setImageResource(R.drawable.statistic_device_no_num);

        statistic_detail_order_text.setTextColor(getResources().getColor(R.color.text_color_light));
        statistic_detail_input_text.setTextColor(getResources().getColor(R.color.text_color_light));
        statistic_detail_device_text.setTextColor(getResources().getColor(R.color.text_color_light));

    }

    public void StatisticBack(View view){
        startActivity(new Intent(this,OperateActivity.class));
        finish();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            startActivity(new Intent(this,OperateActivity.class));
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
