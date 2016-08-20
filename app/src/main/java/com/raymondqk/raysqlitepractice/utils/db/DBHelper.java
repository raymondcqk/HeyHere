package com.raymondqk.raysqlitepractice.utils.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.raymondqk.raysqlitepractice.activity.SetPubInfoActivity;
import com.raymondqk.raysqlitepractice.model.PubInfo;

import java.util.ArrayList;

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
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_PHONE = "phone";
    public static final String COLUMN_PASSWD = "passwd";
    public static final String COLUMN_LOGINSTATE = "loging_status";

    public static final String DB_NAME = "Project.db";

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
                COLUMN_ID + " integer primary key autoincrement," +
                COLUMN_ORGNIZATION + " text," +
                COLUMN_PUBLISHER + " text," +
                COLUMN_PUBDATE + " integer," +
                COLUMN_DEADLINE + " integer," +
                COLUMN_PROJECT + " text," +
                COLUMN_BRIEF + " text)");
        Log.i(SetPubInfoActivity.TAG_TEST, TABLE_PUBLISHER + " table created");

        db.execSQL("create table " + TABLE_USERINFO + "(" +
                "id integer primary key autoincrement," +
                COLUMN_PHONE + " text," +
                COLUMN_PASSWD + " text," +
                COLUMN_LOGINSTATE + " integer)");
        Log.i(SetPubInfoActivity.TAG_TEST, TABLE_USERINFO + " table created");
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
    public ArrayList<String> getProTitleFromDB() {
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

    public long insertPubInfo(ContentValues cv) {
        return mDatabase.insert(TABLE_PUBLISHER, null, cv);
    }

    public void closeDB() {
        mDatabase.close();
        mDBHelper.close();
    }
}
