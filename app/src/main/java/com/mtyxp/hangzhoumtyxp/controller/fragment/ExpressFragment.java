package com.mtyxp.hangzhoumtyxp.controller.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mtyxp.hangzhoumtyxp.R;
import com.mtyxp.hangzhoumtyxp.controller.adapter.ExpressFgAdapter;
import com.mtyxp.hangzhoumtyxp.model.Model;
import com.mtyxp.hangzhoumtyxp.model.bean.Bashinai;
import com.mtyxp.hangzhoumtyxp.utils.Constant;
import com.mtyxp.hangzhoumtyxp.utils.OkhttpNetService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by CaoZhF on 2017-10-28.
 */

public class ExpressFragment extends Fragment {

    private View view;

    private EditText express_fg_et;
    private Button express_fg_btn;
    private ListView express_fg_lv;
    private String pass_number;

    private List<Bashinai> mlist;

    private LinearLayout footlayout;
    private Button achieve_express;

    private LocalBroadcastManager lbm;
    private LinearLayout headlayout;

    private ImageView express_fg_lv_head_img;
    private TextView express_fg_lv_head_pwd;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1){
                String pwd = (String) msg.obj;
                express_fg_lv_head_pwd.setText(pwd);
            }
            if (msg.what == 2){
                Bitmap bitmap = (Bitmap) msg.obj;
                express_fg_lv_head_img.setImageBitmap(bitmap);
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.express_fragment, container, false);
        initView();
        initListener();
        return view;
    }

    private void initListener() {

        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                express_fg_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String get_pass_num = express_fg_et.getText().toString();
                        if (TextUtils.isEmpty(get_pass_num)) {
                            Toast.makeText(getContext(), "请输入编号", Toast.LENGTH_SHORT).show();
                        } else {

                            listView_Head(Constant.OPEN_DOOR_IMG, Constant.OPEN_DOOR_PWD);
                            Server_Data_Request();
                        }
                    }
                });
            }
        });





        achieve_express.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
                    @Override
                    public void run() {
                        String complete_dis_url = Constant.COMPLETE_DIS+express_fg_et.getText().toString();
                        OkHttpClient client = new OkHttpClient();
//                        FormBody body = new FormBody.Builder()
//                                .add("achieve", "complete distribution——" + pass_number)
//                                .build();
                        Request.Builder builder = new Request.Builder();
                        Request request = builder.get().url(complete_dis_url).build();
                        Call call = client.newCall(request);
                        call.enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                Toast.makeText(getContext(), "请再次确认完成", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        pass_number = express_fg_et.getText().toString();
                                        Toast.makeText(getContext(), "您已经完成" + pass_number + "设备的配送", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });
                    }
                });

            }
        });

    }

    private void listView_Head(String openDoorImg, String openDoorPwd) {

        String url_img = openDoorImg + express_fg_et.getText().toString().trim();
        String url_pwd = openDoorPwd + express_fg_et.getText().toString().trim();

        Call call_img = OkhttpNetService.GetOpenDoorImgAndPwd(url_img);
        call_img.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(), "错误" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream inputStream = response.body().byteStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                Message message = new Message();
                message.what = 2;
                message.obj = bitmap;
                handler.sendMessage(message);

            }
        });

        Call call_pwd = OkhttpNetService.GetOpenDoorImgAndPwd(url_pwd);
        call_pwd.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(), "错误" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String pwd = response.body().string();

                Message message = new Message();
                message.what = 1;
                message.obj = pwd;
                handler.sendMessage(message);

            }
        });


    }

    private void Server_Data_Request() {
//        String temperary = "http://192.168.0.105:8080/mtyxp/data.json";

        pass_number = express_fg_et.getText().toString();
        OkHttpClient client = new OkHttpClient();
        Request.Builder builder = new Request.Builder();
        final Request request = builder.get().url(Constant.REQUEST_URL + pass_number).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(), "错误" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {

//                    Intent intent = new Intent();
//                    intent.setAction("device_num");
//                    intent.putExtra("num",pass_number);
//                    lbm.sendBroadcast(intent);

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getContext(), "请求数据不存在！", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    try {
                        String string = response.body().string();

                        mlist = new ArrayList<Bashinai>();
                        JSONObject object = new JSONObject(string);
                        JSONArray jsonArray1 = object.getJSONArray("12");

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
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ExpressFgAdapter myAdapter = new ExpressFgAdapter(getContext(), mlist);
                            express_fg_lv.setAdapter(myAdapter);
                        }
                    });
                }
            }
        });
    }

    private void initView() {

        express_fg_et = view.findViewById(R.id.express_fg_et);
        express_fg_btn = view.findViewById(R.id.express_fg_btn);
        express_fg_lv = view.findViewById(R.id.express_fg_lv);
        footlayout = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.express_fg_lv_foot, null);
        headlayout = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.express_fg_lv_head, null);

        express_fg_lv.addHeaderView(headlayout, null, false);
        express_fg_lv_head_img = headlayout.findViewById(R.id.express_fg_lv_head_img);
        express_fg_lv_head_pwd = headlayout.findViewById(R.id.express_fg_lv_head_pwd);

        express_fg_lv.addFooterView(footlayout, null, false);
        achieve_express = footlayout.findViewById(R.id.express_fg_lv_foot_achieve);

        lbm = LocalBroadcastManager.getInstance(getContext());

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("fragment");
    }

    @Override
    public void onResume() {
        super.onResume();
        System.out.println("resume_fra");
    }
}
