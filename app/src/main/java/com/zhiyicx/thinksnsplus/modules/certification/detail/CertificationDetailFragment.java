package com.zhiyicx.thinksnsplus.modules.certification.detail;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.jakewharton.rxbinding.view.RxView;
import com.zhiyicx.baseproject.base.TSFragment;
import com.zhiyicx.baseproject.config.ImageZipConfig;
import com.zhiyicx.baseproject.impl.photoselector.ImageBean;
import com.zhiyicx.baseproject.impl.photoselector.Toll;
import com.zhiyicx.common.utils.DeviceUtils;
import com.zhiyicx.common.utils.log.LogUtils;
import com.zhiyicx.thinksnsplus.R;
import com.zhiyicx.thinksnsplus.data.beans.AnimationRectBean;
import com.zhiyicx.thinksnsplus.data.beans.UserCertificationInfo;
import com.zhiyicx.thinksnsplus.modules.gallery.GalleryActivity;
import com.zhiyicx.thinksnsplus.utils.ImageUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;

import static com.zhiyicx.common.config.ConstantConfig.JITTER_SPACING_TIME;
import static com.zhiyicx.thinksnsplus.modules.certification.detail.CertificationDetailActivity.BUNDLE_DETAIL_DATA;
import static com.zhiyicx.thinksnsplus.modules.certification.detail.CertificationDetailActivity.BUNDLE_DETAIL_TYPE;

/**
 * @author Catherine
 * @describe
 * @date 2017/8/3
 * @contact email:648129313@qq.com
 */

