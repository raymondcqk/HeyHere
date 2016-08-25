package com.raymondqk.raysqlitepractice.model;

import android.content.Context;
import android.content.Intent;

/**
 * Created by 陈其康 raymondchan on 2016/8/25 0025.
 */
public class ActivityModel {
    private String name;
    private Class activityClass;

    public ActivityModel(String name, Class activityClass) {

        this.name = name;
        this.activityClass = activityClass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class getActivityClass() {
        return activityClass;
    }

    public void setActivityClass(Class activityClass) {
        this.activityClass = activityClass;
    }

    public static void startActivity(Context context,ActivityModel activityModel) {
        Intent intent = new Intent(context, activityModel.getClass());
        context.startActivity(intent);
    }
}
