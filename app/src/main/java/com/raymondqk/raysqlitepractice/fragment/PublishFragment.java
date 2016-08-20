package com.raymondqk.raysqlitepractice.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.raymondqk.raysqlitepractice.R;
import com.raymondqk.raysqlitepractice.interfaces.PublishFgListener;

/**
 * Created by 陈其康 raymondchan on 2016/8/14 0014.
 */
public class PublishFragment extends Fragment {

    public static final int WHAT_BTN_PUBLISH = 0;
    private View mView;
    private Button mBtn_publish;
    private View.OnClickListener listener;
    private static Context mContext;
    private static PublishFragment mPublishFragment;
    private PublishFgListener mPublishFgListener;

    public static PublishFragment getInstance(Context context) {
        if (mPublishFragment == null) {
            mPublishFragment = new PublishFragment();
        }
        mContext = context;
        return mPublishFragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mPublishFgListener = (PublishFgListener) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_publish_publish:
                        //点击发布数据的按钮之后，回调到MainActivity处理，跳转activity到发布界面
                        mPublishFgListener.publishFgCallback(WHAT_BTN_PUBLISH);
                        break;
                }
            }
        };
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_publish, null);
        mBtn_publish = (Button) mView.findViewById(R.id.btn_publish_publish);
        mBtn_publish.setOnClickListener(listener);
        return mView;
    }
}
