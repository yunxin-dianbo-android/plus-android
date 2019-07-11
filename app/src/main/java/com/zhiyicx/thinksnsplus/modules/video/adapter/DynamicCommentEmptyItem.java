package com.zhiyicx.thinksnsplus.modules.video.adapter;

import com.zhiyicx.thinksnsplus.data.beans.DynamicCommentBean;
import com.zhiyicx.thinksnsplus.data.beans.VideoCommentBean;
import com.zhiyicx.thinksnsplus.widget.EmptyItem;

/**
 * @Describe
 * @Author Jungle68
 * @Date 2017/3/10
 * @Contact master.jungle68@gmail.com
 */

public class DynamicCommentEmptyItem extends EmptyItem<VideoCommentBean> {

    @Override
    public boolean isForViewType(VideoCommentBean item, int position) {
        return item.getBody() == null;
    }
}
