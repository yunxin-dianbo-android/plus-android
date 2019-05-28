package com.zhiyicx.thinksnsplus.modules.circle.main;

import com.zhiyicx.baseproject.base.ITSListPresenter;
import com.zhiyicx.baseproject.base.ITSListView;
import com.zhiyicx.thinksnsplus.data.beans.CircleInfo;
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
    interface View extends ITSListView<TopPostListBean, Presenter> {
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

    interface Presenter extends ITSListPresenter<TopPostListBean> {
        //        void getRecommendCircle();
//
//        void dealCircleJoinOrExit(int position, CircleInfo circleInfo, String psd);
//
//        void checkCertification();
//
//        List<RealAdvertListBean> getCircleTopAdvert();
//
//        void canclePay();
//        List<RealAdvertListBean> getBannerAdvert();
//
//        List<RealAdvertListBean> getListAdvert();

    }

}
