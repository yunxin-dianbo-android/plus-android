package com.zhiyicx.thinksnsplus.modules.photopicker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhiyicx.baseproject.base.TSFragment;
import com.zhiyicx.baseproject.impl.imageloader.glide.GlideImageConfig;
import com.zhiyicx.common.utils.ActivityHandler;
import com.zhiyicx.common.utils.DeviceUtils;
import com.zhiyicx.common.utils.ParcelableDataUtil;
import com.zhiyicx.common.utils.imageloader.core.ImageLoader;
import com.zhiyicx.thinksnsplus.R;
import com.zhiyicx.thinksnsplus.base.AppApplication;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.iwf.photopicker.entity.PhotoDirectory;
import me.iwf.photopicker.utils.MediaStoreHelper;

import static com.zhiyicx.thinksnsplus.modules.photopicker.PhotoAlbumDetailsFragment.EXTRA_CAMERA;
import static com.zhiyicx.thinksnsplus.modules.photopicker.PhotoAlbumDetailsFragment.EXTRA_ORIGIN;
import static me.iwf.photopicker.PhotoPicker.EXTRA_SHOW_GIF;

/**
 * @author LiuChao
 * @describe
 * @date 2017/2/6
 * @contact email:450127106@qq.com
 */
public class PhotoAlbumListFragment extends TSFragment {
    // 相册列表被选中的位置
    public static final String SELECTED_DIRECTORY_NUMBER = "selected_directory_number";
    // 相册列表被选中的名称
    public static final String SELECTED_DIRECTORY_NAME = "selected_directory_name";
    // 相册列表的内容
    public static final String ALL_PHOTOS = "all_photos";
    @BindView(R.id.rv_photo_album_list)
    RecyclerView mRvPhotoAlbumList;
    private CommonAdapter<PhotoDirectory> listAdapter;
    // 所有 photos 的路径
    private ArrayList<PhotoDirectory> directories;
    private final static String EXTRA_GIF = "gif";

    View mStatusBarPlaceholder;

    @Override
    protected int getBodyLayoutId() {
        return R.layout.fragment_photo_album_list;
    }


    @Override
    protected int getToolBarLayoutId() {
        return R.layout.toolbar_custom_contain_status_bar;
    }

    @Override
    protected boolean showToolBarDivider() {
        return true;
    }

    @Override
    protected int setLeftImg() {
        return 0;
    }

    @Override
    protected String setCenterTitle() {
        return getString(R.string.album_list);
    }

    @Override
    protected String setRightTitle() {
        return getString(R.string.cancel);
    }

    @Override
    protected void setRightClick() {
        ActivityHandler.getInstance().removeActivity(PhotoAlbumDetailsActivity.class);
        getActivity().finish();
        getActivity().overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
//        getActivity().overridePendingTransition(R.anim.slide_from_right_in, R.anim.slide_from_left_out);
    }

    @Override
    protected void initView(View rootView) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRvPhotoAlbumList.setLayoutManager(linearLayoutManager);
        directories = new ArrayList<>();
        listAdapter = new CommonAdapter<PhotoDirectory>(getContext(), R.layout.item_photo_album, directories) {
            @Override
            protected void convert(ViewHolder holder, final PhotoDirectory photoDirectory, final int position) {
                ImageView ivCover = holder.getView(R.id.iv_dir_cover);
                TextView tvName = holder.getView(R.id.tv_dir_name);
                TextView tvCount = holder.getView(R.id.tv_dir_count);
                ImageLoader imageLoader = AppApplication.AppComponentHolder.getAppComponent().imageLoader();
                imageLoader.loadImage(getContext(), GlideImageConfig.builder()
                        .url(photoDirectory.getCoverPath())
                        .imagerView(ivCover)
                        .build());
                tvName.setText(photoDirectory.getName());
                tvCount.setText(tvCount.getContext().getString(R.string.album_image_count, photoDirectory.getPhotos().size()));

            }
        };
        listAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                final PhotoDirectory photoDirectory = directories.get(position);
                Bundle bundle = new Bundle();
                bundle.putInt(SELECTED_DIRECTORY_NUMBER, position);
                bundle.putParcelableArrayList(ALL_PHOTOS, directories);
                bundle.putString(SELECTED_DIRECTORY_NAME, photoDirectory.getName());
                bundle.putBoolean(ParcelableDataUtil.USEUTIL, true);
                bundle.putStringArrayList(EXTRA_ORIGIN, getArguments().getStringArrayList(EXTRA_ORIGIN));
                ParcelableDataUtil.getSingleInstance().setBundle(bundle);
                Intent intent = new Intent(getActivity(), PhotoAlbumDetailsActivity.class);
                getActivity().setResult(Activity.RESULT_OK, intent);
                getActivity().finish();
                // 从右边进入，从左边出去
                getActivity().overridePendingTransition(R.anim.slide_from_right_in, R.anim.slide_from_left_out);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

        mRvPhotoAlbumList.setAdapter(listAdapter);

        mStatusBarPlaceholder = rootView.findViewById(R.id.v_status_bar_placeholder);
        initStatusBar();

    }
    private void initStatusBar() {
        // toolBar设置状态栏高度的marginTop
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, DeviceUtils
                .getStatuBarHeight(getContext()));
        mStatusBarPlaceholder.setLayoutParams(layoutParams);
    }

    @Override
    protected void initData() {
        Bundle mediaStoreArgs = new Bundle();
        mediaStoreArgs.putBoolean(EXTRA_SHOW_GIF, true);
        MediaStoreHelper.getPhotoDirs(getActivity(), mediaStoreArgs,
                dirs -> {
                    directories.clear();
                    directories.addAll(dirs);
                    listAdapter.notifyDataSetChanged();
                });
    }

    public static PhotoAlbumListFragment initFragment(Bundle bundle) {
        PhotoAlbumListFragment photoAlbumListFragment = new PhotoAlbumListFragment();
        photoAlbumListFragment.setArguments(bundle);
        return photoAlbumListFragment;
    }



    @Override
    protected boolean setUseSatusbar() {
        return true;
    }

    @Override
    protected boolean setStatusbarGrey() {
        return false;
    }

    @Override
    protected boolean setUseStatusView() {
        return false;
    }


//    @Override
//    protected boolean showToolBarDivider() {
//        return false;
//    }

    @Override
    protected boolean showToolbar() {
        return true;
    }

}
