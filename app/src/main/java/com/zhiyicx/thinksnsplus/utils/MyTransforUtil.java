package com.zhiyicx.thinksnsplus.utils;

import com.zhiyicx.thinksnsplus.data.beans.CirclePostListBean;
import com.zhiyicx.thinksnsplus.data.beans.DynamicDetailBeanV2;

public class MyTransforUtil {
    public static DynamicDetailBeanV2 transforCirclePost2DynamicBeanV2(CirclePostListBean circlePostListBean) {
        DynamicDetailBeanV2 dy = new DynamicDetailBeanV2();
        dy.setId(circlePostListBean.getId());
        dy.setAmount(circlePostListBean.amount);
        dy.setAudit_status(circlePostListBean.audit_status);
        dy.setCreated_at(circlePostListBean.getCreated_at());
        dy.setUpdated_at(circlePostListBean.getUpdated_at());
        dy.setDeleted_at(circlePostListBean.getDeleted_at());
        dy.setDiggs(circlePostListBean.getDiggs());
        dy.setFeed_comment_count(circlePostListBean.getComments_count());
        dy.setFeed_content(circlePostListBean.getSummary());
        dy.setFeed_view_count(circlePostListBean.getViews_count());
        dy.setVideo(circlePostListBean.getVideo());
        dy.setFeed_mark(circlePostListBean.getFeed_mark());
        dy.setFeed_from(circlePostListBean.getFeed_from());
        dy.setFollowed(circlePostListBean.getIsFollowed());
        dy.setGroup_id((int) circlePostListBean.getGroup_id());
        dy.setUser_id(circlePostListBean.getUser_id());
        dy.setUserInfoBean(circlePostListBean.getUserInfoBean());
//        dy.setComments(circlePostListBean.getComments());

        return dy;
    }
}
