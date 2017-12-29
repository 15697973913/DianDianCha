package com.diandian.pdd.diandiancha.baseactivity;

import android.app.Application;

import com.diandian.pdd.diandiancha.bean.User;
import com.diandian.pdd.diandiancha.bean.waresInfo;
import com.diandian.pdd.diandiancha.request.MyRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;


public class MyApplication extends Application {
    public static User user;
    /**
     * 商家界面显示的商品信息
     */
    public static List<waresInfo> sellerWaresList;

    @Override
    public void onCreate() {
        super.onCreate();
        user = new Gson().fromJson(getSharedPreferences("user", MODE_PRIVATE).getString("user", ""), User.class);
        sellerWaresList = new ArrayList<>();
    }

}
