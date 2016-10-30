package com.raymondqk.raysqlitepractice.utils.news;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.raymondqk.raysqlitepractice.model.newsbean.Channel;
import com.raymondqk.raysqlitepractice.model.newsbean.ChannelBean;
import com.raymondqk.raysqlitepractice.model.newsbean.NewsBean;
import com.raymondqk.raysqlitepractice.model.newsbean.NewsListBeanRoot;
import com.raymondqk.raysqlitepractice.model.newsbean.NewsParmas;

import java.util.List;

/**
 * Created by Administrator on 2016/10/29 0029.
 */

public class NewsDataLoader {

    private NewsApi newsApi;
    private Context context;
    private OnLoadNewsChannelListener onLoadNewsChannelListener;
    private OnLoadNewsListListener onLoadNewsListListener;

    public NewsDataLoader(Context context) {
        newsApi = new NewsApi(context);
        this.context = context;

    }

    public void getChannels() {
        newsApi.getChannels(new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                Gson gson = new Gson();
                ChannelBean channelBean = gson.fromJson((String) response, ChannelBean.class);
                List<Channel> channelList = channelBean.getBody().getChannelList();
                if (channelList != null) {
                    if (onLoadNewsChannelListener != null) {
                        onLoadNewsChannelListener.onLoadNewsChannleSuccess(channelList);
                    }
                } else {
                    Toast.makeText(context, "channelList == null", Toast.LENGTH_SHORT).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }

    public void setOnLoadNewsChannelListener(OnLoadNewsChannelListener onLoadNewsChannelListener) {
        this.onLoadNewsChannelListener = onLoadNewsChannelListener;
    }

    public interface OnLoadNewsChannelListener {
        void onLoadNewsChannleSuccess(List<Channel> channelList);
    }

    public void getNewsList(String channelId, String page) {
        NewsParmas parmas = new NewsParmas();
        parmas.setChannelId(channelId);
        parmas.setChannelName("");
        parmas.setTitle("");
        parmas.setPage(page);
        parmas.setNeedContent("0");
        parmas.setNeedHtml("1");
        parmas.setMaxResult("20");
        parmas.setNeedAllList("0");

        newsApi.getNewsListByChannel(parmas, new Response.Listener() {
            @Override
            public void onResponse(Object response) {

                NewsListBeanRoot newsBean = new Gson().fromJson((String) response, NewsListBeanRoot.class);
                if (newsBean != null) {
                    List<NewsBean> newsBeanList = newsBean.getNewsListRootBody().getPageBean().getNewsList();
                    if (onLoadNewsListListener != null) {
                        onLoadNewsListListener.onLoadNewsSuccess(newsBeanList);
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }

    public void setOnLoadNewsListListener(OnLoadNewsListListener onLoadNewsListListener) {
        this.onLoadNewsListListener = onLoadNewsListListener;
    }

    public interface OnLoadNewsListListener {
        void onLoadNewsSuccess(List<NewsBean> newsBeanList);
    }


    /**
     * 加载下一页
     */
    public void loadNextPage(String channelId,String page){
        getNewsList(channelId,page);
    }
}
