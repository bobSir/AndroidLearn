package com.bobLearn.fragment.backPress;

import android.support.v4.app.Fragment;

/**
 * Create by cly on 18/10/12
 */
public class BaseBackFragment extends Fragment implements FragmentBackHandler {
    @Override
    public boolean onBackPressed(Fragment currentFragment) {
        return false;
    }
}
