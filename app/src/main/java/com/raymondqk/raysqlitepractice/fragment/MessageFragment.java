package com.raymondqk.raysqlitepractice.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.raymondqk.raysqlitepractice.R;
import com.raymondqk.raysqlitepractice.activity.ContactActivity;
import com.raymondqk.raysqlitepractice.activity.MainActivity;
import com.raymondqk.raysqlitepractice.activity.WeatherActivity;

/**
 * Created by 陈其康 raymondchan on 2016/8/14 0014.
 */
public class MessageFragment extends Fragment implements View.OnClickListener {

    private View mView;
    private MainActivity mMainActivity;
    private LinearLayout mLl_contact;
    private LinearLayout mLl_notification;
    private RelativeLayout mLl_weather;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mMainActivity = (MainActivity) activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_message, null);
        mLl_contact = (LinearLayout) mView.findViewById(R.id.layout_message_contact);
        mLl_contact.setOnClickListener(this);
        mLl_notification = (LinearLayout) mView.findViewById(R.id.layout_message_notification);
        mLl_notification.setOnClickListener(this);

        mLl_weather = (RelativeLayout) mView.findViewById(R.id.layout_message_weather);
        mLl_weather.setOnClickListener(this);

        return mView;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.layout_message_contact:
                intent.setClass(mMainActivity, ContactActivity.class);
                startActivity(intent);
                break;
            case R.id.layout_message_notification:
                break;
            case R.id.layout_message_weather:
                intent.setClass(mMainActivity, WeatherActivity.class);
                startActivity(intent);
                break;
        }

    }
}
