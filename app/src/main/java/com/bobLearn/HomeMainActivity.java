package com.bobLearn;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.bobLearn.aop.AOPDoubleClickActivity;
import com.bobLearn.audioWave.AudioWaveActivity;
import com.bobLearn.fragment.backPress.FragmentBackActivity;
import com.bobLearn.viewpagerOptimization.FragmentVpActivity;

public class HomeMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void aopDoubleClick(View view) {
        startActivity(new Intent(this, AOPDoubleClickActivity.class));
    }

    public void audioWave(View view) {
        startActivity(new Intent(this, AudioWaveActivity.class));
    }

    public void fragmentTest(View view) {
        startActivity(new Intent(this, FragmentBackActivity.class));
    }

    public void fragmentLazy(View view) {
        startActivity(new Intent(this, FragmentVpActivity.class));
    }
}
