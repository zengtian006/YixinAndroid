package com.tim.yixin;

import android.app.Application;

import com.appkefu.lib.interfaces.KFAPIs;

import io.realm.Realm;

/**
 * Created by Zeng on 2017/8/16.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        KFAPIs.DEBUG = false;
        //第一个参数默认设置为false, 即登录普通服务器, 如果设置为true, 则登录IP服务器,
        //注意: 当第一个参数设置为true的时候, 客服端需要选择登录ip服务器 才能够会话
        //正常情况下第一个参数请设置为false
        KFAPIs.enableIPServerMode(false, this);
        //第一种登录方式，推荐
        KFAPIs.visitorLogin(this);
    }
}
