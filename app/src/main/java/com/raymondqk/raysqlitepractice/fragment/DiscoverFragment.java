package com.raymondqk.raysqlitepractice.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.raymondqk.raysqlitepractice.R;
import com.raymondqk.raysqlitepractice.activity.ProjectActivity;
import com.raymondqk.raysqlitepractice.adapter.PubInfoLvAdapter;
import com.raymondqk.raysqlitepractice.interfaces.DiscoverFgListener;
import com.raymondqk.raysqlitepractice.model.PubInfo;
import com.raymondqk.raysqlitepractice.utils.Constant;
import com.raymondqk.raysqlitepractice.utils.db.DBHelper;

import java.util.List;

/**
 * Created by 陈其康 raymondchan on 2016/8/14 0014.
 */
public class DiscoverFragment extends Fragment {

    public static final int WHAT_INITVIEW = 0;
    private View mView;
    private TextView mTv_guide;
    private ListView mLv_pubinfos;
    private List<PubInfo> mPubInfoList;
    private static Context mContext;
    private PubInfoLvAdapter mPubInfoLvAdapter;
    private static DiscoverFragment mDiscoverFragment;
    private DiscoverFgListener mLActivity;
    private DBHelper mDbHelper;
    private int mLv_selected_positon;

    public static DiscoverFragment getInstance(Context context) {
        if (mDiscoverFragment == null) {
            mDiscoverFragment = new DiscoverFragment();
        }
        mContext = context;
        return mDiscoverFragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mLActivity = (DiscoverFgListener) activity;
        mDbHelper = DBHelper.getDBHelperInstance(mContext);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_discover, null);
        initView();
        //fragment的view控件初始化完毕（获取到实例引用），通知Activity做后续工作----给控件上数据，listview绑定adapter等
        mLActivity.discoverFgCallback(WHAT_INITVIEW);
        return mView;
    }

    private void initView() {
        mTv_guide = (TextView) mView.findViewById(R.id.tv_discover_guide);
        mLv_pubinfos = (ListView) mView.findViewById(R.id.lv_discover_pubinfo);
        mLv_pubinfos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO: 2016/8/12 0012 这里暂时把修改选修页面关掉
                //                goToChangePubinfo(position);
                //跳转至详情页
                Intent i = new Intent(mContext, ProjectActivity.class);
                i.putExtra(Constant.PROJECT_NAME, mPubInfoList.get(position).getProject());
                startActivity(i);
            }
        });
        //长按删除
        mLv_pubinfos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                mLv_selected_positon = position;
                AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
                dialog.setTitle("操作提示");
                dialog.setIcon(R.drawable.exit);
                dialog.setMessage("删除当前项目信息？");
                dialog.setCancelable(false);
                dialog.setPositiveButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mDbHelper.deleteAProject(mPubInfoList.get(mLv_selected_positon).getProject());
                        mPubInfoList.remove(mLv_selected_positon);
                        //                        mPubInfoLvAdapter.updateListView(mPubInfoList);
                        updatePubinfoLv();
                    }
                });
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }
                );
                dialog.show();

                return true;
            }
        });
    }

    //初始化界面
    public void initListView(List<PubInfo> pubInfos) {
        //初始化guide提示
        mPubInfoList = pubInfos;
        if (mPubInfoList.size() == 0) {
            mTv_guide.setVisibility(View.VISIBLE);
        } else {
            mTv_guide.setVisibility(View.GONE);
        }
        //初始化listview数据
        setLv_pubinfos();
    }

    //设置“项目发布信息”listview
    private void setLv_pubinfos() {
        mPubInfoLvAdapter = new PubInfoLvAdapter(mContext, mPubInfoList);
        mLv_pubinfos.setAdapter(mPubInfoLvAdapter);

    }


    /**
     * 发布项目信息添加到数据之后，更新listview
     */
    public void updatePubinfoLv() {

        // TODO: 2016/8/17 0017 若数据量大，这样发布一次，都要从数据库读取所有数据。有没方案可以优化，直接取得新添加的数据，或减少对数据库查询的次数与规模
        //从数据库读取所有项目发布信息 ======
        mPubInfoList = mDbHelper.getAllPubInfos();
        if (mPubInfoLvAdapter == null) {
            //数据从无到创建，要进行初始化，因为之前从数据库没读到数据时，无法对其进行初始化
//            mPubInfoLvAdapter = new PubInfoLvAdapter(mContext, mPubInfoList);
//            mLv_pubinfos.setAdapter(mPubInfoLvAdapter);
        } else {
            mPubInfoLvAdapter.updateListView(mPubInfoList);
        }
        if (mPubInfoList.size() == 0) {
            mTv_guide.setVisibility(View.VISIBLE);
        } else {
            mTv_guide.setVisibility(View.GONE);
        }

    }
}
