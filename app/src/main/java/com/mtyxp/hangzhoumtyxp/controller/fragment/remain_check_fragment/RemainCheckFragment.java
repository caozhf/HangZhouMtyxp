package com.mtyxp.hangzhoumtyxp.controller.fragment.remain_check_fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
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
import com.mtyxp.hangzhoumtyxp.model.bean.Bashinai;
import com.mtyxp.hangzhoumtyxp.model.bean.GoodsTitleInfo;
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

    private Spinner remain_check_title1, remain_check_title2, remain_check_title3, remain_check_title4, remain_check_title5, remain_check_title6,
            remain_check_title7, remain_check_title8, remain_check_title9, remain_check_title10, remain_check_title11, remain_check_title12;

    private EditText remain_check_num1, remain_check_num2, remain_check_num3, remain_check_num4, remain_check_num5, remain_check_num6,
            remain_check_num7, remain_check_num8, remain_check_num9, remain_check_num10, remain_check_num11, remain_check_num12;

    private Button remain_check_sort, remain_check_confirm;

    private String[] rec = {"None", "245g酸奶熟了", "180g蔓越莓酸奶", "180g原味酸奶", "180g一代活菌", "200g巴氏奶", "190ml酸奶", "200ml优倍鲜牛奶", "200ml鲜牛奶"};

    private String[] road_num = {"0", "12", "11", "10", "9", "8", "3", "2", "1"};

    private String[] goods_all_num = {"0", "1", "2", "3", "4", "5", "6", "7", "8"};

    private ArrayAdapter adapter;

    private ArrayAdapter goods_all_num_adapter;

    private ArrayAdapter road_num_adapter;

    private JSONObject goods;

    public static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("multipart/form-data; charset=utf-8");

    private List<Bashinai> mlist = new ArrayList<Bashinai>();

    private List spinner_item = new ArrayList();

    private List<GoodsTitleInfo> spinner_info;

    private SpinnerItem spinnerItem;

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

        adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, spinner_item);

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

//        goods_all_num_adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1,goods_all_num);
//        remain_check_num1.setAdapter(goods_all_num_adapter);
//        remain_check_num2.setAdapter(goods_all_num_adapter);
//        remain_check_num3.setAdapter(goods_all_num_adapter);
//        remain_check_num4.setAdapter(goods_all_num_adapter);
//        remain_check_num5.setAdapter(goods_all_num_adapter);
//        remain_check_num6.setAdapter(goods_all_num_adapter);
//        remain_check_num7.setAdapter(goods_all_num_adapter);
//        remain_check_num8.setAdapter(goods_all_num_adapter);
//        remain_check_num9.setAdapter(goods_all_num_adapter);
//        remain_check_num10.setAdapter(goods_all_num_adapter);
//        remain_check_num11.setAdapter(goods_all_num_adapter);
//        remain_check_num12.setAdapter(goods_all_num_adapter);


