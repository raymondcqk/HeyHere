package com.raymondqk.raysqlitepractice.utils.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.raymondqk.raysqlitepractice.activity.SetPubInfoActivity;
import com.raymondqk.raysqlitepractice.interfaces.DBCallback;
import com.raymondqk.raysqlitepractice.model.PubInfo;
import com.raymondqk.raysqlitepractice.model.weather.CityInfo;
import com.raymondqk.raysqlitepractice.model.weather.DailyForecast;
import com.raymondqk.raysqlitepractice.model.weather.WeatherInfo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by 陈其康 raymondchan on 2016/8/11 0011.
 */
public class DBHelper extends SQLiteOpenHelper {


    public static final String TABLE_PUBLISHER = "PubInfos";
    public static final String COLUMN_ORGNIZATION = "orgnization";
    public static final String COLUMN_PUBLISHER = "publisher";
    public static final String COLUMN_PUBDATE = "pubdate";
    public static final String COLUMN_DEADLINE = "deadline";
    public static final String COLUMN_PROJECT = "project";

    public static final String TABLE_USERINFO = "Userinfo";
    public static final String COLUMN_BRIEF = "brief";

    public static final String COLUMN_PHONE = "phone";
    public static final String COLUMN_PASSWD = "passwd";
    public static final String COLUMN_LOGINSTATE = "loging_status";

    public static final String DB_NAME = "Project.db";
    public static final String CREATE_TABLE = "create table ";
    public static final String TABLE_CITY = "City";
    public static final String COLUMN_WEATHER_CURRENT_CITY = "city";
    public static final String COLUMN_WEATHER_CURRENT_CITY_ID = COLUMN_WEATHER_CURRENT_CITY + "_id";
    public static final String COLUMN_WEATHER_CITY_ID = COLUMN_WEATHER_CURRENT_CITY_ID;
    public static final String COLUMN_WEATHER_CITY = COLUMN_WEATHER_CURRENT_CITY;
    public static final String COLUMN_WEATHER_CNTY = "cnty";
    public static final String COLUMN_WEATHER_PROV = "prov";
    public static final String TABLE_CURRENT_WEATHER = "CurrentWeather";
    public static final String ID_PRIMARY_KEY = "id integer primary key autoincrement";
    public static final String COLUMN_WEATHER_CURRENT_AQI = "aqi";
    public static final String COLUMN_WEATHER_CURRENT_PM10 = "pm10";
    public static final String COLUMN_WEATHER_CURRENT_PM25 = "pm25";
    public static final String COLUMN_WEATHER_CURRENT_QLTY = "qlty";
    public static final String COLUMN_WEATHER_CURRENT_LOC = "loc";
    public static final String COLUMN_WEATHER_CURRENT_FL = "fl";
    public static final String COLUMN_WEATHER_CURRENT_HUM = "hum";
    public static final String COLUMN_WEATHER_CURRENT_PCPN = "pcpn";
    public static final String COLUMN_WEATHER_CURRENT_PRES = "pres";
    public static final String COLUMN_WEATHER_CURRENT_TMP = "tmp";
    public static final String COLUMN_WEATHER_CURRENT_VIS = "vis";
    public static final String COLUMN_WEATHER_CURRENT_DEG = "deg";
    public static final String COLUMN_WEATHER_CURRENT_DIR = "dir";
    public static final String COLUMN_WEATHER_CURRENT_SC = "sc";
    public static final String COLUMN_WEATHER_CURRENT_SPD = "spd";
    public static final String TABLE_DAILY_WEATHER = "weatherdaily";
    public static final String COLUMN_WEATHER_DAILY_SR = "sr";
    public static final String COLUMN_WEATHER_DAILY_SS = "ss";
    public static final String COLUMN_WEATHER_DAILY_TXT_D = "txt_d";
    public static final String COLUMN_WEATHER_DAILY_TXT_N = "txt_n";
    public static final String COLUMN_WEATHER_DAILY_DATE = "date";
    public static final String COLUMN_WEATHER_DAILY_HUM = "hum";
    public static final String COLUMN_WEATHER_DAILY_PCPN = "pcpn";
    public static final String COLUMN_WEATHER_DAILY_POP = "pop";
    public static final String COLUMN_WEATHER_DAILY_PRES = "pres";
    public static final String COLUMN_WEATHER_DAILY_MAX = "max";
    public static final String COLUMN_WEATHER_DAILY_MIN = "min";
    public static final String COLUMN_WEATHER_DAILY_VIS = "vis";
    public static final String COLUMN_WEATHER_DAILY_DEG = "deg";
    public static final String COLUMN_WEATHER_DAILY_DIR = "dir";
    public static final String COLUMN_WEATHER_DAILY_SC = "sc";
    public static final String COLUMN_WEATHER_DAILY_SPD = "spd";
    public static final String COLUMN_WEATHER_CURRENT_TXT = "txt";

