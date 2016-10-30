package com.raymondqk.raysqlitepractice.model.newsbean;

import android.graphics.Bitmap;

/**
 * Created by Administrator on 2016/10/29 0029.
 */

public class News {
    private String title;
    private Bitmap img;

    public News(String title, Bitmap img) {
        this.title = title;
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Bitmap getImg() {
        return img;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }
}
