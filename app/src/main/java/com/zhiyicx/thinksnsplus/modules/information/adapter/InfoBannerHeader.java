package com.zhiyicx.thinksnsplus.modules.information.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.zhiyicx.thinksnsplus.R;
import com.zhiyicx.thinksnsplus.utils.BannerImageLoaderUtil;

import java.io.Serializable;
import java.util.List;

/**
 * @Author Jliuer
 * @Date 2017/05/16
 * @Email Jliuer@aliyun.com
 * @Description 动态banner信息
 */
public class InfoBannerHeader {
    private View mInfoBannerHeader;
    private Context mContext;
    private InfoBannerHeaderInfo mHeaderInfo;

    private Banner mBanner;
    private InfoBannerHeadlerClickEvent mHeadlerClickEvent;

    public InfoBannerHeader(Context context) {
        this.mContext = context;
        mInfoBannerHeader = LayoutInflater.from(context).inflate(R.layout.item_banner, null);
        mInfoBannerHeader.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams
                .MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mBanner = (Banner) mInfoBannerHeader.findViewById(R.id.item_banner);
        mBanner.setOnBannerListener(position -> {
            if (mHeadlerClickEvent != null && mHeaderInfo != null) {
                mHeadlerClickEvent.headClick(mHeaderInfo.getLinks().get(position),mHeaderInfo.getTitles().get(position));
            }
        });
    }

    public InfoBannerHeader(Context context, InfoBannerHeaderInfo headInfo) {
        this.mContext = context;
        mInfoBannerHeader = LayoutInflater.from(context).inflate(R.layout.item_banner, null);
        mBanner = (Banner) mInfoBannerHeader.findViewById(R.id.item_banner);
        mBanner.setOnBannerListener(position -> {
            if (mHeadlerClickEvent != null) {
                mHeadlerClickEvent.headClick(mHeaderInfo.getLinks().get(position),mHeaderInfo.getTitles().get(position));
            }
        });
        setHeadInfo(headInfo);
    }

    public void setHeadInfo(InfoBannerHeaderInfo headInfo) {
        if (headInfo == null || mBanner == null) {
            return;
        }
        mHeaderInfo = headInfo;
        mBanner.setDelayTime(headInfo.getDelay());
        mBanner.setImageLoader(new BannerImageLoaderUtil());
        mBanner.setImages(headInfo.getUrls());
        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        mBanner.setTitleTextSize(mContext.getResources().getDimensionPixelSize(R.dimen.size_content));
        mBanner.setBannerTitles(headInfo.getTitles());
        mBanner.setOnBannerListener(headInfo.getOnBannerListener());
        mBanner.start();
        mBanner.setOnBannerListener(position -> {
            if (mHeadlerClickEvent != null && mHeaderInfo != null) {
                mHeadlerClickEvent.headClick(mHeaderInfo.getLinks().get(position),mHeaderInfo.getTitles().get(position));
            }
        });
    }

    public void stopBanner() {
        if (mBanner != null) {
            mBanner.stopAutoPlay();
        }
    }

    public void startBanner() {
        if (mBanner != null) {
            mBanner.startAutoPlay();
        }
    }

    public View getInfoBannerHeader() {
        return mInfoBannerHeader;
    }

    public void setHeadlerClickEvent(InfoBannerHeadlerClickEvent headlerClickEvent) {
        mHeadlerClickEvent = headlerClickEvent;
    }

    public class InfoBannerHeaderInfo implements Serializable {
        private static final long serialVersionUID = 1L;
        private int delay;
        private List<String> titles;
        private List<String> urls;
        private List<String> links;
        private OnBannerListener mOnBannerListener;

        public List<String> getLinks() {
            return links;
        }

        public void setLinks(List<String> links) {
            this.links = links;
        }

        public int getDelay() {
            return delay;
        }

        public void setDelay(int delay) {
            this.delay = delay;
        }

        public List<String> getTitles() {
            return titles;
        }

        public void setTitles(List<String> titles) {
            this.titles = titles;
        }

        public List<String> getUrls() {
            return urls;
        }

        public void setUrls(List<String> urls) {
            this.urls = urls;
        }

        public void setOnBannerListener(OnBannerListener onBannerListener) {
            mOnBannerListener = onBannerListener;
        }

        public OnBannerListener getOnBannerListener() {
            return mOnBannerListener;
        }
    }

    public interface InfoBannerHeadlerClickEvent {
        void headClick(String link,String title);
    }
}
