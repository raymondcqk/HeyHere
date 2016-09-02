package com.raymondqk.raysqlitepractice.fragment.main;

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
import android.widget.TextView;

import com.raymondqk.raysqlitepractice.R;
import com.raymondqk.raysqlitepractice.activity.AdvApkDownloadActivity;
import com.raymondqk.raysqlitepractice.activity.ContactActivity;
import com.raymondqk.raysqlitepractice.activity.MainActivity;
import com.raymondqk.raysqlitepractice.activity.WeatherActivity;
import com.raymondqk.raysqlitepractice.model.weather.WeatherInfo;
import com.raymondqk.raysqlitepractice.utils.SharedPreferenceUtils;

/**
 * Created by 陈其康 raymondchan on 2016/8/14 0014.
 */
public class MessageFragment extends Fragment implements View.OnClickListener {

    public static final int REQUEST_CODE_WEATHER = 22;
    public static final int RESULT_CODE_WEATHER = 22;
    public static final String WEATHER_RESULT = "weather_result";
    private View mView;
    private MainActivity mMainActivity;
    private LinearLayout mLl_contact;
    private LinearLayout mLl_notification;
    private RelativeLayout mLl_weather;
    private TextView mTv_weather;
    private String mWeather_bref;
    private LinearLayout mLl_adv;

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
        mLl_adv = (LinearLayout) mView.findViewById(R.id.layout_message_adv);
        mLl_adv.setOnClickListener(this);

        mLl_weather = (RelativeLayout) mView.findViewById(R.id.layout_message_weather);
        mLl_weather.setOnClickListener(this);

        mTv_weather = (TextView) mView.findViewById(R.id.tv_message_weather_detail);
        mWeather_bref = SharedPreferenceUtils.getWeatherBref();
        mTv_weather.setText(mWeather_bref);

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
                startActivityForResult(intent, REQUEST_CODE_WEATHER);
                break;
            case R.id.layout_message_adv:
                intent.setClass(mMainActivity, AdvApkDownloadActivity.class);
                startActivity(intent);
                break;
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_WEATHER && resultCode == RESULT_CODE_WEATHER) {
            WeatherInfo weatherInfo = (WeatherInfo) data.getSerializableExtra(WEATHER_RESULT);
            if (weatherInfo != null) {
                String city = weatherInfo.getCity();
                String climate = weatherInfo.getTxt();
                String min = weatherInfo.getDailyForecastList().get(0).getMin();
                String max = weatherInfo.getDailyForecastList().get(0).getMax();
                String time = weatherInfo.getLoc().substring(11, 16);
                mWeather_bref = "【 " + city + " 】  " + climate + "  " + min + "℃/" + max + "℃   更新于 " + time;
                mTv_weather.setText(mWeather_bref);
                SharedPreferenceUtils.putLastWeatherBref(mWeather_bref);
            }

        }

    }
}
