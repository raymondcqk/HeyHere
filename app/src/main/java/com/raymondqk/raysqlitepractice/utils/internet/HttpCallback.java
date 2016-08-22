package com.raymondqk.raysqlitepractice.utils.internet;

/**
 * Created by 陈其康 raymondchan on 2016/8/21 0021.
 */
public interface HttpCallback {
    void onFinish(String respones);
    void onError(Exception e);
}
