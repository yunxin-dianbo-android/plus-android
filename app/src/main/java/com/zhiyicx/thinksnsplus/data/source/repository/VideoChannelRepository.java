package com.zhiyicx.thinksnsplus.data.source.repository;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.zhiyicx.common.base.BaseJson;
import com.zhiyicx.common.base.BaseJsonV2;
import com.zhiyicx.common.net.UpLoadFile;
import com.zhiyicx.common.net.listener.ProgressRequestBody;
import com.zhiyicx.common.utils.FileUtils;
import com.zhiyicx.common.utils.log.LogUtils;
import com.zhiyicx.rxerrorhandler.functions.RetryWithInterceptDelay;
import com.zhiyicx.thinksnsplus.data.beans.DeleteOrAddVideoChannelResInfo;
import com.zhiyicx.thinksnsplus.data.beans.UpdateUserInfoTaskParams;
import com.zhiyicx.thinksnsplus.data.beans.UploadTaskParams;
import com.zhiyicx.thinksnsplus.data.beans.UploadTaskResult;
import com.zhiyicx.thinksnsplus.data.beans.VideoChannelBean;
import com.zhiyicx.thinksnsplus.data.beans.VideoChannelListBean;
import com.zhiyicx.thinksnsplus.data.source.remote.CommonClient;
import com.zhiyicx.thinksnsplus.data.source.remote.PasswordClient;
import com.zhiyicx.thinksnsplus.data.source.remote.ServiceManager;
import com.zhiyicx.thinksnsplus.data.source.remote.UserInfoClient;
import com.zhiyicx.thinksnsplus.data.source.remote.VideoChannelClient;
import com.zhiyicx.thinksnsplus.data.source.repository.i.IUploadRepository;
import com.zhiyicx.thinksnsplus.data.source.repository.i.IVideoChannelRepository;
import com.zhiyicx.thinksnsplus.service.backgroundtask.ImageCompress;

import java.io.File;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static com.zhiyicx.thinksnsplus.data.beans.UploadTaskParams.Storage.CHANNEL_PUBLIC;

/**
 * @author LiuChao
 * @describe 上传文件的实现类, 通过dagger注入
 * @date 2017/1/22
 * @contact email:450127106@qq.com
 */

public class VideoChannelRepository implements IVideoChannelRepository {
    private VideoChannelClient videoChannelClient;

    // 这个用于服务器校检 hash
//    private static final int RETRY_MAX_COUNT = 2; // 最大重试次
//    private static final int RETRY_INTERVAL_TIME = 2; // 循环间隔时间 单位 s

    @Inject
    public VideoChannelRepository(ServiceManager serviceManager) {
        videoChannelClient = serviceManager.getVideoChannelClient();
    }

    @Override
    public Observable<VideoChannelListBean> getVideoChannel() {
        return videoChannelClient.getVideoChannel()
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<VideoChannelBean>> getMyVideoChannel() {


        return videoChannelClient.getMyVideoChannel()
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<DeleteOrAddVideoChannelResInfo> addOrDeleteVideoChannel(String channel_id, String act) {
        return videoChannelClient.addOrDeleteVideoChannel(channel_id, act)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}
