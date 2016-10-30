package com.raymondqk.raysqlitepractice.utils.internet;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * Created by 陈其康 raymondchan on 2016/8/21 0021.
 * 网络工具类
 */
public class InternetUtils {
    /**
     * 查询是否有网络连接
     *
     * @param context
     * @return
     */
    public static boolean isNetworkConnected(Context context) {
        //获得"连接管家"
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //通过“连接管家”拿到“网络信息”小册子，它记录着本机网络连接相关信息
        //这里是获得当前活动的网络信息，不区分wifi、Mobile、Bluetooth等
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        //在“网络连接小册子”中查询是否连接着网络
        return (networkInfo != null && networkInfo.isConnected());
    }

    /**
     * 查询当前网络连接种类
     * wifi、Mobil？
     *
     * @param context
     * @return
     */
    public static int checkConnetedType(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        //先判断wifi，后判断Mobile
        //先获得记录wifi的“网络信息”小册子
        networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (networkInfo != null && networkInfo.isConnected())
            //networkInfo记录着对应的网络类型，是一个int常量 --->ConnectivityManager.TYPE_WIFI
            return networkInfo.getType();
        networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (networkInfo != null && networkInfo.isConnected()) {
            return networkInfo.getType();
        }
        return -1;
    }

    public static boolean checkConnected(Context context) {
        if (InternetUtils.isNetworkConnected(context)) {
            switch (InternetUtils.checkConnetedType(context)) {
                case ConnectivityManager.TYPE_WIFI:
                    Toast.makeText(context, "当前网络：WIFI", Toast.LENGTH_SHORT).show();
                    break;
                case ConnectivityManager.TYPE_MOBILE:
                    Toast.makeText(context, "当前网络:移动网络", Toast.LENGTH_SHORT).show();
                    break;
            }
            return true;
        } else {
            Toast.makeText(context, "无网络连接", Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}
