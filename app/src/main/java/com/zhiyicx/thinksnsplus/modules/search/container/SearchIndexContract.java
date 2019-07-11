package com.zhiyicx.thinksnsplus.modules.search.container;

import com.zhiyicx.baseproject.base.IBaseTouristPresenter;
import com.zhiyicx.common.mvp.i.IBaseView;
import com.zhiyicx.thinksnsplus.data.beans.SearchHistoryBeanV2;

import java.util.List;

public class SearchIndexContract {
    public interface View<P> extends IBaseView<P> {

        void onGetSearchHistorySuccess(List<SearchHistoryBeanV2> searchHistoryBeanV2s);

        void onGetHotSearchSuccess(List<SearchHistoryBeanV2> searchHistoryBeanV2s);
    }
    public interface Presenter extends IBaseTouristPresenter {
        void getSearchHistory();
        void getHotSearchHistory();
        void addSearchHistory(String keyword);
        void  clearSearchHistory();
    }
}
