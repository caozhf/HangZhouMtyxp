package com.mtyxp.hangzhoumtyxp.controller.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mtyxp.hangzhoumtyxp.R;
import com.mtyxp.hangzhoumtyxp.utils.SpUtils;
import com.tandong.swichlayout.BaseEffects;
import com.tandong.swichlayout.SwitchLayout;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class RegisterActivity extends AppCompatActivity {

    private TextView user_register_back;
    private EditText user_register_name, user_register_pwd, user_register_again_pwd, user_register_phone;
    private Button user_register_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
//        SwitchLayout.getSlideFromBottom(this, false,
//                BaseEffects.getMoreSlowEffect());
        SwitchLayout.get3DRotateFromRight(this,false, BaseEffects.getMoreSlowEffect());
        initView();
        initListener();
    }

    private void initListener() {
        user_register_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        });
        user_register_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register_register();
            }
        });

    }

    private void register_register() {

        final String user_name = user_register_name.getText().toString().trim();
        final String user_pwd = user_register_pwd.getText().toString().trim();
        String user_again_pwd = user_register_again_pwd.getText().toString().trim();
        String user_phone = user_register_phone.getText().toString().trim();

        if (TextUtils.isEmpty(user_name) || TextUtils.isEmpty(user_pwd) || TextUtils.isEmpty(user_again_pwd) || TextUtils.isEmpty(user_phone)) {
            Toast.makeText(getApplicationContext(), "用户信息不能为空！", Toast.LENGTH_SHORT).show();
        } else if (!user_pwd.equals(user_again_pwd)) {
            Toast.makeText(getApplicationContext(), "两次输入密码不同！", Toast.LENGTH_SHORT).show();
        } else {

            BmobUser user = new BmobUser();
            user.setUsername(user_name);
            user.setPassword(user_pwd);
            user.setMobilePhoneNumber(user_phone);

            user.signUp(new SaveListener<BmobUser>() {
                @Override
                public void done(BmobUser bmobUser, BmobException e) {

                    if (e == null){
                        Toast.makeText(getApplicationContext(),"注册成功！",Toast.LENGTH_SHORT).show();
                        String user_info = user_name + "##" + user_pwd;
                        SpUtils.getInstance(getApplicationContext()).save("user_account",user_info);
                        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                        startActivity(intent);
                        BmobUser.logOut();
                        finish();
                    }else {
                        Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void initView() {
        user_register_back = (TextView) findViewById(R.id.user_register_back);

        user_register_name = (EditText) findViewById(R.id.user_register_name);
        user_register_pwd = (EditText) findViewById(R.id.user_register_pwd);
        user_register_again_pwd = (EditText) findViewById(R.id.user_register_again_pwd);
        user_register_phone = (EditText) findViewById(R.id.user_register_phone);

        user_register_register = (Button) findViewById(R.id.user_register_register);

    }

    public void GoToTest(View view){
        startActivity(new Intent(this,OperateActivity.class));
    }

}

