package com.diandian.pdd.diandiancha.login;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.diandian.pdd.diandiancha.MainActivity;
import com.diandian.pdd.diandiancha.R;
import com.diandian.pdd.diandiancha.baseactivity.BaseActivity;
import com.diandian.pdd.diandiancha.baseactivity.MyApplication;
import com.diandian.pdd.diandiancha.bean.User;
import com.diandian.pdd.diandiancha.request.MyRequest;
import com.diandian.pdd.diandiancha.util.MyLog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegistrActivity extends BaseActivity {

    @BindView(R.id.spinner_type)
    Spinner spinnerType;
    @BindView(R.id.edit_account)
    EditText editAccount;
    @BindView(R.id.edit_pwd)
    EditText editPwd;
    @BindView(R.id.edit_pwd_repeat)
    EditText editPwdRepeat;
    @BindView(R.id.edit_)
    EditText edit;
    @BindView(R.id.edit_phone)
    EditText editPhone;
    @BindView(R.id.text_register)
    TextView textRegister;
    int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registr);
        ButterKnife.bind(this);
        setHeader(Color.WHITE);
        spinnerType.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, new String[]{"管理员登录", "商户登录", "普通用户登录"}));
        spinnerType.setSelection(2);

        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                type = position + 1;
                ((TextView) view).setTextColor(Color.parseColor("#444444"));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @OnClick(R.id.text_register)
    public void onViewClicked() {
        if (check(editAccount) && check(editPwd) && check(editPwdRepeat) && check(edit) && check(editPhone)) {
            if(!editPwd.getText().toString().equals(editPwdRepeat.getText().toString())){
                MyLog.showToast(this,"输入密码不一致");
            return;
            }
            final User user=new User();
            user.setPassWord(editPwd.getText().toString());
            user.setPhoneNum(editPhone.getText().toString());
            user.setUserExplain(edit.getText().toString());
            user.setUserName(editAccount.getText().toString());
            user.setUserType(type);
            //通过注册
            MyRequest.registr(user.getUserName(), user.getPassWord(), type + "", user.getUserExplain(), user.getPhoneNum(), new MyRequest.MyCallback() {
                @Override
                public void sucess(String body) {
                    startActivity(new Intent(RegistrActivity.this, LoginActivity.class));
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            MyLog.showToast(RegistrActivity.this,"注册成功");
                        }
                    });
                    finish();
                }
            });
        }
    }
}
