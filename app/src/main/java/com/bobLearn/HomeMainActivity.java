package com.bobLearn;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.bobLearn.activityBase.BaseLearnActivity;
import com.bobLearn.aop.AOPDoubleClickActivity;
import com.bobLearn.audioWave.AudioWaveActivity;
import com.bobLearn.fragment.backPress.FragmentBackActivity;
import com.bobLearn.fragment.fragmentBase.BasisFragmentActivity;
import com.bobLearn.viewpagerOptimization.FragmentVpActivity;
import com.orhanobut.logger.Logger;

public class HomeMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onPause() {
        Logger.d("[Main ac] onPause Begin");
        super.onPause();
        Logger.d("[Main ac] onPause End");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Logger.d("[Main ac] onDestroy");
    }

    public void activityClick(View view) {
        startActivity(new Intent(this, BaseLearnActivity.class));
    }

    public void aopDoubleClick(View view) {
        startActivity(new Intent(this, AOPDoubleClickActivity.class));
    }

    public void audioWave(View view) {
        startActivity(new Intent(this, AudioWaveActivity.class));
    }

    public void fragmentBack(View view) {
        startActivity(new Intent(this, FragmentBackActivity.class));
    }

    public void fragmentLazy(View view) {
        startActivity(new Intent(this, FragmentVpActivity.class));
    }

    public void fragmentBasis(View view) {
        startActivity(new Intent(this, BasisFragmentActivity.class));
    }
}
