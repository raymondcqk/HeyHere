package com.raymondqk.raysqlitepractice.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.raymondqk.raysqlitepractice.R;
import com.raymondqk.raysqlitepractice.model.PubInfo;
import com.raymondqk.raysqlitepractice.utils.Constant;

/**
 * Created by 陈其康 raymondchan on 2016/8/12 0012.
 */
public class PubinfoChangeActivity extends AppCompatActivity {


    private PubInfo mPubInfo;
    private EditText mEdt_orgnization;
    private EditText mEdt_project;
    private EditText mEdt_publisher;
    private EditText mEdt_pubdate;
    private EditText mEdt_deadline;
    private EditText mEdt_brief;
    private Button mBtn_add2db_publish_info;
    private int mPosition;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepubinfo);
        Intent intent = getIntent();
        //从MainActivity的列表中的被点击项中传来该被点击项的对象数据
        mPubInfo = (PubInfo) intent.getSerializableExtra(Constant.KEY_OBJ_PUBINFO);
        //记录该被点击项所在的list的index（position），根据他来更新listview的list数据源对应元素的数据
        mPosition = intent.getIntExtra(Constant.KEY_POSITION, -1);

        getSupportActionBar().setTitle("修改发布信息");
        getSupportActionBar().setSubtitle("标题: " + mPubInfo.getProject());
        initView();


    }

    private void initView() {
        mEdt_orgnization = (EditText) findViewById(R.id.edt_orgnization);
        mEdt_project = (EditText) findViewById(R.id.edt_project);
        mEdt_publisher = (EditText) findViewById(R.id.edt_publisher);
        mEdt_pubdate = (EditText) findViewById(R.id.edt_pubdate);
        mEdt_deadline = (EditText) findViewById(R.id.edt_deadline);
        mEdt_brief = (EditText) findViewById(R.id.edt_brief);

        mEdt_orgnization.setText(mPubInfo.getOrgnization());
        mEdt_publisher.setText(mPubInfo.getPublisher());
        mEdt_brief.setText(mPubInfo.getBrief());
        mEdt_project.setText(mPubInfo.getProject());
        mEdt_pubdate.setText(mPubInfo.getPubdate());
        mEdt_deadline.setText(mPubInfo.getDeadline());

        mBtn_add2db_publish_info = (Button) findViewById(R.id.btn_change_publish_info);
        mBtn_add2db_publish_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //更新对象数据
                mPubInfo.setProject(mEdt_project.getText().toString());
                mPubInfo.setBrief(mEdt_brief.getText().toString());
                mPubInfo.setOrgnization(mEdt_orgnization.getText().toString());
                mPubInfo.setPublisher(mEdt_publisher.getText().toString());
                mPubInfo.setPubdate(mEdt_pubdate.getText().toString());
                mPubInfo.setDeadline(mEdt_deadline.getText().toString());
                //传送修改好数据的对象给Activity Result
                Intent intent = new Intent(PubinfoChangeActivity.this, MainActivity.class);
                //                Intent intent = new Intent();
                intent.putExtra(Constant.KEY_OBJ_PUBINFO, mPubInfo);
                intent.putExtra(Constant.KEY_POSITION, mPosition);
                setResult(Constant.RESULT_CODE_CHANGE_PUBINFO, intent);
                finish();
            }
        });
    }
}
