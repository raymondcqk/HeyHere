package com.raymondqk.raysqlitepractice.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.raymondqk.raysqlitepractice.R;
import com.raymondqk.raysqlitepractice.fragment.main.MessageFragment;
import com.raymondqk.raysqlitepractice.model.UserInfo;
import com.raymondqk.raysqlitepractice.model.weather.WeatherInfo;
import com.raymondqk.raysqlitepractice.utils.FileUtils;
import com.raymondqk.raysqlitepractice.utils.JSONUtils;
import com.raymondqk.raysqlitepractice.utils.SharedPreferenceUtils;
import com.raymondqk.raysqlitepractice.utils.VerifyPermissionUtils;
import com.raymondqk.raysqlitepractice.utils.WeatherUtils;
import com.raymondqk.raysqlitepractice.utils.XMLParseUtils;
import com.raymondqk.raysqlitepractice.utils.db.DBHelper;
import com.raymondqk.raysqlitepractice.utils.internet.HTTPUtils;
import com.raymondqk.raysqlitepractice.utils.internet.HttpCallback;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by 陈其康 raymondchan on 2016/8/20 0020.
 */
public class WeatherActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String HTTP_HEAD = "http://";
    public static final String REQUEST_METHOD_GET = "GET";
    public static final int MSG_WHAT_UPDATE_TEST = 1;
    public static final int MSG_WHAT_CITY_LIST = 2;
    public static final String DOWNLOAD_RESULT = "downloadResult";
    public static final String KEY_WEATHER_CITY_LIST = "weather_city_list";
    public static final int REQUEST_CODE_SELECT_CITY = 10;
    public static final int RESULT_CODE_SELECT_CITY = 10;
    public static final int MSG_WHAT_UPDATE_CITY = 11;
    public static final String KEY_RESPONSE = "key_response";
    public static final String CITY_ID_GUANGZHOU = "CN101280101";
    private TextView mTv_city;
    private WeatherInfo mWeatherInfo;
    /*基于消息的异步机制*/
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_WHAT_UPDATE_CITY:
                    mWeatherInfo = (WeatherInfo) msg.obj;
                    if (mWeatherInfo != null) {
                        mTv_city.setText(mWeatherInfo.getCity());
                        mTv_climate.setText(mWeatherInfo.getTxt());
                        mTv_tmp.setText(mWeatherInfo.getTmp());
                        mTv_aqi.setText("空气质量指数: " + mWeatherInfo.getAqi());
                        mTv_pm25.setText("PM2.5   " + mWeatherInfo.getPm25());
                        mTv_qualty.setText(mWeatherInfo.getQlty());
                        mTitle.setText("更新于" + mWeatherInfo.getLoc());
                        mPb.setVisibility(View.GONE);
                        SharedPreferenceUtils.putLastWeatherCity(mWeatherInfo.getId());

                    } else {
                        Toast.makeText(WeatherActivity.this, "所选择地区的数据炸了，切换至默认地区", Toast.LENGTH_SHORT).show();
                        getWeather(CITY_ID_GUANGZHOU);
                    }
                    break;
            }
        }
    };
    private ImageButton mIb_select_city;
    private ImageButton mIb_back;
    private DBHelper mDbHelper;
    private TextView mTv_climate;
    private TextView mTv_tmp;
    private TextView mTv_aqi;
    private TextView mTv_pm25;
    private TextView mTv_qualty;
    private TextView mTitle;
    private ProgressBar mPb;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_weather);
        mDbHelper = DBHelper.getDBHelperInstance(this);
        initView();


    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    private String getJSONDataFromAssets(String filename) {
        try {
            InputStream inputStream = getAssets().open(filename);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder builder = new StringBuilder();
            String line = "";
            while ((line = reader.readLine()) != null) {
                builder.append(line.trim());
            }
            return builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void pullXMLResult(String xmlStr) {
        //            userInfo = null;
        List<UserInfo> userInfos = XMLParseUtils.parseXMLWithPull(xmlStr);
        StringBuilder b = new StringBuilder();
        for (UserInfo u : userInfos) {
            b.append(u.toString() + "\n");
        }
        mTv_city.setText(b.toString());
    }

    private void saxXmlResultTest(String xmlStr) {
        List<UserInfo> list = XMLParseUtils.parseXMLWithSAX(xmlStr);
        StringBuilder builder = new StringBuilder();
        builder.setLength(0);
        for (UserInfo userInfo : list) {
            builder.append(userInfo.toString() + "\n");
        }
        mTv_city.setText(builder.toString());
    }

    private String getXMLFromRaw() {
        try {
            InputStream in = getResources().openRawResource(R.raw.test);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder builder = new StringBuilder();
            String line = "";
            while ((line = reader.readLine()) != null) {
                builder.append(line.trim());
            }
            Log.i("", builder.toString());
            return builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void initView() {
        mIb_select_city = (ImageButton) findViewById(R.id.ib_weather_city);
        mIb_back = (ImageButton) findViewById(R.id.ib_weather_back);
        mIb_select_city.setOnClickListener(this);
        mIb_back.setOnClickListener(this);

        mTv_city = (TextView) findViewById(R.id.tv_weather_city);
        mTv_climate = (TextView) findViewById(R.id.tv_weather_climate);
        mTv_tmp = (TextView) findViewById(R.id.tv_weather_tmp);
        mTv_aqi = (TextView) findViewById(R.id.tv_weather_aqi);
        mTv_qualty = (TextView) findViewById(R.id.tv_weather_qulity);
        mTv_pm25 = (TextView) findViewById(R.id.tv_weather_pm25);
        mTitle = (TextView) findViewById(R.id.tv_weather_toolbar_title);

        mPb = (ProgressBar) findViewById(R.id.pb_weather);
        SharedPreferenceUtils.getSharedPreferencesInstance(WeatherActivity.this);
        String id = SharedPreferenceUtils.getWeatherCity();
        if (id != null) {
            getWeather(id);
        } else {
            getWeather(CITY_ID_GUANGZHOU);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_weather_back:
                onBackPressed();
                break;
            case R.id.ib_weather_city:
                Intent city_intent = new Intent(WeatherActivity.this, SelectCityActivity.class);
                startActivityForResult(city_intent, REQUEST_CODE_SELECT_CITY);
                break;
        }
    }


    /**
     * AsyncTask方式的异步操作
     */
    class HttpGetTask extends AsyncTask<Void, Integer, String> {

        /**
         * 主线程，初始化ui
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            mTv_city.setText("onPreExecute() -- 准备get");

        }

        /**
         * 工作线程，后台网络操作
         *
         * @param params
         * @return
         */
        @Override
        protected String doInBackground(Void... params) {
            int progress = 0;
            while (progress < 100) {
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                progress++;
                publishProgress(progress);
            }
            return HTTPUtils.getHTTPTest("www.zhihu.com", new HttpCallback() {
                @Override
                public void onFinish(String respones) {

                }

                @Override
                public void onError(Exception e) {

                }
            });
        }

        /**
         * 进度更新，主线程
         *
         * @param values
         */
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            //此处记得：返回的进度参数为数组
            mTv_city.setText(values[0] + "%");
        }

        /**
         * 结果操作，主线程
         *
         * @param s
         */
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            mTv_city.setText(s);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    private void writeJsonFile(String data) {
        VerifyPermissionUtils.verifyStoragePermission(WeatherActivity.this);
        String filename = "json.txt";
        FileUtils fileUtils = new FileUtils(WeatherActivity.this);
        fileUtils.createFile(data, filename);
    }

    @Override
    protected void onActivityResult(final int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            String city = data.getStringExtra(SelectCityActivity.SELECTED_CITY);
            mTv_city.setText(city);
            String cityId = mDbHelper.getCityIdByCity(city);
            if (cityId == null)
                return;
            getWeather(cityId);
        }

    }

    private void getWeather(String cityId) {
        mPb.setVisibility(View.VISIBLE);
        WeatherUtils.getWeatherInfoJson(cityId, new HttpCallback() {
            @Override
            public void onFinish(String respones) {
                WeatherInfo weatherInfo = JSONUtils.parseWeatherInfoFromJSONWithGSON(respones);
                Message msg = Message.obtain();
                msg.what = MSG_WHAT_UPDATE_CITY;
                msg.obj = weatherInfo;
                mHandler.sendMessage(msg);
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra(MessageFragment.WEATHER_RESULT, mWeatherInfo);
        setResult(MessageFragment.RESULT_CODE_WEATHER, intent);
        super.onBackPressed();
    }
}
