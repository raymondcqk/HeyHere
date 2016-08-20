package com.raymondqk.raysqlitepractice.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.raymondqk.raysqlitepractice.R;

/**
 * Created by 陈其康 raymondchan on 2016/8/14 0014.
 */
public class CareFragment extends Fragment {

    private View mView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_care, null);
        return mView;
    }
}
