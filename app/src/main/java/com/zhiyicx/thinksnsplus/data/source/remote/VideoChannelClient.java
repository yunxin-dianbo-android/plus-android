package com.zhiyicx.thinksnsplus.data.source.remote;

import com.zhiyicx.baseproject.config.ApiConfig;
import com.zhiyicx.thinksnsplus.data.beans.DeleteOrAddVideoChannelResInfo;
import com.zhiyicx.thinksnsplus.data.beans.GroupDynamicListBean;
import com.zhiyicx.thinksnsplus.data.beans.VideoChannelBean;
import com.zhiyicx.thinksnsplus.data.beans.VideoChannelListBean;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * @author LiuChao
 * @describe
 * @date 2017/4/8
 * @contact email:450127106@qq.com
 */

public interface VideoChannelClient {

    /**
     * 获取频道的动态列表
     *
     * @return
     */
    @GET(ApiConfig.APP_PATH_VIDEO_CHANNEL)
    Observable<VideoChannelListBean> getVideoChannel();

    /**
     * 获取频道的动态列表
     * String channel_id, String act:add:deletes
     *
     * @return
     */
    @PATCH(ApiConfig.APP_PATH_VIDEO_CHANNEL)
    Observable<DeleteOrAddVideoChannelResInfo> addOrDeleteVideoChannel(@Query("channel_id") String channel_id, @Query("act") String act);
}
