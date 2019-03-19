package com.bobLearn.service;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.bobLearn.R;

import java.util.HashMap;

public class ServiceActivity extends AppCompatActivity {
    private ServiceOne.MyBinder mBinder;
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mBinder = (ServiceOne.MyBinder) iBinder;
            mBinder.start();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
    }

    public void s1(View view) {
        startService(new Intent(this, ServiceOne.class));

//        stopService(new Intent(this,ServiceOne.class));
    }

    public void s2(View view) {
        Intent bindIntent = new Intent(this, ServiceOne.class);
        bindService(bindIntent, mConnection, BIND_AUTO_CREATE);

//        unbindService(mConnection);
    }
}