//        RemainCheckAdapter adapter = new RemainCheckAdapter(getActivity(),mlist);


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.remain_check_confirm_add_id:
                remain_check_device_num.setText(DeviceNumBroadCast.tem_un);
                mlist.clear();

                spinner_item.clear();

                spinner_info = DeviceNumBroadCast.data;
                if (spinner_info == null) {
                    Toast.makeText(getActivity(), "请先确定设备编号！", Toast.LENGTH_SHORT).show();
                } else {

                    for (int i = 0; i < 12; i++) {
                        spinnerItem = new SpinnerItem(String.valueOf(spinner_info.get(i).getGoods_id()), spinner_info.get(i).getGoods_title());
                        spinner_item.add(spinnerItem);
                    }
                    initView();
                }
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

    private void DefaultData() {
        Call call = OkhttpNetService.DefaultDataInput(Constant.TEMP_QUANTITY_CHECK + DeviceNumBroadCast.tem_un);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(getActivity(), "error+" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getContext(), "请求数据不存在！", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {

                    String string = response.body().string();
                    try {
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

                    System.out.println("kk" + mlist.toString());

                    for (int i = 0; i < 12; i++) {
                        SpinnerItem item = new SpinnerItem(String.valueOf(mlist.get(i).getBa_id()), mlist.get(i).getBa_title());
                        spinner_item.add(item);
                    }

                }
            }
        });
    }

    public class SpinnerItem {
        private String Key = "";
        private String Value = "";

        public SpinnerItem() {
            Key = "";
            Value = "";
        }

        public SpinnerItem(String _Key, String _Value) {
            Key = _Key;
            Value = _Value;
        }

        @Override
        public String toString() {
            return Value;
        }

        public String GetKey() {
            return Key;
        }

        public String GetValue() {
            return Value;
        }

    }

    private void ConfirmData() {


        String device_num = remain_check_device_num.getText().toString();

        if (goods == null) {
            Toast.makeText(getActivity(), "请确认完成数据整理!", Toast.LENGTH_SHORT).show();
        } else {
            String device_remain = goods.toString();

//            String temp = "{\"1\":[0,\"None\",0],\"2\":[0,\"None\",0],\"3\":[0,\"None\",0],\"4\":[0,\"None\",0],\"5\":[0,\"None\",0],\"6\":[0,\"None\",0],\"7\":[1,\"200ml鲜牛奶\",3],\"8\":[2,\"200ml优倍鲜牛奶\",1],\"9\":[3,\"190ml酸奶\",4],\"10\":[1,\"200ml鲜牛奶\",6],\"11\":[2,\"200ml优倍鲜牛奶\",6],\"12\":[3,\"190ml酸奶\",5]}";

            Call call = OkhttpNetService.RemainSendServePost(Constant.DEVICE_REMAIN, device_num, device_remain);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Toast.makeText(getActivity(), "failure" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String remain_num = response.body().string();
                    Looper.prepare();
                    Toast.makeText(getActivity(), "统计成功" + remain_num, Toast.LENGTH_LONG).show();
                    Looper.loop();
                }
            });
        }
    }

    private void SortAllData() {
        SpinnerItem goods_name1 = (SpinnerItem) remain_check_title1.getSelectedItem();
        SpinnerItem goods_name2 = (SpinnerItem) remain_check_title2.getSelectedItem();
        SpinnerItem goods_name3 = (SpinnerItem) remain_check_title3.getSelectedItem();
        SpinnerItem goods_name4 = (SpinnerItem) remain_check_title4.getSelectedItem();
        SpinnerItem goods_name5 = (SpinnerItem) remain_check_title5.getSelectedItem();
        SpinnerItem goods_name6 = (SpinnerItem) remain_check_title6.getSelectedItem();
        SpinnerItem goods_name7 = (SpinnerItem) remain_check_title7.getSelectedItem();
        SpinnerItem goods_name8 = (SpinnerItem) remain_check_title8.getSelectedItem();
        SpinnerItem goods_name9 = (SpinnerItem) remain_check_title9.getSelectedItem();
        SpinnerItem goods_name10 = (SpinnerItem) remain_check_title10.getSelectedItem();
        SpinnerItem goods_name11 = (SpinnerItem) remain_check_title11.getSelectedItem();
        SpinnerItem goods_name12 = (SpinnerItem) remain_check_title12.getSelectedItem();

        if (goods_name1 == null) {
            Toast.makeText(getActivity(), "请先确认设备编号！", Toast.LENGTH_SHORT).show();
        } else {

            List<String> goods_name = new ArrayList<>();
            goods_name.add(goods_name1.GetValue());
            goods_name.add(goods_name2.GetValue());
            goods_name.add(goods_name3.GetValue());
            goods_name.add(goods_name4.GetValue());
            goods_name.add(goods_name5.GetValue());
            goods_name.add(goods_name6.GetValue());
            goods_name.add(goods_name7.GetValue());
            goods_name.add(goods_name8.GetValue());
            goods_name.add(goods_name9.GetValue());
            goods_name.add(goods_name10.GetValue());
            goods_name.add(goods_name11.GetValue());
            goods_name.add(goods_name12.GetValue());

            List<Integer> goods_id = new ArrayList<>();
            goods_id.add(Integer.parseInt(goods_name1.GetKey()));
            goods_id.add(Integer.parseInt(goods_name2.GetKey()));
            goods_id.add(Integer.parseInt(goods_name3.GetKey()));
            goods_id.add(Integer.parseInt(goods_name4.GetKey()));
            goods_id.add(Integer.parseInt(goods_name5.GetKey()));
            goods_id.add(Integer.parseInt(goods_name6.GetKey()));
            goods_id.add(Integer.parseInt(goods_name7.GetKey()));
            goods_id.add(Integer.parseInt(goods_name8.GetKey()));
            goods_id.add(Integer.parseInt(goods_name9.GetKey()));
            goods_id.add(Integer.parseInt(goods_name10.GetKey()));
            goods_id.add(Integer.parseInt(goods_name11.GetKey()));
            goods_id.add(Integer.parseInt(goods_name12.GetKey()));

            if (TextUtils.isEmpty(remain_check_num1.getText()) || TextUtils.isEmpty(remain_check_num2.getText()) || TextUtils.isEmpty(remain_check_num3.getText())
                    || TextUtils.isEmpty(remain_check_num4.getText()) || TextUtils.isEmpty(remain_check_num5.getText()) || TextUtils.isEmpty(remain_check_num6.getText())
                    || TextUtils.isEmpty(remain_check_num7.getText()) || TextUtils.isEmpty(remain_check_num8.getText()) || TextUtils.isEmpty(remain_check_num9.getText())
                    || TextUtils.isEmpty(remain_check_num10.getText()) || TextUtils.isEmpty(remain_check_num11.getText()) || TextUtils.isEmpty(remain_check_num12.getText())) {
                Toast.makeText(getActivity(), "产品数量不能为空!", Toast.LENGTH_SHORT).show();
            } else {
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


                goods = new JSONObject();

                for (int i = 0; i < 12; i++) {

                    JSONArray array = new JSONArray();
                    try {
                        array.put(0, goods_id.get(i));
                        array.put(1, goods_name.get(i));
                        array.put(2, goods_num.get(i));
                        goods.put("" + (i + 1), array);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                Toast.makeText(getActivity(), "数据统计完成！", Toast.LENGTH_SHORT).show();
                System.out.println("Te" + goods.toString());

            }
        }

    }
}
