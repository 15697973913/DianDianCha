package com.diandian.pdd.diandiancha.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.diandian.pdd.diandiancha.R;
import com.diandian.pdd.diandiancha.admin.AdminActivity;
import com.diandian.pdd.diandiancha.baseactivity.BaseActivity;
import com.diandian.pdd.diandiancha.baseactivity.MyApplication;
import com.diandian.pdd.diandiancha.sellerview.SellerActivity;
import com.diandian.pdd.diandiancha.user.UserActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SplashActivity extends BaseActivity {
    @BindView(R.id.button_login)
    TextView button_login;
    @BindView(R.id.button_registr)
    TextView button_registr;

    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (MyApplication.user != null) {

            button_login.setVisibility(View.GONE);
            button_registr.setVisibility(View.GONE);
            handler.postDelayed(r, 3000);
        } else {
            button_registr.setVisibility(View.VISIBLE);
            button_login.setVisibility(View.VISIBLE);

        }
    }

    @Override
    public void onBackPressed() {
        if (button_login.getVisibility() == View.GONE) {
            return;
        }
    }

    @OnClick(R.id.button_registr)
    public void onRegistrClick() {
        startActivity(new Intent(this, RegistrActivity.class));
    }

    @OnClick(R.id.button_login)
    public void onLoginClick() {
        startActivity(new Intent(this, LoginActivity.class));
    }

    private Runnable r = new Runnable() {
        @Override
        public void run() {
            switch (MyApplication.user.getUserType()) {
                case 1:
                    startActivity(new Intent(SplashActivity.this, AdminActivity.class));
                    break;
                case 2:
                    startActivity(new Intent(SplashActivity.this, SellerActivity.class));
                    break;
                case 3:
                    startActivity(new Intent(SplashActivity.this, UserActivity.class));
                    break;
            }
        }
    };


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // 為了讓 Toolbar 的 Menu 有作用，這邊的程式不可以拿掉
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }

}
