package bobLearn.viewpagerOptimization;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import learn.bob.com.androidlearn.R;

/**
 * Create by cly on 18/10/16
 */
public class TwoFragment extends BaseVpFragment {

    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.two_fragment_layou, container, false);
        return view;
    }

    @Override
    protected void onFragmentHind() {

    }

    @Override
    protected void loadData() {

    }
}
