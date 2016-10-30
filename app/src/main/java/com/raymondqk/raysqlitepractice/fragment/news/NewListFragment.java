package com.raymondqk.raysqlitepractice.fragment.news;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.raymondqk.raysqlitepractice.R;
import com.raymondqk.raysqlitepractice.activity.NewsActivity;
import com.raymondqk.raysqlitepractice.adapter.NewsListAdapter;
import com.raymondqk.raysqlitepractice.model.newsbean.NewsBean;
import com.raymondqk.raysqlitepractice.utils.news.NewsDataLoader;

import java.util.List;

/**
 * Created by Administrator on 2016/10/29 0029.
 */

public class NewListFragment extends Fragment {

    private View view;
    private int page;
    private String name;
    private String channelId;
    private RecyclerView recyclerView;
    private NewsDataLoader loader;
    private NewsListAdapter adapter;
    private SwipeRefreshLayout refreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_news_list, container, false);
        init();
        return view;
    }

    private void init() {
        adapter = new NewsListAdapter(getContext());
        page = 1;
        name = getArguments().getString("name");
        channelId = getArguments().getString("id");
//        Toast.makeText(getContext(), name + ":" + channelId, Toast.LENGTH_SHORT).show();

        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_news_list);
        refreshLayout.setColorSchemeResources(R.color.colorPrimaryDark);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getNewsListInit();
            }
        });

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_news_list);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (layoutManager.getItemCount() == layoutManager.findLastVisibleItemPosition() + 1) {
                        loadNextPage();
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        getNewsListInit();


    }

    private void loadNextPage() {
        loader.setOnLoadNewsListListener(new NewsDataLoader.OnLoadNewsListListener() {
            @Override
            public void onLoadNewsSuccess(List<NewsBean> newsBeanList) {
                adapter.addNewsList(newsBeanList);
//                adapter.notifyDataSetChanged();
            }
        });
        loader.loadNextPage(channelId, ++page + "");
    }

    /**
     * 获取新闻数据List
     * <p>
     * 在这里修改新闻数据取得方式
     *
     * @return
     */
    @NonNull
    private void getNewsListInit() {

        if (!refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(true);
        }
        loader = new NewsDataLoader(getContext());
        loader.setOnLoadNewsListListener(new NewsDataLoader.OnLoadNewsListListener() {

            @Override
            public void onLoadNewsSuccess(List<NewsBean> newsBeanList) {
                adapter.setOnClickListener(new NewsListAdapter.OnClickListener() {
                    @Override
                    public void onClick(NewsBean newsBean) {
                        Intent intent = new Intent(getContext(), NewsActivity.class);
                        intent.putExtra("link", newsBean.getLink());
                        intent.putExtra("title", newsBean.getTitle());
                        startActivity(intent);

                    }
                });

                adapter.setNewsList(newsBeanList);
                recyclerView.setAdapter(adapter);
                refreshLayout.setRefreshing(false);
                page = 1;
            }
        });
        loader.getNewsList(channelId, "1");
    }
}
