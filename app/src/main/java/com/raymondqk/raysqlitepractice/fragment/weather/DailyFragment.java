package com.raymondqk.raysqlitepractice.fragment.weather;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.raymondqk.raysqlitepractice.R;
import com.raymondqk.raysqlitepractice.adapter.WeatherDailyPageAdapter;
import com.raymondqk.raysqlitepractice.interfaces.FragmentViewCallback;
import com.raymondqk.raysqlitepractice.model.weather.DailyForecast;
import com.raymondqk.raysqlitepractice.model.weather.WeatherInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 陈其康 raymondchan on 2016/8/23 0023.
 */
public class DailyFragment extends Fragment {


    public static final String DAILY = "daily";
    private List<View> mViews;
    private View mView;
    private static Context sContext;
    private static FragmentViewCallback sFragmentViewCallback;
    private ViewPager mViewPager;
    private TextView mDate_01;
    private TextView mDate_02;
    private TextView mDate_03;
    private TextView mDate_04;
    private TextView mDate_05;
    private TextView mDate_06;
    private TextView mClimate_01;
    private TextView mClimate_02;
    private TextView mClimate_03;
    private TextView mClimate_04;
    private TextView mClimate_05;
    private TextView mClimate_06;
    private TextView mTmp_01;
    private TextView mTmp_02;
    private TextView mTmp_03;
    private TextView mTmp_04;
    private TextView mTmp_05;
    private TextView mTmp_06;
    private TextView[] mDates;
    private TextView[] mClimates;
    private TextView[] mTmps;
    private WeatherDailyPageAdapter mAdapter;

    public static DailyFragment getInstance(Context context, FragmentViewCallback callback) {
        sContext = context;
        sFragmentViewCallback = callback;
        return new DailyFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_daily, null);
        mViews = new ArrayList<View>();
        int[] layouts = new int[]{R.layout.weather_daily_page_01, R.layout.weather_daily_page_02};
        mViews.add(inflater.inflate(layouts[0], null));
        mViews.add(inflater.inflate(layouts[1], null));
        mViewPager = (ViewPager) mView.findViewById(R.id.vp_weather_daily);
        mDate_01 = (TextView) mViews.get(0).findViewById(R.id.tv_weather_daily_date1);
        mDate_02 = (TextView) mViews.get(0).findViewById(R.id.tv_weather_daily_date2);
        mDate_03 = (TextView) mViews.get(0).findViewById(R.id.tv_weather_daily_date3);
        mDate_04 = (TextView) mViews.get(1).findViewById(R.id.tv_weather_daily_date4);
        mDate_05 = (TextView) mViews.get(1).findViewById(R.id.tv_weather_daily_date5);
        mDate_06 = (TextView) mViews.get(1).findViewById(R.id.tv_weather_daily_date6);

        mDates = new TextView[]{mDate_01, mDate_02, mDate_03, mDate_04, mDate_05, mDate_06};

        mClimate_01 = (TextView) mViews.get(0).findViewById(R.id.tv_weather_daily_climate1);
        mClimate_02 = (TextView) mViews.get(0).findViewById(R.id.tv_weather_daily_climate2);
        mClimate_03 = (TextView) mViews.get(0).findViewById(R.id.tv_weather_daily_climate3);
        mClimate_04 = (TextView) mViews.get(1).findViewById(R.id.tv_weather_daily_climate4);
        mClimate_05 = (TextView) mViews.get(1).findViewById(R.id.tv_weather_daily_climate5);
        mClimate_06 = (TextView) mViews.get(1).findViewById(R.id.tv_weather_daily_climate6);

        mClimates = new TextView[]{mClimate_01, mClimate_02, mClimate_03, mClimate_04, mClimate_05, mClimate_06};

        mTmp_01 = (TextView) mViews.get(0).findViewById(R.id.tv_weather_daily_tmp1);
        mTmp_02 = (TextView) mViews.get(0).findViewById(R.id.tv_weather_daily_tmp2);
        mTmp_03 = (TextView) mViews.get(0).findViewById(R.id.tv_weather_daily_tmp3);
        mTmp_04 = (TextView) mViews.get(1).findViewById(R.id.tv_weather_daily_tmp4);
        mTmp_05 = (TextView) mViews.get(1).findViewById(R.id.tv_weather_daily_tmp5);
        mTmp_06 = (TextView) mViews.get(1).findViewById(R.id.tv_weather_daily_tmp6);

        mTmps = new TextView[]{mTmp_01, mTmp_02, mTmp_03, mTmp_04, mTmp_05, mTmp_06};

        return mView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sFragmentViewCallback.onCreatedView();
    }

    public void initViewPager(Context context, WeatherInfo weatherInfo) {
        if (mAdapter == null) {
            mAdapter = new WeatherDailyPageAdapter(mViews, context);
            mViewPager.setAdapter(mAdapter);
        }
        if (weatherInfo != null) {
            List<DailyForecast> dailys = weatherInfo.getDailyForecastList();

            for (int i = 1; i < dailys.size(); i++) {
                mDates[i - 1].setText(dailys.get(i).getDate());
                mClimates[i - 1].setText(dailys.get(i).getTxt_d());
                String tmp = dailys.get(i).getMin() + "℃ ~ " + dailys.get(i).getMax() + "℃";
                mTmps[i - 1].setText(tmp);
                Log.i(DAILY, "Date:" + dailys.get(i).getDate());
                Log.i(DAILY, "Weather:" + dailys.get(i).getTxt_d());
                Log.i(DAILY, "Tmp:" + tmp);
                Log.i(DAILY, "========================================");


            }
        }


    }
}