public class CertificationDetailFragment extends TSFragment<CertificationDetailContract.Presenter>
        implements CertificationDetailContract.View {

    private static final String TYPE_USER = "user";
    private static final String TYPE_ORG = "org";

    @BindView(R.id.tv_status_hint)
    TextView mTvStatusHint;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_id_card)
    TextView mTvIdCard;
    @BindView(R.id.tv_id_phone)
    TextView mTvIdPhone;
    @BindView(R.id.ll_personage)
    LinearLayout mLlPersonage;
    @BindView(R.id.tv_company_address)
    TextView mTvCompanyAddress;
    @BindView(R.id.tv_company_name)
    TextView mTvCompanyName;
    @BindView(R.id.tv_company_principal)
    TextView mTvCompanyPrincipal;
    @BindView(R.id.tv_company_principal_id_card)
    TextView mTvCompanyPrincipalIdCard;
    @BindView(R.id.tv_company_principal_phone)
    TextView mTvCompanyPrincipalPhone;
    @BindView(R.id.ll_company)
    LinearLayout mLlCompany;
    @BindView(R.id.tv_description)
    TextView mTvDescription;
    @BindView(R.id.iv_pic_one)
    ImageView mIvPicOne;
    @BindView(R.id.iv_pic_two)
    ImageView mIvPicTwo;

    private UserCertificationInfo mInfo;
    private List<ImageBean> imageBeen;
    ArrayList<AnimationRectBean> mAnimationRectBeanArrayList;
    private int mImageWith;
    private int mImageHeight;

    public CertificationDetailFragment instance(Bundle bundle) {
        CertificationDetailFragment fragment = new CertificationDetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initView(View rootView) {
        initType();

        mImageWith = (DeviceUtils.getScreenWidth(mActivity) - getResources().getDimensionPixelOffset(R.dimen.spacing_mid) * 2 - getResources()
                .getDimensionPixelOffset(R.dimen.certification_detail_width) - getResources().getDimensionPixelOffset(R.dimen.spacing_small)) / 2;
        mImageHeight = mImageWith * 3 / 4;
        mIvPicOne.getLayoutParams().width = mImageWith;
        mIvPicOne.getLayoutParams().height = mImageHeight;
        mIvPicTwo.getLayoutParams().width = mImageWith;
        mIvPicTwo.getLayoutParams().height = mImageHeight;
    }

    @Override
    protected void initData() {
        imageBeen = new ArrayList<>();
        mAnimationRectBeanArrayList = new ArrayList<>();
        if (mInfo != null) {
            setCertificationInfo(mInfo);
        }
        mPresenter.getCertificationInfo();
        initListener();
    }

    @Override
    protected int getBodyLayoutId() {
        return R.layout.fragment_certification_detail;
    }

    @Override
    protected boolean showToolBarDivider() {
        return true;
    }

    @Override
    protected String setCenterTitle() {
        return getString(R.string.certification_personage);
    }

    private void initType() {
        if (getArguments() == null) {
            return;
        }
        int type = getArguments().getInt(BUNDLE_DETAIL_TYPE);
        mInfo = getArguments().getParcelable(BUNDLE_DETAIL_DATA);
        mLlPersonage.setVisibility(type == 0 ? View.VISIBLE : View.GONE);
        mLlCompany.setVisibility(type == 1 ? View.VISIBLE : View.GONE);
        setCenterText(type == 0 ? getString(R.string.certification_personage) : getString(R.string.certification_company));
    }

    @Override
    public void setCertificationInfo(UserCertificationInfo info) {
        this.mInfo = info;
        if (info == null || info.getData() == null) {
            return;
        }

        if (info.getStatus() == UserCertificationInfo.CertifyStatusEnum.REVIEWING.value) {
            mTvStatusHint.setVisibility(View.VISIBLE);
        } else if (info.getStatus() == UserCertificationInfo.CertifyStatusEnum.PASS.value) {
            mTvStatusHint.setVisibility(View.GONE);
        }else if (info.getStatus() == UserCertificationInfo.CertifyStatusEnum.REJECTED.value){
            mTvStatusHint.setVisibility(View.VISIBLE);
            mTvStatusHint.setText(R.string.certification_ing_refuse);
        }
        if (info.getCertification_name().equals(TYPE_USER)) {
            mLlPersonage.setVisibility(View.VISIBLE);
            mLlCompany.setVisibility(View.GONE);
            mTvName.setText(info.getData().getName());
            mTvIdCard.setText(info.getData().getNumber());
            mTvIdPhone.setText(info.getData().getPhone());
            setCenterText(getString(R.string.certification_personage));
        } else {
            mLlPersonage.setVisibility(View.GONE);
            mLlCompany.setVisibility(View.VISIBLE);
            mTvCompanyName.setText(info.getData().getOrg_name());
            mTvCompanyAddress.setText(info.getData().getOrg_address());
            mTvCompanyPrincipal.setText(info.getData().getName());
            mTvCompanyPrincipalIdCard.setText(info.getData().getNumber());
            mTvCompanyPrincipalPhone.setText(info.getData().getPhone());
            mIvPicTwo.setVisibility(View.GONE);
        }
        List<Integer> files = info.getData().getFiles();
        if (files != null) {
            imageBeen.clear();
            if (files.size() > 0) {
                Glide.with(getContext())
                        .load(ImageUtils.imagePathConvertV2(info.getData().getFiles().get(0), mImageWith, mImageHeight, ImageZipConfig.IMAGE_70_ZIP))
                        .placeholder(R.drawable.shape_default_image)
                        .error(R.drawable.shape_default_image)
                        .into(mIvPicOne);
            }
            if (files.size() > 1) {
                Glide.with(getContext())
                        .load(ImageUtils.imagePathConvertV2(info.getData().getFiles().get(1), mImageWith, mImageHeight, ImageZipConfig.IMAGE_70_ZIP))
                        .placeholder(R.drawable.shape_default_image)
                        .error(R.drawable.shape_default_image)
                        .into(mIvPicTwo);
            }
        }
        mTvDescription.setText(info.getData().getDesc());
    }

    private void initListener() {
        RxView.clicks(mIvPicOne)
                .throttleFirst(JITTER_SPACING_TIME, TimeUnit.SECONDS)
                .compose(this.bindToLifecycle())
                .subscribe(aVoid -> {
                    try {
                        toBigPic(0);
                    } catch (Exception e) {
                        LogUtils.d("Cathy", e.toString());
                    }
                });
        RxView.clicks(mIvPicTwo)
                .throttleFirst(JITTER_SPACING_TIME, TimeUnit.SECONDS)
                .compose(this.bindToLifecycle())
                .subscribe(aVoid -> {
                    try {
                        toBigPic(1);
                    } catch (Exception e) {
                        LogUtils.d("Cathy", e.toString());
                    }
                });
    }

    private void toBigPic(int position) {

        List<ImageBean> imageBeanList = new ArrayList<>();
        ArrayList<AnimationRectBean> animationRectBeanArrayList
                = new ArrayList<>();

        for (int i = 0; i < mInfo.getData().getFiles().size(); i++) {
            ImageBean imageBean = new ImageBean();
            Toll toll = new Toll(); // 收费信息
            toll.setPaid(true);// 是否已經付費
            toll.setToll_money(0);// 付费金额
            toll.setToll_type_string("");// 付费类型
            toll.setPaid_node(0);// 付费节点
            imageBean.setWidth(mInfo.getFiles().get(i).getWidth());
            imageBean.setHeight(mInfo.getFiles().get(i).getHeight());
            imageBean.setImgMimeType(mInfo.getFiles().get(i).getImgMimeType());
            imageBean.setToll(toll);
            imageBean.setStorage_id(mInfo.getData().getFiles().get(i));// 图片附件id
            imageBean.setListCacheUrl(new GlideUrl(ImageUtils.imagePathConvertV2(mInfo.getData().getFiles().get(i), mImageWith, mImageHeight,
                    ImageZipConfig.IMAGE_70_ZIP)));
            imageBeanList.add(imageBean);
            AnimationRectBean rect = AnimationRectBean.buildFromImageView(i == 0 ? mIvPicOne : mIvPicTwo);// 动画矩形
            animationRectBeanArrayList.add(rect);
        }

        GalleryActivity.startToGallery(getContext(), position, imageBeanList,
                animationRectBeanArrayList);

    }
}
