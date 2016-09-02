package com.raymondqk.raysqlitepractice.activity;

import android.app.Activity;
import android.content.ContentValues;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.raymondqk.raysqlitepractice.R;
import com.raymondqk.raysqlitepractice.utils.Constant;
import com.raymondqk.raysqlitepractice.utils.db.DBHelper;

import java.util.ArrayList;

/**
 * Created by 陈其康 raymondchan on 2016/8/11 0011.
 */
public class SetPubInfoActivity extends Activity implements View.OnClickListener {

    public static final String TAG_TEST = "test";
    private Button mBtn_add2db_publish_info;
    private EditText mEdt_orgnization;
    private EditText mEdt_project;
    private EditText mEdt_publisher;
    private EditText mEdt_pubdate;
    private EditText mEdt_deadline;
    private EditText mEdt_brief;
    private DBHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_write);
        initDB();
        initView();
    }

    private void initView() {
        mEdt_orgnization = (EditText) findViewById(R.id.edt_orgnization);
        mEdt_project = (EditText) findViewById(R.id.edt_project);
        mEdt_publisher = (EditText) findViewById(R.id.edt_publisher);
        mEdt_pubdate = (EditText) findViewById(R.id.edt_pubdate);
        mEdt_deadline = (EditText) findViewById(R.id.edt_deadline);
        mEdt_brief = (EditText) findViewById(R.id.edt_brief);

        mBtn_add2db_publish_info = (Button) findViewById(R.id.btn_add_publish_info);
        mBtn_add2db_publish_info.setOnClickListener(this);

        mEdt_project.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus == false) {
                    ArrayList<String> titles = mDbHelper.getProjectTitleFromDB();
                    String tmpTitle = mEdt_project.getText().toString();
                    if (titles.contains(tmpTitle)) {
                        mEdt_project.setTextColor(Color.RED);
                        Toast.makeText(SetPubInfoActivity.this, "该名称已存在！请修改", Toast.LENGTH_SHORT).show();
                    }
                }
                if (hasFocus == true) {
                    mEdt_project.setTextColor(Color.BLACK);
                }
            }
        });

    }

    private void initDB() {
        mDbHelper = DBHelper.getDBHelperInstance(this);
        //因为当磁盘满了，新建数据库的操作会抛出异常，onCreate中数据库语句错误，也会抛出异常
        //若本身就已经创建的数据库，且无升级项，则直接获得存储上db，而不执行onCreate
//        mDb = mDbHelper.getDBInstance();
    }

    private long insertDB(ContentValues cv) {
        if (mDbHelper != null) {
            return mDbHelper.insertPubInfo(cv);
        }
        //若返回-2，代表当前数据库未被创建或读取
        return -2;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_publish_info:
                long rowNum = insertDB(getPubInfoCV());
                if ((rowNum != -1) && (rowNum != -2)) {
                    Toast.makeText(SetPubInfoActivity.this, "数据插入成功,rowNum: " + rowNum, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SetPubInfoActivity.this, "数据插入失败,ErrorCode: " + rowNum, Toast.LENGTH_SHORT).show();
                }
                setResult(Constant.RESULT_CODE_Publish);
                finish();
                break;
        }
    }

    @NonNull
    private ContentValues getPubInfoCV() {
        ContentValues cv = new ContentValues();
        cv.put(DBHelper.COLUMN_ORGNIZATION, mEdt_orgnization.getText().toString());
        cv.put(DBHelper.COLUMN_PUBLISHER, mEdt_publisher.getText().toString());
        cv.put(DBHelper.COLUMN_PROJECT, mEdt_project.getText().toString());
        cv.put(DBHelper.COLUMN_PUBDATE, mEdt_pubdate.getText().toString());
        cv.put(DBHelper.COLUMN_DEADLINE, mEdt_deadline.getText().toString());
        cv.put(DBHelper.COLUMN_BRIEF, mEdt_brief.getText().toString());
        return cv;
    }
}