    private SQLiteDatabase mDatabase;
    private static DBHelper mDBHelper;
    private Context mContext;

    /**
     * 创建数据库
     *
     * @param context
     */
    public DBHelper(Context context) {
        super(context, DB_NAME, null, 1);
        mContext = context;
    }

    /**
     * 创建数据表
     *
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        //建表
        db.execSQL("create table " + TABLE_PUBLISHER + "(" +
                ID_PRIMARY_KEY + "," +
                COLUMN_ORGNIZATION + " text," +
                COLUMN_PUBLISHER + " text," +
                COLUMN_PUBDATE + " integer," +
                COLUMN_DEADLINE + " integer," +
                COLUMN_PROJECT + " text," +
                COLUMN_BRIEF + " text)");
        Log.i(SetPubInfoActivity.TAG_TEST, TABLE_PUBLISHER + " table created");

        db.execSQL(CREATE_TABLE + TABLE_USERINFO + "(" +
                ID_PRIMARY_KEY + "," +
                COLUMN_PHONE + " text," +
                COLUMN_PASSWD + " text," +
                COLUMN_LOGINSTATE + " integer)");
        Log.i(SetPubInfoActivity.TAG_TEST, TABLE_USERINFO + " table created");

        db.execSQL(CREATE_TABLE + TABLE_CITY + "(" +
                ID_PRIMARY_KEY + "," +
                COLUMN_WEATHER_CITY + " text," +
                COLUMN_WEATHER_CNTY + " text," +
                COLUMN_WEATHER_CITY_ID + " text," +
                COLUMN_WEATHER_PROV + " text )");
        Log.i(SetPubInfoActivity.TAG_TEST, TABLE_CITY + " table created");

        db.execSQL(CREATE_TABLE + TABLE_CURRENT_WEATHER + "(" +
                ID_PRIMARY_KEY + "," +
                COLUMN_WEATHER_CURRENT_CITY_ID + " text," +
                COLUMN_WEATHER_CURRENT_CITY + " text," +
                COLUMN_WEATHER_CURRENT_AQI + " text," +
                COLUMN_WEATHER_CURRENT_PM10 + " text," +
                COLUMN_WEATHER_CURRENT_PM25 + " text," +
                COLUMN_WEATHER_CURRENT_QLTY + " text," +
                COLUMN_WEATHER_CURRENT_LOC + " text," +
                COLUMN_WEATHER_CURRENT_TXT + " text," +
                COLUMN_WEATHER_CURRENT_FL + " text," +
                COLUMN_WEATHER_CURRENT_HUM + " text," +
                COLUMN_WEATHER_CURRENT_PCPN + " text," +
                COLUMN_WEATHER_CURRENT_PRES + " text," +
                COLUMN_WEATHER_CURRENT_TMP + " text," +
                COLUMN_WEATHER_CURRENT_VIS + " text," +
                COLUMN_WEATHER_CURRENT_DEG + " text," +
                COLUMN_WEATHER_CURRENT_DIR + " text," +
                COLUMN_WEATHER_CURRENT_SC + " text," +
                COLUMN_WEATHER_CURRENT_SPD + " text" +
                ")");

        db.execSQL(CREATE_TABLE + TABLE_DAILY_WEATHER + "(" +
                ID_PRIMARY_KEY + "," +
                COLUMN_WEATHER_DAILY_SR + " text," +
                COLUMN_WEATHER_DAILY_SS + " text," +
                COLUMN_WEATHER_DAILY_TXT_D + " text," +
                COLUMN_WEATHER_DAILY_TXT_N + " text," +
                COLUMN_WEATHER_DAILY_DATE + " text," +
                COLUMN_WEATHER_DAILY_HUM + " text," +
                COLUMN_WEATHER_DAILY_PCPN + " text," +
                COLUMN_WEATHER_DAILY_POP + " text," +
                COLUMN_WEATHER_DAILY_PRES + " text," +
                COLUMN_WEATHER_DAILY_MAX + " text," +
                COLUMN_WEATHER_DAILY_MIN + " text," +
                COLUMN_WEATHER_DAILY_VIS + " text," +
                COLUMN_WEATHER_DAILY_DEG + " text," +
                COLUMN_WEATHER_DAILY_DIR + " text," +
                COLUMN_WEATHER_DAILY_SC + " text," +
                COLUMN_WEATHER_DAILY_SPD + " text" +
                ")");


    }

    /**
     * 升级数据库
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * 获得数据库实例，单例模式
     *
     * @return
     */
    public SQLiteDatabase getDBInstance() {
        if (mDatabase == null) {
            try {
                //若磁盘满了或数据库表语句错误，都会抛出异常
                mDatabase = this.getWritableDatabase();
            } catch (Exception e) {
                Log.e(SetPubInfoActivity.TAG_TEST, "create db failed：" + e.toString());
            }
        }
        return mDatabase;
    }

