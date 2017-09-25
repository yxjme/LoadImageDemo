package com.deringmobile.jbh.loadimagedemo.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import android.support.annotation.Nullable;

import com.deringmobile.jbh.loadimagedemo.util.CallBack;
import com.deringmobile.jbh.loadimagedemo.util.LogUtil;


/**
 * Created by zbsdata on 2017/8/30.
 */

public class MyService extends Service {

    private int a = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.showLogV("======onCreate==","服务已经开启了");
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        MyBinder b=null;
        if(b==null){
            b=new MyBinder();
        }
        return b;
    }

    public String name="adfasd";
    public class MyBinder extends Binder{
        public MyService getService() {
            return new  MyService();
        }
    }


    public void fun(final CallBack<Integer> callBack){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    a++;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    callBack.result(a);
                }
            }
        }).start();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        stopSelf();
    }
}
