package com.raymondqk.raysqlitepractice.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.raymondqk.raysqlitepractice.R;
import com.raymondqk.raysqlitepractice.utils.Constant;
import com.raymondqk.raysqlitepractice.utils.db.DBHelper;

/**
 * Created by 陈其康 raymondchan on 2016/8/13 0013.
 */
public class RegisterActivity extends AppCompatActivity {

    public static final String KEY_REG_PHONE = "key_reg_phone";
    private EditText mEdit_phone;
    private EditText mEdit_passwd;
    private Button mBtn_regist;
    private ImageView mEye;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_regist);

        toolbarInit();
        viewInti();


    }

    private void viewInti() {
        mEdit_phone = (EditText) findViewById(R.id.edt_reg_phone);
        mEdit_passwd = (EditText) findViewById(R.id.edt_reg_passwd);
        mBtn_regist = (Button) findViewById(R.id.btn_reg_regist);
        mEye = (ImageView) findViewById(R.id.img_reg_eye);

        mEdit_passwd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus == true) {
                    mEye.setImageResource(R.drawable.eye_closed);
                } else {
                    mEye.setImageResource(R.drawable.lookup);
                }
            }
        });

        mBtn_regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEye.setImageResource(R.drawable.logging);
                String phone = mEdit_phone.getText().toString();
                String passwd = mEdit_passwd.getText().toString();
                //获得DBHelper
                DBHelper dbHelper = DBHelper.getDBHelperInstance(RegisterActivity.this);
                //插入 用户注册信息 到数据库
                long rawNum = dbHelper.insertUserRegist(phone, passwd, Constant.LOGINSTATUS_LOGOUT);
                if (rawNum != -1) {
                    Toast.makeText(RegisterActivity.this, "注册信息插入成功: " + rawNum, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RegisterActivity.this, "注册信息插入失败", Toast.LENGTH_SHORT).show();
                }

                //回传注册成功的数据给LoginActivity
                Intent i = new Intent();
                i.putExtra(KEY_REG_PHONE, phone);
                setResult(LoginActivity.RESULT_CODE_REGIST, i);

                finish();

            }
        });
    }

    private void toolbarInit() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.regist_toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
