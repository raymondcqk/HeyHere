package com.raymondqk.raysqlitepractice.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by 陈其康 raymondchan on 2016/8/13 0013.
 */
public class GuidePagerAdapter extends PagerAdapter {

    //数据源 -- view list
    private List<View> mViews;
    private Context mContext;


    public GuidePagerAdapter(List<View> views, Context context) {
        super();//干嘛用？
        mViews = views;
        mContext = context;
    }

    /**
     * view的数量
     *
     * @return
     */
    @Override
    public int getCount() {
        return mViews.size();
    }

    /**
     * 判断当前view是否是我们需要的view
     *
     * @param view
     * @param object
     * @return
     */
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == object);//是否指向同一view对象
    }

    /**
     * 移除view
     *
     * @param container
     * @param position
     * @param object
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager)container).removeView(mViews.get(position));
    }

    /**
     * 加载View ，类似于ListAdapter里面的getView
     *
     * @param container
     * @param position
     * @return
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ((ViewPager)container).addView(mViews.get(position));
        return mViews.get(position);
    }
}
