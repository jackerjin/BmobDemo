package com.example.tarena.bmobdemo.bean;

import cn.bmob.v3.BmobObject;

/**
 * 1.继承BmobObject
 * 2.
 * Created by tarena on 2017/6/29.
 */

public class MyUser extends BmobObject {
    String username;
    String password;
    Boolean gender;
    String avatar;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "MyUser{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", gender=" + gender +
                ", avatar='" + avatar + '\'' +
                '}';
    }

    public MyUser(String username, String password, boolean gender, String avatar) {
        this.username = username;
        this.password = password;
        this.gender = gender;
        this.avatar = avatar;
    }

    public MyUser() {
    }
}
