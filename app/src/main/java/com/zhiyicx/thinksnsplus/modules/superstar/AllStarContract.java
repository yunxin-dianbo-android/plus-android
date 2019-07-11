package com.zhiyicx.thinksnsplus.modules.superstar;

import com.zhiyicx.baseproject.base.IBaseTouristPresenter;
import com.zhiyicx.common.mvp.i.IBaseView;
import com.zhiyicx.thinksnsplus.data.beans.SuperStarClassifyBean;

import java.util.List;

public class AllStarContract {
    public interface View<P extends Presenter> extends IBaseView<P> {
        void onGetAllSuperStarSuccess(List<SuperStarClassifyBean> superStarListBean);
        void onGetAllSuperStarFailed();
    }

    public interface Presenter extends IBaseTouristPresenter {
        void requestNetData();
    }
}
