package com.raymondqk.raysqlitepractice.utils.news;


import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.raymondqk.raysqlitepractice.model.newsbean.NewsParmas;

/**
 * Created by Administrator on 2016/10/29 0029.
 */

public class NewsApi {

    private static final String TAG = NewsApi.class.getSimpleName();

    //易源API
    // URLs
    private static final String MYSECRET = "71bc82efb47b414788c0e77af475e40c";
    private static final String MYAPPID = "26360";
    // http://route.showapi.com/109-34?showapi_appid=26360&showapi_sign=71bc82efb47b414788c0e77af475e40c
    private static final String URL_CHANNELS = "http://route.showapi.com/109-34?showapi_appid=%s&showapi_sign=%s";
    private static final String URL_NEWS = "http://route.showapi.com/109-35?" +
            "showapi_appid=26360" +
            "&channelId=%s" +
            "&channelName=%s" +
            "&title=%s" +
            "&page=%s" +
            "&needContent=%s" +
            "&needHtml=%s" +
            "&needAllList=%s" +
            "&maxResult=%s" +
            "&showapi_sign=71bc82efb47b414788c0e77af475e40c";


    private RequestQueue requestQueue;
    private Context context;

    public NewsApi(Context context) {
        this.requestQueue = Volley.newRequestQueue(context);
        this.context = context;
    }

    public void getChannels(Response.Listener responseListener, Response.ErrorListener errorListener) {
        String url = "http://route.showapi.com/109-34?showapi_appid=26360&showapi_sign=71bc82efb47b414788c0e77af475e40c";
        StringRequest request = new StringRequest(url, responseListener, errorListener);
        requestQueue.add(request);
    }

    public void getNewsListByChannel(NewsParmas parmas, Response.Listener responseListener, Response.ErrorListener errorListener) {
        //http://route.showapi.com/109-35?showapi_appid=26360&channelId=5572a108b3cdc86cf39001cd&channelName=&title=&page=1&needContent=&needHtml=1needAllList=1maxResult=20&showapi_sign=71bc82efb47b414788c0e77af475e40c

        String url = String.format(URL_NEWS, parmas.getChannelId(), parmas.getChannelName(), parmas.getTitle(), parmas.getPage(),
                parmas.getNeedContent(), parmas.getNeedHtml(), parmas.getNeedAllList(), parmas.getMaxResult());
        StringRequest request = new StringRequest(url, responseListener, errorListener);
        requestQueue.add(request);
    }


}
