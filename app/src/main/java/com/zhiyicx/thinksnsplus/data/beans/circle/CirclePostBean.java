package com.zhiyicx.thinksnsplus.data.beans.circle;

import com.zhiyicx.thinksnsplus.data.beans.CirclePostListBean;
import com.zhiyicx.thinksnsplus.data.beans.DynamicDetailBeanV2;

import java.util.List;

/**
 * @author Jliuer
 * @Date 2017/12/01/11:58
 * @Email Jliuer@aliyun.com
 * @Description
 */
public class CirclePostBean {

    private List<CirclePostListBean> pinned;
    private List<CirclePostListBean> feeds;
    private List<CirclePostListBean> posts;

    public List<CirclePostListBean> getFeeds() {
        return feeds;
    }

    public void setFeeds(List<CirclePostListBean> feeds) {
        this.feeds = feeds;
    }



    public List<CirclePostListBean> getPinned() {
        return pinned;
    }

    public void setPinneds(List<CirclePostListBean> pinneds) {
        this.pinned = pinneds;
    }

    public List<CirclePostListBean> getPosts() {
        return posts;
    }

    public void setPosts(List<CirclePostListBean> posts) {
        this.posts = posts;
    }
}
