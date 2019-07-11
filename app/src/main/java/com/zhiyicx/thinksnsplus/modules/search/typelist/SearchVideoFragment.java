package com.zhiyicx.thinksnsplus.modules.search.typelist;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.zhiyicx.thinksnsplus.data.beans.VideoChannelBean;
import com.zhiyicx.thinksnsplus.modules.video.VideoHomeFragment;

public class SearchVideoFragment extends VideoHomeFragment {

    String searchKeyWords;

    public static SearchVideoFragment newInstance(VideoChannelBean videoChannelBean, String searchKeyWords) {
        SearchVideoFragment fragment = new SearchVideoFragment();
//      fragment.setOnCommentClickListener(l);
        Bundle args = new Bundle();
        args.putParcelable(VideoChannelBean.class.getSimpleName(), videoChannelBean);
        args.putString("searchKeyWords", searchKeyWords);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        searchKeyWords = getArguments().getString("searchKeyWords");
    }

    @Override
    public String getSearchKeyWord() {
        return searchKeyWords;
    }
}
