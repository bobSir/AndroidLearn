package com.bobLearn.threadpool;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.bobLearn.R;
import com.orhanobut.logger.Logger;

import java.util.concurrent.Callable;

public class ThreadPoolActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_pool);
    }

    public void textOne(View view) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Logger.d("runnable start");
            }
        };
        Logger.d("runnable hashCode = " + runnable.hashCode());
        FundWXThreadFactory.newThread().start(runnable);
        FundWXThreadFactory.newThread(FundWXThreadPriority.IMMEDIATE).start(new Runnable() {
            @Override
            public void run() {
                Logger.d("thread immediate");
            }
        });

        FundWXThreadFactory.newThread(FundWXThreadPriority.IMMEDIATE).start(
                new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        return "111111";
                    }
                },
                new Handler.Callback() {
                    @Override
                    public boolean handleMessage(Message msg) {
                        Logger.d(msg);
                        return false;
                    }
                });
    }
}
