package com.diandian.pdd.diandiancha.baseactivity;

import android.app.Application;

import com.diandian.pdd.diandiancha.bean.User;
import com.google.gson.Gson;


public class MyApplication extends Application {
    public static User user;

    @Override
    public void onCreate() {
        super.onCreate();
        user = new Gson().fromJson(getSharedPreferences("user", MODE_PRIVATE).getString("user", ""), User.class);
    }
}
