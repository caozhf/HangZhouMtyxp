package com.mtyxp.hangzhoumtyxp.controller.fragment.remain_check_fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.mtyxp.hangzhoumtyxp.R;
import com.mtyxp.hangzhoumtyxp.controller.activity.OperateActivity;
import com.mtyxp.hangzhoumtyxp.utils.Constant;
import com.mtyxp.hangzhoumtyxp.utils.DeviceNumBroadCast;
import com.mtyxp.hangzhoumtyxp.utils.OkhttpNetService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.Response;

/**
 * Created by CaoZhF on 2018-05-10.
 */

public class RemainCheckFragment extends Fragment implements View.OnClickListener {

    private View view;

    private Button remain_check_confirm_add_id, remain_check_back;
    private EditText remain_check_device_num;

    private EditText remain_check_id1, remain_check_id2, remain_check_id3, remain_check_id4, remain_check_id5, remain_check_id6,
            remain_check_id7, remain_check_id8, remain_check_id9, remain_check_id10, remain_check_id11, remain_check_id12, remain_check_id13, remain_check_id14;

    private Spinner remain_check_title1, remain_check_title2, remain_check_title3, remain_check_title4, remain_check_title5, remain_check_title6,
            remain_check_title7, remain_check_title8, remain_check_title9, remain_check_title10, remain_check_title11, remain_check_title12, remain_check_title13, remain_check_title14;

    private EditText remain_check_num1, remain_check_num2, remain_check_num3, remain_check_num4, remain_check_num5, remain_check_num6,
            remain_check_num7, remain_check_num8, remain_check_num9, remain_check_num10, remain_check_num11, remain_check_num12, remain_check_num13, remain_check_num14;

    private Button remain_check_sort, remain_check_confirm;

    private String[] rec = {"null","巴氏奶", "一带活菌", "原味酸奶", "蔓越莓", "酸奶熟了", "杭州酸奶", "杭州鲜奶", "杭州优倍", "9", "10", "11", "12", "13", "14"};

    private ArrayAdapter adapter;
    private JSONObject goods;

