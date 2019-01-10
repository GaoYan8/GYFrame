package com.gy.gylibrary.image;


import android.content.Context;
import android.support.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.module.AppGlideModule;
import com.gy.gylibrary.utils.FileUtil;

/**
 * setMemoryCache()
 * <p>
 * 用于配置Glide的内存缓存策略，默认配置是LruResourceCache。
 *
 * <p>
 * <p>
 * setBitmapPool()
 * <p>
 * 用于配置Glide的Bitmap缓存池，默认配置是LruBitmapPool。
 *
 * <p>
 * <p>
 * setDiskCache()
 * <p>
 * 用于配置Glide的硬盘缓存策略，默认配置是InternalCacheDiskCacheFactory。
 *
 * <p>
 * <p>
 * setDiskCacheService()
 * <p>
 * 用于配置Glide读取缓存中图片的异步执行器，默认配置是FifoPriorityThreadPoolExecutor，
 * <p>
 * 也就是先入先出原则。
 *
 * <p>
 * <p>
 * setResizeService()
 * <p>
 * 用于配置Glide读取非缓存中图片的异步执行器，默认配置也是FifoPriorityThreadPoolExecutor。
 *
 * <p>
 * <p>
 * setDecodeFormat()
 * <p>
 * 用于配置Glide加载图片的解码模式，默认配置是RGB_565。
 *
 * 用户配置图片缓存地址
 *
 * @author 高炎
 * @email yan.gao@zarltech.com
 * @create 2018/10/29
 * @Describe
 */
@GlideModule
public class GlideCache extends AppGlideModule {
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        //设置图片的显示格式ARGB_8888(指图片大小为32bit)
        //builder.setDiskCache(new DiskLruCacheFactory(FileUtil.getCacheDownloadDir(context), 100 * 1000 * 1000));
        builder.setDiskCache(new DiskLruCacheFactory(FileUtil.getCacheDownloadDir(context), 100 * 1000 * 1000));
    }


    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {

    }
}

