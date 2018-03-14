package com.mtyxp.hangzhoumtyxp.controller.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mtyxp.hangzhoumtyxp.R;

/**
 * Created by CaoZhF on 2017-11-15.
 */

public class PersonalInfoFragment extends Fragment {

    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.personalinfo_fragment, container, false);
        return view;
    }
}
