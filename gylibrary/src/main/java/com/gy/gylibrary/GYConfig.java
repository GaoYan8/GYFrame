package com.gy.gylibrary;

import android.app.Application;
import android.content.Context;

import com.gy.gylibrary.exception.CrashHandlerUtil;
import com.gy.gylibrary.http.okhttp.OkHttpUtils;
import com.gy.gylibrary.http.okhttp.cookie.CookieJarImpl;
import com.gy.gylibrary.http.okhttp.cookie.store.MemoryCookieStore;
import com.gy.gylibrary.okhttplib.OkHttpUtil;
import com.gy.gylibrary.okhttplib.annotation.Encoding;
import com.gy.gylibrary.okhttplib.config.HttpConfig;
import com.gy.gylibrary.okhttplib.cookie.PersistentCookieJar;
import com.gy.gylibrary.okhttplib.cookie.cache.SetCookieCache;
import com.gy.gylibrary.okhttplib.cookie.persistence.SharedPrefsCookiePersistor;
import com.gy.gylibrary.okhttplib.interceptor.impl.DefaultExceptionInterceptor;
import com.gy.gylibrary.okhttplib.interceptor.impl.DefaultResultInterceptor;
import com.gy.gylibrary.utils.FileUtil;
import com.gy.gylibrary.utils.LogUtils;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * 全局配置类
 *
 * @author 高炎
 * @email yan.gao@zarltech.com
 * @create 2019/1/10
 * @Describe
 */
public class GYConfig {

    /**
     * 是否为开发模式
     */
    public static boolean isDebug = true;

    /**
     * 文件初始化
     *
     * @param mApplication
     */
    public static void initFileDir(Application mApplication) {
        if (null != mApplication) {
            FileUtil.initFileDir(mApplication);
        }
    }

    /**
     * 初始化网络请求实例
     * 该接口已过时，请使用initOkHttp3Client
     */
    @Deprecated
    public static void initOkHttpClient() {
        OkHttpUtils.initClient(new OkHttpClient.Builder()
                .connectTimeout(com.gy.gylibrary.http.HttpConfig.connectTimeout, TimeUnit.MILLISECONDS)
                .readTimeout(com.gy.gylibrary.http.HttpConfig.readTimeout, TimeUnit.MILLISECONDS)
                .cookieJar(new CookieJarImpl(new MemoryCookieStore()))
                .build());
    }

    /**
     * 异常捕捉
     *
     * @param context
     */
    public static void initCrashHandlerUtil(Context context) {
        if (null != context) {
            CrashHandlerUtil crashHandlerUtil = CrashHandlerUtil.getInstance();
            crashHandlerUtil.init(context);
            crashHandlerUtil.setCrashTip("很抱歉，程序出现异常，即将退出！");
        }
    }

    /**
     * 初始化网络请求框架
     * 需要先初始化 initFileDir()
     */
    public static void initOkHttp3Client(Context context, HttpConfig httpConfig) {
        OkHttpUtil.init(context)
                .setConnectTimeout(httpConfig.getConnectTimeout())//连接超时时间
                .setWriteTimeout(httpConfig.getWriteTimeout())//写超时时间
                .setReadTimeout(httpConfig.getReadTimeout())//读超时时间
                .setMaxCacheSize(httpConfig.getMaxCacheSize())//缓存空间大小
                .setCacheType(httpConfig.getCacheType())//缓存类型
                .setHttpLogTAG(httpConfig.getHttpLogTAG())//设置请求日志标识
                .setIsGzip(httpConfig.isGzip())//Gzip压缩，需要服务端支持
                .setShowHttpLog(httpConfig.isShowHttpLog())//显示请求日志
                .setShowLifecycleLog(httpConfig.isShowLifecycleLog())//显示Activity销毁日志
                .setRetryOnConnectionFailure(httpConfig.isRetryOnConnectionFailure())//失败后不自动重连
                .setCachedDir(new File(FileUtil.getHttpCacheDir(context)))//设置缓存目录
                .setDownloadFileDir(FileUtil.getFileDownloadDir(context))//文件下载保存目录
                .setResponseEncoding(Encoding.UTF_8)//设置全局的服务器响应编码
                .setRequestEncoding(Encoding.UTF_8)//设置全局的请求参数编码
//                .setHttpsCertificate("12306.cer")//设置全局Https证书
                .addResultInterceptor(new DefaultResultInterceptor())//请求结果拦截器
                .addExceptionInterceptor(new DefaultExceptionInterceptor())//请求链路异常拦截器
                .setCookieJar(new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(context)))//持久化cookie
                .build();
        LogUtils.d("OkHttp已初始化");
    }

}


