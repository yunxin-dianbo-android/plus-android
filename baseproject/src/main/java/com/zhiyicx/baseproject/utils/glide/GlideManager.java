package com.zhiyicx.baseproject.utils.glide;

import android.app.Activity;
import android.app.Notification;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Looper;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.NotificationTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.superrtc.util.BitmapUtil;
import com.zhiyi.emoji.ViewUtils;
import com.zhiyicx.common.base.BaseApplication;

import java.io.File;


/**
 * Created by linbinghuang on 2016/6/23.
 * glide管理
 */
public class GlideManager {

    //画圆图
    public static void glideTextMida(Context context, ImageView iv, String res) {

        try {
            if (context == null) {
                return;
            }
            if (context instanceof Activity) {
                Activity activity = (Activity) context;
                if (activity.isFinishing()) {
                    return;
                }
            }
            Glide.with(context).load(Uri.fromFile(new File(res))).into(iv);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //没有默认图
    public static void glide(Context context, ImageView iv, String path, int w, int h) {
        try {
            if (path == null) return;
            Glide.with(context).load(path.trim()).asBitmap().override(w, h).centerCrop().into(iv);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void load(Context context, String url, ImageView iv) {
        Glide.with(context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)//让Glide既缓存全尺寸图片，下次在任何ImageView中加载图片的时候，全尺寸的图片将从缓存中取出，重新调整大小，然后缓存
                //加上这个，防止瀑布流图片加载不全
                .dontTransform()
                .centerCrop()
                .crossFade()
                .into(iv);
    }


    public static void load(Context context, String url, ImageView iv,
                            int width, int height) {
        Glide.with(context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)//让Glide既缓存全尺寸图片，下次在任何ImageView中加载图片的时候，全尺寸的图片将从缓存中取出，重新调整大小，然后缓存
                //加上这个，防止瀑布流图片加载不全
                .dontTransform()
                .centerCrop()
                .crossFade()
                .override(width, height)
                .into(iv);
    }

    public static void load(Context context, int res, ImageView iv,
                            int width, int height) {
        Glide.with(context)
                .load(res)
                .asBitmap()
                .override(width, height)
                .into(iv);
    }

    //没有默认图
    public static void glide(Context context, ImageView iv, String path) {
        try {
            if (path == null) return;
            Glide.with(context).load(path.trim()).asBitmap().into(iv);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


//    public static void glideGrayscaleTransformation(Context context, ImageView iv, String path, int defualtIco) {
//        try {
////            if (path == null) return;
//            if (path == null) {
//                path = "";
//            }
//            Glide.with(context).load(path.trim()).bitmapTransform(new GrayscaleTransformation(context)).placeholder(defualtIco).into(iv);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }

    public static void glide(Context context, ImageView iv, String path, int defualtIco) {
        try {
//            if (path == null) return;
            if (path == null) {
                path = "";
            }
            Glide.with(context).load(path.trim()).asBitmap().placeholder(defualtIco).into(iv);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void glideLoadFailedLoadDefault(Context context, final ImageView iv, String path, final int defualtIco) {
        try {
//            if (path == null) return;
            if (path == null) {
                path = "";
            }
            Glide.with(context).load(path.trim()).asBitmap().into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    //加载完成后的处理
                    iv.setImageBitmap(resource);
                }

                @Override
                public void onLoadFailed(Exception e, Drawable errorDrawable) {
                    super.onLoadFailed(e, errorDrawable);
                    iv.setImageResource(defualtIco);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void glideNoCache(Context context, ImageView iv, String path, int defualtIco) {
        try {
//            if (path == null) return;
            if (path == null) {
                path = "";
            }
            Glide.with(context).load(path.trim()).asBitmap().placeholder(defualtIco).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).into(iv);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void glideNoCache(Context context, ImageView iv, String path) {
        try {
//            if (path == null) return;
            if (path == null) {
                path = "";
            }
            Glide.with(context).load(path.trim()).asBitmap().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).into(iv);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void glideRoundNoCache(Context context, ImageView iv, int roundSize, int res) {
        try {
            Glide.with(context).load(res).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).bitmapTransform(new CornersTransform(context, ViewUtils.dip2px(BaseApplication.getContext(),roundSize))).into(iv);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void glideRoundNoCache(Context context, ImageView iv, int roundSize, String path) {
        try {
//            if (path == null) return;
            if (path == null) {
                path = "";
            }
            Glide.with(context).load(path.trim()).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).bitmapTransform(new CornersTransform(context, ViewUtils.dip2px(BaseApplication.getContext(),roundSize))).into(iv);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void glideGif(Context context, ImageView iv, String path) {
        try {
            if (path == null) {
                path = "";
            }
            Glide.with(context).load(path).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).skipMemoryCache(true).into(iv);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void glideGifNoCatch(Context context, ImageView iv, String path) {
        try {
            if (path == null) {
                path = "";
            }
            Glide.with(context).load(path).asGif().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).into(iv);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

//    GlideApp.with(mContext)
//            .asBitmap()
//                        .load(item.getContent())
//            .error(R.drawable.default_bg)
//                        .diskCacheStrategy(DiskCacheStrategy.ALL)
//                        .into(new SimpleTarget<Bitmap>() {
//        @Override
//        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
//            int widht = resource.getWidth();
//            int height = resource.getHeight();
//            if(widht>pic_max_width){
//                float multiple = ((float) widht)/pic_max_width+0.5f;
//                widht = (int) (widht/multiple);
//                height = (int) (height/multiple);
//            }
//            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) img.getLayoutParams();
//            params.width = widht;
//            params.height = height;
//            img.setLayoutParams(params);
//            img.setImageBitmap(resource);
//        }
//    });

    public static void glideWrapContent(final Context context, final ImageView iv, String path, /*int defualtIco,*/final int maxWidth, final int maxHeith) {
        try {
            if (path == null) {
                path = "";
            }
            Glide.with(context).load(path.trim()).asBitmap()/*.placeholder(defualtIco)*/.diskCacheStrategy(DiskCacheStrategy.ALL).into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    int widht = resource.getWidth();
                    int height = resource.getHeight();
                    int width4Width = widht;
                    int height4Width = height;
                    int width4Height = widht;
                    int height4Height = height;
                    if (widht > maxWidth) {
                        float multiple = ((float) widht) / maxWidth + 0.5f;
                        width4Width = (int) (widht / multiple);
                        height4Width = (int) (height / multiple);
                    }
                    if (height > maxHeith) {
                        float multiple = ((float) height) / maxHeith + 0.5f;
                        width4Height = (int) (widht / multiple);
                        height4Height = (int) (height / multiple);
                    }
                    if (width4Width < width4Height) {
                        widht = width4Width;
                        height = height4Width;
                    } else {
                        widht = width4Height;
                        height = height4Height;
                    }
                    ViewGroup.LayoutParams params = iv.getLayoutParams();
                    params.width = widht;
                    params.height = height;
                    iv.setLayoutParams(params);
                    iv.setImageBitmap(resource);
                }

                @Override
                public void onLoadFailed(Exception e, Drawable errorDrawable) {
                    super.onLoadFailed(e, errorDrawable);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void glideWrapContentHasDefaultHasMinWidth(final Context context, final ImageView iv, String path, final int defualtIco, final int maxWidth, final int maxHeith, final int minWidth) {
        try {
            if (path == null) {
                path = "";
            }
            Glide.with(context).load(path.trim()).asBitmap().placeholder(defualtIco).diskCacheStrategy(DiskCacheStrategy.ALL).into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    int widht = resource.getWidth();
                    int height = resource.getHeight();
                    int width4Width = widht;
                    int height4Width = height;
                    int width4Height = widht;
                    int height4Height = height;
                    if (widht < minWidth) {
                        float multiple = (float) minWidth / widht;
                        height = (int) (height * multiple);
                        widht = minWidth;
                    } else {
                        if (widht > maxWidth) {
                            float multiple = ((float) widht) / maxWidth;
                            width4Width = (int) (widht / multiple);
                            height4Width = (int) (height / multiple);
                        }
                        if (height > maxHeith) {
                            float multiple = ((float) height) / maxHeith;
                            width4Height = (int) (widht / multiple);
                            height4Height = (int) (height / multiple);
                        }
                        if (width4Width <= width4Height) {
                            widht = width4Width;
                            height = height4Width;
                        } else {
                            widht = width4Height;
                            height = height4Height;
                        }
                    }
                    ViewGroup.LayoutParams params = iv.getLayoutParams();
                    params.width = widht;
                    params.height = height;
                    iv.setLayoutParams(params);
                    iv.setImageBitmap(resource);
                }

                @Override
                public void onLoadFailed(Exception e, Drawable errorDrawable) {
                    ViewGroup.LayoutParams layoutParams = iv.getLayoutParams();

                    layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                    layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    iv.setLayoutParams(layoutParams);
                    iv.setImageResource(defualtIco);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void glideWrapContentHasDefault(final Context context, final ImageView iv, String path, final int defualtIco, final int maxWidth, final int maxHeith) {
        try {
            if (path == null) {
                path = "";
            }
            Glide.with(context).load(path.trim()).asBitmap().placeholder(defualtIco).diskCacheStrategy(DiskCacheStrategy.ALL).into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    int widht = resource.getWidth();
                    int height = resource.getHeight();
                    int width4Width = widht;
                    int height4Width = height;
                    int width4Height = widht;
                    int height4Height = height;
                    if (widht > maxWidth) {
                        float multiple = ((float) widht) / maxWidth;
                        width4Width = (int) (widht / multiple);
                        height4Width = (int) (height / multiple);
                    }
                    if (height > maxHeith) {
                        float multiple = ((float) height) / maxHeith;
                        width4Height = (int) (widht / multiple);
                        height4Height = (int) (height / multiple);
                    }
                    if (width4Width <= width4Height) {
                        widht = width4Width;
                        height = height4Width;
                    } else {
                        widht = width4Height;
                        height = height4Height;
                    }
                    ViewGroup.LayoutParams params = iv.getLayoutParams();
                    params.width = widht;
                    params.height = height;
                    iv.setLayoutParams(params);
                    iv.setImageBitmap(resource);
                }

                @Override
                public void onLoadFailed(Exception e, Drawable errorDrawable) {
                    ViewGroup.LayoutParams layoutParams = iv.getLayoutParams();

                    layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                    layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    iv.setLayoutParams(layoutParams);
                    iv.setImageResource(defualtIco);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void glideWrapContentHasMinWidthNoCatch(final Context context, final ImageView iv, String path,/* final int defualtIco, */final int maxWidth, final int maxHeith, final int minWidth) {
        try {
            if (path == null) {
                path = "";
            }
            Glide.with(context).load(path.trim()).asBitmap()/*.placeholder(defualtIco)*/.diskCacheStrategy(DiskCacheStrategy.NONE).into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    int widht = resource.getWidth();
                    int height = resource.getHeight();
                    int width4Width = widht;
                    int height4Width = height;
                    int width4Height = widht;
                    int height4Height = height;
                    if (widht < minWidth) {
                        float multiple = (float) minWidth / widht;
                        height = (int) (height * multiple);
                        widht = minWidth;
                    } else {
                        if (widht > maxWidth) {
                            float multiple = ((float) widht) / maxWidth;
                            width4Width = (int) (widht / multiple);
                            height4Width = (int) (height / multiple);
                        }
                        if (height > maxHeith) {
                            float multiple = ((float) height) / maxHeith;
                            width4Height = (int) (widht / multiple);
                            height4Height = (int) (height / multiple);
                        }
                        if (width4Width <= width4Height) {
                            widht = width4Width;
                            height = height4Width;
                        } else {
                            widht = width4Height;
                            height = height4Height;
                        }
                    }
                    ViewGroup.LayoutParams params = iv.getLayoutParams();
                    params.width = widht;
                    params.height = height;
                    iv.setLayoutParams(params);
                    iv.setImageBitmap(resource);
                }

                @Override
                public void onLoadFailed(Exception e, Drawable errorDrawable) {
                    ViewGroup.LayoutParams layoutParams = iv.getLayoutParams();

                    layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                    layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    iv.setLayoutParams(layoutParams);
//                    iv.setImageResource(defualtIco);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void glide(Context context, ImageView iv, File file) {
        try {
            if (file == null) return;
            Glide.with(context).load(file).asBitmap().into(iv);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void glide(Context context, ImageView iv, File file, int defualtIco) {
        try {
            if (file == null) return;
            Glide.with(context).load(file).asBitmap().placeholder(defualtIco).into(iv);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void glideCircle(Context context, ImageView iv, File file, int defaultIco) {
        try {
            if (file == null) return;
            Glide.with(context).load(file).asBitmap().transform(new GlideCircleTransform(context)).placeholder(defaultIco).into(iv);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void glide1MinuteTimeOut(Context context, ImageView iv, String url, int errRes, RequestListener<? super String, GlideDrawable> requestListener) {
        Glide.with(context).load(url).listener(requestListener).error(errRes).crossFade(1000).into(iv);
    }

    /**
     * 图片加载及异常处理
     *
     * @param context
     * @param iv
     * @param path
     * @param requestListener
     */
    public static void glide(Context context, ImageView iv, String path, RequestListener<? super String, GlideDrawable> requestListener) {
        try {
            if (path == null) return;
            Glide.with(context).load(path).listener(requestListener).into(iv);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 图片加载及异常处理
     *
     * @param context
     * @param iv
     * @param path
     * @param requestListener
     */
    public static void glideAsBitmap(Context context, ImageView iv, String path, RequestListener<? super String, Bitmap> requestListener) {
        try {
            if (path == null) return;
            Glide.with(context).load(path).asBitmap().listener(requestListener).into(iv);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void glide(Context context, ImageView iv, int res, int defualtIco) {
        try {
            Glide.with(context).load(res).asBitmap().placeholder(defualtIco).into(iv);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //没有默认图
    public static void glideWh(Context context, ImageView iv, String path, int defualtIco, int w, int h) {
        try {
            if (path == null) return;
            Glide.with(context).load(path.trim()).asBitmap().override(w, h).placeholder(defualtIco).into(iv);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //加载本地gif图
    public static void glideForLocalGif(Context context, ImageView iv, int resId) {
        try {
            Glide.with(context).load(resId).asGif().into(iv);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    //加载本地gif图
    public static void glideForSdcardGif(Context context, ImageView iv, int w, int h, String str) {
        try {
            Glide.with(context).load(str).asGif().override(w, h).into(iv);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //加载本地gif图
    public static void glideForSdcardGif(Context context, ImageView iv, String str, int w, int h) {
        try {
            Glide.with(context).load(str).asGif().override(w, h).into(iv);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //加载本地gif图
    public static void glideForSdcardGif(Context context, ImageView iv, String str) {
        try {
            Glide.with(context).load(str).asGif().into(iv);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void glideForLocalGif(Context context, ImageView imageView, String path) {
        try {
            Glide.with(context).load(path).asGif().into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置图片宽高的加载
     *
     * @param context    上下文
     * @param iv         图片控件
     * @param path       图片的路径
     * @param height     设置图片高度
     * @param width      设置图片宽度
     * @param defaultIco 默认的图片
     */
    public static void glide(Context context, ImageView iv, String path, int width, int height, int defaultIco) {

        try {
            if (path == null) return;
            Glide.with(context).load(path.trim()).asBitmap().override(width, height).placeholder(defaultIco).into(iv);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void glide(Context context, ImageView iv, int res, int width, int height, int defaultIco) {

        try {
            Glide.with(context).load(res).asBitmap().override(width, height).placeholder(defaultIco).into(iv);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //画圆图
    public static void glideCircle(Context context, ImageView iv, String res, int defaultIco) {

        try {
            if (context == null) {
                return;
            }
            if (context instanceof Activity) {
                Activity activity = (Activity) context;
                if (activity.isFinishing()) {
                    return;
                }
            }
            Glide.with(context).load(res).asBitmap().transform(new GlideCircleTransform(context)).placeholder(defaultIco).into(iv);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //画圆图
    public static void glideCircleGif(Context context, ImageView iv, String res, int defaultIco) {

        try {
            if (context == null) {
                return;
            }
            if (context instanceof Activity) {
                Activity activity = (Activity) context;
                if (activity.isFinishing()) {
                    return;
                }
            }
            Glide.with(context).load(res).transform(new GlideCircleTransform(context)).placeholder(defaultIco).into(iv);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //画圆图
    public static void glideGif(Context context, ImageView iv, String res, RequestListener<String, GifDrawable> listener) {
        try {
            if (context == null) {
                return;
            }
            if (context instanceof Activity) {
                Activity activity = (Activity) context;
                if (activity.isFinishing()) {
                    return;
                }
            }
            Glide.with(context).load(res).asGif().into(iv);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void glideCircle(Context context, ImageView iv, String res) {
        try {
            Glide.with(context).load(res).asBitmap().transform(new GlideCircleTransform(context)).into(iv);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void glideCircle(Context context, ImageView iv, int res) {
        try {
            Glide.with(context).load(res).asBitmap().transform(new GlideCircleTransform(context)).into(iv);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public static void glideRound2(Context context, ImageView iv, String res, int dp, int defaultRes) {
//        try {
//            Glide.with(context).load(res).bitmapTransform(new CornersTransform(context, SystemUtils.dip2px(dp))).error(defaultRes).into(iv);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


    /**
     * 圆角图片
     *
     * @param context
     * @param iv
     * @param res
     */
    public static void glideRound(Context context, ImageView iv, String res, int roundSize, int defaultRes) {
        try {
            Glide.with(context).load(res).asBitmap().error(defaultRes).transform(new GlideRoundTransform(context, roundSize)).into(iv);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 高斯模糊
     *
     * @param context
     * @param iv
     * @param res
     * @param defaultIco
     * @param blurRadius 模糊程度 0-100
     */
    public static void glideBlur(Context context, ImageView iv, String res, int defaultIco, float blurRadius) {

        try {
            Glide.with(context).load(res).asBitmap().centerCrop().transform(new FastBlur(context, blurRadius)).placeholder(defaultIco).into(iv);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 高斯模糊
     *
     * @param context
     * @param iv
     * @param res
     * @param blurRadius 模糊程度 0-100
     */
    public static void glideBlur(Context context, ImageView iv, String res, float blurRadius) {

        try {
            Glide.with(context).load(res).asBitmap().centerCrop().transform(new FastBlur(context, blurRadius)).into(iv);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 高斯模糊
     *
     * @param context
     * @param iv
     * @param res
     * @param defaultIco
     * @param blurRadius 模糊程度 0-100
     */
    public static void glideBlur(Context context, ImageView iv, File res, int defaultIco, float blurRadius) {

        try {
            Glide.with(context).load(res).asBitmap().override(300, 150).centerCrop().transform(new FastBlur(context, blurRadius)).placeholder(defaultIco).into(iv);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 高斯模糊
     *
     * @param context
     * @param iv
     * @param res
     * @param defaultIco
     * @param blurRadius 模糊程度 0-100
     */
    public static void glideBlur(Context context, ImageView iv, String res, int defaultIco, int w, int h, float blurRadius) {

        try {
            Glide.with(context).load(res).override(w, h).centerCrop()/*.bitmapTransform(new CropTransformation(context, w, h, CropTransformation.CropType.CENTER) ,new BlurTransformation(context,37))*/.into(iv);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void glideBlur(Context context, ImageView iv, int res, int defaultIco, float blurRadius) {
        Glide.with(context).load(res).asBitmap().override(300, 150).centerCrop().transform(new FastBlur(context, blurRadius)).placeholder(defaultIco).into(iv);
    }

    public static void glideBlurNoDefault(Context context, ImageView iv, String res, float blurRadius) {
        Glide.with(context).load(res).crossFade().override(300, 150).centerCrop().transform(new FastBlur(context, blurRadius)).into(iv);
    }

    /**
     * 不要默认图片的设置
     *
     * @param context
     * @param iv
     * @param res
     */
    public static void glideNoDefault(Context context, ImageView iv, String res) {
        Glide.with(context).load(res).asBitmap().into(iv);
    }

    /**
     * 设置NotificationTarget
     */
    public static NotificationTarget glide(Context context, Notification mNotificationItem, int resId, int notificationId) {
        return new NotificationTarget(
                context,
                mNotificationItem.contentView,
                resId,
                mNotificationItem,
                notificationId);
    }

    public static void glideRotate(Context context, ImageView iv, String path, float rotate, int defaultIco) {
        if (path == null) return;
        Glide.with(context).load(path).asBitmap().placeholder(defaultIco).transform(new RotateTransformation(context, rotate)).into(iv);
    }

    /**
     * glide 旋转的
     */
    public static class RotateTransformation extends BitmapTransformation {

        private float rotateRotationAngle = 0f;

        public RotateTransformation(Context context, float rotateRotationAngle) {
            super(context);

            this.rotateRotationAngle = rotateRotationAngle;
        }

        @Override
        protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
            Matrix matrix = new Matrix();

            matrix.postRotate(rotateRotationAngle);
            return Bitmap.createBitmap(toTransform, 0, 0, toTransform.getWidth(), toTransform.getHeight(), matrix, true);
        }

        @Override
        public String getId() {
            return "rotate" + rotateRotationAngle;
        }
    }

    public static void getDrawable(Context context, String url, Drawable drawable) {

        try {
            Glide.with(context)
                    .load(url)
                    .into(new SimpleTarget<GlideDrawable>() {
                        @Override
                        public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {

                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 清除glide图片磁盘缓存
     */
    public static void clearImageDiskCache() {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.get(BaseApplication.getContext()).clearDiskCache();
                    }
                }).start();
            } else {
                Glide.get(BaseApplication.getContext()).clearDiskCache();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取glide的磁盘缓存大小
     *
     * @return
     */
//    public static String getImageDiskCache() {
//        return Util.getFormatSize(FileUtils.getFileAllSize(Glide.getPhotoCacheDir(BaseApplication.getInstance()).getPath()));
//    }

//    public static class GifIconTransformation extends BitmapTransformation {
//        Paint paint;
//        Bitmap gifbmp;
//
//        public GifIconTransformation(Context context) {
//            super(context);
//        }
//
//        @Override
//        protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
//            return addGifIcon(toTransform);
//        }
//
//        @Override
//        public String getId() {
//            return getClass().getName();
//        }
//
//        private Bitmap addGifIcon(Bitmap oldbitmap) {
//            gifbmp = BitmapUtil.drawable2Bitmap(BaseApplication.getContext().getResources().getDrawable(R.drawable.icon_doll_watermark_logo));
//            int width = oldbitmap.getWidth();
//            int height = oldbitmap.getHeight();
//
//            int gifbmpWidth = gifbmp.getWidth();
//            int gifbmpHeight = gifbmp.getHeight();
//
//            Canvas canvas = new Canvas(oldbitmap);
//            canvas.drawBitmap(oldbitmap, 0, 0, null);
//            canvas.drawRect(width - gifbmpWidth - 18, height - gifbmpHeight, width, height, paint);
//            canvas.drawBitmap(gifbmp, width - gifbmpWidth - 9, height - gifbmpHeight, null);
//            //canvas.save(Canvas.ALL_SAVE_FLAG);
//            //canvas.restore();
//
//            return oldbitmap;
//        }
//    }

    public static class GlideCircleTransform extends BitmapTransformation {
        public GlideCircleTransform(Context context) {
            super(context);
        }

        @Override
        protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
            return circleCrop(pool, toTransform);
        }

        private Bitmap circleCrop(BitmapPool pool, Bitmap source) {
            if (source == null) return null;
            int size = Math.min(source.getWidth(), source.getHeight());
            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;
            Bitmap squared = Bitmap.createBitmap(source, x, y, size, size);
            Bitmap result = pool.get(size, size, Bitmap.Config.ARGB_8888);
            if (result == null) {
                result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
            }
            Canvas canvas = new Canvas(result);
            Paint paint = new Paint();
            paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
            paint.setAntiAlias(true);
            float r = size / 2f;
            canvas.drawCircle(r, r, r, paint);
            return result;
        }

        @Override
        public String getId() {
            return getClass().getName();
        }
    }

    public static class GlideRoundTransform extends BitmapTransformation {

        private static float radius = 0f;

        public GlideRoundTransform(Context context) {
            this(context, 4);
        }

        public GlideRoundTransform(Context context, int dp) {
            super(context);
            this.radius = Resources.getSystem().getDisplayMetrics().density * dp;
        }

        @Override
        protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
            return roundCrop(pool, toTransform);
        }

        private static Bitmap roundCrop(BitmapPool pool, Bitmap source) {
            if (source == null) return null;

            Bitmap result = pool.get(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
            if (result == null) {
                result = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
            }

            Canvas canvas = new Canvas(result);
            Paint paint = new Paint();
            paint.setShader(new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
            paint.setAntiAlias(true);
            RectF rectF = new RectF(0f, 0f, source.getWidth(), source.getHeight());
            canvas.drawRoundRect(rectF, radius, radius, paint);
            return result;
        }

        @Override
        public String getId() {
            return getClass().getName() + Math.round(radius);
        }
    }

//    public class MyGlideModule implements GlideModule {
//        @Override public void applyOptions(Context context, GlideBuilder builder) {
//            // Apply options to the builder here.
//        }
//
//        @Override public void registerComponents(Context context, Glide glide) {
//
//            OkHttpClient client = new OkHttpClient();
//            client.setConnectTimeout(1, TimeUnit.SECONDS);
//            client  .setReadTimeout(1, TimeUnit.SECONDS);
//            client  .setWriteTimeout(1, TimeUnit.SECONDS);
//            OkHttpUrlLoader.Factory factory=new OkHttpUrlLoader.Factory(client);
//
//            glide.register(GlideUrl.class, InputStream.class, factory);
//        }
//    }
}
