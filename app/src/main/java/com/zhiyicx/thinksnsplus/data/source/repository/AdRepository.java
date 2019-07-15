package com.zhiyicx.thinksnsplus.data.source.repository;

import com.zhiyicx.thinksnsplus.data.beans.AdListBeanV2;
import com.zhiyicx.thinksnsplus.data.beans.DeleteOrAddVideoChannelResInfo;
import com.zhiyicx.thinksnsplus.data.beans.VideoChannelBean;
import com.zhiyicx.thinksnsplus.data.beans.VideoChannelListBean;
import com.zhiyicx.thinksnsplus.data.source.remote.AdClient;
import com.zhiyicx.thinksnsplus.data.source.remote.ServiceManager;
import com.zhiyicx.thinksnsplus.data.source.remote.VideoChannelClient;
import com.zhiyicx.thinksnsplus.data.source.repository.i.IAdRepository;
import com.zhiyicx.thinksnsplus.data.source.repository.i.IVideoChannelRepository;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author LiuChao
 * @describe 上传文件的实现类, 通过dagger注入
 * @date 2017/1/22
 * @contact email:450127106@qq.com
 */

public class AdRepository implements IAdRepository {
    private AdClient adClient;

    // 这个用于服务器校检 hash
//    private static final int RETRY_MAX_COUNT = 2; // 最大重试次
//    private static final int RETRY_INTERVAL_TIME = 2; // 循环间隔时间 单位 s

    @Inject
    public AdRepository(ServiceManager serviceManager) {
        adClient = serviceManager.getAdClient();
    }

    @Override
    public Observable<List<AdListBeanV2>> getAd(int position) {
        return adClient.getAd(position).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

//    @Override
//    public Observable<VideoChannelListBean> getVideoChannel() {
//        return videoChannelClient.getVideoChannel()
//                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
//    }
//
//    @Override
//    public Observable<List<VideoChannelBean>> getMyVideoChannel() {
//
//
//        return videoChannelClient.getMyVideoChannel()
//                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
//    }
//
//    @Override
//    public Observable<DeleteOrAddVideoChannelResInfo> addOrDeleteVideoChannel(String channel_id, String act) {
//        return videoChannelClient.addOrDeleteVideoChannel(channel_id, act)
//                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
//    }
}
