package com.bobLearn.backFragment;

import com.bobLearn.backlayout.BackBackTestOneFragment;
import com.bobLearn.backlayout.BaseBackFragment;

/**
 * created by cly on 2019-05-22
 */
public class BackBackTestOneActivity extends BaseBackActivity {
    @Override
    protected BaseBackFragment initFragment() {
        return new BackBackTestOneFragment();
    }
}
