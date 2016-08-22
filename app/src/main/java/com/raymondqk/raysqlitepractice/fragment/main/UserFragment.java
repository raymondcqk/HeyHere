package com.raymondqk.raysqlitepractice.fragment.main;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.raymondqk.raysqlitepractice.R;

/**
 * Created by 陈其康 raymondchan on 2016/8/14 0014.
 */
public class UserFragment extends Fragment {

    private View mView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_user, null);
        return mView;
    }

    /**
     * 这里要处理自己发布的消息的修改工作
     */
//    private void goToChangePubinfo(int position) {
    //        Intent intent = new Intent(MainActivity.this, PubinfoChangeActivity.class);
    //        intent.putExtra(KEY_OBJ_PUBINFO, mPubInfos.get(position));
    //        intent.putExtra(KEY_POSITION, position);
    //        mOld_protitle = mPubInfos.get(position).getProject();
    //        startActivityForResult(intent, REQUEST_CODE_CHANGE_PUBINFO);
    //    }
    //===========================================================================================
    //修改项目发布信息 后回调
//    if (requestCode == REQUEST_CODE_CHANGE_PUBINFO && resultCode == RESULT_CODE_CHANGE_PUBINFO) {
    //        int position = data.getIntExtra(KEY_POSITION, -1);
    //        PubInfo pubInfo = (PubInfo) data.getSerializableExtra(KEY_OBJ_PUBINFO);
    //        if (position != -1 && pubInfo != null) {
    //            mPubInfos.set(position, pubInfo);
    //            mTv_guide.setVisibility(View.GONE);
    //            mDbHelper.updateOnePubInfo(mPubInfos.get(position),mOld_protitle);
    //            // TODO: 2016/8/14 0014 添加完新记录到数据库之后，改进Listview更新的方案 --- 记录那条记录被就该，直接修改list
    //            //添加完“项目发布信息”到数据库中，回到 列表界面，重新从数据库读取并更新Listview ---- 若数据量大，这个方案需要改进
    //            mPubInfos = mDbHelper.getAllPubInfos();
    //            mAdapter.updateListView(mPubInfos);
    //        }
}
