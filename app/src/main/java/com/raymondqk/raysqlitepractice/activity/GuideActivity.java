package com.raymondqk.raysqlitepractice.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.raymondqk.raysqlitepractice.R;
import com.raymondqk.raysqlitepractice.adapter.GuidePagerAdapter;
import com.raymondqk.raysqlitepractice.utils.transformer.ZoomOutPageTransformer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 陈其康 raymondchan on 2016/8/13 0013.
 */
public class GuideActivity extends Activity implements View.OnClickListener {

    private List<View> mViews;
    private LayoutInflater mInflater;
    private GuidePagerAdapter mViewAdapter;
    private ViewPager mViewPager;
    //viewpager 子布局layout数组
    private int[] layouts = new int[]{R.layout.guide_page_01, R.layout.guide_page_02, R.layout.guide_page_03,
            R.layout.guide_page_04, R.layout.guide_page_05, R.layout.guide_page_06};
    private ImageView mImg_dot_0;
    private ImageView mImg_dot_1;
    private ImageView mImg_dot_2;
    private ImageView mImg_dot_3;
    private ImageView mImg_dot_4;
    private LinearLayout mDots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        mDots = (LinearLayout) findViewById(R.id.ll_guide_dots);
        mImg_dot_0 = (ImageView) findViewById(R.id.img_guide_dot_0);
        mImg_dot_1 = (ImageView) findViewById(R.id.img_guide_dot_1);
        mImg_dot_2 = (ImageView) findViewById(R.id.img_guide_dot_2);
//        mImg_dot_3 = (ImageView) findViewById(R.id.img_guide_dot_3);
//        mImg_dot_4 = (ImageView) findViewById(R.id.img_guide_dot_4);
        mImg_dot_0.setImageResource(R.mipmap.redball_72px);

        //为viewpager adapter准备view数据源列表
        mInflater = LayoutInflater.from(this);

        mViews = new ArrayList<View>();
        mViews.add(mInflater.inflate(R.layout.guide_page_01, null));
//        mViews.add(mInflater.inflate(R.layout.guide_page_02, null));
        mViews.add(mInflater.inflate(R.layout.guide_page_03, null));
//        mViews.add(mInflater.inflate(R.layout.guide_page_04, null));
        mViews.add(mInflater.inflate(R.layout.guide_page_05, null));
        mViews.add(mInflater.inflate(R.layout.guide_page_06, null));

        //初始化pagerAdapter
        mViewAdapter = new GuidePagerAdapter(mViews, this);
        mViewPager = (ViewPager) findViewById(R.id.viewpager_guide);

        //设置viewpager动画
//        mViewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        //参数2：transformer接口对象 --->哪里可以找到现成的实现了transformer接口的实现类

        //为viewpager绑定adapter
        mViewPager.setAdapter(mViewAdapter);


        //获取最后一页的布局view对象，并获得该布局的控件引用，设置button的点击事件
        View guide_log_view = mViews.get(3);
        Button btn_try = (Button) guide_log_view.findViewById(R.id.btn_guide_try);
        Button btn_signin = (Button) guide_log_view.findViewById(R.id.btn_guide_sign_in);
        btn_try.setOnClickListener(this);
        btn_signin.setOnClickListener(this);


        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position != 3) {
                    mDots.setVisibility(View.VISIBLE);
                    changeDots(position);
                } else {
                    mDots.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void changeDots(int i) {

        int defaultResid = R.drawable.ultra_ball_96px;
        int selectedResid = R.mipmap.redball_72px;

        mImg_dot_0.setImageResource(defaultResid);
        mImg_dot_1.setImageResource(defaultResid);
        mImg_dot_2.setImageResource(defaultResid);
//        mImg_dot_3.setImageResource(defaultResid);
//        mImg_dot_4.setImageResource(defaultResid);

        switch (i) {
            case 0:
                mImg_dot_0.setImageResource(selectedResid);
                break;
            case 1:
                mImg_dot_1.setImageResource(selectedResid);
                break;
            case 2:
                mImg_dot_2.setImageResource(selectedResid);
                break;
//            case 3:
//                mImg_dot_3.setImageResource(selectedResid);
//                break;
//            case 4:
//                mImg_dot_4.setImageResource(selectedResid);
//                break;
        }

    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.btn_guide_try:
                intent = new Intent(GuideActivity.this, MainActivity.class);
                break;
            case R.id.btn_guide_sign_in:
                intent = new Intent(GuideActivity.this, LoginActivity.class);
                break;
        }
        startActivity(intent);
        finish();
    }
}
