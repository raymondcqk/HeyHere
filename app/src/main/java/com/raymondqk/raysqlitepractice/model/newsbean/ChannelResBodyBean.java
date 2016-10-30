package com.raymondqk.raysqlitepractice.model.newsbean;

import java.util.List;

/**
 * Created by Administrator on 2016/10/29 0029.
 */
public class ChannelResBodyBean {
    private int totalNum;
    private int ret_code;
    /**
     * channelId : 5572a108b3cdc86cf39001cd
     * name : 国内焦点
     */

    private List<Channel> channelList;

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public int getRet_code() {
        return ret_code;
    }

    public void setRet_code(int ret_code) {
        this.ret_code = ret_code;
    }

    public List<Channel> getChannelList() {
        return channelList;
    }

    public void setChannelList(List<Channel> channelList) {
        this.channelList = channelList;
    }

}
