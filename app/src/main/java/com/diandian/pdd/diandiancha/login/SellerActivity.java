package com.diandian.pdd.diandiancha.login;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.diandian.pdd.diandiancha.R;
import com.diandian.pdd.diandiancha.adapter.SellerAdapter;
import com.diandian.pdd.diandiancha.baseactivity.MyApplication;
import com.diandian.pdd.diandiancha.bean.waresInfo;
import com.diandian.pdd.diandiancha.request.MyRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SellerActivity extends AppCompatActivity {


    @BindView(R.id.seller_bt_tab)
    Button sellerBtTab;
    @BindView(R.id.seller_list_wares)
    ListView sellerListWares;
    @BindView(R.id.seller_bt_addwares)
    LinearLayout sellerBtAddwares;
    public static SellerActivity activity;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller);
        ButterKnife.bind(this);
        activity=this;
        initData();
    }

    private void initData() {
        MyRequest.queryAllWares(MyApplication.user.getUserName(), new MyRequest.MyCallback() {
            public void sucess(String body) {
                MyApplication.sellerWaresList = new Gson().fromJson(body, new TypeToken<ArrayList<waresInfo>>() {
                }.getType());
                handler.sendEmptyMessage(0x1111);
            }
        });
    }
    public void updateAdapter(){
        sellerListWares.setAdapter(new SellerAdapter(SellerActivity.this));
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0x1111:
                    sellerListWares.setAdapter(new SellerAdapter(SellerActivity.this));
                    break;
            }
        }
    };

    @OnClick({R.id.seller_bt_tab, R.id.seller_bt_addwares})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //报表按钮
            case R.id.seller_bt_tab:

                break;
            //添加商品按钮
            case R.id.seller_bt_addwares:

                break;
        }
    }
}
