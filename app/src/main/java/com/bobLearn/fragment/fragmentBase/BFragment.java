package com.bobLearn.fragment.fragmentBase;

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
 * Create by cly on 18/10/19
 */
public class BFragment extends Fragment {
    public static final String TAG = BFragment.class.getSimpleName();

    private OnBFEventListener mOnBFEventListener;

    public void setOnBFEventListener(OnBFEventListener onBFEventListener) {
        mOnBFEventListener = onBFEventListener;
    }

    @Override
    public void onAttach(Context context) {
        Logger.d("BF [onAttach] BEGIN");
        super.onAttach(context);
        Logger.d("BF [onAttach] END");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Logger.d("BF [onCreate] BEGIN");
        super.onCreate(savedInstanceState);
        Logger.d("BF [onCreate] END");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.b_basis_fragment_layout, container, false);
        view.findViewById(R.id.btn_show).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnBFEventListener != null) mOnBFEventListener.onShowAClick();
            }
        });
        view.findViewById(R.id.btn_c).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnBFEventListener != null) mOnBFEventListener.onShowCClick();
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Logger.d("BF [onActivityCreated] BEGIN");
        super.onActivityCreated(savedInstanceState);
        Logger.d("BF [onActivityCreated] END");
    }

    @Override
    public void onStart() {
        Logger.d("BF [onStart] BEGIN");
        super.onStart();
        Logger.d("BF [onStart] END");
    }

    @Override
    public void onResume() {
        Logger.d("BF [onResume] BEGIN");
        super.onResume();
        Logger.d("BF [onResume] END");
    }

    @Override
    public void onPause() {
        Logger.d("BF [onPause] BEGIN");
        super.onPause();
        Logger.d("BF [onPause] END");
    }

    @Override
    public void onStop() {
        Logger.d("BF [onStop] BEGIN");
        super.onStop();
        Logger.d("BF [onStop] END");
    }

    public interface OnBFEventListener {
        void onShowAClick();

        void onShowCClick();
    }
}
