package com.diandian.pdd.diandiancha.baseactivity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.diandian.pdd.diandiancha.R;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.util.ArrayList;
import java.util.List;


public class BaseActivity extends AppCompatActivity {


    Toolbar toolbar;

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.activity_slidin, R.anim.stay);
    }

    @Override
    protected void onStart() {
        super.onStart();
//       toolbar = (Toolbar) findViewById(R.id.toolbar);
//       toolbar= (Toolbar) LayoutInflater.from(this).inflate(R.layout.layout,null);
//        // App Logo
//        toolbar.setLogo(R.mipmap.ic_launcher);
//// Title
//        toolbar.setTitle("My Title");
//// Sub Title
//        toolbar.setSubtitle("Sub title");
//        setSupportActionBar(toolbar);
//        toolbar.setNavigationIcon(R.mipmap.ic_launcher);

        // Menu item click 的監聽事件一樣要設定在 setSupportActionBar 才有作用
//        toolbar.setOnMenuItemClickListener(onMenuItemClick);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.stay, R.anim.activity_slidout);
    }


    long time;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            if (System.currentTimeMillis() - time < 500) {
                return true;
            }
            time = System.currentTimeMillis();
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitys.add(this);
    }

    @Override
    protected void onDestroy() {
        activitys.remove(this);
        super.onDestroy();
    }

    static List<Activity> activitys = new ArrayList<>();

    public void finishAll() {

        Log.i("====", "退出" + activitys.size());
        for (Activity activity : activitys) {
            activity.finish();
        }
        System.exit(0);
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    public void setHeader(int color) {

        ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0)
                .setFitsSystemWindows(true);
        // getWindow().getDecorView().setFitsSystemWindows(true);
        setTranslucentStatus(true);
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setNavigationBarTintEnabled(true);
        tintManager.setTintColor(color);
        // 自定义颜色
    }
    public boolean check(TextView textView) {
        if (TextUtils.isEmpty(textView.getText())) {
            textView.setFocusable(true);
            textView.requestFocus();
            Toast.makeText(this, "请填写完整数据", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }
    public void initback() {
        View title_back = findViewById(R.id.title_back);
        title_back.setVisibility(View.VISIBLE);
        title_back .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void initTitle(boolean isBack,String title,String rightText) {
        TextView tt = findViewById(R.id.title_center);
        tt.setVisibility(View.VISIBLE);
        tt.setText(title);
        if(isBack){
            initback();
        }
        if(!TextUtils.isEmpty(rightText)){
            setTitleRight(rightText);
        }
    }
    public void setTitleRight(String ss){
        TextView aa = findViewById(R.id.title_right);
        aa.setText(ss);
    }
    public void setOnRightClick(View.OnClickListener listener){
        findViewById(R.id.title_right).setOnClickListener(listener);
    }
}
