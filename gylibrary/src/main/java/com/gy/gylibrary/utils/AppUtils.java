package com.gy.gylibrary.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.telephony.TelephonyManager;

import java.io.File;
import java.util.List;
import java.util.Locale;

/**
 * @author 高炎
 * @email yan.gao@zarltech.com
 * @create 2018/9/27
 * @Describe
 */
public class AppUtils {


    /**
     * 最低版本设置
     */
    public static final int MINIMUM_VERSION_CODES = Build.VERSION_CODES.M;


    /**
     * 安装指定路径下的Apk
     */
    public void installApk(Activity context, String filePath) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setDataAndType(Uri.fromFile(new File(filePath)), "application/vnd.android.package-archive");
        context.startActivityForResult(intent, 0);
    }

    /**
     * 卸载指定包名的App
     */
    public void uninstallApp(Activity context, String packageName) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.DELETE");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setData(Uri.parse("package:" + packageName));
        context.startActivityForResult(intent, 0);
    }

    /**
     * 获取App名称
     */
    public static String getAppName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取当前App版本号
     */
    public static String getVersionName(Context context) {
        String versionName = null;
        PackageManager pm = context.getPackageManager();
        PackageInfo info = null;
        try {
            info = pm.getPackageInfo(context.getApplicationContext().getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (info != null) {
            versionName = info.versionName;
        }
        return versionName;
    }

    /**
     * 获取当前App版本Code
     */
    public static int getVersionCode(Context context) {
        int versionCode = 0;
        PackageManager pm = context.getPackageManager();
        PackageInfo info = null;
        try {
            info = pm.getPackageInfo(context.getApplicationContext().getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (info != null) {
            versionCode = info.versionCode;
        }
        return versionCode;
    }

    /**
     * 打开指定包名的App
     */
    public void openOtherApp(Context context, String packageName) {
        PackageManager manager = context.getPackageManager();
        Intent launchIntentForPackage = manager.getLaunchIntentForPackage(packageName);
        if (launchIntentForPackage != null) {
            context.startActivity(launchIntentForPackage);
        }
    }

    /**
     * 分享Apk信息
     */
    public void shareApkInfo(Context context, String info) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.SEND");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, info);
        context.startActivity(intent);
    }

    // 需添加<uses-permission android:name="android.permission.GET_TASKS"/>
    // 并且必须是系统应用该方法才有效

    /**
     * 判断当前App处于前台还是后台
     */
    public static boolean isApplicationBackground(final Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        @SuppressWarnings("deprecation")
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }


    /**
     * 获取当前手机系统语言。
     *
     * @return 返回当前系统语言。例如：当前设置的是“中文-中国”，则返回“zh-CN”
     */

    public static String getSystemLanguage() {
        return Locale.getDefault().getLanguage();
    }


    /**
     * 获取当前系统上的语言列表(Locale列表)
     *
     * @return 语言列表
     */
    public static Locale[] getSystemLanguageList() {
        return Locale.getAvailableLocales();
    }


    /**
     * 获取当前手机系统版本号
     *
     * @return 系统版本号
     */

    public static String getSystemVersion() {
        return Build.VERSION.RELEASE;
    }


    /**
     * 获取手机型号
     *
     * @return 手机型号
     */

    public static String getSystemModel() {
        return Build.MODEL;
    }


    /**
     * 获取手机厂商
     *
     * @return 手机厂商
     */

    public static String getDeviceBrand() {
        return Build.BRAND;
    }


    /**
     * 获取手机IMEI(需要“android.permission.READ_PHONE_STATE”权限)
     *
     * @return 手机IMEI
     */

    public static String getIMEI(Context ctx) {
        TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Activity.TELEPHONY_SERVICE);
        if (tm != null) {
            return tm.getDeviceId();
        }
        return "";
    }

    /**
     * 直接拨号，需要增加CALL_PHONE权限
     *
     * @param context 上下文
     * @param phone   手机号码
     */
    public static void actionCall(Context context, String phone) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
        intent.setAction(Intent.ACTION_CALL);// 直接拨号
        context.startActivity(intent);
    }

    /**
     * 跳到拨号盘-拨打电话
     *
     * @param context 上下文
     * @param phone   手机号码
     */
    public static void actionDial(Context context, String phone) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
        intent.setAction(Intent.ACTION_DIAL);// 拨号盘
        context.startActivity(intent);
    }
}
