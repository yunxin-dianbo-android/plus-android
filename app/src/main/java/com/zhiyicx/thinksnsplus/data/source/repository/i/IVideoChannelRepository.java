package com.zhiyicx.thinksnsplus.data.source.repository.i;

import com.zhiyicx.thinksnsplus.data.beans.DeleteOrAddVideoChannelResInfo;
import com.zhiyicx.thinksnsplus.data.beans.MessageItemBean;
import com.zhiyicx.thinksnsplus.data.beans.MessageItemBeanV2;
import com.zhiyicx.thinksnsplus.data.beans.TSPNotificationBean;
import com.zhiyicx.thinksnsplus.data.beans.UnReadNotificaitonBean;
import com.zhiyicx.thinksnsplus.data.beans.VideoChannelBean;
import com.zhiyicx.thinksnsplus.data.beans.VideoChannelListBean;

import java.util.List;

import retrofit2.http.Query;
import rx.Observable;

/**
 * @Describe 视频频道
 * @Author Jungle68
 * @Date 2017/1/19
 * @Contact master.jungle68@gmail.com
 */

public interface IVideoChannelRepository {
    /**
     * 获取对话列表信息
     *
     * @return
     */
    Observable<VideoChannelListBean> getVideoChannel();


    /**
     * 获取对话列表信息
     *
     * @return
     */
    Observable<List<VideoChannelBean>> getMyVideoChannel();

    /**
     * 获取对话列表信息
     *
     * @return
     */
    Observable<DeleteOrAddVideoChannelResInfo> addOrDeleteVideoChannel(String channel_id, String act);

}
