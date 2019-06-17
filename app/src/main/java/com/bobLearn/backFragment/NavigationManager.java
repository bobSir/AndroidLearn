package com.bobLearn.backFragment;

import android.text.TextUtils;

import com.bobLearn.BackTestOneFragment;
import com.bobLearn.BaseFragment;
import com.bobLearn.R;
import com.bobLearn.backlayout.BackBackTestOneFragment;
import com.bobLearn.backlayout.BaseBackFragment;

/**
 * created by cly on 2019-05-21
 */
public class NavigationManager {
    public static void startTestFragment(BaseFragment baseFragment) {
        BackTestOneFragment backTestOneFragment = new BackTestOneFragment();
        start(baseFragment, backTestOneFragment);
    }

    public static void startTestBackFragment(BaseBackFragment baseBackFragment) {
        BackBackTestOneFragment backTestOneFragment = new BackBackTestOneFragment();
        startFragment(baseBackFragment, backTestOneFragment);
    }

    public static void startFragment(BaseBackFragment currentFragment, BaseBackFragment toFragment) {
        if (TextUtils.isEmpty(toFragment.fragmentTag())) {
            throw new NullPointerException("fragment TAG must be NOT NULL!!!");
        }
        BaseBackFragment.TransitionConfig transitionConfig = toFragment.onFetchTransitionConfig();
        currentFragment.getActivity().getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(transitionConfig.enter, transitionConfig.exit,
                        transitionConfig.popenter, transitionConfig.popout)
                .replace(R.id.fragment_container_id_mask, toFragment, toFragment.fragmentTag())
                .addToBackStack(toFragment.fragmentTag())
                .commitAllowingStateLoss();
    }


    private static void start(BaseFragment currentFragment, BaseFragment toFragment) {
        if (TextUtils.isEmpty(toFragment.fragmentTag())) {
            throw new NullPointerException("fragment TAG must be NOT NULL!!!");
        }
        currentFragment.getActivity().getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.mp_slide_in_right, R.anim.mp_slide_out_left,
                        R.anim.mp_slide_in_left, R.anim.mp_slide_out_right)
                .hide(currentFragment)
                .add(R.id.fragment_container_id_mask, toFragment, toFragment.fragmentTag())
                .addToBackStack(toFragment.fragmentTag())
                .commitAllowingStateLoss();
    }
}
