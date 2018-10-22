package com.bobLearn.fragment.backPress;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.bobLearn.R;
import com.orhanobut.logger.Logger;


/**
 * 单个Activity 多个fragment 测试
 * fragment 实现onBackPress
 *
 * @author cly
 */
public class FragmentBackActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super .onCreate(savedInstanceState);
        Logger.d("Activity onCreate");
        setContentView(R.layout.activity_fragment_learn);
        BackFragmentA aFragment = new BackFragmentA();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, aFragment)
                .commitAllowingStateLoss();
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
    }

    @Override
    public void onBackPressed() {
        if (!BackHandlerHelper.handleBackPress(this, R.id.container)) {
            super.onBackPressed();
        }
    }
}
