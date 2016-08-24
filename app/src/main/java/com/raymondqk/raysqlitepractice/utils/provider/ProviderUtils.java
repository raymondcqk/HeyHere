package com.raymondqk.raysqlitepractice.utils.provider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.raymondqk.raysqlitepractice.interfaces.DBCallback;
import com.raymondqk.raysqlitepractice.model.weather.DailyForecast;
import com.raymondqk.raysqlitepractice.model.weather.WeatherInfo;
import com.raymondqk.raysqlitepractice.utils.db.DBHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 陈其康 raymondchan on 2016/8/24 0024.
 */
public class ProviderUtils {
    public static final String AUTHORITY = "com.raymondcqk.ishere.provider";

    public static void insertCurrentWeather(final Context context, final WeatherInfo weatherInfo, final DBCallback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Uri uri = Uri.parse("content://" + AUTHORITY + "/tableWeatherCurrent");
                ContentValues cv = new ContentValues();
                cv.put(DBHelper.COLUMN_WEATHER_CURRENT_CITY_ID, weatherInfo.getId());
                cv.put(DBHelper.COLUMN_WEATHER_CURRENT_CITY, weatherInfo.getCity());
                cv.put(DBHelper.COLUMN_WEATHER_CURRENT_AQI, weatherInfo.getAqi());
                cv.put(DBHelper.COLUMN_WEATHER_CURRENT_PM10, weatherInfo.getPm10());
                cv.put(DBHelper.COLUMN_WEATHER_CURRENT_PM25, weatherInfo.getPm25());
                cv.put(DBHelper.COLUMN_WEATHER_CURRENT_QLTY, weatherInfo.getQlty());
                cv.put(DBHelper.COLUMN_WEATHER_CURRENT_LOC, weatherInfo.getLoc());
                cv.put(DBHelper.COLUMN_WEATHER_CURRENT_TXT, weatherInfo.getTxt());
                cv.put(DBHelper.COLUMN_WEATHER_CURRENT_TMP, weatherInfo.getTmp());
                cv.put(DBHelper.COLUMN_WEATHER_CURRENT_VIS, weatherInfo.getVis());
                cv.put(DBHelper.COLUMN_WEATHER_CURRENT_DEG, weatherInfo.getDeg());
                cv.put(DBHelper.COLUMN_WEATHER_CURRENT_DIR, weatherInfo.getDir());
                cv.put(DBHelper.COLUMN_WEATHER_CURRENT_SC, weatherInfo.getSc());
                cv.put(DBHelper.COLUMN_WEATHER_CURRENT_SPD, weatherInfo.getSpd());
                context.getContentResolver().insert(uri, cv);
                cv.clear();
                uri = Uri.parse("content://" + AUTHORITY + "/tableWeatherDaily");
                context.getContentResolver().delete(uri,null,null);
                for (DailyForecast day : weatherInfo.getDailyForecastList()) {
                    cv.put(DBHelper.COLUMN_WEATHER_DAILY_SR, day.getSr());
                    cv.put(DBHelper.COLUMN_WEATHER_DAILY_SS, day.getSs());
                    cv.put(DBHelper.COLUMN_WEATHER_DAILY_TXT_D, day.getTxt_d());
                    cv.put(DBHelper.COLUMN_WEATHER_DAILY_TXT_N, day.getTxt_n());
                    cv.put(DBHelper.COLUMN_WEATHER_DAILY_DATE, day.getDate());
                    cv.put(DBHelper.COLUMN_WEATHER_DAILY_HUM, day.getHum());
                    cv.put(DBHelper.COLUMN_WEATHER_DAILY_PCPN, day.getPcpn());
                    cv.put(DBHelper.COLUMN_WEATHER_DAILY_POP, day.getPop());
                    cv.put(DBHelper.COLUMN_WEATHER_DAILY_PRES, day.getPres());
                    cv.put(DBHelper.COLUMN_WEATHER_DAILY_MAX, day.getMax());
                    cv.put(DBHelper.COLUMN_WEATHER_DAILY_MIN, day.getMin());
                    cv.put(DBHelper.COLUMN_WEATHER_DAILY_VIS, day.getVis());
                    cv.put(DBHelper.COLUMN_WEATHER_DAILY_DEG, day.getDir());
                    cv.put(DBHelper.COLUMN_WEATHER_DAILY_SC, day.getSc());
                    cv.put(DBHelper.COLUMN_WEATHER_DAILY_SPD, day.getSpd());
                    context.getContentResolver().insert(uri, cv);
                    cv.clear();
                }
                callback.onFinished(true, null);
            }
        }).start();
    }

    public static void getCurrentWeatherInfo(final Context context, final DBCallback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                WeatherInfo weatherInfo = new WeatherInfo();

                //先读取当前天气表，在读取7天预报表
                Uri uri = Uri.parse("content://" + AUTHORITY + "/tableWeatherCurrent");
                Cursor cursor = context.getContentResolver().query(uri, null, null, null, null, null);
                if (cursor.moveToFirst()) {
                    weatherInfo.setId(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_WEATHER_CURRENT_CITY_ID)));
                    weatherInfo.setCity(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_WEATHER_CURRENT_CITY)));
                    weatherInfo.setAqi(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_WEATHER_CURRENT_AQI)));
                    weatherInfo.setPm10(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_WEATHER_CURRENT_PM10)));
                    weatherInfo.setPm25(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_WEATHER_CURRENT_PM25)));
                    weatherInfo.setQlty(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_WEATHER_CURRENT_QLTY)));
                    weatherInfo.setLoc(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_WEATHER_CURRENT_LOC)));
                    weatherInfo.setTxt(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_WEATHER_CURRENT_TXT)));
                    weatherInfo.setFl(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_WEATHER_CURRENT_FL)));
                    weatherInfo.setHum(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_WEATHER_CURRENT_HUM)));
                    weatherInfo.setPcpn(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_WEATHER_CURRENT_PCPN)));
                    weatherInfo.setPres(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_WEATHER_CURRENT_PRES)));
                    weatherInfo.setTmp(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_WEATHER_CURRENT_TMP)));
                    weatherInfo.setVis(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_WEATHER_CURRENT_VIS)));
                    weatherInfo.setDeg(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_WEATHER_CURRENT_DEG)));
                    weatherInfo.setDir(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_WEATHER_CURRENT_DIR)));
                    weatherInfo.setSc(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_WEATHER_CURRENT_SC)));
                    weatherInfo.setSpd(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_WEATHER_CURRENT_SPD)));
                    uri = Uri.parse("content://" + AUTHORITY + "/tableWeatherDaily");
                    cursor = context.getContentResolver().query(uri, null, null, null, null, null);
                    if (cursor.moveToFirst()) {
                        List<DailyForecast> dayList = new ArrayList<DailyForecast>();
                        weatherInfo.setDailyForecastList(dayList);
                        do {
                            DailyForecast day = new DailyForecast();
                            day.setSr(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_WEATHER_DAILY_SR)));
                            day.setSs(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_WEATHER_DAILY_SS)));
                            day.setTxt_d(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_WEATHER_DAILY_TXT_D)));
                            day.setTxt_n(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_WEATHER_DAILY_TXT_N)));
                            day.setDate(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_WEATHER_DAILY_DATE)));
                            day.setHum(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_WEATHER_DAILY_HUM)));
                            day.setPcpn(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_WEATHER_DAILY_PCPN)));
                            day.setPop(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_WEATHER_DAILY_POP)));
                            day.setPres(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_WEATHER_DAILY_PRES)));
                            day.setMax(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_WEATHER_DAILY_MAX)));
                            day.setMin(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_WEATHER_DAILY_MIN)));
                            day.setVis(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_WEATHER_DAILY_VIS)));
                            day.setDeg(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_WEATHER_DAILY_DEG)));
                            day.setDir(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_WEATHER_DAILY_DIR)));
                            day.setSc(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_WEATHER_DAILY_SC)));
                            day.setSpd(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_WEATHER_DAILY_SPD)));
                            weatherInfo.getDailyForecastList().add(day);
                        } while (cursor.moveToNext());
                        callback.onFinished(true, weatherInfo);
                    }
                }
                callback.onFinished(false, null);
            }
        }).start();

    }
}
