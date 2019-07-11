package com.zhiyicx.thinksnsplus.modules.search.container;


import dagger.Module;
import dagger.Provides;

/**
 * ThinkSNS Plus
 * Copyright (c) 2018 Chengdu ZhiYiChuangXiang Technology Co., Ltd.
 *
 * @Author Jliuer
 * @Date 2018/08/06/10:44
 * @Email Jliuer@aliyun.com
 * @Description
 */
@Module
public class SearchIndexPresenterModule {
    SearchIndexContract.View mView;

    public SearchIndexPresenterModule(SearchIndexContract.View view) {
        mView = view;
    }

    @Provides
    SearchIndexContract.View provideSearchIndexContractView(){
        return mView;
    }
}
