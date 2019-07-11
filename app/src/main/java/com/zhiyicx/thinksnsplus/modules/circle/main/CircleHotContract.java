package com.zhiyicx.thinksnsplus.modules.circle.main;

import com.zhiyicx.baseproject.base.ITSListPresenter;
import com.zhiyicx.baseproject.base.ITSListView;
import com.zhiyicx.thinksnsplus.base.fordownload.ITSListPresenterForDownload;
import com.zhiyicx.thinksnsplus.base.fordownload.ITSListViewForDownload;
import com.zhiyicx.thinksnsplus.data.beans.CircleInfo;
import com.zhiyicx.thinksnsplus.data.beans.CirclePostListBean;
import com.zhiyicx.thinksnsplus.data.beans.DynamicDetailBeanV2;
import com.zhiyicx.thinksnsplus.data.beans.RealAdvertListBean;
import com.zhiyicx.thinksnsplus.data.beans.TopPostListBean;
import com.zhiyicx.thinksnsplus.data.beans.TopSuperStarBean;
import com.zhiyicx.thinksnsplus.data.beans.UserCertificationInfo;

import java.util.List;

/**
 * @author Jliuer
 * @Date 2017/11/14/11:29
 * @Email Jliuer@aliyun.com
 * @Description
 */
public interface CircleHotContract {
    interface View extends ITSListViewForDownload<DynamicDetailBeanV2, Presenter> {
//        void updateCircleCount(int count);
//
//        List<CircleInfo> getJoinedCircles();
//        List<CircleInfo> getRecommendCircles();
//
//        void setJoinedCircles(List<CircleInfo> circles);
//        void setRecommendCircles(List<CircleInfo> circles);
//        void loadAllError();
//        void setUserCertificationInfo(UserCertificationInfo data);

        void onNetSuccessHotSuperStar(List<TopSuperStarBean> topSuperStarBeans);
    }

    interface Presenter extends ITSListPresenterForDownload<DynamicDetailBeanV2> {

    }

}
