package com.raymondqk.raysqlitepractice.utils.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.raymondqk.raysqlitepractice.utils.db.DBHelper;

/**
 * Created by 陈其康 raymondchan on 2016/8/24 0024.
 */
public class WeatherProvider extends ContentProvider {

    private DBHelper mDbHelper;

    public static final String AUTHORITY = "com.raymondcqk.ishere.provider";

    public static final int PROVINCE = 0;
    public static final int CITY = 1;
    public static final int WEATHERINFO_CURRENT = 2;
    public static final int WEATHERINFO_DAILY = 3;
    public static final int CITY_ID = 4;


    private static UriMatcher sUriMatcher;


    static {
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sUriMatcher.addURI(AUTHORITY, "tableProv", PROVINCE);
        sUriMatcher.addURI(AUTHORITY, "tableCity", CITY);
        sUriMatcher.addURI(AUTHORITY, "tableCityID", CITY_ID);
        sUriMatcher.addURI(AUTHORITY, "tableWeatherCurrent", WEATHERINFO_CURRENT);
        sUriMatcher.addURI(AUTHORITY, "tableWeatherDaily", WEATHERINFO_DAILY);
    }

    /**
     * 初始化调用
     * 通常进行数据库创建、升级
     * 只有ContentResolver尝试访问，才会被初始化
     *
     * @return
     */
    @Override
    public boolean onCreate() {
        mDbHelper = DBHelper.getDBHelperInstance(getContext());
        return true;
    }

    /**
     * 查询数据
     *
     * @param uri           根据uri确定查询哪张表（哪类数据）
     * @param projection    要返回的列
     * @param selection     约束条件
     * @param selectionArgs 约束值
     * @param sortOrder     排序
     * @return
     */
    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection,
                        String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = mDbHelper.getDBInstance();
        Cursor cursor = null;
        switch (sUriMatcher.match(uri)) {
            case PROVINCE:
                cursor = db.query(DBHelper.TABLE_CITY, new String[]{DBHelper.COLUMN_WEATHER_PROV},
                        null, null, null, null, null);
                break;
            case CITY:
                cursor = db.query(DBHelper.TABLE_CITY, new String[]{DBHelper.COLUMN_WEATHER_CITY},
                        DBHelper.COLUMN_WEATHER_PROV + "=?", selectionArgs,
                        null, null, null);
                break;
            case WEATHERINFO_CURRENT:
                cursor = db.query(DBHelper.TABLE_CURRENT_WEATHER, null, null, null, null, null, null, null);
                break;
            case WEATHERINFO_DAILY:
                cursor = db.query(DBHelper.TABLE_DAILY_WEATHER, null, null, null, null, null, null, null);
                break;
            case CITY_ID:
                cursor = db.query(DBHelper.TABLE_CITY, new String[]{DBHelper.COLUMN_WEATHER_CITY_ID},
                        DBHelper.COLUMN_WEATHER_CITY + "=?", selectionArgs,
                        null, null, null);
                break;
        }
        return cursor;
    }

    /**
     * 根据URI 返回 MIME类型
     *
     * @param uri
     * @return
     */
    @Nullable
    @Override
    public String getType(Uri uri) {
        String mime_dir_header = "vnd.android.cursor.dir/vnd.com.example.databasetest.provider.";
        switch (sUriMatcher.match(uri)) {
            case PROVINCE:
                return mime_dir_header + ".province";
            case CITY:
                return mime_dir_header + ".city";
            case CITY_ID:
                return mime_dir_header + ".cityid";
            case WEATHERINFO_CURRENT:
                return mime_dir_header + ".weathercurrent";
            case WEATHERINFO_DAILY:
                return mime_dir_header + ".daily";

        }
        return null;
    }

    /**
     * 向数据库添加一条数据
     *
     * @param uri
     * @param values
     * @return
     */
    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = mDbHelper.getDBInstance();
        Uri uriReturn = null;
        switch (sUriMatcher.match(uri)) {
            case CITY:
                long cityDbId = db.insert(DBHelper.TABLE_CITY, null, values);
                uriReturn = Uri.parse("content://" + AUTHORITY + "/" + DBHelper.TABLE_CITY + "/" + cityDbId);
                break;
            case WEATHERINFO_CURRENT:
                db.beginTransaction();
                try {
                    db.delete(DBHelper.TABLE_CURRENT_WEATHER, null, null);
                    long weatherCurrentId = db.insert(DBHelper.TABLE_CURRENT_WEATHER, null, values);
                    uriReturn = Uri.parse("content://" + AUTHORITY + "/" + DBHelper.TABLE_CURRENT_WEATHER + "/" + weatherCurrentId);
                    db.setTransactionSuccessful();
                } catch (Exception e) {

                } finally {
                    db.endTransaction();
                }
                break;
            case WEATHERINFO_DAILY:
                db.beginTransaction();
                try {
                    long weatherDailyId = db.insert(DBHelper.TABLE_DAILY_WEATHER, null, values);
                    uriReturn = Uri.parse("content://" + AUTHORITY + "/" + DBHelper.TABLE_DAILY_WEATHER + "/" + weatherDailyId);
                    db.setTransactionSuccessful();
                } catch (Exception e) {

                } finally {
                    db.endTransaction();
                }
                break;
        }
        return uriReturn;
    }

    /**
     * 删除数据
     *
     * @param uri
     * @param selection
     * @param selectionArgs
     * @return
     */
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = mDbHelper.getDBInstance();
        switch (sUriMatcher.match(uri)){
            case WEATHERINFO_DAILY:
                db.delete(DBHelper.TABLE_DAILY_WEATHER, null, null);
                break;
        }
        return 0;
    }

    /**
     * 更新已有数据
     *
     * @param uri
     * @param values
     * @param selection
     * @param selectionArgs
     * @return
     */
    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
