package com.raymondqk.raysqlitepractice.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.widget.TextView;

import com.raymondqk.raysqlitepractice.R;
import com.raymondqk.raysqlitepractice.model.UserInfo;
import com.raymondqk.raysqlitepractice.utils.SaxParseHandler;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by 陈其康 raymondchan on 2016/8/20 0020.
 */
public class WeatherActivity extends AppCompatActivity {

    public static final String HTTP_HEAD = "http://";
    public static final String REQUEST_METHOD_GET = "GET";
    public static final int MSG_WHAT_UPDATE_TEST = 1;
    public static final String DOWNLOAD_RESULT = "downloadResult";
    private TextView mTest;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_WHAT_UPDATE_TEST:
                    Bundle data = msg.getData();
                    mTest.setText(data.getString(DOWNLOAD_RESULT));
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_weather);
        initView();
        //        new Thread(new Runnable() {
        //            @Override
        //            public void run() {
        //                httpTest();
        //            }
        //        }).start();

    }

    @Override
    protected void onResume() {
        super.onResume();
        //网络请求
        //        new HttpGetTast().execute();

        //读取xml数据
        String xmlStr = getXMLFromRaw();

        //sax解析xml结果处理
        //        saxXmlResultTest(xmlStr);

        //pull解析xml
        parseXMLWithPull(xmlStr);
    }

    private void saxXmlResultTest(String xmlStr) {
        List<UserInfo> list = parseXMLWithSAX(xmlStr);
        StringBuilder builder = new StringBuilder();
        builder.setLength(0);
        for (UserInfo userInfo : list) {
            builder.append(userInfo.toString() + "\n");
        }
        mTest.setText(builder.toString());
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
        mTest = (TextView) findViewById(R.id.tv_weather_test);
    }


    private String httpTest() {
        try {
            //准备url
            String website = "www.baidu.com";
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
            return builder.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    class HttpGetTast extends AsyncTask<Void, Integer, String> {

        /**
         * 主线程，初始化ui
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            mTest.setText("onPreExecute() -- 准备get");

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
            return httpTest();
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
            mTest.setText(values[0] + "%");
        }

        /**
         * 结果操作，主线程
         *
         * @param s
         */
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            mTest.setText(s);
        }
    }

    /**
     * SAX解析xml文件
     * 主题处理部分在自创建的handler类
     */
    private List<UserInfo> parseXMLWithSAX(String xmlData) {

        /*通过SAX的工厂获得SAXParser对象，从而获得xmlReader对象。*/
        /*给XMLReader设置其解析xml节点的handler*/
        /*最后把xml文件中读到的数据字符串给xmlReader解析即可*/
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            XMLReader xmlReader = factory.newSAXParser().getXMLReader();
            SaxParseHandler handler = new SaxParseHandler();
            xmlReader.setContentHandler(handler);
            xmlReader.parse(new InputSource(new StringReader(xmlData)));
            List<UserInfo> list = handler.getList();
            return list;
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 通过pull解析xml
     * @param xmlData
     * @return
     */
    private List<UserInfo> parseXMLWithPull(String xmlData) {
        UserInfo userInfo = new UserInfo();
        List<UserInfo> userInfos = new ArrayList<UserInfo>();
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = factory.newPullParser();
            xmlPullParser.setInput(new StringReader(xmlData));
            //数据装载完毕，开始进行解析
            int eventType = xmlPullParser.getEventType();
            while (eventType != xmlPullParser.END_DOCUMENT) {
                String nodeName = xmlPullParser.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if ("user".equals(nodeName)) {
                            Log.i("xmlTest", "开始解析User");
                            userInfo = new UserInfo();
                        }
                        if ("phone".equals(nodeName)) {
                            userInfo.setPhone(xmlPullParser.nextText());
                            Log.i("xmlTest", userInfo.getPhone());
                        } else if ("passwd".equals(nodeName)) {
                            userInfo.setPasswd(xmlPullParser.nextText());
                            Log.i("xmlTest", userInfo.getPasswd());
                        } else if ("logginstatus".equals(nodeName)) {
                            userInfo.setLogging(Boolean.parseBoolean(xmlPullParser.nextText()));
                            Log.i("xmlTest", userInfo.isLogging() ? "true" : "false");
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (nodeName.equals("user")) {
                            Log.i("xmlTest", userInfo.toString());
                            userInfos.add(userInfo);
                        }
                        break;
                }
                eventType = xmlPullParser.next();
            }
            //            userInfo = null;
            StringBuilder b = new StringBuilder();
            for (UserInfo u : userInfos) {
                b.append(u.toString() + "\n");
            }
            mTest.setText(b.toString());

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
