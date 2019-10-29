package com.bobLearn;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.bobLearn.activityBase.BaseLearnActivity;
import com.bobLearn.amui.QmiuiTestActivity;
import com.bobLearn.aop.AOPDoubleClickActivity;
import com.bobLearn.audioWave.AudioWaveActivity;
import com.bobLearn.backFragment.BackBackTestOneActivity;
import com.bobLearn.danmu.DanmuTestActivity;
import com.bobLearn.fragment.backPress.FragmentBackActivity;
import com.bobLearn.fragment.fragmentBase.BasisFragmentActivity;
import com.bobLearn.orm.OrmActivity;
import com.bobLearn.task.TaskTestActivity;
import com.bobLearn.threadpool.ThreadPoolActivity;
import com.bobLearn.util.FileUtil;
import com.bobLearn.viewpagerOptimization.FragmentVpActivity;
import com.bobLearn.x5.BrowserActivity;
import com.bobLearn.x5.FilechooserActivity;
import com.bobLearn.x5.FullScreenActivity;
import com.bobLearn.x5.X5TestActivity;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

public class HomeMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Logger.d("@handler 1");
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                Logger.d("@handler 2");
            }
        });

        Logger.d("@handler 3");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Logger.d("@handler 4");
            }
        }, 1_000);
        Logger.d("@handler 5");
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


    public void jsonTest(View view) {
        String json = FileUtil.loadAssets("json_test", this);
        new Gson().fromJson(json, Test.class);
    }

    public class Test {
        String a;
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

    public void moveTask(View view) {
        Intent intent = new Intent(this, TaskTestActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    public void orm(View view) {
        startActivity(new Intent(this, OrmActivity.class));
    }

    public void backFragment(View view) {
        startActivity(new Intent(this, BackBackTestOneActivity.class));
    }

    public void qmui(View view) {
        startActivity(new Intent(this, QmiuiTestActivity.class));
    }
}
