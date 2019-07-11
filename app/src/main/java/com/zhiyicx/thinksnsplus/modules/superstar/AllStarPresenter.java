package com.zhiyicx.thinksnsplus.modules.superstar;

import com.zhiyicx.common.dagger.scope.FragmentScoped;
import com.zhiyicx.thinksnsplus.base.AppBasePresenter;
import com.zhiyicx.thinksnsplus.base.BaseSubscribeForV2;
import com.zhiyicx.thinksnsplus.data.beans.SuperStarClassifyBean;
import com.zhiyicx.thinksnsplus.data.source.repository.VideoRepository2;

import java.util.List;

import javax.inject.Inject;

@FragmentScoped
public class AllStarPresenter extends AppBasePresenter<AllStarContract.View> implements AllStarContract.Presenter {
    @Inject
    VideoRepository2 videoRepository2;

    @Inject
    public AllStarPresenter(AllStarContract.View rootView) {
        super(rootView);
    }

    @Override
    public void requestNetData() {
        videoRepository2.getAllSuperStar().subscribe(new BaseSubscribeForV2<List<SuperStarClassifyBean>>() {
            @Override
            protected void onSuccess(List<SuperStarClassifyBean> data) {
                mRootView.onGetAllSuperStarSuccess(data);
            }

            @Override
            protected void onException(Throwable throwable) {
                super.onException(throwable);
                mRootView.onGetAllSuperStarFailed();
            }
        });
    }
}
