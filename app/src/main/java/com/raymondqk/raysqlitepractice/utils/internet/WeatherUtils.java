package com.raymondqk.raysqlitepractice.utils.internet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by 陈其康 raymondchan on 2016/8/22 0022.
 */
public class WeatherUtils {
    public static final String MY_KEY = "3aebbb48d03d40aab50114b6791452e7";
    public static final String CITY_LIST_ALL_CHINA = "allchina";
    public static final String CITY_LIST_HOT_WORLD = "hotworld";
    public static final String CITY_LIST_WORLD = "allworld";


    /**
     * 读取城市列表的json
     *
     * @param cityListType
     * @param callback
     */
    public static void getCityListJson(final String cityListType, final HttpCallback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String cityListUrl = "https://api.heweather.com/x3/citylist?search=" + cityListType + "&key=" + MY_KEY;
                String jsonData = request(cityListUrl);
                callback.onFinish(jsonData);
            }
        }).start();

    }

    /**
     * 根据id，读取城市天气 json
     * @param cityId
     * @param callback
     */
    public static void getWeatherInfoJson(final String cityId, final HttpCallback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String url = "https://api.heweather.com/x3/weather?cityid=" + cityId + "&key=" + MY_KEY;
                String jsonData = request(url);
                callback.onFinish(jsonData);
            }
        }).start();
    }

    /**
     * 根据url get http 数据，得到最终字符串
     *
     * @param httpUrl
     * @return
     */
    public static String request(String httpUrl) {
        BufferedReader reader = null;
        StringBuilder builder = null;
        String line = "";
        try {
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(8000);
            connection.setReadTimeout(8000);
            connection.connect();
            InputStream is = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is));
            builder = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                builder.append(line.trim());
            }
            reader.close();
            return builder.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

}
