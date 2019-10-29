package com.bobLearn.amui;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;

import com.bobLearn.R;
import com.bobLearn.util.AndroidBug5497Workaround;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;

public class QmiuiTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        QMUIStatusBarHelper.translucent(this);
        setContentView(R.layout.activity_qmiui_test);
        ViewGroup viewGroup = findViewById(R.id.rl_root_view);
//        AndroidBug5497Workaround.assistView(viewGroup);

        QmuiFragment qmuiFragment = new QmuiFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.rl_root_view, qmuiFragment)
                .commitAllowingStateLoss();
    }
}
