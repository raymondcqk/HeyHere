package com.raymondqk.raysqlitepractice.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.raymondqk.raysqlitepractice.fragment.news.NewListFragment;

import java.util.List;

/**
 * Created by Administrator on 2016/10/29 0029.
 */

public class NewsListPagerAdapter extends FragmentStatePagerAdapter {

    private List<String> titles;
    private List<NewListFragment> fragments;



    public NewsListPagerAdapter(FragmentManager fm, List<String> titles, List<NewListFragment> fragments) {
        super(fm);
        this.titles = titles;
        this.fragments = fragments;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }


    @Override
    public int getCount() {
        return titles.size();
    }
}
