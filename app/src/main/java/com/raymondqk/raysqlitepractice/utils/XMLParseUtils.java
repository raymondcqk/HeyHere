package com.raymondqk.raysqlitepractice.utils;

import android.util.Log;

import com.raymondqk.raysqlitepractice.model.UserInfo;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by 陈其康 raymondchan on 2016/8/21 0021.
 */
public class XMLParseUtils {
    /**
     * SAX解析xml文件
     * 主题处理部分在自创建的handler类
     */
    public static List<UserInfo> parseXMLWithSAX(String xmlData) {

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
     *
     * @param xmlData
     * @return
     */
    public static List<UserInfo> parseXMLWithPull(String xmlData) {
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
            return userInfos;

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
