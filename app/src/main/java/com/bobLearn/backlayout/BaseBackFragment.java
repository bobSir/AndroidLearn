package com.bobLearn.backlayout;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.bobLearn.R;
import com.bobLearn.backFragment.StatusBarUtil;
import com.orhanobut.logger.Logger;

/**
 * created by cly on 2019-05-22
 */
public abstract class BaseBackFragment extends QMUIFragment implements
        View.OnClickListener {
    private ViewGroup mRootLayout;
    private View mStatusBar;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        beforeInitView();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.d("@life: [onCreate] -- " + this.hashCode());
    }

    @Override
    protected View onCreateView() {
        Logger.d("@life: [onCreateView] -- " + this.hashCode());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_base, null);
        setBaseView(view);
        findView(view);
        initView();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Logger.d("@life: [onStart] -- " + this.hashCode());
    }

    @Override
    public void onResume() {
        super.onResume();
        Logger.d("@life: [onResume] -- " + this.hashCode());
    }

    @Override
    public void onPause() {
        super.onPause();
        Logger.d("@life: [onPause] -- " + this.hashCode());
    }


    @Override
    public void onStop() {
        super.onStop();
        Logger.d("@life: [onStop] -- " + this.hashCode());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Logger.d("@life: [onDestroyView] -- " + this.hashCode());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Logger.d("@life: [onDestroy] -- " + this.hashCode());
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Logger.d("@life: [onDetach] -- " + this.hashCode());
    }

    @Override

    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void setBaseView(View view) {
        mStatusBar = view.findViewById(R.id.view_statusbar);
        mRootLayout = view.findViewById(R.id.base_content);
        initStatusBar();
        View contentLayout = LayoutInflater.from(getContext()).inflate(layoutResID(), null);
        mRootLayout.addView(contentLayout,
                new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        setBackgroundColor(Color.TRANSPARENT);
    }

    private void initStatusBar() {
        ViewGroup.LayoutParams layoutParams = mStatusBar.getLayoutParams();
        layoutParams.height = StatusBarUtil.getStatusBarHeight();
        mStatusBar.setLayoutParams(layoutParams);
        mStatusBar.setVisibility(View.VISIBLE);
    }

    private void setBackgroundColor(@ColorInt int color) {
        mRootLayout.setBackgroundColor(color);
    }

    public Bundle setResult() {
        return null;
    }

    public void onResult(Bundle bundle) {
    }

    protected abstract boolean iniTitleBar();

    protected abstract void beforeInitView();

    protected abstract int layoutResID();

    protected abstract void findView(View view);

    protected abstract void initView();

    public abstract String fragmentTag();
}
