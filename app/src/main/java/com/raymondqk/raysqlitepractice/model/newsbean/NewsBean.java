package com.raymondqk.raysqlitepractice.model.newsbean;

import java.util.List;

/**
 * Created by Administrator on 2016/10/29 0029.
 */
public class NewsBean {
    private String pubDate;
    private boolean havePic;
    private String title;
    private String channelName;
    private String desc;
    private String source;
    private String channelId;
    private String link;
    /**
     * height : 287
     * width : 550
     * url : http://n.sinaimg.cn/news/crawl/20161029/AgUy-fxxfyev8739153.jpg
     */

//    private List<AllListBean> allList;
    /**
     * height : 287
     * width : 550
     * url : http://n.sinaimg.cn/news/crawl/20161029/AgUy-fxxfyev8739153.jpg
     */

    private List<ImageurlsBean> imageurls;

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public boolean isHavePic() {
        return havePic;
    }

    public void setHavePic(boolean havePic) {
        this.havePic = havePic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

//    public List<AllListBean> getAllList() {
//        return allList;
//    }
//
//    public void setAllList(List<AllListBean> allList) {
//        this.allList = allList;
//    }

    public List<ImageurlsBean> getImageurls() {
        return imageurls;
    }

    public void setImageurls(List<ImageurlsBean> imageurls) {
        this.imageurls = imageurls;
    }

//    public static class AllListBean {
//        private int height;
//        private int width;
//        private String url;
//
//        public int getHeight() {
//            return height;
//        }
//
//        public void setHeight(int height) {
//            this.height = height;
//        }
//
//        public int getWidth() {
//            return width;
//        }
//
//        public void setWidth(int width) {
//            this.width = width;
//        }
//
//        public String getUrl() {
//            return url;
//        }
//
//        public void setUrl(String url) {
//            this.url = url;
//        }
//    }

}
