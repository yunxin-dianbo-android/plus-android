package com.zhiyicx.thinksnsplus.data.source.local;

import android.app.Application;

import com.zhiyicx.baseproject.config.AdvertConfig;
import com.zhiyicx.thinksnsplus.data.beans.AllAdverListBean;
import com.zhiyicx.thinksnsplus.data.beans.AllAdverListBeanDao;
import com.zhiyicx.thinksnsplus.data.source.local.db.CommonCacheImpl;

import java.util.List;

import javax.inject.Inject;

/**
 * @Author Jliuer
 * @Date 2017/08/01/10:37
 * @Email Jliuer@aliyun.com
 * @Description
 */
public class AllAdvertListBeanGreenDaoImpl extends CommonCacheImpl<AllAdverListBean> {


    @Inject
    RealAdvertListBeanGreenDaoImpl mRealAdvertListBeanGreenDao;

    @Inject
    public AllAdvertListBeanGreenDaoImpl(Application context) {
        super(context);
    }

    @Override
    public long saveSingleData(AllAdverListBean singleData) {
        return getWDaoSession().getAllAdverListBeanDao().insertOrReplace(singleData);
    }

    @Override
    public void saveMultiData(List<AllAdverListBean> multiData) {
        getWDaoSession().getAllAdverListBeanDao().insertOrReplaceInTx(multiData);
    }

    @Override
    public boolean isInvalide() {
        return false;
    }

    @Override
    public AllAdverListBean getSingleDataFromCache(Long primaryKey) {
        return getRDaoSession().getAllAdverListBeanDao().load(primaryKey);
    }

    @Override
    public List<AllAdverListBean> getMultiDataFromCache() {
        return getRDaoSession().getAllAdverListBeanDao().loadAll();
    }

    @Override
    public void clearTable() {
        getWDaoSession().getAllAdverListBeanDao().deleteAll();
    }

    @Override
    public void deleteSingleCache(Long primaryKey) {
        getWDaoSession().getAllAdverListBeanDao().deleteByKey(primaryKey);
    }

    @Override
    public void deleteSingleCache(AllAdverListBean dta) {
        getWDaoSession().getAllAdverListBeanDao().delete(dta);
    }

    @Override
    public void updateSingleData(AllAdverListBean newData) {
        getWDaoSession().getAllAdverListBeanDao().insertOrReplace(newData);
    }

    @Override
    public long insertOrReplace(AllAdverListBean newData) {
        return getWDaoSession().getAllAdverListBeanDao().insertOrReplace(newData);
    }

    public AllAdverListBean getBootAdvert() {
        List<AllAdverListBean> data = getRDaoSession().getAllAdverListBeanDao().queryBuilder().where(AllAdverListBeanDao.Properties.Space.eq(AdvertConfig.APP_BOOT_ADVERT)).build().list();
        if (data != null && !data.isEmpty()) {
            return data.get(0);
        }
        return null;
    }

    public AllAdverListBean getDynamicBannerAdvert() {

        List<AllAdverListBean> data = getRDaoSession().getAllAdverListBeanDao().queryBuilder().where(AllAdverListBeanDao.Properties.Space.eq(AdvertConfig.APP_DYNAMIC_BANNER_ADVERT)).build().list();
        if (data != null && !data.isEmpty()) {
            return data.get(0);
        }
        return null;
    }

    public AllAdverListBean getDynamicListAdvert() {

        List<AllAdverListBean> data = getRDaoSession().getAllAdverListBeanDao().queryBuilder().where(AllAdverListBeanDao.Properties.Space.eq(AdvertConfig.APP_DYNAMIC_LIST_ADVERT)).build().list();
        if (data != null && !data.isEmpty()) {
            return data.get(0);
        }
        return null;
    }

    public AllAdverListBean getDynamicDetailAdvert() {

        List<AllAdverListBean> data = getRDaoSession().getAllAdverListBeanDao().queryBuilder().where(AllAdverListBeanDao.Properties.Space.eq(AdvertConfig.APP_DYNAMIC_DETAILS_ADVERT)).build().list();
        if (data != null && !data.isEmpty()) {
            return data.get(0);
        }
        return null;
    }

    public AllAdverListBean getInfoBannerAdvert() {

        List<AllAdverListBean> data = getRDaoSession().getAllAdverListBeanDao().queryBuilder().where(AllAdverListBeanDao.Properties.Space.eq(AdvertConfig.APP_INFO_BANNER_ADVERT)).build().list();
        if (data != null && !data.isEmpty()) {
            return data.get(0);
        }
        return null;
    }

    public AllAdverListBean getInfoListAdvert() {

        List<AllAdverListBean> data = getRDaoSession().getAllAdverListBeanDao().queryBuilder().where(AllAdverListBeanDao.Properties.Space.eq(AdvertConfig.APP_INFO_LIST_ADVERT)).build().list();
        if (data != null && !data.isEmpty()) {
            return data.get(0);
        }
        return null;
    }

    public AllAdverListBean getInfoDetailAdvert() {

        List<AllAdverListBean> data = getRDaoSession().getAllAdverListBeanDao().queryBuilder().where(AllAdverListBeanDao.Properties.Space.eq(AdvertConfig.APP_INFO_DETAILS_ADVERT)).build().list();
        if (data != null && !data.isEmpty()) {
            return data.get(0);
        }
        return null;
    }

    public AllAdverListBean getCircleTopAdvert() {

        List<AllAdverListBean> data = getRDaoSession().getAllAdverListBeanDao().queryBuilder().where(AllAdverListBeanDao.Properties.Space.eq(AdvertConfig.APP_GROUP_TOP_ADVERT)).build().list();
        if (data != null && !data.isEmpty()) {
            return data.get(0);
        }
        return null;
    }

    public AllAdverListBean getCircleDetailAdvert() {

        List<AllAdverListBean> data = getRDaoSession().getAllAdverListBeanDao().queryBuilder().where(AllAdverListBeanDao.Properties.Space.eq(AdvertConfig.APP_GROUP_DETAIL_ADVERT)).build().list();
        if (data != null && !data.isEmpty()) {
            return data.get(0);
        }
        return null;
    }

    /**
     * @return 积分页广告
     */
    public AllAdverListBean getIntegrationAdvert() {

        List<AllAdverListBean> data = getWDaoSession().getAllAdverListBeanDao().queryBuilder().where(AllAdverListBeanDao.Properties.Space.eq(AdvertConfig.APP_WALLET_INTEGRATION_ADVERT))
                .build()
                .list();
        if (data != null && !data.isEmpty()) {
            return data.get(0);
        }
        return null;
    }
}
