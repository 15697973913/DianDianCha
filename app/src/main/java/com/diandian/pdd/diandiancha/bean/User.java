package com.diandian.pdd.diandiancha.bean;


import com.google.gson.Gson;

public class User {
String ss="{\"passWord\":\"11111\",\"userExplain\":\"杰哥奶茶店\",\"phoneNum\":\"15697973913\",\"id\":1,\"userType\":1,\"userName\":\"11111\"}";

    /**
     * passWord : 11111
     * userExplain : 杰哥奶茶店
     * phoneNum : 15697973913
     * id : 1
     * userType : 1  用户类型：1、管理员密码2：商家密码:3：客户密码
     * userName : 11111
     */

    private String passWord;
    private String userExplain;
    private String phoneNum;
    private int id;
    private int userType;
    private String userName;

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getUserExplain() {
        return userExplain;
    }

    public void setUserExplain(String userExplain) {
        this.userExplain = userExplain;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
