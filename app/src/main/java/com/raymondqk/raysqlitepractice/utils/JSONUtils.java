package com.raymondqk.raysqlitepractice.utils;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.raymondqk.raysqlitepractice.model.UserInfo;
import com.raymondqk.raysqlitepractice.model.weather.CityInfo;
import com.raymondqk.raysqlitepractice.model.weather.DailyForecast;
import com.raymondqk.raysqlitepractice.model.weather.HourlyForecast;
import com.raymondqk.raysqlitepractice.model.weather.WeatherInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 陈其康 raymondchan on 2016/8/21 0021.
 */
public class JSONUtils {
    /**
     * JSONObjec解析jons数据
     *
     * @param jsonData
     * @return
     */
    public static List<UserInfo> parseUserInfoFromJSONWithJSON(String jsonData) {
        try {
            List<UserInfo> userInfos = new ArrayList<UserInfo>();
            JSONArray jsonArray = new JSONArray(jsonData);
            for (int i = 0; i < jsonArray.length(); i++) {
                UserInfo userInfo = new UserInfo();
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                userInfo.setPhone(jsonObject.getString("phone"));
                userInfo.setPasswd(jsonObject.getString("passwd"));
                userInfo.setLogging(Boolean.parseBoolean(jsonObject.getString("status")));
                userInfos.add(userInfo);
            }
            return userInfos;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 使用GSON开源库解析JSON数据
     *
     * @param jsonData
     * @return
     */
    public static List<UserInfo> parseUserInfoFromJSONWithGSON(String jsonData) {
        Gson gson = new Gson();
        List<UserInfo> userInfos = gson.fromJson(jsonData, new TypeToken<List<UserInfo>>() {
        }.getType());
        return userInfos;
    }

    public static List<CityInfo> parseCityListFromJSONWithGSON(String cityListJson) {
        /*通过GSON解析对象内包含对象，且值为数组的形式,并转为对象list*/
        /*
        {
	    "city_info":
	       [
	        {
		    "city": "南子岛",
		    "cnty": "中国",
		    "id": "CN101310230",
		    "lat": "11.26",
		    "lon": "114.20",
		    "prov": "海南"
	        },
	        {
		    "city": "北京",
		    "cnty": "中国",
		    "id": "CN101010100",
		    "lat": "39.904000",
		    "lon": "116.391000",
		    "prov": "直辖市"
	        }
	       ]
	    }
        */
        Gson gson = new Gson();
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = jsonParser.parse(cityListJson).getAsJsonObject();
        JsonArray jsonArray = jsonObject.getAsJsonArray("city_info");
        List<CityInfo> cityInfos = gson.fromJson(jsonArray, new TypeToken<List<CityInfo>>() {
        }.getType());
        return cityInfos;
    }

    public static WeatherInfo parseWeatherInfoFromJSONWithGSON(String jsondata) {
        WeatherInfo weatherInfo = null;
        try {
            weatherInfo = getWeatherInfoWithGSON(jsondata);
        } catch (Exception e) {
        }

        //end 当前天气==============================================================================
        return weatherInfo;
    }

    @NonNull
    private static WeatherInfo getWeatherInfoWithGSON(String jsondata) {
        WeatherInfo weatherInfo = new WeatherInfo();
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        JsonObject jsonObject = parser.parse(jsondata).getAsJsonObject();
        JsonArray jsonArray = jsonObject.getAsJsonArray("HeWeather data service 3.0");
        JsonObject wrap = jsonArray.get(0).getAsJsonObject();


        //空气质量===============================================================================
        JsonObject aqiObj = wrap.getAsJsonObject("aqi");
        JsonObject aqis = aqiObj.getAsJsonObject("city");
        String aqi = aqis.get("aqi").getAsString();
        String pm10 = aqis.get("pm10").getAsString();
        String pm25 = aqis.get("pm25").getAsString();
        String qlty = aqis.get("qlty").getAsString();
        weatherInfo.setAqi(aqi);
        weatherInfo.setPm10(pm10);
        weatherInfo.setPm25(pm25);
        weatherInfo.setQlty(qlty);
        //end 空气质量===============================================================================
        //城市基本情况===============================================================================
        JsonObject basic = wrap.getAsJsonObject("basic");
        String city = basic.get("city").getAsString();
        String cnty = basic.get("cnty").getAsString();
        String id = basic.get("id").getAsString();
        String lat = basic.get("lat").getAsString();
        String lon = basic.get("lon").getAsString();
        JsonObject update = basic.getAsJsonObject("update");
        String loc = update.get("loc").getAsString();//更新时间
        String utc = update.get("utc").getAsString();
        weatherInfo.setId(id);
        weatherInfo.setLoc(loc);
        weatherInfo.setCity(city);
        //end 城市基本情况===============================================================================
        //7日预报==================================================================================
        JsonArray daily_forecast_wrap = wrap.getAsJsonArray("daily_forecast");
        JsonObject day1 = daily_forecast_wrap.get(0).getAsJsonObject();
        JsonObject day2 = daily_forecast_wrap.get(1).getAsJsonObject();
        JsonObject day3 = daily_forecast_wrap.get(2).getAsJsonObject();
        JsonObject day4 = daily_forecast_wrap.get(3).getAsJsonObject();
        JsonObject day5 = daily_forecast_wrap.get(4).getAsJsonObject();
        JsonObject day6 = daily_forecast_wrap.get(5).getAsJsonObject();
        JsonObject day7 = daily_forecast_wrap.get(6).getAsJsonObject();

        List<JsonObject> dayily = new ArrayList<JsonObject>();
        dayily.add(day1);
        dayily.add(day2);
        dayily.add(day3);
        dayily.add(day4);
        dayily.add(day5);
        dayily.add(day6);
        dayily.add(day7);
        List<DailyForecast> dailyForecasts = new ArrayList<DailyForecast>();
        for (JsonObject day : dayily) {
            JsonObject astro = day.getAsJsonObject("astro");
            String sr = astro.get("sr").getAsString();//日出
            String ss = astro.get("ss").getAsString();//日落
            JsonObject cond = day.getAsJsonObject("cond");
            String txt_d = cond.get("txt_d").getAsString();//白天天气 -- 多云
            String txt_n = cond.get("txt_n").getAsString();//晚上天气
            String date = day.get("date").getAsString();//预报日期
            String hum = day.get("hum").getAsString();//湿度
            String pcpn = day.get("pcpn").getAsString();//不知啥
            String pop = day.get("pop").getAsString();//不只啥
            String pres = day.get("pres").getAsString();//气压
            JsonObject tmp = day.getAsJsonObject("tmp");
            String max = tmp.get("max").getAsString();//最高气温
            String min = tmp.get("min").getAsString();//最低气温
            String vis = day.get("vis").getAsString();//能见度
            JsonObject wind = day.getAsJsonObject("wind");
            String deg = wind.get("deg").getAsString();//风速 35km/s
            String dir = wind.get("dir").getAsString();//风向
            String sc = wind.get("sc").getAsString();//风速描述 微风
            String spd = wind.get("spd").getAsString();//风速级别

            DailyForecast dailyForecast = new DailyForecast();
            dailyForecast.setSr(sr);
            dailyForecast.setSs(ss);
            dailyForecast.setTxt_d(txt_d);
            dailyForecast.setTxt_n(txt_n);
            dailyForecast.setDate(date);
            dailyForecast.setHum(hum);
            dailyForecast.setPcpn(pcpn);
            dailyForecast.setPop(pop);
            dailyForecast.setPres(pres);
            dailyForecast.setMax(max);
            dailyForecast.setMin(min);
            dailyForecast.setVis(vis);
            dailyForecast.setDeg(deg);
            dailyForecast.setDir(dir);
            dailyForecast.setSc(sc);
            dailyForecast.setSpd(spd);
            dailyForecasts.add(dailyForecast);
        }
        weatherInfo.setDailyForecastList(dailyForecasts);
        //end 7日预报==================================================================================
        //小时级别预报==================================================================================
        JsonArray hourly_forecast_wrap = wrap.getAsJsonArray("hourly_forecast");
        List<HourlyForecast> hourlyForecasts = new ArrayList<HourlyForecast>();
        for (int i = 0; i < hourly_forecast_wrap.size(); i++) {
            JsonObject obj = hourly_forecast_wrap.get(i).getAsJsonObject();
            HourlyForecast hourly = new HourlyForecast();
            hourly.setDate(obj.get("date").getAsString());
            hourly.setHum(obj.get("hum").getAsString());
            hourly.setPop(obj.get("pop").getAsString());
            hourly.setPres(obj.get("pres").getAsString());
            hourly.setTmp(obj.get("tmp").getAsString());
            JsonObject wind = obj.getAsJsonObject("wind");
            hourly.setDeg(wind.get("deg").getAsString());
            hourly.setDir(wind.get("dir").getAsString());
            hourly.setSc(wind.get("sc").getAsString());
            hourly.setSpd(wind.get("spd").getAsString());
            hourlyForecasts.add(hourly);
        }
        weatherInfo.setHourlyForecasts(hourlyForecasts);
        //end 小时级别预报==============================================================================
        //当前天气==============================================================================
        JsonObject now_wrap = wrap.getAsJsonObject("now");
        JsonObject cond = now_wrap.getAsJsonObject("cond");
        String txt = cond.get("txt").getAsString();
        String fl = now_wrap.get("fl").getAsString();
        String hum = now_wrap.get("hum").getAsString();
        String pcpn = now_wrap.get("pcpn").getAsString();
        String pres = now_wrap.get("pres").getAsString();
        String tmp = now_wrap.get("tmp").getAsString();
        String vis = now_wrap.get("vis").getAsString();
        JsonObject wind = now_wrap.getAsJsonObject("wind");
        String deg = wind.get("deg").getAsString();
        String dir = wind.get("dir").getAsString();
        String sc = wind.get("sc").getAsString();
        String spd = wind.get("spd").getAsString();

        weatherInfo.setTxt(txt);
        weatherInfo.setFl(fl);
        weatherInfo.setHum(hum);
        weatherInfo.setPcpn(pcpn);
        weatherInfo.setPres(pres);
        weatherInfo.setTmp(tmp);
        weatherInfo.setVis(vis);
        weatherInfo.setDeg(deg);
        weatherInfo.setDir(dir);
        weatherInfo.setSc(sc);
        weatherInfo.setSpd(spd);
        return weatherInfo;
    }
}
