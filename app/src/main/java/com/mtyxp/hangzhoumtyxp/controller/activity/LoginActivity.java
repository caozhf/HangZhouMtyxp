package com.mtyxp.hangzhoumtyxp.controller.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bm.library.Info;
import com.bm.library.PhotoView;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.mtyxp.hangzhoumtyxp.R;
import com.mtyxp.hangzhoumtyxp.model.Model;
import com.mtyxp.hangzhoumtyxp.utils.Constant;
import com.mtyxp.hangzhoumtyxp.utils.OkhttpNetService;
import com.mtyxp.hangzhoumtyxp.utils.RequestAppVersion;
import com.mtyxp.hangzhoumtyxp.utils.SpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    private PhotoView attention_big_img, attention_small_img;
    private Info mRectF;

    private TextView user_login_register, update_vision;
    private EditText user_login_name, user_login_pwd;
    private Button user_login_login;
    private BmobUser currentUser;
    private boolean isNet;

    private String[] dialog_item = new String[]{"更新", "取消"};

    private static final int UPDATE_YES = 1;
    private static final int UPDATE_NO = 2;
    private static final int URL_ERROR = 3;
    private static final int IO_ERROR = 4;
    private static final int JSON_ERROR = 5;

    private String serveVersionName;
    private int serveVersionCode;
    private String serveContent;
    private String serveUrl;
    private String local_version_name;
    private int local_version_code;

    private Message message = new Message();

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case UPDATE_YES:
                    ShowUpdateDialog();
                    break;
                case UPDATE_NO:

                    break;
                case JSON_ERROR:
                    Toast.makeText(LoginActivity.this, "请检查网络", Toast.LENGTH_SHORT).show();
                    break;
            }

        }
    };
    private AlertDialog.Builder builder;
    private AlertDialog alertDialog;

    private void ShowUpdateDialog() {
        builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setTitle("检测到新的版本，请更新！");
        builder.setItems(dialog_item, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == 0) {
                    StartUpdate();
                }
                if (i == 1) {
                    Toast.makeText(LoginActivity.this, "您已取消更新！", Toast.LENGTH_SHORT).show();
                }
            }
        });
        alertDialog = builder.create();
        alertDialog.show();
    }

    private void StartUpdate() {
        alertDialog.dismiss();
        DownLoadApk();
    }

    private void DownLoadApk() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

            final String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/AppUpdate.apk";
            HttpUtils httpUtils = new HttpUtils();

            httpUtils.download(serveUrl, path, new RequestCallBack<File>() {

                @Override
                public void onLoading(long total, long current, boolean isUploading) {
                    super.onLoading(total, current, isUploading);
                    update_vision.setVisibility(View.VISIBLE);
                    user_login_name.setVisibility(View.GONE);
                    user_login_pwd.setVisibility(View.GONE);
                    user_login_login.setVisibility(View.GONE);
                    update_vision.setText(100 * current / total + "%");
                    System.out.println(isUploading);
                }

                @Override
                public void onSuccess(ResponseInfo<File> responseInfo) {
                    update_vision.setVisibility(View.GONE);
                    user_login_name.setVisibility(View.VISIBLE);
                    user_login_pwd.setVisibility(View.VISIBLE);
                    user_login_login.setVisibility(View.VISIBLE);
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.addCategory(Intent.CATEGORY_DEFAULT);
                    intent.setDataAndType(Uri.fromFile(new File(path)), "application/vnd.android.package-archive");
                    startActivity(intent);

                }

                @Override
                public void onFailure(HttpException e, String s) {
                    System.out.println("reason" + s);
                }
            });
        } else {
            Toast.makeText(LoginActivity.this, "no find SD card!", Toast.LENGTH_SHORT).show();
        }
    }

    private LocalBroadcastManager lbm = LocalBroadcastManager.getInstance(LoginActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        isNet = Model.getInstance().isNetWorkAvailable(this);
        initView();
        initListener();

        GetAppLocalVersion();
        GetAppServeVersion();

    }

    private void GetAppLocalVersion() {
        Map<String, String> app_version = RequestAppVersion.getAPpVersion(LoginActivity.this);
        local_version_name = app_version.get("versionName");
        local_version_code = Integer.parseInt(app_version.get("versionCode"));
    }

    private void GetAppServeVersion() {
        Call call = OkhttpNetService.GetOpenDoorImgAndPwd(Constant.UPDATE_VERSION);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                try {

                    JSONObject jsonObject = new JSONObject(string);
                    serveVersionName = jsonObject.getString("versionName");
                    serveVersionCode = jsonObject.getInt("versionCode");
                    serveContent = jsonObject.getString("content");
                    serveUrl = jsonObject.getString("url");

                    if (local_version_code < serveVersionCode) {
                        message.what = UPDATE_YES;

                        Intent intent = new Intent();
                        intent.setAction("get_version_number");
                        Bundle extras = new Bundle();
                        extras.putInt("local_version_num",local_version_code);
                        extras.putInt("serve_version_num",serveVersionCode);
                        intent.putExtras(extras);
                        lbm.sendBroadcast(intent);

                    } else {
                        message.what = UPDATE_NO;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    message.what = IO_ERROR;
                } finally {
                    try {
                        Thread.sleep(2000);
                        handler.sendMessage(message);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void initListener() {

        currentUser = BmobUser.getCurrentUser();
        if (currentUser != null) {
            startActivity(new Intent(getApplication(), OperateActivity.class));
            finish();
        }
        attention_small_img.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                attention_small_img.setVisibility(View.GONE);
                attention_big_img.setVisibility(View.VISIBLE);
                mRectF = attention_small_img.getInfo();
                attention_big_img.animaFrom(mRectF);
            }
        });

        attention_big_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attention_big_img.animaTo(mRectF, new Runnable() {
                    @Override
                    public void run() {
                        attention_big_img.setVisibility(View.GONE);
                        attention_small_img.setVisibility(View.VISIBLE);
                    }
                });
            }
        });


        user_login_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });

        user_login_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String username = user_login_name.getText().toString().trim();
                final String userpwd = user_login_pwd.getText().toString().trim();
                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(userpwd)) {
                    Toast.makeText(getApplicationContext(), "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    user_login_login.setText("正在登录！");
                                    if (!isNet) {
                                        Toast.makeText(getApplicationContext(), "系统错误，请检查您的网络！", Toast.LENGTH_SHORT).show();
                                        user_login_login.setText("登录");
                                    }
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {

                                            BmobUser user = new BmobUser();
                                            user.setUsername(username);
                                            user.setPassword(userpwd);
                                            user.login(new SaveListener<BmobUser>() {
                                                @Override
                                                public void done(BmobUser bmobUser, BmobException e) {
                                                    if (e == null) {
                                                        String user_info = username + "##" + userpwd;
                                                        SpUtils.getInstance(getApplicationContext()).save("user_account", user_info);
                                                        Toast.makeText(getApplicationContext(), "登录成功！", Toast.LENGTH_SHORT).show();
                                                        startActivity(new Intent(getApplicationContext(), OperateActivity.class));
                                                        finish();
                                                    } else {
                                                        Toast.makeText(getApplicationContext(), "failure failure" + e.toString(), Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                        }
                                    }, 2000);
                                }
                            });
                        }
                    });
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (attention_big_img.getVisibility() == View.VISIBLE) {
            attention_big_img.animaTo(mRectF, new Runnable() {
                @Override
                public void run() {
                    attention_big_img.setVisibility(View.GONE);
                    attention_small_img.setVisibility(View.VISIBLE);
                }
            });
        } else {
            super.onBackPressed();
        }
    }

    private void initView() {
        user_login_register = (TextView) findViewById(R.id.user_login_register);
        update_vision = (TextView) findViewById(R.id.update_vision);
        user_login_name = (EditText) findViewById(R.id.user_login_name);
        user_login_pwd = (EditText) findViewById(R.id.user_login_pwd);
        user_login_login = (Button) findViewById(R.id.user_login_login);

        attention_small_img = (PhotoView) findViewById(R.id.attention_small_img);
        attention_small_img.disenable();
        attention_small_img.setScaleType(ImageView.ScaleType.CENTER);

        attention_big_img = (PhotoView) findViewById(R.id.attention_big_img);
        attention_big_img.enable();
        attention_big_img.setAlpha(240);

        isFirstLogin();
    }

    private void isFirstLogin() {
        final String user_account = SpUtils.getInstance(getApplicationContext()).getString("user_account", "");
        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (TextUtils.isEmpty(user_account)) {
                            Toast.makeText(getApplicationContext(), "请先登录！", Toast.LENGTH_SHORT).show();
                        } else {
                            String[] split = user_account.split("##");
                            user_login_name.setText(split[0]);
                            user_login_pwd.setText(split[1]);
                        }
                    }
                });

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }

}
