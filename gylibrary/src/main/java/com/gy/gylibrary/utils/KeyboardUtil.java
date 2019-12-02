package com.gy.gylibrary.utils;

import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;

/**
 * 键盘
 *
 * @author 高炎
 * @email yan.gao@zarltech.com
 * @create 2019/1/25
 * @Describe
 */
public class KeyboardUtil {

    /**
     * 关闭键盘
     *
     * @param activity Activity
     */
    public static void hideSoftInput(Activity activity) {
        if (activity.getCurrentFocus() != null)
            ((InputMethodManager) activity
                    .getSystemService(Context.INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(activity.getCurrentFocus()
                            .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 如果输入法在窗口上已经显示，则隐藏，反之则显示
     */
    public void showOrHide(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

}
