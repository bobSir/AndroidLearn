package com.bobLearn.fragment.backPress;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bobLearn.R;


/**
 * Create by cly on 18/10/12
 */
public class CBackFragment extends BaseBackFragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.c_fragment_layout, container, false);
    }

}
