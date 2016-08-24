package com.raymondqk.raysqlitepractice.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by 陈其康 raymondchan on 2016/8/23 0023.
 */
public class WeatherDailyPageAdapter extends PagerAdapter {
    //数据源 -- view list
    private List<View> mViews;
    private Context mContext;

    public WeatherDailyPageAdapter(List<View> views, Context context) {
        mViews = views;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mViews.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView(mViews.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ((ViewPager) container).addView(mViews.get(position));
        return mViews.get(position);
    }
}
