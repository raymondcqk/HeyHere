package com.raymondqk.raysqlitepractice.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.raymondqk.raysqlitepractice.R;
import com.raymondqk.raysqlitepractice.adapter.NewsListAdapter;
import com.raymondqk.raysqlitepractice.adapter.NewsListPagerAdapter;
import com.raymondqk.raysqlitepractice.fragment.news.NewListFragment;
import com.raymondqk.raysqlitepractice.model.newsbean.Channel;
import com.raymondqk.raysqlitepractice.utils.news.NewsDataLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/28 0028.
 */

public class NewsMainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_main);
        initView();

    }

    public  void initView(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.news_main_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("新闻资讯");

        tabLayout = (TabLayout) findViewById(R.id.news_tablayout);
        viewPager = (ViewPager) findViewById(R.id.news_main_viewpager);
        dataInit();

    }

    private void dataInit(){
        final List<String> titles = new ArrayList<>();
        final List<NewListFragment> fragments = new ArrayList<>();
        NewsDataLoader loader = new NewsDataLoader(getApplicationContext());
        loader.setOnLoadNewsChannelListener(new NewsDataLoader.OnLoadNewsChannelListener() {
            @Override
            public void onLoadNewsChannleSuccess(List<Channel> channelList) {
                for (Channel channel:channelList){
                    titles.add(channel.getName());
                    NewListFragment fragment = new NewListFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("name",channel.getName());
                    bundle.putString("id",channel.getChannelId());
                    fragment.setArguments(bundle);
                    fragments.add(fragment);
                }
                NewsListPagerAdapter adapter = new NewsListPagerAdapter(getSupportFragmentManager(),titles,fragments);
                viewPager.setAdapter(adapter);
//                viewPager.notify();

                tabLayout.setupWithViewPager(viewPager);
                viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
                tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        viewPager.setCurrentItem(tab.getPosition(),true);
                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {

                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {

                    }
                });
            }
        });
        loader.getChannels();



        //test pageradapter
//        List<String> titles = getTitles();
//
//        List<NewListFragment> fragments = new ArrayList<>();
//        fragments.add(new NewListFragment());
//        fragments.add(new NewListFragment());
//        fragments.add(new NewListFragment());
//        fragments.add(new NewListFragment());
//
//        NewsListPagerAdapter adapter = new NewsListPagerAdapter(getSupportFragmentManager(),titles,fragments);
//        viewPager.setAdapter(adapter);
    }

    @NonNull
    private List<String> getTitles() {

        List<String> titles = new ArrayList<>();
        titles.add("NBA");
        titles.add("CBA");
        titles.add("国内新闻");
        titles.add("国际新闻");
        return titles;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_news,menu);
        MenuItem item = menu.findItem(R.id.menu_news_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setQueryHint("请输入要搜索的标题");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(NewsMainActivity.this,SearchActivity.class);
                intent.putExtra("title",query);
                startActivity(intent);
                searchView.onActionViewCollapsed();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_news_search:
                break;
            case R.id.menu_news_download:

                break;
            case R.id.menu_news_manage:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
