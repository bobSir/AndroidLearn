package com.bobLearn;

import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.bobLearn.backFragment.BackConstance;
import com.bobLearn.backFragment.NavigationManager;


/**
 * created by cly on 2019-05-21
 */
public class BackTestOneFragment extends BaseFragment {
    private RelativeLayout mRootView;
    private Button mButton;

    @Override
    protected boolean iniTitleBar() {
        return false;
    }

    @Override
    protected void beforeInitView() {

    }

    @Override
    protected int layoutResID() {
        return R.layout.fragment_back_test_one;
    }

    @Override
    protected void findView(View view) {
        mRootView = view.findViewById(R.id.root_view);
        mButton = view.findViewById(R.id.btn_back_test);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavigationManager.startTestFragment(BackTestOneFragment.this);
            }
        });
    }

    @Override
    protected void initView() {
        mRootView.setBackgroundResource(BackConstance.getColor());
        mButton.setText("第" + BackConstance.getCount() + "个");
    }

    @Override
    public String fragmentTag() {
        return BackTestOneFragment.class.getSimpleName();
    }

    @Override
    public void onClick(View view) {

    }
}
