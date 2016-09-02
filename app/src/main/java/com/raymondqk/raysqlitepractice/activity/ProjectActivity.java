package com.raymondqk.raysqlitepractice.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.raymondqk.raysqlitepractice.R;
import com.raymondqk.raysqlitepractice.model.PubInfo;
import com.raymondqk.raysqlitepractice.utils.Constant;
import com.raymondqk.raysqlitepractice.utils.FileUtils;
import com.raymondqk.raysqlitepractice.utils.VerifyPermissionUtils;
import com.raymondqk.raysqlitepractice.utils.db.DBHelper;

/**
 * Created by 陈其康 raymondchan on 2016/8/12 0012.
 */
public class ProjectActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private TextView mTv_title;
    private TextView mTv_org;
    private TextView mTv_puber;
    private TextView mTv_pubdate;
    private TextView mTv_content;
    private PubInfo mPubInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_project_detail);
        //进入项目详情页之后，立马获取外部存储器读写权限,因为这里有文件读写的功能，而API23+需要从代码层面获取权限。
        VerifyPermissionUtils.verifyStoragePermission(ProjectActivity.this);
        initToolbar();
        initView();
        mPubInfo = initData();
        setData(mPubInfo);

    }

    private PubInfo initData() {
        DBHelper dbHelper = DBHelper.getDBHelperInstance(this);
        return dbHelper.getPubInfoFromDB(getIntent().getStringExtra(Constant.PROJECT_NAME));
    }

    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.project_toolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mToolbar.inflateMenu(R.menu.project_detail_menu);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    //菜单项：导出到文件
                    case R.id.menu_projectdetail_file:
                        Toast.makeText(ProjectActivity.this, "导出到文件", Toast.LENGTH_SHORT).show();
                        FileUtils fileUtils = new FileUtils(ProjectActivity.this);
                        fileUtils.createFilePubinfo(mPubInfo);
                        break;
                }
                return true;
            }
        });
    }

    private void setData(PubInfo pubInfo) {
        mTv_title.setText(pubInfo.getProject());
        mTv_org.setText(pubInfo.getOrgnization());
        mTv_pubdate.setText(pubInfo.getPubdate());
        mTv_puber.setText(pubInfo.getPublisher());
        mTv_content.setText(pubInfo.getBrief());
    }

    private void initView() {
        mTv_title = (TextView) findViewById(R.id.pro_tv_title);
        mTv_org = (TextView) findViewById(R.id.pro_tv_orgnization);
        mTv_puber = (TextView) findViewById(R.id.pro_tv_publisher);
        mTv_pubdate = (TextView) findViewById(R.id.pro_tv_pubdate);
        mTv_content = (TextView) findViewById(R.id.pro_tv_pro_content);
    }


}
