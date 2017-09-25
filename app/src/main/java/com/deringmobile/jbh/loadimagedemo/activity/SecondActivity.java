package com.deringmobile.jbh.loadimagedemo.activity;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.MessageQueue;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.deringmobile.jbh.loadimagedemo.R;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);




        handler();

    }

    private void handler() {
        MessageQueue queue=Looper.myQueue();
        Handler handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
            }
        };
        Message m=Message.obtain();
        m.what=121;
        m.obj="dasdfas";
        handler.sendMessage(m);

        Looper.prepare();

        Looper.loop();

    }
}
