package com.raymondqk.raysqlitepractice.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.raymondqk.raysqlitepractice.R;
import com.raymondqk.raysqlitepractice.fragment.cityselect.CityFragment;
import com.raymondqk.raysqlitepractice.fragment.cityselect.ProvinceFragment;
import com.raymondqk.raysqlitepractice.interfaces.LocationSelectedCallback;
import com.raymondqk.raysqlitepractice.model.weather.CityInfo;
import com.raymondqk.raysqlitepractice.utils.JSONUtils;
import com.raymondqk.raysqlitepractice.utils.internet.WeatherUtils;
import com.raymondqk.raysqlitepractice.utils.db.DBHelper;
import com.raymondqk.raysqlitepractice.utils.internet.HttpCallback;
import com.raymondqk.raysqlitepractice.utils.internet.InternetUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by 陈其康 raymondchan on 2016/8/22 0022.
 */
public class SelectCityActivity extends AppCompatActivity {

    public static final int MSG_WHAT_PROV = 0;
    public static final String KEY_WEATHER_CITY_LIST = "city_list";
    public static final String KEY_WEATHER_PROVINCE_LIST = "province_list";
    public static final int FRAGMENT_PROVINCE = 1;
    public static final int FRAGMENT_CITY = 2;
    public static final String SELECTED_CITY = "selected_city";
    private ProvinceFragment mProvinceFragment;
    private DBHelper mDbHelper;
    private Set<String> mSet_prov;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_WHAT_PROV:
                    mProvinceFragment.initListView(SelectCityActivity.this, new ArrayList<String>(mSet_prov));
                    break;

            }
        }
    };

    LocationSelectedCallback mProvSelectedCallback = new LocationSelectedCallback() {
        @Override
        public void onInitView() {
            setProvinceList();
        }

        @Override
        public void onSelected(String id) {
            mFt = mFragmentManager.beginTransaction();
            mFt.hide(mProvinceFragment);
            mFt.show(mCityFragment);
            mFt.commit();
            mCurrentFragment = FRAGMENT_CITY;
            mCityFragment.initListView(SelectCityActivity.this, id);
            mBar.setTitle("请选择城市");
            mBar.setSubtitle("当前省份：" + id);

        }
    };
    LocationSelectedCallback mCitySelectedCallback = new LocationSelectedCallback() {
        @Override
        public void onInitView() {

        }

        @Override
        public void onSelected(String id) {
            Intent intent = new Intent();
            intent.putExtra(SELECTED_CITY, id);
            setResult(WeatherActivity.RESULT_CODE_SELECT_CITY, intent);
            finish();
        }
    };
    private CityFragment mCityFragment;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFt;
    private int mCurrentFragment;
    private ActionBar mBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_city);
        initToolBar();
        mDbHelper = DBHelper.getDBHelperInstance(SelectCityActivity.this);
        initFragment();


    }

    private void initToolBar() {
        mBar = getSupportActionBar();
        mBar.setTitle("请选择省份");
    }

    private void initFragment() {
        mProvinceFragment = ProvinceFragment.getInstance(this, mProvSelectedCallback);
        mCityFragment = CityFragment.getInstance(mCitySelectedCallback);
        mFragmentManager = getFragmentManager();
        mFt = mFragmentManager.beginTransaction();
        mFt.add(R.id.frame_weather_city_select, mProvinceFragment);
        mFt.add(R.id.frame_weather_city_select, mCityFragment);
        mFt.hide(mCityFragment);
        mFt.commit();
        mCurrentFragment = FRAGMENT_PROVINCE;

    }


    private void setProvinceList() {
    /*从网络获取城市信息，存入数据，读出省份信息*/

        //先检测本地数据库是否有城市信息，若有，则从数据库获取，否则通过网络获取
        // TODO: 2016/8/22 0022 后期可以在程序退出时，清空掉数据库的城市缓存，当重新打开app时重新获取
        if ((mSet_prov = mDbHelper.queryProvince()).size() == 0) {
            if (InternetUtils.checkConnected(SelectCityActivity.this)) {
                //读取城市列表的JSON数据
                WeatherUtils.getCityListJson(WeatherUtils.CITY_LIST_ALL_CHINA, new HttpCallback() {
                    @Override
                    public void onFinish(String respones) {
                        parseJson(respones);

                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });
            }
        } else {
            Message msg = Message.obtain();
            msg.what = MSG_WHAT_PROV;
            mHandler.sendMessage(msg);
        }
    }

    private void parseJson(String respones) {
        //返回json数据字符串，进行解析
        List<CityInfo> list = JSONUtils.parseCityListFromJSONWithGSON(respones);
        //解析得到城市对象列表，存入数据库
        if (mDbHelper.initCityTable(list)) {
            mSet_prov = mDbHelper.queryProvince();
            Message msg = Message.obtain();
            msg.what = MSG_WHAT_PROV;
            mHandler.sendMessage(msg);
        }
    }

    @Override
    public void onBackPressed() {
        if (mCurrentFragment == FRAGMENT_CITY) {
            mFt = mFragmentManager.beginTransaction();
            mFt.hide(mCityFragment);
            mFt.show(mProvinceFragment);
            mFt.commit();
            mBar.setSubtitle("");
            mBar.setTitle("请选择省份");
            mCurrentFragment = FRAGMENT_PROVINCE;
        } else if (mCurrentFragment == FRAGMENT_PROVINCE) {
            setResult(WeatherActivity.RESULT_CODE_SELECT_CITY);
            finish();
        }

    }
}
