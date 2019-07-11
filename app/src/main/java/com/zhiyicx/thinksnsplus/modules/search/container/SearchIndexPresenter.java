package com.zhiyicx.thinksnsplus.modules.search.container;

import android.text.TextUtils;

import com.zhiyicx.thinksnsplus.base.AppBasePresenter;
import com.zhiyicx.thinksnsplus.base.BaseSubscribeForV2;
import com.zhiyicx.thinksnsplus.data.beans.AddSearchKeyResInfo;
import com.zhiyicx.thinksnsplus.data.beans.SearchHistoryBeanV2;
import com.zhiyicx.thinksnsplus.data.source.repository.VideoRepository2;

import java.util.List;

import javax.inject.Inject;

public class SearchIndexPresenter extends AppBasePresenter<SearchIndexContract.View> implements SearchIndexContract.Presenter {
    @Inject
    VideoRepository2 videoRepository2;

    @Inject
    public SearchIndexPresenter(SearchIndexContract.View rootView) {
        super(rootView);
    }

    public void getSearchHistory() {
        videoRepository2.getSearchHistory(null).subscribe(new BaseSubscribeForV2<List<SearchHistoryBeanV2>>() {
            @Override
            protected void onSuccess(List<SearchHistoryBeanV2> data) {
                mRootView.onGetSearchHistorySuccess(data);
            }
        });
    }

    public void getHotSearchHistory() {
        videoRepository2.getSearchHistory("hot").subscribe(new BaseSubscribeForV2<List<SearchHistoryBeanV2>>() {
            @Override
            protected void onSuccess(List<SearchHistoryBeanV2> data) {
                mRootView.onGetHotSearchSuccess(data);
            }
        });
    }

    public void addSearchHistory(String keyword) {
        if (!TextUtils.isEmpty(keyword)) {
            videoRepository2.addSearchKey(keyword).subscribe(new BaseSubscribeForV2<AddSearchKeyResInfo>() {
                @Override
                protected void onSuccess(AddSearchKeyResInfo data) {
//                    mRootView.onGetHotSearchSuccess(data);
                }
            });
        }
    }

    @Override
    public void clearSearchHistory() {
        videoRepository2.clearSearchHistory().subscribe(new BaseSubscribeForV2<AddSearchKeyResInfo>() {
            @Override
            protected void onSuccess(AddSearchKeyResInfo data) {
//                    mRootView.onGetHotSearchSuccess(data);
            }
        });
    }


}
