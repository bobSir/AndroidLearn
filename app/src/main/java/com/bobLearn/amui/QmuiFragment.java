package com.bobLearn.amui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bobLearn.R;
import com.bobLearn.util.AndroidBug5497Workaround;

/**
 * created by cly on 2019-06-17
 */
public class QmuiFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qmui_test, container, false);
        ViewGroup viewGroup = view.findViewById(R.id.rl_root_view);
        AndroidBug5497Workaround.assistView(viewGroup);
        return view;
    }
}
