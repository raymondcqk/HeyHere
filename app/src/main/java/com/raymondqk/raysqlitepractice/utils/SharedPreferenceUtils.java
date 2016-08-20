package com.raymondqk.raysqlitepractice.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by 陈其康 raymondchan on 2016/8/13 0013.
 */
public class SharedPreferenceUtils {

    private Context mContext;
    private static SharedPreferences mSharedPreferences;
    public final static String SP_NAME = "com.raymondcqk.here";
    private static SharedPreferences.Editor mEditor;

    public SharedPreferenceUtils(Context context) {
        getSharedPreferencesInstance(context);
    }

    /**
     * 获得sp单例
     *
     * @param context
     * @return
     */
    public static SharedPreferences getSharedPreferencesInstance(Context context) {
        if (mSharedPreferences == null) {
            mSharedPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
            mEditor = mSharedPreferences.edit();
        }
        return mSharedPreferences;
    }

    /**
     * 存数据
     *
     * @param key
     * @param data
     */
    public void putData(String key, String data) {
        mEditor.putString(key, data);
        mEditor.apply();
    }

    /**
     * 存数据
     *
     * @param key
     * @param data
     */
    public void putData(String key, boolean data) {
        mEditor.putBoolean(key, data);
        mEditor.apply();
    }

    /**
     * 读取String数据，若不存在，返回“”
     *
     * @param key
     * @return
     */
    public String getStrData(String key) {
        return mSharedPreferences.getString(key, "");
    }

    /**
     * 去读Boolean，若不存在，返回true
     *
     * @param key
     * @return
     */
    public boolean getBoolData(String key) {
        return mSharedPreferences.getBoolean(key, true);
    }

}
