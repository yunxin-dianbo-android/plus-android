//package com.zhiyicx.baseproject.utils.glide;
//
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.graphics.Canvas;
//import android.graphics.drawable.BitmapDrawable;
//import android.graphics.drawable.Drawable;
//import android.text.Html;
//import android.widget.TextView;
//
//import com.bumptech.glide.Glide;
//import com.bumptech.glide.request.animation.GlideAnimation;
//import com.bumptech.glide.request.target.SimpleTarget;
//
///**
// * 富文本用来加载网络图片
// * Created by willy on 2018/4/17.
// */
//
//public class RichTextImageGetter implements Html.ImageGetter {
//
//    private URLDrawable urlDrawable = null;
//    private TextView textView;
//    private Context context;
//
//    public RichTextImageGetter(Context context, TextView textView) {
//        this.textView = textView;
//        this.context = context;
//    }
//
//    @Override
//    public Drawable getDrawable(final String source) {
//        CLog.d("richText","getDrawable");
//        urlDrawable = new URLDrawable();
//
//        Glide.with(context).load(source).asBitmap().centerCrop().into(new SimpleTarget<Bitmap>() {
//            @Override
//            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
//                CLog.d("richText","onReadyStart");
//                urlDrawable.bitmap = BitmapUtil.zoomImage(resource,com.cyjh.util.ScreenUtil.dip2px(BaseApplication.getInstance(),20), com.cyjh.util.ScreenUtil.dip2px(BaseApplication.getInstance(),20));
//                urlDrawable.setBounds(0, 0, com.cyjh.util.ScreenUtil.dip2px(BaseApplication.getInstance(),20), com.cyjh.util.ScreenUtil.dip2px(BaseApplication.getInstance(),20));
//                textView.invalidate();
//                textView.setText(textView.getText());//不加这句显示不出来图片
//                CLog.d("richText","onReadyEnd");
//            }
//        });
//        return urlDrawable;
//    }
//
//    public class URLDrawable extends BitmapDrawable {
//        public Bitmap bitmap;
//
//        @Override
//        public void draw(Canvas canvas) {
//            super.draw(canvas);
//            CLog.d("richText","draw start");
//            if (bitmap != null) {
//                canvas.drawBitmap(bitmap, 0, 0, getPaint());
//                CLog.d("richText","draw inner");
//            }
//        }
//    }
//}
