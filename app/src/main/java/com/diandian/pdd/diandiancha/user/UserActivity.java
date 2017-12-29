package com.diandian.pdd.diandiancha.user;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.widget.TextView;

import com.diandian.pdd.diandiancha.R;
import com.diandian.pdd.diandiancha.baseactivity.BaseActivity;
import com.diandian.pdd.diandiancha.util.MyLog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        ButterKnife.bind(this);

        setHeader(Color.BLUE);
//        item1.set
    }

    long time;

    @Override
    public void onBackPressed() {
        long t = System.currentTimeMillis();
        if (t - time < 1500) {
            finishAll();
        } else {
            MyLog.showToast(this, "点两次退出");
            time = t;
        }
    }

    @BindView(R.id.item1)
    TextView item1;
    @BindView(R.id.item2)
    TextView item2;
    @BindView(R.id.item3)
    TextView item3;

    @OnClick(R.id.item1)
    public void onItem1Click() {
        getFragmentManager().beginTransaction().replace(R.id.container_layout,new Pager1Fragment()).commit();
        setViewColor(item1);
        clearViewColor(item2);
        clearViewColor(item3);
    }

    @OnClick(R.id.item2)
    public void onItem2Click() {

        getFragmentManager().beginTransaction().replace(R.id.container_layout,new Pager2Fragment()).commit();
        setViewColor(item2);
        clearViewColor(item1);
        clearViewColor(item3);
    }

    @OnClick(R.id.item3)
    public void onItem3Click() {
        setViewColor(item3);
        clearViewColor(item1);
        clearViewColor(item2);
    }

    void setViewColor(TextView tv) {
        tv.getCompoundDrawables()[1].setColorFilter(Color.parseColor("#1afa29"), PorterDuff.Mode.SRC_IN);
        tv.setTextColor(Color.parseColor("#1afa29"));
    }

    void clearViewColor(TextView tv) {
        tv.setTextColor(Color.parseColor("#333333"));
        tv.getCompoundDrawables()[1].clearColorFilter();
    }

}
