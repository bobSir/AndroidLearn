package com.bobLearn.threadpool;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.bobLearn.R;
import com.orhanobut.logger.Logger;

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
                Logger.d("@thread", "runnable start");
            }
        };
        Logger.d("@thread", "runnable hashCode = " + runnable.hashCode());
        FundWXThreadFactory.newThread().start(runnable);
    }
}
