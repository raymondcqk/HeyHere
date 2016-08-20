package com.raymondqk.raysqlitepractice.utils;

/**
 * Created by 陈其康 raymondchan on 2016/8/13 0013.
 */
public class Constant {

    public static final String SP_KEY_LAST_USER = "lastUser";
    public static final String SP_KEY_FIRST_LAUNCH = "isFirstLaunch";

    public static final String KEY_LOGED_USERNAME = "key_loged_username";

    public static final int REQUEST_CODE_CHANGE_PUBINFO = 1;
    public static final int RESULT_CODE_CHANGE_PUBINFO = 1;
    public static final int REQUEST_CODE_Publish = 2;
    public static final int RESULT_CODE_Publish = 2;


    public static final int INDEX_FRAGMENT_DISCOVER = 0;
    public static final int INDEX_FRAGMENT_CARE = 1;
    public static final int INDEX_FRAGMENT_PUBLISH = 2;
    public static final int INDEX_FRAGMENT_MESSAGE = 3;
    public static final int INDEX_FRAGMENT_USER = 4;

    public static final int LOGINSTATUS_LOGIN = 1;
    public static final int LOGINSTATUS_LOGOUT = 0;

    //从discover的list点击跳转到详情页用到的key
    public static final String KEY_OBJ_PUBINFO = "obj";
    public static final String KEY_POSITION = "position";
    public static final String PROJECT_NAME = "project_name";
}
