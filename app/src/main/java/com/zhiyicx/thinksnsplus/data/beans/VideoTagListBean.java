package com.zhiyicx.thinksnsplus.data.beans;

import java.util.List;

public class VideoTagListBean {

    public List<VideoListBean.TagsBean> getTags() {
        return tags;
    }

    public void setTags(List<VideoListBean.TagsBean> tags) {
        this.tags = tags;
    }

    /**
     * id : 1
     * name : 默认分类
     * weight : 0
     */

    private int id;
    private String name;
    private int weight;
    private List<VideoListBean.TagsBean> tags;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
