package com.mtyxp.hangzhoumtyxp.controller.temp_activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.mtyxp.hangzhoumtyxp.R;
import com.mtyxp.hangzhoumtyxp.controller.activity.OperateActivity;
import com.mtyxp.hangzhoumtyxp.model.Model;
import com.mtyxp.hangzhoumtyxp.model.bean.Bashinai;
import com.mtyxp.hangzhoumtyxp.utils.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TempDetailActivity extends AppCompatActivity {

    private EditText temp_detail_edittext;
    private Button temp_detail_button;
    private ListView temp_detail_listview;

    private Button refresh;

    private String pass_number;

    private List<Bashinai> mlist;

    private Button temp_back,temp_detail_refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp_detail);
        initView();
        initListener();
    }

    private void initListener() {
        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                temp_detail_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String get_pass_num = temp_detail_edittext.getText().toString();
                        if (TextUtils.isEmpty(get_pass_num)) {
                            Toast.makeText(getApplicationContext(), "请输入编号", Toast.LENGTH_SHORT).show();
                        } else {
                            Server_Data_Request();
                        }
                    }
                });
            }
        });

        temp_detail_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String get_pass_num = temp_detail_edittext.getText().toString();
                if (TextUtils.isEmpty(get_pass_num)) {
                    Toast.makeText(getApplicationContext(), "确定后再刷新！", Toast.LENGTH_SHORT).show();
                } else {
                    Server_Data_Request();
                }
            }
        });

        temp_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TempDetailActivity.this, OperateActivity.class));
                finish();
            }
        });

    }

    private void Server_Data_Request() {

        pass_number = temp_detail_edittext.getText().toString();
        OkHttpClient client = new OkHttpClient();
        Request.Builder builder = new Request.Builder();
        final Request request = builder.get().url(Constant.TEMP_QUANTITY_CHECK + pass_number).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(TempDetailActivity.this, "错误" + e.getMessage(), Toast.LENGTH_SHORT).show();
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

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(TempDetailActivity.this, "请求数据不存在！", Toast.LENGTH_SHORT).show();
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
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            TempAdapter adapter = new TempAdapter(getApplicationContext(),mlist);
                            temp_detail_listview.setAdapter(adapter);
                        }
                    });
                }
            }
        });
    }

    private void initView() {
        temp_detail_edittext = (EditText) findViewById(R.id.temp_detail_edittext);
        temp_detail_button = (Button) findViewById(R.id.temp_detail_button);
        temp_detail_listview = (ListView) findViewById(R.id.temp_detail_listview);

        temp_back = (Button) findViewById(R.id.temp_back);
        temp_detail_refresh = (Button) findViewById(R.id.temp_detail_refresh);

    }


}
