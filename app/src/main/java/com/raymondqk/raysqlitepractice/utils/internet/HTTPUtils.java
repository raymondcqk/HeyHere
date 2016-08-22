package com.raymondqk.raysqlitepractice.utils.internet;

import android.os.Bundle;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by 陈其康 raymondchan on 2016/8/21 0021.
 */
public class HTTPUtils {

    private static final String HTTP_HEAD = "http://";
    private static final String REQUEST_METHOD_GET = "GET";

    /**
     * HTTP请求函数
     *
     * @param website
     * @return
     */
    public static String getHTTPTest(String website, HttpCallback callback) {
        try {
            //准备url
            URL url = new URL(HTTP_HEAD + website);
            //打开网络http连接
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            //http请求 设置请求方式
            connection.setRequestMethod(REQUEST_METHOD_GET);
            //设置连接超时
            connection.setConnectTimeout(8000);
            //设置读取超时
            connection.setReadTimeout(8000);
            //开始读取数据
            //获取服务器返回的输入流
            InputStream in = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder builder = new StringBuilder();
            String line = "";
            while ((line = reader.readLine()) != null) {
                builder.append(line + "\n");
            }
            /*使用handler的基于消息的异步处理*/
            //            Message message = Message.obtain();
            //            message.what = MSG_WHAT_UPDATE_TEST;
            //            Bundle data = new Bundle();
            //            data.putString(DOWNLOAD_RESULT, builder.toString());
            //            message.setData(data);
            //            mHandler.sendMessage(message);

            //关闭http连接
            connection.disconnect();
            if (callback != null) {
                callback.onFinish(builder.toString());
            }

            return builder.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 提交一条 用户名+密码
     *
     * @param website
     * @param data
     */
    public static void postHTTPTest(String website, Bundle data) {
        try {
            URL url = new URL(HTTP_HEAD + website);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setConnectTimeout(8000);
            httpURLConnection.setReadTimeout(8000);

            //提交数据 --- 写数据到服务器
            DataOutputStream dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
            String username = data.getString("username");
            String passwd = data.getString("passwd");
            dataOutputStream.writeBytes("username=" + username + "&" + "passwd=" + passwd);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
