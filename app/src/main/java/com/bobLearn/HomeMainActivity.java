package com.bobLearn;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.bobLearn.activityBase.BaseLearnActivity;
import com.bobLearn.aop.AOPDoubleClickActivity;
import com.bobLearn.audioWave.AudioWaveActivity;
import com.bobLearn.danmu.DanmuTestActivity;
import com.bobLearn.fragment.backPress.FragmentBackActivity;
import com.bobLearn.fragment.fragmentBase.BasisFragmentActivity;
import com.bobLearn.threadpool.ThreadPoolActivity;
import com.bobLearn.viewpagerOptimization.FragmentVpActivity;
import com.bobLearn.x5.BrowserActivity;
import com.bobLearn.x5.FilechooserActivity;
import com.bobLearn.x5.FullScreenActivity;
import com.bobLearn.x5.X5TestActivity;
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

    public void danmu(View view) {
        startActivity(new Intent(this, DanmuTestActivity.class));
    }

    public void threadPool(View view) {
        startActivity(new Intent(this, ThreadPoolActivity.class));
    }

    public void xFive(View view) {
        startActivity(new Intent(this, BrowserActivity.class));
    }

    public void fileChooser(View view) {
        startActivity(new Intent(this, FilechooserActivity.class));
    }

    public void fullScreen(View view) {
        startActivity(new Intent(this, FullScreenActivity.class));
    }

    public void x5Test(View view) {
        startActivity(new Intent(this, X5TestActivity.class));
    }
}
