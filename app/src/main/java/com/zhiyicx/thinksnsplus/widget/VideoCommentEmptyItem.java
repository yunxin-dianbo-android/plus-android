package com.zhiyicx.thinksnsplus.widget;

import com.zhiyicx.thinksnsplus.data.beans.VideoCommentBean;

/**
 * @Describe
 * @Author Jungle68
 * @Date 2017/3/10
 * @Contact master.jungle68@gmail.com
 */

public class VideoCommentEmptyItem extends EmptyItem<VideoCommentBean> {

    @Override
    public boolean isForViewType(VideoCommentBean item, int position) {
        return item.getBody() == null;
    }
}
