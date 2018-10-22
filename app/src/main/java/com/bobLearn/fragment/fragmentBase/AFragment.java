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
public class AFragment extends Fragment {
    public static final String TAG = AFragment.class.getSimpleName();

    private onJumpBFOnClickListener mJumpBFOnClickListener;

    public void setJumpBFOnClickListener(onJumpBFOnClickListener jumpBFOnClickListener) {
        mJumpBFOnClickListener = jumpBFOnClickListener;
    }

    @Override
    public void onAttach(Context context) {
        Logger.d("AF [onAttach] begin");
        super.onAttach(context);
        Logger.d("AF [onAttach] end");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Logger.d("AF [onCreate] begin");
        super.onCreate(savedInstanceState);
        Logger.d("AF [onCreate] end");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Logger.d("AF [onCreateView]");
        View view = inflater.inflate(R.layout.a_basis_fragment_layout, container, false);
        view.findViewById(R.id.btn_jump).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mJumpBFOnClickListener != null)
                    mJumpBFOnClickListener.onJumpBClick();
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Logger.d("AF [onActivityCreated] begin");
        super.onActivityCreated(savedInstanceState);
        Logger.d("AF [onActivityCreated] end");
    }

    @Override
    public void onStart() {
        Logger.d("AF [onStart] begin");
        super.onStart();
        Logger.d("AF [onStart] end");
    }

    @Override
    public void onResume() {
        Logger.d("AF [onResume] begin");
        super.onResume();
        Logger.d("AF [onResume] end");
    }

    @Override
    public void onPause() {
        Logger.d("AF [onPause] begin");
        super.onPause();
        Logger.d("AF [onPause] end");
    }

    @Override
    public void onStop() {
        Logger.d("AF [onStop] begin");
        super.onStop();
        Logger.d("AF [onStop] end");
    }

    @Override
    public void onDestroyView() {
        Logger.d("AF [onDestroyView] begin");
        super.onDestroyView();
        Logger.d("AF [onDestroyView] end");
    }

    @Override
    public void onDestroy() {
        Logger.d("AF [onDestroy] begin");
        super.onDestroy();
        Logger.d("AF [onDestroy] end");
    }

    @Override
    public void onDetach() {
        Logger.d("AF [onDetach] begin");
        super.onDetach();
        Logger.d("AF [onDetach] end");
    }

    public interface onJumpBFOnClickListener {
        void onJumpBClick();
    }
}
