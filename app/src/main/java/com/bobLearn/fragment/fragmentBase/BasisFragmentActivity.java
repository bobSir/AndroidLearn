package com.bobLearn.fragment.fragmentBase;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.bobLearn.R;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;

public class BasisFragmentActivity extends AppCompatActivity {
    private AFragment mAFragment;
    private BFragment mBFragment;
    private CFragment mCFragment;

    @Override
    public void onAttachFragment(Fragment fragment) {
        Logger.d("activity [onAttachFragment] begin");
        super.onAttachFragment(fragment);
        Logger.d("activity [onAttachFragment] end");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Logger.d("activity [onCreate] begin");
        super.onCreate(savedInstanceState);
        Logger.d("activity [onCreate] end");
        setContentView(R.layout.activity_basis_fragment);
        initAFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, mAFragment, AFragment.TAG)
                .commit();
    }

    private void initAFragment() {
        mAFragment = new AFragment();
        mAFragment.setJumpBFOnClickListener(new AFragment.onJumpBFOnClickListener() {
            @Override
            public void onJumpBClick() {
                initBFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//                if (mBFragment.isAdded()) {
//                    fragmentTransaction.hide(mAFragment)
//                            .show(mBFragment)
//                            .commit();
//                } else {
                fragmentTransaction
                        .addToBackStack(BFragment.TAG)
                        .replace(R.id.container, mBFragment, BFragment.TAG)
//                        .hide(mAFragment)
                        .commit();
            }
//            }
        });
    }

    private void initBFragment() {
        if (mBFragment == null) {
            mBFragment = new BFragment();
            mBFragment.setOnBFEventListener(new BFragment.OnBFEventListener() {
                @Override
                public void onShowAClick() {
//                getSupportFragmentManager().beginTransaction()
//                        .show(mAFragment)
//                        .hide(mBFragment)
//                        .commit();
                }

                @Override
                public void onShowCClick() {
                    initCFragment();
                    getSupportFragmentManager().beginTransaction()
                            .addToBackStack(CFragment.TAG)
                            .replace(R.id.container, mCFragment)
                            .commit();
                }
            });
        }
    }

    private void initCFragment() {
        if (mCFragment == null) {
            mCFragment = new CFragment();
            mCFragment.setOnCFEventClickListener(new CFragment.OnCFEventClickListener() {
                @Override
                public void onCEventClick() {
                    getSupportFragmentManager().popBackStack(BFragment.TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                }
            });
        }
    }

    @Override
    protected void onStart() {
        Logger.d("activity [onStart] begin");
        super.onStart();
        Logger.d("activity [onStart] end");
    }

    @Override
    protected void onResume() {
        Logger.d("activity [onResume] begin");
        super.onResume();
        Logger.d("activity [onResume] end");
    }

    @Override
    protected void onPause() {
        Logger.d("activity [onPause] begin");
        super.onPause();
        Logger.d("activity [onPause] end");
    }
}
