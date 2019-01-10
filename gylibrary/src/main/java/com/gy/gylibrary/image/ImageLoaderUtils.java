package com.gy.gylibrary.image;

import android.content.Context;
import android.widget.ImageView;

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



    public <T> void displayUserHead(Context context, T res, ImageView imageview){
        display(context,res,imageview, R.mipmap.img_default_header,R.mipmap.img_default_header);
    }




}
