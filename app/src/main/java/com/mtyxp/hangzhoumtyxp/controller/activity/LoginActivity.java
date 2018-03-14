package com.mtyxp.hangzhoumtyxp.controller.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import com.mtyxp.hangzhoumtyxp.R;
import com.mtyxp.hangzhoumtyxp.model.Model;
import com.mtyxp.hangzhoumtyxp.utils.SpUtils;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class LoginActivity extends AppCompatActivity {

    private PhotoView attention_big_img,attention_small_img;
    private Info mRectF;

    private TextView user_login_register;
    private EditText user_login_name,user_login_pwd;
    private Button user_login_login;
    private BmobUser currentUser;
    private boolean isNet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        isNet = Model.getInstance().isNetWorkAvailable(this);
        initView();
        initListener();
    }

    private void initListener() {

        currentUser = BmobUser.getCurrentUser();
        if (currentUser!=null){
            startActivity(new Intent(getApplication(),OperateActivity.class));
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
                startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
            }
        });

        user_login_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String username = user_login_name.getText().toString().trim();
                final String userpwd = user_login_pwd.getText().toString().trim();
                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(userpwd)){
                    Toast.makeText(getApplicationContext(),"用户名或密码不能为空",Toast.LENGTH_SHORT).show();
                }
                else {
                    Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    user_login_login.setText("正在登录！");
                                    if (!isNet){
                                        Toast.makeText(getApplicationContext(),"系统错误，请检查您的网络！",Toast.LENGTH_SHORT).show();
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
                                                    if(e == null){
                                                        String user_info = username + "##" + userpwd;
                                                        SpUtils.getInstance(getApplicationContext()).save("user_account",user_info);
                                                        Toast.makeText(getApplicationContext(),"登录成功！",Toast.LENGTH_SHORT).show();
                                                        startActivity(new Intent(getApplicationContext(),OperateActivity.class));
                                                        finish();
                                                    }else {
                                                        Toast.makeText(getApplicationContext(),"failure failure"+e.toString(),Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                        }
                                    },2000);
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
        if (attention_big_img.getVisibility() == View.VISIBLE){
            attention_big_img.animaTo(mRectF, new Runnable() {
                @Override
                public void run() {
                    attention_big_img.setVisibility(View.GONE);
                    attention_small_img.setVisibility(View.VISIBLE);
                }
            });
        }else {
            super.onBackPressed();
        }
    }

    private void initView() {
        user_login_register = (TextView) findViewById(R.id.user_login_register);
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
                        if (TextUtils.isEmpty(user_account)){
                        Toast.makeText(getApplicationContext(),"请先登录！",Toast.LENGTH_SHORT).show();
                        }else {
                            String[] split = user_account.split("##");
                            user_login_name.setText(split[0]);
                            user_login_pwd.setText(split[1]);
                        }
                    }
                });

            }
        });
    }
}
