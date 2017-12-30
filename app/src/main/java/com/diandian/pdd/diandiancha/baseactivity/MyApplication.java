package com.diandian.pdd.diandiancha.baseactivity;

import android.app.Application;
import android.content.Context;

import com.diandian.pdd.diandiancha.bean.User;
import com.diandian.pdd.diandiancha.bean.WaresBean;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MyApplication extends Application {
    public static User user;
    public static HashMap<Integer, Integer> map;//购物车
    public static Context context;


    /**
     * 商家界面显示的商品信息
     */
    public static List<WaresBean> sellerWaresList;
    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        user = new Gson().fromJson(getSharedPreferences("user", MODE_PRIVATE).getString("user", ""), User.class);
        sellerWaresList = new ArrayList<>();
    }
}
