package com.raymondqk.raysqlitepractice.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.raymondqk.raysqlitepractice.R;
import com.raymondqk.raysqlitepractice.fragment.main.CareFragment;
import com.raymondqk.raysqlitepractice.fragment.main.DiscoverFragment;
import com.raymondqk.raysqlitepractice.fragment.main.MessageFragment;
import com.raymondqk.raysqlitepractice.fragment.main.PublishFragment;
import com.raymondqk.raysqlitepractice.fragment.main.UserFragment;
import com.raymondqk.raysqlitepractice.interfaces.DiscoverFgListener;
import com.raymondqk.raysqlitepractice.interfaces.PublishFgListener;
import com.raymondqk.raysqlitepractice.model.PubInfo;
import com.raymondqk.raysqlitepractice.utils.Constant;
import com.raymondqk.raysqlitepractice.utils.SharedPreferenceUtils;
import com.raymondqk.raysqlitepractice.utils.db.DBHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 陈其康 raymondchan on 2016/8/12 0012.
 */
public class MainActivity extends AppCompatActivity implements DiscoverFgListener, PublishFgListener {


    public static final int resid_seleted_tab_img = R.drawable.tabselected;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;
    private DiscoverFragment mDiscoverFragment;
    private LinearLayout mTab_discover;
    private LinearLayout mTab_care;
    private LinearLayout mTab_publish;
    private LinearLayout mTab_message;
    private LinearLayout mTab_user;
    private CareFragment mCareFragment;
    private PublishFragment mPublishFragment;
    private MessageFragment mMessageFragment;
    private UserFragment mUserFragment;
    private List<Fragment> mFragments;
    private View.OnClickListener mListenerTab;
    private ImageView mImg_tab_discover;
    private ImageView mImg_tab_care;
    private ImageView mImg_tab_publish;
    private ImageView mImg_tab_message;
    private ImageView mImg_tab_user;
    private int mCurrentTabIndex;
    private SharedPreferenceUtils mSharedPreferenceUtils;
    private String mCurrentUserPhone;
    private DBHelper mDbHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main_framework);
        initListener();
        initTab();
        initFragment();
        initUtils();
        initUser();

    }

    /**
     * 通知Fragment做View初始化
     */
    private void initDiscoverFgView() {
        //初始化discover_fragment
        //判断当前是否有“项目发布信息”数据
        //从数据库读取“项目发布信息”数据
        List<PubInfo> pubInfos = mDbHelper.getAllPubInfos();
        //调用Fragment的接口方法，程序去到Fragment类中，把数据给fragment处理，显示到listview
        mDiscoverFragment.initListView(pubInfos);
    }

    /**
     * 初始化用户信息，更新登陆状态，记录当前登陆用户
     */
    private void initUser() {
        Intent intent = getIntent();
        mCurrentUserPhone = intent.getStringExtra(Constant.KEY_LOGED_USERNAME);
        if (mCurrentUserPhone != null) {
            //若intent携带有用户手机号，代表是通过登陆进来的，非游客
            //记录最后登陆的用户手机号（用户名）
            mSharedPreferenceUtils.putData(Constant.SP_KEY_LAST_USER, mCurrentUserPhone);
            //更新当前用户登陆状态(数据库)---->日后通过网络进行，同步到服务器，记录用户登陆情况
            mDbHelper.updateLoginStatus(mCurrentUserPhone, Constant.LOGINSTATUS_LOGIN);

            //提示用户登陆成功
            //查询登陆状态
            int status = mDbHelper.getLoginStatus(mCurrentUserPhone);
            if (status == Constant.LOGINSTATUS_LOGIN) {
                Toast.makeText(MainActivity.this, "热烈欢迎！撒花！鼓掌！歌颂~\n" +
                                mCurrentUserPhone,
                        Toast.LENGTH_SHORT).show();
            }
        } else {
            //若intent未携带用户手机号，则代表是游客
            Toast.makeText(MainActivity.this, "热烈欢迎！撒花！鼓掌！歌颂~\n" +
                            "您当前身份：游客",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void initUtils() {
        //初始化SP
        mSharedPreferenceUtils = new SharedPreferenceUtils(this);
        //初始化数据库
        mDbHelper = DBHelper.getDBHelperInstance(this);
    }

    private void initListener() {
        //Tab监听器
        mListenerTab = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.tab_discover:
                        setTabSelected(Constant.INDEX_FRAGMENT_DISCOVER);
                        mCurrentTabIndex = Constant.INDEX_FRAGMENT_DISCOVER;
                        break;
                    case R.id.tab_care:
                        setTabSelected(Constant.INDEX_FRAGMENT_CARE);
                        mCurrentTabIndex = Constant.INDEX_FRAGMENT_CARE;
                        break;
                    case R.id.tab_publish:
                        setTabSelected(Constant.INDEX_FRAGMENT_PUBLISH);
                        mCurrentTabIndex = Constant.INDEX_FRAGMENT_PUBLISH;
                        break;
                    case R.id.tab_message:
                        setTabSelected(Constant.INDEX_FRAGMENT_MESSAGE);
                        mCurrentTabIndex = Constant.INDEX_FRAGMENT_MESSAGE;
                        break;
                    case R.id.tab_user:
                        setTabSelected(Constant.INDEX_FRAGMENT_USER);
                        mCurrentTabIndex = Constant.INDEX_FRAGMENT_USER;
                        break;
                }
            }
        };
    }

    private void setTabSelected(int index) {
        setTabImg(index);
        setFragmentSelected(index);
    }

    private void setFragmentSelected(int currentTabIndex) {

    }

    private void setTabImg(int index) {
        resetTabImg(mCurrentTabIndex);
        setTabImgSelected(index);
    }

    private void setTabImgSelected(int index) {
        switch (index) {
            case Constant.INDEX_FRAGMENT_DISCOVER:
                mImg_tab_discover.setImageResource(resid_seleted_tab_img);
                mFragmentTransaction.show(mDiscoverFragment);
                break;
            case Constant.INDEX_FRAGMENT_CARE:
                mImg_tab_care.setImageResource(resid_seleted_tab_img);
                mFragmentTransaction.show(mCareFragment);
                break;
            case Constant.INDEX_FRAGMENT_PUBLISH:
                mImg_tab_publish.setImageResource(resid_seleted_tab_img);
                mFragmentTransaction.show(mPublishFragment);
                break;
            case Constant.INDEX_FRAGMENT_MESSAGE:
                mImg_tab_message.setImageResource(resid_seleted_tab_img);
                mFragmentTransaction.show(mMessageFragment);
                break;
            case Constant.INDEX_FRAGMENT_USER:
                mImg_tab_user.setImageResource(resid_seleted_tab_img);
                mFragmentTransaction.show(mUserFragment);
                break;
        }
        mFragmentTransaction.commit();
    }

    private void resetTabImg(int currentTabIndex) {
        mFragmentTransaction = mFragmentManager.beginTransaction();
        switch (currentTabIndex) {
            case Constant.INDEX_FRAGMENT_DISCOVER:
                mImg_tab_discover.setImageResource(R.drawable.lookup);
                mFragmentTransaction.hide(mDiscoverFragment);
                break;
            case Constant.INDEX_FRAGMENT_CARE:
                mImg_tab_care.setImageResource(R.drawable.eye_open);
                mFragmentTransaction.hide(mCareFragment);
                break;
            case Constant.INDEX_FRAGMENT_PUBLISH:
                mImg_tab_publish.setImageResource(R.drawable.ultra_ball_96px);
                mFragmentTransaction.hide(mPublishFragment);
                break;
            case Constant.INDEX_FRAGMENT_MESSAGE:
                mImg_tab_message.setImageResource(R.drawable.conversation_96px);
                mFragmentTransaction.hide(mMessageFragment);
                break;
            case Constant.INDEX_FRAGMENT_USER:
                mImg_tab_user.setImageResource(R.drawable.head_bear);
                mFragmentTransaction.hide(mUserFragment);
                break;
        }
    }

    private void initTab() {
        mTab_discover = (LinearLayout) findViewById(R.id.tab_discover);
        mTab_care = (LinearLayout) findViewById(R.id.tab_care);
        mTab_publish = (LinearLayout) findViewById(R.id.tab_publish);
        mTab_message = (LinearLayout) findViewById(R.id.tab_message);
        mTab_user = (LinearLayout) findViewById(R.id.tab_user);

        //设置tab点击事件
        mTab_care.setOnClickListener(mListenerTab);
        mTab_discover.setOnClickListener(mListenerTab);
        mTab_publish.setOnClickListener(mListenerTab);
        mTab_message.setOnClickListener(mListenerTab);
        mTab_user.setOnClickListener(mListenerTab);

        mImg_tab_discover = (ImageView) findViewById(R.id.img_tab_discover);
        mImg_tab_care = (ImageView) findViewById(R.id.img_tab_care);
        mImg_tab_publish = (ImageView) findViewById(R.id.img_tab_publish);
        mImg_tab_message = (ImageView) findViewById(R.id.img_tab_message);
        mImg_tab_user = (ImageView) findViewById(R.id.img_tab_user);
    }

    private void initFragment() {
        //实例化fragment
        mDiscoverFragment = DiscoverFragment.getInstance(this);
        mCareFragment = new CareFragment();
        mPublishFragment = PublishFragment.getInstance(this);
        mMessageFragment = new MessageFragment();
        mUserFragment = new UserFragment();
        //添加到Fragment列表中，方便遍历
        mFragments = new ArrayList<Fragment>();
        mFragments.add(mDiscoverFragment);
        mFragments.add(mCareFragment);
        mFragments.add(mPublishFragment);
        mFragments.add(mMessageFragment);
        mFragments.add(mUserFragment);

        //初始化FragmentManager
        mFragmentManager = getFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        for (Fragment f : mFragments) {
            mFragmentTransaction.add(R.id.main_layout_fragment, f);
        }
        for (Fragment f : mFragments) {
            mFragmentTransaction.hide(f);
        }
        mFragmentTransaction.show(mDiscoverFragment);
        mFragmentTransaction.commit();
        mCurrentTabIndex = Constant.INDEX_FRAGMENT_DISCOVER;
        mImg_tab_discover.setImageResource(resid_seleted_tab_img);
    }

    @Override
    protected void onDestroy() {
        //当被销毁时，同步数据库 登录状态：下线
        if (mCurrentUserPhone != null) {
            mDbHelper.updateLoginStatus(mCurrentUserPhone, Constant.LOGINSTATUS_LOGOUT);
        }
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        //        super.onBackPressed();
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("退出提示");
        dialog.setIcon(R.drawable.exit);
        dialog.setMessage("主银要离开我了吗？");
        dialog.setCancelable(false);
        dialog.setPositiveButton("狠心", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (mCurrentUserPhone != null) {
                    mDbHelper.updateLoginStatus(mCurrentUserPhone, Constant.LOGINSTATUS_LOGOUT);
                }

                System.exit(0);//调用这个函数才能释放蓝牙资源
                /*finish是Activity的类，仅仅针对Activity，当调用finish()时，
                只是将活动推向后台，并没有立即释放内存，活动的资源并没有被清理；
                当调用System.exit(0)时，杀死了整个进程，这时候活动所占的资源也会被释放。*/
            }
        });
        dialog.setNegativeButton("爱你", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }
        );
        dialog.show();
    }

    /**
     * DiscoverFragment的回调
     *
     * @param what
     */
    @Override
    public void discoverFgCallback(int what) {
        switch (what) {
            //请求码：初始化view -- 给listview上数据
            case DiscoverFragment.WHAT_INITVIEW:
                initDiscoverFgView();
                break;
        }
    }

    /**
     * 发布信息Fg回调
     * @param what
     */
    @Override
    public void publishFgCallback(int what) {
        switch (what) {
            case PublishFragment.WHAT_BTN_PUBLISH:
                Intent intent = new Intent(MainActivity.this, SetPubInfoActivity.class);
                startActivityForResult(intent, Constant.REQUEST_CODE_Publish);
                break;
        }

    }

    /**
     * ActivityResult回调
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /*回调情况1：
        * =====添加完项目发布信息到数据库了=====*/
        if (requestCode == Constant.REQUEST_CODE_Publish&&resultCode ==Constant.RESULT_CODE_Publish ){
            // TODO: 2016/8/15 0015 需要优化
            //从数据库读取更新数据
            mDiscoverFragment.updatePubinfoLv();
        }
    }
}

