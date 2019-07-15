package com.zhiyicx.thinksnsplus.data.source.repository.i;

import com.zhiyicx.thinksnsplus.data.beans.AdListBeanV2;
import com.zhiyicx.thinksnsplus.data.beans.DeleteOrAddVideoChannelResInfo;
import com.zhiyicx.thinksnsplus.data.beans.VideoChannelBean;
import com.zhiyicx.thinksnsplus.data.beans.VideoChannelListBean;

import java.util.List;

import rx.Observable;

/**
 * @Describe 视频频道
 * @Author Jungle68
 * @Date 2017/1/19
 * @Contact master.jungle68@gmail.com
 */

public interface IAdRepository {
    /**
     * 获取对话列表信息
     *
     * @return
     */
    Observable<List<AdListBeanV2>> getAd(int position);

}
