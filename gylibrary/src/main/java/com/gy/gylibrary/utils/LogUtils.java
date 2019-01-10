package com.gy.gylibrary.utils;

import android.util.Log;

import com.gy.gylibrary.BuildConfig;

/**
 *
 */
public class LogUtils {
    /**
     * 日志输出时的TAG
     */
    private static String TAG = LogUtils.class.getSimpleName();

    /**
     * 是否允许输出log
     */
    private static boolean isDebug = BuildConfig.DEBUG;
    /**
     * 用于记时的变量
     */
    private static long mTimestamp = 0;

    /**
     * 以级别为 v 的形式输出LOG
     */
    public static void v(String msg) {
        v(TAG, msg);
    }

    /**
     * 以级别为 v 的形式输出LOG
     */
    public static void v(String tag, String msg) {
        if (isDebug) {
            Log.v(tag, msg);
        }
    }

    /**
     * 以级别为 d 的形式输出LOG
     */
    public static void d(String msg) {
        d(TAG, msg);
    }

    /**
     * 以级别为 d 的形式输出LOG
     */
    public static void d(String tag, String msg) {
        if (isDebug) {
            Log.d(tag, msg);
        }
    }

    /**
     * 以级别为 i 的形式输出LOG
     */
    public static void i(String msg) {
        i(TAG, msg);
    }

    /**
     * 以级别为 i 的形式输出LOG
     */
    public static void i(String tag, String msg) {
        if (isDebug) {
            Log.i(tag, msg);
        }
    }

    /**
     * 以级别为 w 的形式输出LOG
     */
    public static void w(String msg) {
        w(TAG, msg);
    }

    /**
     * 以级别为 w 的形式输出LOG
     */
    public static void w(String tag, String msg) {
        if (isDebug) {
            Log.w(tag, msg);
        }
    }

    /**
     * 以级别为 w 的形式输出Throwable
     */
    public static void w(Throwable tr) {
        w("", tr);
    }

    /**
     * 以级别为 w 的形式输出LOG信息和Throwable
     */
    public static void w(String msg, Throwable tr) {
        w(TAG, msg, tr);
    }

    /**
     * 以级别为 w 的形式输出LOG信息和Throwable
     */
    public static void w(String tag, String msg, Throwable tr) {
        if (isDebug) {
            Log.w(tag, msg, tr);
        }
    }

    /**
     * 以级别为 e 的形式输出LOG
     */
    public static void e(String msg) {
        e(TAG, msg);
    }

    /**
     * 以级别为 e 的形式输出LOG
     */
    public static void e(String tag, String msg) {
        if (isDebug) {
            Log.e(tag, msg);
        }
    }

    /**
     * 以级别为 e 的形式输出Throwable
     */
    public static void e(Throwable tr) {
        e("", tr);
    }

    /**
     * 以级别为 e 的形式输出LOG信息和Throwable
     */
    public static void e(String msg, Throwable tr) {
        e(TAG, msg, tr);
    }

    /**
     * 以级别为 e 的形式输出LOG信息和Throwable
     */
    public static void e(String tag, String msg, Throwable tr) {
        if (isDebug) {
            Log.e(tag, msg, tr);
        }
    }

    /**
     * 以级别为 e 的形式输出msg信息,附带时间戳，用于输出一个时间段结束点* @param msg 需要输出的msg
     */
    public static void elapsed(String msg) {
        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - mTimestamp;
        mTimestamp = currentTime;
        e("[Elapsed：" + elapsedTime + "]" + msg);
    }
}
