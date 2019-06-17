package com.bobLearn.backFragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.bobLearn.R;
import com.bobLearn.backlayout.BaseBackFragment;
import com.bobLearn.backlayout.QMUIFragment;
import com.bobLearn.backlayout.Utils;

/**
 * created by cly on 2019-05-22
 */
public abstract class BaseBackActivity extends AppCompatActivity {
    private FrameLayout mFragmentContainer;

    public static int getFragmentContainerId() {
        return R.id.fragment_container_id_mask;
    }

    public FrameLayout getContainerLayout() {
        return mFragmentContainer;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        initFullScreen();
        super.onCreate(savedInstanceState);
        mFragmentContainer = new FrameLayout(this);
        mFragmentContainer.setId(getFragmentContainerId());
        setContentView(mFragmentContainer);

        BaseBackFragment fragment = initFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.mp_slide_in_right, R.anim.mp_slide_out_left,
                R.anim.mp_slide_in_left, R.anim.mp_slide_out_right);
        transaction.add(getFragmentContainerId(), fragment, fragment.fragmentTag())
                .addToBackStack(fragment.fragmentTag())
                .commitAllowingStateLoss();
    }

    @Override
    public void onBackPressed() {
        QMUIFragment fragment = getCurrentFragment();
        if (fragment != null && !fragment.isInSwipeBack()) {
            fragment.onBackPressed();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        QMUIFragment fragment = getCurrentFragment();
        if (fragment != null && !fragment.isInSwipeBack() && fragment.onKeyDown(keyCode, event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        QMUIFragment fragment = getCurrentFragment();
        if (fragment != null && !fragment.isInSwipeBack() && fragment.onKeyUp(keyCode, event)) {
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

    private void initFullScreen() {
        int uiFlags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
        getWindow().getDecorView().setSystemUiVisibility(uiFlags);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    /**
     * Exit the current Fragment。
     */
    public void popBackStack() {
        Log.i("@cly:", "popBackStack: getSupportFragmentManager().getBackStackEntryCount() = " + getSupportFragmentManager().getBackStackEntryCount());
        if (getSupportFragmentManager().getBackStackEntryCount() <= 1) {
            QMUIFragment fragment = getCurrentFragment();
            if (fragment == null) {
                finish();
                return;
            }
            QMUIFragment.TransitionConfig transitionConfig = fragment.onFetchTransitionConfig();
            Object toExec = fragment.onLastFragmentFinish();
            if (toExec != null) {
                if (toExec instanceof QMUIFragment) {
                    QMUIFragment mFragment = (QMUIFragment) toExec;
//                    startFragment(mFragment);
                } else if (toExec instanceof Intent) {
                    Intent intent = (Intent) toExec;
                    startActivity(intent);
                    overridePendingTransition(transitionConfig.popenter, transitionConfig.popout);
                    finish();
                } else {
                    throw new Error("can not handle the result in onLastFragmentFinish");
                }
            } else {
                finish();
                overridePendingTransition(transitionConfig.popenter, transitionConfig.popout);
            }
        } else {
            getSupportFragmentManager().popBackStackImmediate();
        }
    }

    /**
     * get the current Fragment.
     */
    public QMUIFragment getCurrentFragment() {
        return (QMUIFragment) getSupportFragmentManager().findFragmentById(getFragmentContainerId());
    }

    protected abstract BaseBackFragment initFragment();

    private static int NO_REQUESTED_ORIENTATION_SET = -100;
    private boolean mConvertToTranslucentCauseOrientationChanged = false;
    private int mPendingRequestedOrientation = NO_REQUESTED_ORIENTATION_SET;

    public void convertToTranslucentCauseOrientationChanged() {
        Utils.convertActivityToTranslucent(this);
        mConvertToTranslucentCauseOrientationChanged = true;
    }

    @Override
    public void setRequestedOrientation(int requestedOrientation) {
        if (mConvertToTranslucentCauseOrientationChanged && (Build.VERSION.SDK_INT == Build.VERSION_CODES.O
                || Build.VERSION.SDK_INT == Build.VERSION_CODES.O_MR1)) {
            Log.i("InnerBaseActivity", "setRequestedOrientation when activity is translucent");
            mPendingRequestedOrientation = requestedOrientation;
        } else {
            super.setRequestedOrientation(requestedOrientation);
        }

    }

    @SuppressLint("WrongConstant")
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (mConvertToTranslucentCauseOrientationChanged) {
            mConvertToTranslucentCauseOrientationChanged = false;
            Utils.convertActivityFromTranslucent(this);
            if (mPendingRequestedOrientation != NO_REQUESTED_ORIENTATION_SET) {
                super.setRequestedOrientation(mPendingRequestedOrientation);
                mPendingRequestedOrientation = NO_REQUESTED_ORIENTATION_SET;
            }
        }
    }
}
