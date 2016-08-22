package com.raymondqk.raysqlitepractice.utils;

import android.text.TextUtils;
import android.util.Log;

import com.raymondqk.raysqlitepractice.model.UserInfo;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 陈其康 raymondchan on 2016/8/20 0020.
 */
public class SaxParseHandler extends DefaultHandler {

    public static final String NODE_USER = "user";
    public static final String TAG = "saxTest";
    private String nodeName;

    private int count = 0;

    private UserInfo userInfo;
    private List<UserInfo> userInfos;

    @Override
    public void startDocument() throws SAXException {
        //开始解析xml文档，初始化相关变量
        userInfo = new UserInfo();
        userInfos = new ArrayList<UserInfo>();
        Log.i(TAG, "=============开始解析xml文件================");
    }

    @Override
    public void endDocument() throws SAXException {
        Log.i(TAG, "=============结束解析xml文件================");
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {

        String string = new String(ch, start, length);
        if (TextUtils.equals(nodeName, "phone")) {
            Log.i(TAG, "解析 phone: " + string);
            //            String phone = new String(ch, start, length);
            userInfo.setPhone(string);
        } else if (TextUtils.equals(nodeName, "passwd")) {
            Log.i(TAG, "解析 passwd: " + string);
            //            String phone = new String(ch, start, length);
            userInfo.setPasswd(string);
        } else if (TextUtils.equals(nodeName, "logginstatus")) {
            Log.i(TAG, "解析 logginstatus: " + string);
            //            String phone = new String(ch, start, length);
            if (TextUtils.equals(string, "true")) {
                userInfo.setLogging(true);
            } else {
                userInfo.setLogging(false);
            }

        }
    }


    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        /**
         * 有一个问题，对user节点的解析，在characters里面判断会失效，是否说：
         * 父节点在startElement中处理
         * 子节点在characters()里面进行
         * 目前只能这样子
         *
         * 问题2：
         * list中的元素变量，必须在新节点时new一个新的，否则，list中的每一个元素都指向该变量，而该变量只管理一个对象，每次都修改了该对象
         */
        //开始解析节点
        nodeName = localName;
        if (TextUtils.equals(nodeName, NODE_USER)) {
            Log.i(TAG, "开始解析一个User节点: count=" + (count++));
            userInfo = new UserInfo();
        }
        //接下来会跳转到 characters()

    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (TextUtils.equals(localName, NODE_USER)) {
            Log.i(TAG, "解析完一个Use节点\n" + userInfo.toString());
            userInfos.add(userInfo);

        }
    }

    public List<UserInfo> getList() {
        return userInfos;
    }
}
