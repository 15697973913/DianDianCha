package com.diandian.pdd.diandiancha.login;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.diandian.pdd.diandiancha.R;
import com.diandian.pdd.diandiancha.admin.AdminActivity;
import com.diandian.pdd.diandiancha.baseactivity.BaseActivity;
import com.diandian.pdd.diandiancha.baseactivity.MyApplication;
import com.diandian.pdd.diandiancha.bean.User;
import com.diandian.pdd.diandiancha.request.MyRequest;
import com.diandian.pdd.diandiancha.sellerview.SellerActivity;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.edit_account)
    EditText editAccount;
    @BindView(R.id.edit_pwd)
    EditText editPwd;
    @BindView(R.id.text_login)
    Button textLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        setHeader(Color.WHITE);
    }
    @OnClick(R.id.text_login)
    public void onLoginClick(){
        if(check(editAccount)&&check(editPwd)){
            //登录
            MyRequest.login(editAccount.getText().toString(), editPwd.getText().toString(), new MyRequest.MyCallback() {
                @Override
                public void sucess(String body) {
                   MyApplication.user= new Gson().fromJson(body, User.class);
                   //记录登录状态
                    getSharedPreferences("user",MODE_PRIVATE).edit().putString("user",body).apply();
                    switch (MyApplication.user.getUserType()){
                        //系统管理员
                        case 1:
                            startActivity(new Intent(LoginActivity.this, AdminActivity.class));
                            finish();
                            break;
                            //商家
                        case 2:
                            startActivity(new Intent(LoginActivity.this, SellerActivity.class));
                            finish();
                            break;
                        case 3:
                            break;
                    }
                }
            });
        }
    }
}
