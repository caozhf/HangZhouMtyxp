package com.mtyxp.hangzhoumtyxp.controller.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.mtyxp.hangzhoumtyxp.R;
import com.mtyxp.hangzhoumtyxp.controller.activity.DataStatisticActivity;
import com.mtyxp.hangzhoumtyxp.controller.activity.RemainCheckActivity;

/**
 * Created by CaoZhF on 2017-10-28.
 */

public class StatisticFragment extends Fragment {

    private View view;
    private Button statistic_fg_input_check,statistic_fg_remain_count;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.statistic_fragment, container, false);
        initView();
        initData();
        return view;
    }

    private void initData() {
        statistic_fg_input_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), DataStatisticActivity.class);
                startActivity(intent);
            }
        });
        statistic_fg_remain_count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), RemainCheckActivity.class));
            }
        });
    }

    private void initView() {
        statistic_fg_input_check = view.findViewById(R.id.statistic_fg_input_check);
        statistic_fg_remain_count = view.findViewById(R.id.statistic_fg_remain_count);
    }
}
