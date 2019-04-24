package com.zhiyicx.thinksnsplus.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zhiyicx.common.utils.ConvertUtils;
import com.zhiyicx.common.utils.log.LogUtils;
import com.zhiyicx.thinksnsplus.R;

public class BannerImageLoaderUtil extends com.youth.banner.loader.ImageLoader {

    private static final long serialVersionUID = 4346287432534848693L;

    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        String url = (String) path;
        Glide.with(context)
                .load(url)
                .asBitmap()
                .placeholder(R.drawable.shape_default_image)
                .error(R.drawable.shape_default_image)
                .into(imageView);
    }

    @Override
    public void displayImage(Context context, String base64, ImageView imageView) {
        byte[] decodedString = ConvertUtils.file2Bytes(base64);
        if (decodedString == null) {
            return;
        }
        Glide.with(context)
                .load(decodedString)
                .asBitmap()
                .placeholder(R.drawable.shape_default_image)
                .error(R.drawable.shape_default_image)
                .into(imageView);
    }
}