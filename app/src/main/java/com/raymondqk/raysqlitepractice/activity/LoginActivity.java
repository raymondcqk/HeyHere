package com.raymondqk.raysqlitepractice.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.raymondqk.raysqlitepractice.R;
import com.raymondqk.raysqlitepractice.utils.db.DBHelper;
import com.raymondqk.raysqlitepractice.utils.Constant;
import com.raymondqk.raysqlitepractice.utils.SharedPreferenceUtils;

/**
 * Created by 陈其康 raymondchan on 2016/8/13 0013.
 */
public class LoginActivity extends Activity implements View.OnClickListener {

    public static final int REQUEST_CODE_REGIST = 3;
    public static final int RESULT_CODE_REGIST = 3;
    private EditText mEdt_username;
    private EditText mEdt_passwd;
    private ImageView mEye;
    private Button mBtn_log;
    private Button mBtn_regist;
    private Button mBtn_try;
    private DBHelper mDbHelper;
    private SharedPreferenceUtils mSpUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initUtils();
        initView();
        if (!TextUtils.equals("", mSpUtils.getStrData(Constant.SP_KEY_LAST_USER))) {
            mEdt_username.setText(mSpUtils.getStrData(Constant.SP_KEY_LAST_USER));
        }
        initSetting();
    }

    private void initSetting() {
        // TODO: 2016/8/14 0014 测试GuidePage
                mSpUtils.putData(Constant.SP_KEY_FIRST_LAUNCH, false);
//        mSpUtils.putData(Constant.SP_KEY_FIRST_LAUNCH, true);
    }

    private void initView() {
        mBtn_log = (Button) findViewById(R.id.btn_login);
        mBtn_regist = (Button) findViewById(R.id.btn_regist);
        mBtn_try = (Button) findViewById(R.id.btn_try);
        mBtn_log.setOnClickListener(this);
        mBtn_regist.setOnClickListener(this);
        mBtn_try.setOnClickListener(this);

        mEdt_username = (EditText) findViewById(R.id.login_username);
        mEdt_passwd = (EditText) findViewById(R.id.login_passwd);

        mEye = (ImageView) findViewById(R.id.login_img_eye);

        mEdt_passwd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus == true) {
                    mEye.setImageResource(R.drawable.eye_closed);
                } else {
                    mEye.setImageResource(R.drawable.eye_open);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                mEye.setImageResource(R.drawable.logging);
                mDbHelper = DBHelper.getDBHelperInstance(LoginActivity.this);
                String phone = mEdt_username.getText().toString();
                String[] infos = mDbHelper.getUserNamePasswd(phone);
                if (infos == null) {
                    Toast.makeText(LoginActivity.this, "用户名错误！", Toast.LENGTH_SHORT).show();
                    return;
                }
                //若数据库密码和用户输入一致，则登陆
                if (TextUtils.equals(infos[1], mEdt_passwd.getText().toString())) {
                    Intent loged_intent = new Intent(LoginActivity.this, MainActivity.class);
                    loged_intent.putExtra(Constant.KEY_LOGED_USERNAME, infos[0]);

                    startActivity(loged_intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "密码错误!\n" + infos[0] + ":" + infos[1], Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_regist:
                mEye.setImageResource(R.drawable.logging);
                Intent regist_intnet = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivityForResult(regist_intnet, REQUEST_CODE_REGIST);
                break;
            case R.id.btn_try:
                mEye.setImageResource(R.drawable.logging);
                Intent try_intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(try_intent);
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_REGIST && resultCode == RESULT_CODE_REGIST) {
            mEdt_username.setText(data.getStringExtra(RegisterActivity.KEY_REG_PHONE));
        }
    }

    private void initUtils() {
        mSpUtils = new SharedPreferenceUtils(this);
    }
}
