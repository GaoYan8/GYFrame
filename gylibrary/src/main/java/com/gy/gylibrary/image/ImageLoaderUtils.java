package com.gy.gylibrary.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.gy.gylibrary.R;


/**
 * @author 高炎
 * @email yan.gao@zarltech.com
 * @create 2018/9/26
 * @Describe
 */
public class ImageLoaderUtils {
    /**
     * 单例模式，实例化ImageLoader对象
     */
    private volatile static ImageLoaderUtils singleton;

    private ImageLoaderUtils() {

    }

    public static ImageLoaderUtils getInstance() {
        if (singleton == null) {
            synchronized (ImageLoaderUtils.class) {
                if (singleton == null) {
                    singleton = new ImageLoaderUtils();
                }
            }
        }
        return singleton;
    }

    /**
     * 加载图片
     * <p>
     * load SD卡资源：load("file://"+ Environment.getExternalStorageDirectory().getPath()+"/test.jpg")
     * load assets资源：load("file:///android_asset/f003.gif")
     * load raw资源：load("Android.resource://com.frank.glide/raw/raw_1")或load("android.resource://com.frank.glide/raw/"+R.raw.raw_1)
     * load drawable资源：load("android.resource://com.frank.glide/drawable/news")或load("android.resource://com.frank.glide/drawable/"+R.drawable.news)
     * load ContentProvider资源：load("content://media/external/images/media/139469")
     * load http资源：load("http://img.my.csdn.net/uploads/201508/05/1438760757_3588.jpg")
     * load https资源：load("https://img.alicdn.com/tps/TB1uyhoMpXXXXcLXVXXXXXXXXXX-476-538.jpg_240x5000q50.jpg_.webp")
     */
    public <T> void display(Context context, T res, ImageView imageview) {
        display(context, res, imageview, 0, 0);
    }

    /**
     * 加载图片
     *
     * @param context
     * @param res
     * @param imageview
     * @param defaultResource
     * @param failureResouse
     * @param <T>
     */
    public <T> void display(Context context, T res, ImageView imageview, int defaultResource, int failureResouse) {
        GlideRequest build = GlideApp.with(context).load(res);
        if (0 != defaultResource) {
            build.placeholder(defaultResource); //设置占位图
        }
        if (0 != failureResouse) {
            build.error(failureResouse); //设置错误图片;
        }
        build.into(imageview);
    }

    /**
     * 加载用户图像
     *
     * @param context
     * @param res
     * @param imageview
     * @param <T>
     */
    public <T> void displayUserHead(Context context, T res, ImageView imageview) {
        display(context, res, imageview, R.mipmap.img_default_header, R.mipmap.img_default_header);
    }




    /**
     * 加载gif
     *
     * @param context   上下文
     * @param url       图片路径
     * @param imageView 承载图片ImageView
     */
    public void loadAsGifImage(@NonNull Context context, @NonNull String url, @NonNull ImageView imageView) {
        GlideApp.with(context)
                .asGif()
                .load(url)
                .into(imageView);
    }


    /**
     * 加载相册目录
     *
     * @param context   上下文
     * @param url       图片路径
     * @param imageView 承载图片ImageView
     */
    public void loadAlbumFolderImage(@NonNull final Context context, @NonNull final String url, @NonNull final ImageView imageView) {
        GlideApp.with(context)
                .asBitmap()
                .override(180, 180)
                .centerCrop()
                .sizeMultiplier(0.5f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.picture_icon_placeholder)
                .load(url)
                .into(new BitmapImageViewTarget(imageView) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.
                                        create(context.getResources(), resource);
                        circularBitmapDrawable.setCornerRadius(8);
                        imageView.setImageDrawable(circularBitmapDrawable);
                    }
                });
    }


    /**
     * 加载图片列表图片
     *
     * @param context   上下文
     * @param url       图片路径
     * @param imageView 承载图片ImageView
     */
    public void loadAlbumGridImage(@NonNull Context context, @NonNull String url, @NonNull ImageView imageView) {
        GlideApp.with(context)
                .load(url)
                .override(200, 200)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.picture_image_placeholder)
                .into(imageView);
    }
}
