package com.diandian.pdd.diandiancha.baseactivity;

import android.app.Application;
import android.content.Context;

import com.diandian.pdd.diandiancha.bean.User;
import com.google.gson.Gson;

import java.util.HashMap;


public class MyApplication extends Application {
    public static User user;
    public static HashMap<Integer, Integer> map;//购物车
    public static Context context;


    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        user = new Gson().fromJson(getSharedPreferences("user", MODE_PRIVATE).getString("user", ""), User.class);
    }
}
