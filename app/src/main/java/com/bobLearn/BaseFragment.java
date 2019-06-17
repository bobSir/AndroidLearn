package com.bobLearn;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.bobLearn.backFragment.StatusBarUtil;


/**
 * created by cly on 2019/4/12
 */
public abstract class BaseFragment extends Fragment implements
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
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_base, null);
        setBaseView(view);
        findView(view);
        initView();
        return view;
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
