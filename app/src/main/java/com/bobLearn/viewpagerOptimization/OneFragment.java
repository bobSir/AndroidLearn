package com.bobLearn.viewpagerOptimization;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bobLearn.R;


/**
 * Create by cly on 18/10/16
 */
public class OneFragment extends BaseVpFragment {
    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.one_fragment_layou, container, false);
        return view;
    }

    @Override
    protected void onFragmentHind() {
        //当Fragment滑过前台时调用，可以做取消网络请求操作
    }

    @Override
    protected void loadData() {
        //当fragment滑至前台时调用，可以延迟请求数据(当快速划走时可在onFragmentHind取消请求)
    }
}