    /**
     * 根据项目名从数据库读取该项目所有数据，返回项目对象
     *
     * @param project
     * @return
     */
    public PubInfo getPubInfoFromDB(String project) {
        PubInfo pubInfo = new PubInfo();
        Cursor cursor = mDatabase.query(DBHelper.TABLE_PUBLISHER, null, DBHelper.COLUMN_PROJECT + "=?", new String[]{project}, null, null, null);
        if (cursor.moveToFirst() && cursor.getCount() == 1) {
            do {
                pubInfo.setProject(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_PROJECT)));
                pubInfo.setBrief(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_BRIEF)));
                pubInfo.setOrgnization(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_ORGNIZATION)));
                pubInfo.setPublisher(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_PUBLISHER)));
                pubInfo.setPubdate(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_PUBDATE)));
                pubInfo.setDeadline(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_DEADLINE)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return pubInfo;
    }

    /**
     * 读取所有 项目的标题
     *
     * @return
     */
    public ArrayList<String> getProjectTitleFromDB() {
        ArrayList<String> proTitles = new ArrayList<String>();
        Cursor cursor = mDatabase.query(TABLE_PUBLISHER, new String[]{COLUMN_PROJECT}, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                proTitles.add(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PROJECT)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return proTitles;
    }

    /**
     * 插入用户注册信息
     *
     * @param phone
     * @param passwd
     * @param loginstate
     * @return
     */
    public Long insertUserRegist(String phone, String passwd, int loginstate) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_PHONE, phone);
        cv.put(COLUMN_PASSWD, passwd);
        cv.put(COLUMN_LOGINSTATE, loginstate);
        return mDatabase.insert(TABLE_USERINFO, null, cv);
    }

    /**
     * 返回DBHelper的实例以及实例化数据库对象
     *
     * @param context
     * @return
     */
    public static DBHelper getDBHelperInstance(Context context) {
        if (mDBHelper == null) {
            mDBHelper = new DBHelper(context);
            mDBHelper.getDBInstance();
        }
        return mDBHelper;
    }

    /**
     * 获取对应用户名各密码，返回一个数组，0：phone 1：passwd
     *
     * @param phone
     * @return 返回一个数组，0：phone 1：passwd
     */
    public String[] getUserNamePasswd(String phone) {
        if (mDatabase != null) {
            Cursor cursor = mDatabase.query(TABLE_USERINFO,
                    new String[]{COLUMN_PHONE, COLUMN_PASSWD},
                    COLUMN_PHONE + "=?", new String[]{phone},
                    null, null, null);
            if (cursor.getCount() == 1) {
                if (cursor.moveToFirst()) {
                    String db_phone = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONE));
                    String db_passwd = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSWD));
                    cursor.close();
                    return new String[]{db_phone, db_passwd};
                }
            }
            cursor.close();
        }
        return null;
    }

    /**
     * 更新用户登陆状态
     *
     * @param phone
     * @param loginStatus
     */
    public void updateLoginStatus(String phone, int loginStatus) {
        if (mDatabase != null) {
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_LOGINSTATE, loginStatus);
            mDatabase.update(TABLE_USERINFO, cv, COLUMN_PHONE + "=?", new String[]{phone});
        }
    }

    /**
     * 读取登录状态
     *
     * @param phone
     * @return
     */
    public int getLoginStatus(String phone) {
        if (mDatabase != null) {
            Cursor cursor = mDatabase.query(TABLE_USERINFO,
                    new String[]{COLUMN_LOGINSTATE}, COLUMN_PHONE + "=?", new String[]{phone},
                    null, null, null);
            if (cursor.getCount() == 1) {
                if (cursor.moveToFirst()) {
                    int loginstatus = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_LOGINSTATE));
                    cursor.close();
                    return loginstatus;
                }
            }
        }
        return 0;
    }

    /**
     * 根据项目名删除一条发布记录
     *
     * @param project
     */
    public void deleteAProject(String project) {
        mDatabase.delete(TABLE_PUBLISHER, COLUMN_PROJECT + "=?", new String[]{project});
    }

    public ArrayList<PubInfo> getAllPubInfos() {
        ArrayList<PubInfo> pubInfos = new ArrayList<PubInfo>();
        Cursor cursor = mDatabase.query(TABLE_PUBLISHER, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                PubInfo pubInfo = new PubInfo();
                pubInfo.setOrgnization(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_ORGNIZATION)));
                pubInfo.setPublisher(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_PUBLISHER)));
                pubInfo.setProject(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_PROJECT)));
                pubInfo.setBrief(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_BRIEF)));
                pubInfo.setPubdate(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_PUBDATE)));
                pubInfo.setDeadline(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_DEADLINE)));
                pubInfos.add(pubInfo);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return pubInfos;
    }

    /**
     * 更新 项目发布信息 记录
     * 完整更新（后期赢该改成检测更新了那个属性的内容，针对性更新）
     *
     * @param pubInfo
     */
    public void updateOnePubInfo(PubInfo pubInfo, String old_protitle) {
        ContentValues cv = pubinfo2CV(pubInfo);
        mDatabase.update(TABLE_PUBLISHER, cv, COLUMN_PROJECT + "=?", new String[]{old_protitle});
    }

    /**
     * 将 PubInfo对象转成ContentValues对象
     *
     * @param pubInfo
     * @return
     */
    public ContentValues pubinfo2CV(PubInfo pubInfo) {
        ContentValues cv = new ContentValues();
        cv.put(DBHelper.COLUMN_ORGNIZATION, pubInfo.getOrgnization());
        cv.put(DBHelper.COLUMN_PUBLISHER, pubInfo.getPublisher());
        cv.put(DBHelper.COLUMN_PROJECT, pubInfo.getProject());
        cv.put(DBHelper.COLUMN_PUBDATE, pubInfo.getPubdate());
        cv.put(DBHelper.COLUMN_DEADLINE, pubInfo.getDeadline());
        cv.put(DBHelper.COLUMN_BRIEF, pubInfo.getBrief());
        return cv;
    }

    /**
     * 插入项目发布数据
     *
     * @param cv
     * @return
     */
    public long insertPubInfo(ContentValues cv) {
        return mDatabase.insert(TABLE_PUBLISHER, null, cv);
    }

    public void closeDB() {
        mDatabase.close();
        mDBHelper.close();
    }

    /**
     * 初始化城市信息数据
     * 这是一个城市数据表，字段包括城市名、城市id、所属省份
     *
     * @param cityInfos
     * @return
     */
    public boolean initCityTable(List<CityInfo> cityInfos) {
        for (CityInfo city : cityInfos) {
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_WEATHER_CITY, city.getCity());
            cv.put(COLUMN_WEATHER_CNTY, city.getCnty());
            cv.put(COLUMN_WEATHER_CITY_ID, city.getId());
            cv.put(COLUMN_WEATHER_PROV, city.getProv());
            mDatabase.insert(TABLE_CITY, null, cv);
        }
        Log.i("test", "城市数据录入成功");
        return true;

    }

    /**
     * 查询数据库里面的城市表的省份
     *
     * @return
     */
    public Set<String> queryProvince() {

        Set<String> provs = new HashSet<>();
        Cursor cursor = mDatabase.query(TABLE_CITY, new String[]{COLUMN_WEATHER_PROV}, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                provs.add(cursor.getString(cursor.getColumnIndex(COLUMN_WEATHER_PROV)));
            } while (cursor.moveToNext());
        }
        return provs;
    }


    /**
     * 根据省份名，返回城市列表
     * @param prov
     * @return
     */
    public List<String> queryCity(String prov) {
        List<String> citys = new ArrayList<String>();
        Cursor cursor = mDatabase.query(TABLE_CITY, new String[]{COLUMN_WEATHER_CITY}, COLUMN_WEATHER_PROV + "=?", new String[]{prov}, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                citys.add(cursor.getString(cursor.getColumnIndex(COLUMN_WEATHER_CITY)));
            } while (cursor.moveToNext());
        }
        return citys;
    }

    /**
     * 根据城市名，返回城市ID
     * @param city
     * @return
     */
    public String getCityIdByCity(String city) {
        String cityId = null;
        Cursor cursor = mDatabase.query(TABLE_CITY, new String[]{COLUMN_WEATHER_CITY_ID}, COLUMN_WEATHER_CITY + "=?", new String[]{city}, null, null, null);
        if (cursor.moveToFirst()) {
            cityId = cursor.getString(cursor.getColumnIndex(COLUMN_WEATHER_CITY_ID));
        }
        return cityId;
    }

    /**
     * 将当前城市的天气信息录入数据，作为缓存
     *
     * @param weatherInfo
     * @param callback
     */
    public void insertCurrentWeather(final WeatherInfo weatherInfo, final DBCallback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mDatabase.beginTransaction();
                try {
                    mDatabase.delete(TABLE_CURRENT_WEATHER, null, null);
                    mDatabase.delete(TABLE_DAILY_WEATHER, null, null);
                    ContentValues cv = new ContentValues();
                    cv.put(COLUMN_WEATHER_CURRENT_CITY_ID, weatherInfo.getId());
                    cv.put(COLUMN_WEATHER_CURRENT_CITY, weatherInfo.getCity());
                    cv.put(COLUMN_WEATHER_CURRENT_AQI, weatherInfo.getAqi());
                    cv.put(COLUMN_WEATHER_CURRENT_PM10, weatherInfo.getPm10());
                    cv.put(COLUMN_WEATHER_CURRENT_PM25, weatherInfo.getPm25());
                    cv.put(COLUMN_WEATHER_CURRENT_QLTY, weatherInfo.getQlty());
                    cv.put(COLUMN_WEATHER_CURRENT_LOC, weatherInfo.getLoc());
                    cv.put(COLUMN_WEATHER_CURRENT_TXT, weatherInfo.getTxt());
                    cv.put(COLUMN_WEATHER_CURRENT_TMP, weatherInfo.getTmp());
                    cv.put(COLUMN_WEATHER_CURRENT_VIS, weatherInfo.getVis());
                    cv.put(COLUMN_WEATHER_CURRENT_DEG, weatherInfo.getDeg());
                    cv.put(COLUMN_WEATHER_CURRENT_DIR, weatherInfo.getDir());
                    cv.put(COLUMN_WEATHER_CURRENT_SC, weatherInfo.getSc());
                    cv.put(COLUMN_WEATHER_CURRENT_SPD, weatherInfo.getSpd());
                    mDatabase.insert(TABLE_CURRENT_WEATHER, null, cv);
                    cv.clear();
                    for (DailyForecast day : weatherInfo.getDailyForecastList()) {
                        cv.put(COLUMN_WEATHER_DAILY_SR, day.getSr());
                        cv.put(COLUMN_WEATHER_DAILY_SS, day.getSs());
                        cv.put(COLUMN_WEATHER_DAILY_TXT_D, day.getTxt_d());
                        cv.put(COLUMN_WEATHER_DAILY_TXT_N, day.getTxt_n());
                        cv.put(COLUMN_WEATHER_DAILY_DATE, day.getDate());
                        cv.put(COLUMN_WEATHER_DAILY_HUM, day.getHum());
                        cv.put(COLUMN_WEATHER_DAILY_PCPN, day.getPcpn());
                        cv.put(COLUMN_WEATHER_DAILY_POP, day.getPop());
                        cv.put(COLUMN_WEATHER_DAILY_PRES, day.getPres());
                        cv.put(COLUMN_WEATHER_DAILY_MAX, day.getMax());
                        cv.put(COLUMN_WEATHER_DAILY_MIN, day.getMin());
                        cv.put(COLUMN_WEATHER_DAILY_VIS, day.getVis());
                        cv.put(COLUMN_WEATHER_DAILY_DEG, day.getDir());
                        cv.put(COLUMN_WEATHER_DAILY_SC, day.getSc());
                        cv.put(COLUMN_WEATHER_DAILY_SPD, day.getSpd());
                        mDatabase.insert(TABLE_DAILY_WEATHER, null, cv);
                        cv.clear();
                    }
                    mDatabase.setTransactionSuccessful();
                    callback.onFinished(true, null);
                } catch (Exception e) {
                    callback.onFinished(false, null);
                } finally {
                    mDatabase.endTransaction();

                }


            }
        }).start();
    }

    /**
     * 从数据读取当前/最后 城市的天气新，通过接口回调返回 weatherInfo对象
     *
     * @param callback
     */
    public void getCurrentWeatherInfo(final DBCallback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                WeatherInfo weatherInfo = new WeatherInfo();
                Cursor cursor = mDatabase.query(TABLE_CURRENT_WEATHER, null, null, null, null, null, null, null);
                if (cursor.moveToFirst()) {
                    weatherInfo.setId(cursor.getString(cursor.getColumnIndex(COLUMN_WEATHER_CURRENT_CITY_ID)));
                    weatherInfo.setCity(cursor.getString(cursor.getColumnIndex(COLUMN_WEATHER_CURRENT_CITY)));
                    weatherInfo.setAqi(cursor.getString(cursor.getColumnIndex(COLUMN_WEATHER_CURRENT_AQI)));
                    weatherInfo.setPm10(cursor.getString(cursor.getColumnIndex(COLUMN_WEATHER_CURRENT_PM10)));
                    weatherInfo.setPm25(cursor.getString(cursor.getColumnIndex(COLUMN_WEATHER_CURRENT_PM25)));
                    weatherInfo.setQlty(cursor.getString(cursor.getColumnIndex(COLUMN_WEATHER_CURRENT_QLTY)));
                    weatherInfo.setLoc(cursor.getString(cursor.getColumnIndex(COLUMN_WEATHER_CURRENT_LOC)));
                    weatherInfo.setTxt(cursor.getString(cursor.getColumnIndex(COLUMN_WEATHER_CURRENT_TXT)));
                    weatherInfo.setFl(cursor.getString(cursor.getColumnIndex(COLUMN_WEATHER_CURRENT_FL)));
                    weatherInfo.setHum(cursor.getString(cursor.getColumnIndex(COLUMN_WEATHER_CURRENT_HUM)));
                    weatherInfo.setPcpn(cursor.getString(cursor.getColumnIndex(COLUMN_WEATHER_CURRENT_PCPN)));
                    weatherInfo.setPres(cursor.getString(cursor.getColumnIndex(COLUMN_WEATHER_CURRENT_PRES)));
                    weatherInfo.setTmp(cursor.getString(cursor.getColumnIndex(COLUMN_WEATHER_CURRENT_TMP)));
                    weatherInfo.setVis(cursor.getString(cursor.getColumnIndex(COLUMN_WEATHER_CURRENT_VIS)));
                    weatherInfo.setDeg(cursor.getString(cursor.getColumnIndex(COLUMN_WEATHER_CURRENT_DEG)));
                    weatherInfo.setDir(cursor.getString(cursor.getColumnIndex(COLUMN_WEATHER_CURRENT_DIR)));
                    weatherInfo.setSc(cursor.getString(cursor.getColumnIndex(COLUMN_WEATHER_CURRENT_SC)));
                    weatherInfo.setSpd(cursor.getString(cursor.getColumnIndex(COLUMN_WEATHER_CURRENT_SPD)));

                    cursor = mDatabase.query(TABLE_DAILY_WEATHER, null, null, null, null, null, null, null);
                    if (cursor.moveToFirst()) {
                        List<DailyForecast> dayList = new ArrayList<DailyForecast>();
                        weatherInfo.setDailyForecastList(dayList);
                        do {
                            DailyForecast day = new DailyForecast();
                            day.setSr(cursor.getString(cursor.getColumnIndex(COLUMN_WEATHER_DAILY_SR)));
                            day.setSs(cursor.getString(cursor.getColumnIndex(COLUMN_WEATHER_DAILY_SS)));
                            day.setTxt_d(cursor.getString(cursor.getColumnIndex(COLUMN_WEATHER_DAILY_TXT_D)));
                            day.setTxt_n(cursor.getString(cursor.getColumnIndex(COLUMN_WEATHER_DAILY_TXT_N)));
                            day.setDate(cursor.getString(cursor.getColumnIndex(COLUMN_WEATHER_DAILY_DATE)));
                            day.setHum(cursor.getString(cursor.getColumnIndex(COLUMN_WEATHER_DAILY_HUM)));
                            day.setPcpn(cursor.getString(cursor.getColumnIndex(COLUMN_WEATHER_DAILY_PCPN)));
                            day.setPop(cursor.getString(cursor.getColumnIndex(COLUMN_WEATHER_DAILY_POP)));
                            day.setPres(cursor.getString(cursor.getColumnIndex(COLUMN_WEATHER_DAILY_PRES)));
                            day.setMax(cursor.getString(cursor.getColumnIndex(COLUMN_WEATHER_DAILY_MAX)));
                            day.setMin(cursor.getString(cursor.getColumnIndex(COLUMN_WEATHER_DAILY_MIN)));
                            day.setVis(cursor.getString(cursor.getColumnIndex(COLUMN_WEATHER_DAILY_VIS)));
                            day.setDeg(cursor.getString(cursor.getColumnIndex(COLUMN_WEATHER_DAILY_DEG)));
                            day.setDir(cursor.getString(cursor.getColumnIndex(COLUMN_WEATHER_DAILY_DIR)));
                            day.setSc(cursor.getString(cursor.getColumnIndex(COLUMN_WEATHER_DAILY_SC)));
                            day.setSpd(cursor.getString(cursor.getColumnIndex(COLUMN_WEATHER_DAILY_SPD)));
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
