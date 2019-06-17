package com.zhiyicx.thinksnsplus.widget.comment;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhiyicx.thinksnsplus.R;
import com.zhiyicx.thinksnsplus.data.beans.CirclePostListBean;
import com.zhiyicx.thinksnsplus.data.beans.DynamicDetailBeanV2;
import com.zhiyicx.thinksnsplus.data.beans.TopicListBean;
import com.zhiyicx.thinksnsplus.modules.topic.detail.TopicDetailActivity;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * @Describe 动态列表 评论控件
 * @Author Jungle68
 * @Date 2017/3/7
 * @Contact master.jungle68@gmail.com
 */

public class CirclePostListTopicView extends LinearLayout {
    public static final int SHOW_MORE_COMMENT_SIZE_LIMIT = 6;

    private TagFlowLayout mDynamicNoPullRecycleView;

    private CirclePostListBean mDynamicBean;

    private OnTopicClickListener mOnTopicClickListener;

    public CirclePostListTopicView(Context context) {
        super(context);
        init();
    }

    public CirclePostListTopicView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CirclePostListTopicView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOrientation(VERTICAL);
        LayoutInflater.from(getContext()).inflate(R.layout.view_dynamic_list_topic, this);
        mDynamicNoPullRecycleView = findViewById(R.id.fl_topics);
    }

    /**
     * 设置数据
     *
     * @param dynamicBean
     */
    public void setData(CirclePostListBean dynamicBean) {
        setData(dynamicBean, null);
    }

    public void setData(CirclePostListBean dynamicBean, TopicListBean topic) {
        mDynamicBean = dynamicBean;
        List<TopicListBean> result = null;
        if (topic != null && dynamicBean.getTopics() != null) {
            result = new ArrayList<>(dynamicBean.getTopics());
            result.remove(topic);
        }
        mDynamicNoPullRecycleView.setAdapter(new TopicTagAdapter(result == null ? dynamicBean.getTopics() : result));
    }

    class TopicTagAdapter extends TagAdapter<TopicListBean> {
        private final LayoutInflater mInflater;

        public TopicTagAdapter(List<TopicListBean> datas) {
            super(datas);
            mInflater = LayoutInflater.from(getContext());
        }

        public TopicTagAdapter(TopicListBean[] datas) {
            super(datas);
            mInflater = LayoutInflater.from(getContext());
        }

        @Override
        public View getView(FlowLayout parent, int position, TopicListBean topicListBean) {
            View v = mInflater.inflate(R.layout.item_topic_feed_channel,
                    parent, false);
            TextView textView = v.findViewById(R.id.tv_content);
            textView.setText(topicListBean.getName());
            v.setOnClickListener(view -> {
                if (mOnTopicClickListener != null) {
                    if (mOnTopicClickListener.onTopicClick(topicListBean)) {
                        TopicDetailActivity.startTopicDetailActivity(getContext(), mOnTopicClickListener.onTopicClickFrom(), topicListBean.getId());
                    }
                    return;
                }
                TopicDetailActivity.startTopicDetailActivity(getContext(), topicListBean.getId());
            });
            return v;
        }
    }

    public void setOnTopicClickListener(OnTopicClickListener onTopicClickListener) {
        mOnTopicClickListener = onTopicClickListener;
    }

    public interface OnTopicClickListener {
        boolean onTopicClick(TopicListBean topicListBean);

        TopicListBean doNotShowThisTopic();

        Long onTopicClickFrom();
    }
}
