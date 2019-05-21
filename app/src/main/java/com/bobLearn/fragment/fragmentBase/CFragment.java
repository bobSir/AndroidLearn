package com.bobLearn.fragment.fragmentBase;

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
 * Create by cly on 18/10/22
 */
public class CFragment extends Fragment {
    public static final String TAG = CFragment.class.getSimpleName();

    private OnCFEventClickListener mOnCFEventClickListener;

    public void setOnCFEventClickListener(OnCFEventClickListener onCFEventClickListener) {
        mOnCFEventClickListener = onCFEventClickListener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.c_basis_fragment_layout, container, false);
        view.findViewById(R.id.btn_a).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnCFEventClickListener != null) mOnCFEventClickListener.onCEventClick();
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        Logger.d("CF [onResume] BEGIN");
        super.onResume();
        Logger.d("CF [onResume] END");
    }

    public interface OnCFEventClickListener {
        void onCEventClick();
    }
}
