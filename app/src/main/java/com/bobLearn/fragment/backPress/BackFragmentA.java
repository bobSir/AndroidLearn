package com.bobLearn.fragment.backPress;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bobLearn.R;
import com.orhanobut.logger.Logger;


/**
 * Create by cly on 18/10/12
 */
public class BackFragmentA extends BaseBackFragment {
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Logger.d("fragment onAttach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.d("fragment onCreate");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Logger.d("fragment onActivityCreate");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.a_fragment_layout, container, false);
        initView(view);
        return view;
    }

    @Override
    public boolean onBackPressed(Fragment currentFragment) {
        if (currentFragment instanceof BackFragmentA) {
            Logger.d("A onback");
            return true;
        }
        return super.onBackPressed(currentFragment);
    }

    private void initView(View view) {
        view.findViewById(R.id.btn_b).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assert getFragmentManager() != null;
                getFragmentManager().beginTransaction()
                        .addToBackStack(null)
                        .add(R.id.container, new BBackFragment())
                        .commitAllowingStateLoss();
            }
        });
    }
}