    public static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("multipart/form-data; charset=utf-8");

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.remain_fg_check, container, false);
        initView();
        initListener();
        initData();

        return view;
    }

    private void initData() {

    }

    private void initListener() {

        remain_check_confirm_add_id.setOnClickListener(this);
        remain_check_back.setOnClickListener(this);
        remain_check_confirm.setOnClickListener(this);
        remain_check_sort.setOnClickListener(this);

    }

    private void initView() {

        remain_check_confirm_add_id = view.findViewById(R.id.remain_check_confirm_add_id);
        remain_check_back = view.findViewById(R.id.remain_check_back);
        remain_check_device_num = view.findViewById(R.id.remain_check_device_num);

        remain_check_confirm = view.findViewById(R.id.remain_check_confirm);
        remain_check_sort = view.findViewById(R.id.remain_check_sort);

        remain_check_id1 = view.findViewById(R.id.remain_check_id1);
        remain_check_id2 = view.findViewById(R.id.remain_check_id2);
        remain_check_id3 = view.findViewById(R.id.remain_check_id3);
        remain_check_id4 = view.findViewById(R.id.remain_check_id4);
        remain_check_id5 = view.findViewById(R.id.remain_check_id5);
        remain_check_id6 = view.findViewById(R.id.remain_check_id6);
        remain_check_id7 = view.findViewById(R.id.remain_check_id7);
        remain_check_id8 = view.findViewById(R.id.remain_check_id8);
        remain_check_id9 = view.findViewById(R.id.remain_check_id9);
        remain_check_id10 = view.findViewById(R.id.remain_check_id10);
        remain_check_id11 = view.findViewById(R.id.remain_check_id11);
        remain_check_id12 = view.findViewById(R.id.remain_check_id12);
        remain_check_id13 = view.findViewById(R.id.remain_check_id13);
        remain_check_id14 = view.findViewById(R.id.remain_check_id14);

        remain_check_title1 = view.findViewById(R.id.remain_check_title1);
        remain_check_title2 = view.findViewById(R.id.remain_check_title2);
        remain_check_title3 = view.findViewById(R.id.remain_check_title3);
        remain_check_title4 = view.findViewById(R.id.remain_check_title4);
        remain_check_title5 = view.findViewById(R.id.remain_check_title5);
        remain_check_title6 = view.findViewById(R.id.remain_check_title6);
        remain_check_title7 = view.findViewById(R.id.remain_check_title7);
        remain_check_title8 = view.findViewById(R.id.remain_check_title8);
        remain_check_title9 = view.findViewById(R.id.remain_check_title9);
        remain_check_title10 = view.findViewById(R.id.remain_check_title10);
        remain_check_title11 = view.findViewById(R.id.remain_check_title11);
        remain_check_title12 = view.findViewById(R.id.remain_check_title12);
        remain_check_title13 = view.findViewById(R.id.remain_check_title13);
        remain_check_title14 = view.findViewById(R.id.remain_check_title14);

        remain_check_num1 = view.findViewById(R.id.remain_check_num1);
        remain_check_num2 = view.findViewById(R.id.remain_check_num2);
        remain_check_num3 = view.findViewById(R.id.remain_check_num3);
        remain_check_num4 = view.findViewById(R.id.remain_check_num4);
        remain_check_num5 = view.findViewById(R.id.remain_check_num5);
        remain_check_num6 = view.findViewById(R.id.remain_check_num6);
        remain_check_num7 = view.findViewById(R.id.remain_check_num7);
        remain_check_num8 = view.findViewById(R.id.remain_check_num8);
        remain_check_num9 = view.findViewById(R.id.remain_check_num9);
        remain_check_num10 = view.findViewById(R.id.remain_check_num10);
        remain_check_num11 = view.findViewById(R.id.remain_check_num11);
        remain_check_num12 = view.findViewById(R.id.remain_check_num12);
        remain_check_num13 = view.findViewById(R.id.remain_check_num13);
        remain_check_num14 = view.findViewById(R.id.remain_check_num14);

        adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, rec);

        remain_check_title1.setAdapter(adapter);
        remain_check_title2.setAdapter(adapter);
        remain_check_title3.setAdapter(adapter);
        remain_check_title4.setAdapter(adapter);
        remain_check_title5.setAdapter(adapter);
        remain_check_title6.setAdapter(adapter);
        remain_check_title7.setAdapter(adapter);
        remain_check_title8.setAdapter(adapter);
        remain_check_title9.setAdapter(adapter);
        remain_check_title10.setAdapter(adapter);
        remain_check_title11.setAdapter(adapter);
        remain_check_title12.setAdapter(adapter);
        remain_check_title13.setAdapter(adapter);
        remain_check_title14.setAdapter(adapter);


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.remain_check_confirm_add_id:
                remain_check_device_num.setText(DeviceNumBroadCast.tem_un);
                break;
            case R.id.remain_check_back:
                Intent intent = new Intent(getActivity(), OperateActivity.class);
                startActivity(intent);
                getActivity().finish();
                break;
            case R.id.remain_check_confirm:
                ConfirmData();
                break;
            case R.id.remain_check_sort:
                SortAllData();
                break;


        }
    }

    private void ConfirmData() {



        String device_num = remain_check_device_num.getText().toString();

        if (goods == null) {
            Toast.makeText(getActivity(), "请确认完成数据整理!", Toast.LENGTH_SHORT).show();
        } else {
            String device_remain = goods.toString();
            Call call = OkhttpNetService.RemainSendServePost(Constant.DEVICE_REMAIN, device_num, device_remain);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Toast.makeText(getActivity(),"failure"+e.getMessage(),Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String remain_num = response.body().string();
                    Looper.prepare();
                    Toast.makeText(getActivity(),"统计成功"+ remain_num,Toast.LENGTH_LONG).show();
                    Looper.loop();
                }
            });

        }


    }

    private void SortAllData() {

        int goods_id1 = Integer.parseInt(remain_check_id1.getText().toString());
        int goods_id2 = Integer.parseInt(remain_check_id2.getText().toString());
        int goods_id3 = Integer.parseInt(remain_check_id3.getText().toString());
        int goods_id4 = Integer.parseInt(remain_check_id4.getText().toString());
        int goods_id5 = Integer.parseInt(remain_check_id5.getText().toString());
        int goods_id6 = Integer.parseInt(remain_check_id6.getText().toString());
        int goods_id7 = Integer.parseInt(remain_check_id7.getText().toString());
        int goods_id8 = Integer.parseInt(remain_check_id8.getText().toString());
        int goods_id9 = Integer.parseInt(remain_check_id9.getText().toString());
        int goods_id10 = Integer.parseInt(remain_check_id10.getText().toString());
        int goods_id11 = Integer.parseInt(remain_check_id11.getText().toString());
        int goods_id12 = Integer.parseInt(remain_check_id12.getText().toString());
        int goods_id13 = Integer.parseInt(remain_check_id13.getText().toString());
        int goods_id14 = Integer.parseInt(remain_check_id14.getText().toString());

        List<Integer> goods_all_id = new ArrayList<>();
        goods_all_id.add(goods_id1);
        goods_all_id.add(goods_id2);
        goods_all_id.add(goods_id3);
        goods_all_id.add(goods_id4);
        goods_all_id.add(goods_id5);
        goods_all_id.add(goods_id6);
        goods_all_id.add(goods_id7);
        goods_all_id.add(goods_id8);
        goods_all_id.add(goods_id9);
        goods_all_id.add(goods_id10);
        goods_all_id.add(goods_id11);
        goods_all_id.add(goods_id12);
        goods_all_id.add(goods_id13);
        goods_all_id.add(goods_id14);


        String goods_name1 = (String) remain_check_title1.getSelectedItem();
        String goods_name2 = (String) remain_check_title2.getSelectedItem();
        String goods_name3 = (String) remain_check_title3.getSelectedItem();
        String goods_name4 = (String) remain_check_title4.getSelectedItem();
        String goods_name5 = (String) remain_check_title5.getSelectedItem();
        String goods_name6 = (String) remain_check_title6.getSelectedItem();
        String goods_name7 = (String) remain_check_title7.getSelectedItem();
        String goods_name8 = (String) remain_check_title8.getSelectedItem();
        String goods_name9 = (String) remain_check_title9.getSelectedItem();
        String goods_name10 = (String) remain_check_title10.getSelectedItem();
        String goods_name11 = (String) remain_check_title11.getSelectedItem();
        String goods_name12 = (String) remain_check_title12.getSelectedItem();
        String goods_name13 = (String) remain_check_title13.getSelectedItem();
        String goods_name14 = (String) remain_check_title14.getSelectedItem();

        List<String> goods_name = new ArrayList<>();
        goods_name.add(goods_name1);
        goods_name.add(goods_name2);
        goods_name.add(goods_name3);
        goods_name.add(goods_name4);
        goods_name.add(goods_name5);
        goods_name.add(goods_name6);
        goods_name.add(goods_name7);
        goods_name.add(goods_name8);
        goods_name.add(goods_name9);
        goods_name.add(goods_name10);
        goods_name.add(goods_name11);
        goods_name.add(goods_name12);
        goods_name.add(goods_name13);
        goods_name.add(goods_name14);


        int goods_num1 = Integer.parseInt(remain_check_num1.getText().toString());
        int goods_num2 = Integer.parseInt(remain_check_num2.getText().toString());
        int goods_num3 = Integer.parseInt(remain_check_num3.getText().toString());
        int goods_num4 = Integer.parseInt(remain_check_num4.getText().toString());
        int goods_num5 = Integer.parseInt(remain_check_num5.getText().toString());
        int goods_num6 = Integer.parseInt(remain_check_num6.getText().toString());
        int goods_num7 = Integer.parseInt(remain_check_num7.getText().toString());
        int goods_num8 = Integer.parseInt(remain_check_num8.getText().toString());
        int goods_num9 = Integer.parseInt(remain_check_num9.getText().toString());
        int goods_num10 = Integer.parseInt(remain_check_num10.getText().toString());
        int goods_num11 = Integer.parseInt(remain_check_num11.getText().toString());
        int goods_num12 = Integer.parseInt(remain_check_num12.getText().toString());
        int goods_num13 = Integer.parseInt(remain_check_num13.getText().toString());
        int goods_num14 = Integer.parseInt(remain_check_num14.getText().toString());

        List<Integer> goods_num = new ArrayList<>();
        goods_num.add(goods_num1);
        goods_num.add(goods_num2);
        goods_num.add(goods_num3);
        goods_num.add(goods_num4);
        goods_num.add(goods_num5);
        goods_num.add(goods_num6);
        goods_num.add(goods_num7);
        goods_num.add(goods_num8);
        goods_num.add(goods_num9);
        goods_num.add(goods_num10);
        goods_num.add(goods_num11);
        goods_num.add(goods_num12);
        goods_num.add(goods_num13);
        goods_num.add(goods_num14);


        goods = new JSONObject();

        for (int i = 0; i < 14; i++) {

            JSONArray array = new JSONArray();
            try {
                array.put(0, goods_all_id.get(i));
                array.put(1, goods_name.get(i));
                array.put(2, goods_num.get(i));
                goods.put("" + (i + 1), array);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }



        Toast.makeText(getActivity(),"数据统计完成！",Toast.LENGTH_SHORT).show();


    }
}
