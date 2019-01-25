package com.gy.gylibrary;

import android.app.Application;
import android.content.Context;

import com.gy.gylibrary.exception.CrashHandlerUtil;
import com.gy.gylibrary.http.HttpConfig;
import com.gy.gylibrary.http.okhttp.OkHttpUtils;
import com.gy.gylibrary.http.okhttp.cookie.CookieJarImpl;
import com.gy.gylibrary.http.okhttp.cookie.store.MemoryCookieStore;
import com.gy.gylibrary.utils.FileUtil;

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
     */
    public static void initOkHttpClient() {
        OkHttpUtils.initClient(new OkHttpClient.Builder()
                .connectTimeout(HttpConfig.connectTimeout, TimeUnit.MILLISECONDS)
                .readTimeout(HttpConfig.readTimeout, TimeUnit.MILLISECONDS)
                .cookieJar(new CookieJarImpl(new MemoryCookieStore()))
                .build());
    }

    /**
     * 异常捕捉
     * @param context
     */
    public static void initCrashHandlerUtil(Context context) {
        if (null != context) {
            CrashHandlerUtil crashHandlerUtil = CrashHandlerUtil.getInstance();
            crashHandlerUtil.init(context);
            crashHandlerUtil.setCrashTip("很抱歉，程序出现异常，即将退出！");
        }
    }
}


