package com.gy.gylibrary.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

/**
 * <p>
 * Toast 显示。
 * <p>
 * <p>
 * 创建日期 2016年5月16日<br>
 *
 * @author 高炎<p>
 * @since 1.0.0
 */
public class ToastUtils {
    private static Toast mToast;
    private static Handler mhandler = new Handler(Looper.getMainLooper());
    private static Runnable r = new Runnable() {
        public void run() {
            mToast.cancel();
        }

        ;
    };

    /**
     * 自定义
     *
     * @param context
     * @param text
     * @param duration
     */
    public static void showToast(Context context, String text, int duration) {
        mhandler.removeCallbacks(r);
        if (null != mToast) {
            mToast.setText(text);
        } else {
            mToast = Toast.makeText(context, text, duration);
        }
        mhandler.postDelayed(r, 2000);
        mToast.show();
    }

    /**
     * 显示 文本 显示 资源   默认Toast.LENGTH_SHORT
     *
     * @param context
     * @param msg
     */
    public static void showToast(Context context, String msg) {
        showToast(context, msg, Toast.LENGTH_SHORT);
    }

    /**
     * 显示 资源   默认Toast.LENGTH_SHORT
     *
     * @param context
     * @param msgId
     */
    public static void showToast(Context context, int msgId) {
        showToast(context, context.getString(msgId), Toast.LENGTH_SHORT);
    }

    /**
     * 显示 文本 显示 资源   默认Toast.LENGTH_SHORT
     *
     * @param context
     * @param msg
     */
    public static void showToastL(Context context, String msg) {
        showToast(context, msg, Toast.LENGTH_LONG);
    }

    /**
     * 显示 资源   默认Toast.LENGTH_SHORT
     *
     * @param context
     * @param msgId
     */
    public static void showToastL(Context context, int msgId) {
        showToast(context, context.getString(msgId), Toast.LENGTH_LONG);
    }
}
