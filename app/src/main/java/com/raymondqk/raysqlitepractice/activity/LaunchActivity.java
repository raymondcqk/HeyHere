package com.raymondqk.raysqlitepractice.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.raymondqk.raysqlitepractice.R;
import com.raymondqk.raysqlitepractice.utils.db.DBHelper;
import com.raymondqk.raysqlitepractice.utils.Constant;
import com.raymondqk.raysqlitepractice.utils.SharedPreferenceUtils;

/**
 * Created by 陈其康 raymondchan on 2016/8/13 0013.
 */
public class LaunchActivity extends Activity {


    private Handler mHandler;
    private int mCount = 2;
    private TextView mTv_count;
    private SharedPreferenceUtils mSP_utils;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        mTv_count = (TextView) findViewById(R.id.tv_launch_count);
        //初始化DBHelper实例以及SQLitedatabase实例
        DBHelper dbHelper = DBHelper.getDBHelperInstance(this);
        mSP_utils = new SharedPreferenceUtils(this);

        mHandler = new Handler();
        count();

    }

    private void count() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mCount == 0) {
                    Intent i = new Intent();
                    if (mSP_utils.getBoolData(Constant.SP_KEY_FIRST_LAUNCH)) {
                        i.setClass(LaunchActivity.this, GuideActivity.class);
                    } else {
                        i.setClass(LaunchActivity.this, LoginActivity.class);
                    }
                    startActivity(i);
                    finish();
                } else {
                    mCount--;
                    mTv_count.setText((mCount + 1) + "");
                    count();
                }
            }
        }, 1000);
    }
}
