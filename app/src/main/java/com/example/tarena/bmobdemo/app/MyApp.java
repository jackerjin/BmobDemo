package com.example.tarena.bmobdemo.app;

import android.app.Application;
import android.media.MediaPlayer;

import com.example.tarena.bmobdemo.R;

import cn.bmob.push.BmobPush;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobInstallation;

/**
 * Created by tarena on 2017/6/29.
 */

public class MyApp extends Application {
    public static MyApp CONTEXT;
    public static MediaPlayer player;
    @Override
    public void onCreate() {
        super.onCreate();
        CONTEXT=this;
        Bmob.initialize(this, "303fa54fc82a1ba90bf104de07594b15");//先初始化SDK//303fa54fc82a1ba90bf104de07594b15
        // 使用推送服务时的初始化操作
        BmobInstallation.getCurrentInstallation(this).save();
        // 启动接受服务器推送服务
        BmobPush.startWork(this);
      player=MediaPlayer.create(this, R.raw.chimes);



    }
}
