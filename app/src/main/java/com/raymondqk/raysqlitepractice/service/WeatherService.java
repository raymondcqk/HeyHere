package com.raymondqk.raysqlitepractice.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.raymondqk.raysqlitepractice.activity.WeatherActivity;
import com.raymondqk.raysqlitepractice.interfaces.DBCallback;
import com.raymondqk.raysqlitepractice.interfaces.ServiceCallback;
import com.raymondqk.raysqlitepractice.model.weather.WeatherInfo;
import com.raymondqk.raysqlitepractice.utils.JSONUtils;
import com.raymondqk.raysqlitepractice.utils.SharedPreferenceUtils;
import com.raymondqk.raysqlitepractice.utils.db.DBHelper;
import com.raymondqk.raysqlitepractice.utils.internet.HttpCallback;
import com.raymondqk.raysqlitepractice.utils.internet.WeatherUtils;
import com.raymondqk.raysqlitepractice.utils.provider.ProviderUtils;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by 陈其康 raymondchan on 2016/8/24 0024.
 */
public class WeatherService extends Service {

    public static final String TAG = "service_test";
    public static final String ACTION_COM_RAYMONDCQK_ISHERE_WEATHER_SERVICE = "com.raymondcqk.ishere.weather_service";
    private DBHelper mDBHelper;
    private WeatherUpdateReceiver mWeatherUpdateReceiver;
    private static final String ACTION_TIME_CHANGED = Intent.ACTION_TIME_TICK;
    private IntentFilter mIntentFilter;
    private int mHour;
    private int mMin;
    private ServiceCallback mServiceCallback;
    private int mCounttest;
    private Timer mTimer;
    private TimerTask timerTask;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "======后台服务被销毁=========");
        unregisterReceiver(mWeatherUpdateReceiver);
        super.onDestroy();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "======后台服务被创建=========");
        mDBHelper = new DBHelper(this);
        mWeatherUpdateReceiver = new WeatherUpdateReceiver();
        mIntentFilter = new IntentFilter(ACTION_COM_RAYMONDCQK_ISHERE_WEATHER_SERVICE);
        registerReceiver(mWeatherUpdateReceiver, mIntentFilter);

        mTimer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                //定时到了之后要处理的任务
                Intent i = new Intent(ACTION_COM_RAYMONDCQK_ISHERE_WEATHER_SERVICE);
                sendBroadcast(i);
            }
        };

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "======后台服务开始运行=========");
        mCounttest = 0;

        new Thread(new Runnable() {
            @Override
            public void run() {
                //定时 Timer TimerTask
                //这里为了演示，定时时间比较短
                mTimer.schedule(timerTask, 60000, 60000);//两个参数，循环执行
            }
        }).start();
        return START_STICKY;
    }


    class WeatherUpdateReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(final Context context, Intent intent) {
            //服务中更新当前所选地区的数据库天气信息
            WeatherUtils.getWeatherInfoJson(SharedPreferenceUtils.getWeatherCity(), new HttpCallback() {
                @Override
                public void onFinish(String respones) {
                    WeatherInfo weatherInfo = JSONUtils.parseWeatherInfoFromJSONWithGSON(respones);
                    ProviderUtils.insertCurrentWeather(context, weatherInfo, new DBCallback() {
                        @Override
                        public void onFinished(Boolean isOk, Object object) {
                            Intent i = new Intent(WeatherActivity.ACTION_COM_RAYMONDCQK_WEATHER_TIME);
                            sendBroadcast(i);
                        }
                    });
                }

                @Override
                public void onError(Exception e) {

                }
            });
            //            ProviderUtils.insertCurrentWeather(context,);
        }
    }
}
