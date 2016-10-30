package com.raymondqk.raysqlitepractice.model.newsbean;

/**
 * Created by Administrator on 2016/10/29 0029.
 */

public class NewsParmas {
    String channelId;
    String channelName;
    String title;
    String page;
    String needContent;
    String needHtml;
    String needAllList;
    String maxResult;

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getNeedContent() {
        return needContent;
    }

    public void setNeedContent(String needContent) {
        this.needContent = needContent;
    }

    public String getNeedHtml() {
        return needHtml;
    }

    public void setNeedHtml(String needHtml) {
        this.needHtml = needHtml;
    }

    public String getNeedAllList() {
        return needAllList;
    }

    public void setNeedAllList(String needAllList) {
        this.needAllList = needAllList;
    }

    public String getMaxResult() {
        return maxResult;
    }

    public void setMaxResult(String maxResult) {
        this.maxResult = maxResult;
    }
}
