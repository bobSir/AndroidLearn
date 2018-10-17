package bobLearn.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orhanobut.logger.Logger;

import learn.bob.com.androidlearn.R;

/**
 * Create by cly on 18/10/12
 */
public class AFragment extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.a_fragment_layout, container, false);
        initView(view);
        return view;
    }

    @Override
    public boolean onBackPressed(Fragment currentFragment) {
        if (currentFragment instanceof AFragment) {
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
                        .add(R.id.container, new BFragment())
                        .commitAllowingStateLoss();
            }
        });
    }
}
