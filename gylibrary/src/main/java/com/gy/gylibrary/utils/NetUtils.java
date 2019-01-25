package com.gy.gylibrary.utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import com.gy.gylibrary.R;

import java.util.List;


/**
 * Created by 高炎 on 2018/4/18.
 */

public class NetUtils {

    /**
     * 打开网络设置界面
     * 3.0以下打开设置界面
     */
    public static void openWirelessSettings(Context context) {
        if (android.os.Build.VERSION.SDK_INT > 10) {
            context.startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));
        } else {
            context.startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
        }
    }

    /**
     * 判断是否网络连接
     * 需添加权限<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
     */
    public static boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return info != null && info.isConnected();
    }

    /**
     * 判断wifi是否连接状态
     * 需添加权限<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
     */
    public static boolean isWifiConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm != null && cm.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI;
    }

    /**
     * 获取移动网络运营商名称
     * 如中国联通、中国移动、中国电信
     */
    public static String getNetworkOperatorName(Context context) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        return tm != null ? tm.getNetworkOperatorName() : null;
    }

    /**
     * 获取移动终端类型
     * PHONE_TYPE_NONE  : 0 手机制式未知
     * PHONE_TYPE_GSM   : 1 手机制式为GSM，移动和联通
     * PHONE_TYPE_CDMA  : 2 手机制式为CDMA，电信
     * PHONE_TYPE_SIP   : 3
     */
    public static int getPhoneType(Context context) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        return tm != null ? tm.getPhoneType() : -1;
    }

    /**
     * 获取连接的网络类型(2G,3G,4G)
     * 联通的3G为UMTS或HSDPA，移动和联通的2G为GPRS或EGDE，电信的2G为CDMA，电信的3G为EVDO
     */
    public static int getNetworkTpye(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        switch (telephonyManager.getNetworkType()) {
            case TelephonyManager.NETWORK_TYPE_GPRS:
            case TelephonyManager.NETWORK_TYPE_EDGE:
            case TelephonyManager.NETWORK_TYPE_CDMA:
            case TelephonyManager.NETWORK_TYPE_1xRTT:
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return NETWORK_2G;
            case TelephonyManager.NETWORK_TYPE_UMTS:
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
            case TelephonyManager.NETWORK_TYPE_HSDPA:
            case TelephonyManager.NETWORK_TYPE_HSUPA:
            case TelephonyManager.NETWORK_TYPE_HSPA:
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
            case TelephonyManager.NETWORK_TYPE_EHRPD:
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                return NETWORK_3G;
            case TelephonyManager.NETWORK_TYPE_LTE:
                return NETWORK_4G;
            default:
                return NETWORK_NONE;
        }
    }

    /**
     * 获取当前手机的可用网络类型(WIFI,2G,3G,4G)
     * 需添加权限<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
     * 需要用到上面的方法
     */
    public static int getNetworkTypeDetail(Context context) {
        int netWorkType = NETWORK_NONE;
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            int type = networkInfo.getType();
            if (type == ConnectivityManager.TYPE_WIFI) {
                netWorkType = NETWORK_WIFI;
            } else if (type == ConnectivityManager.TYPE_MOBILE) {
                netWorkType = getNetworkTpye(context);
            }
        }
        return netWorkType;
    }

    public static int getNetworkState(Context context) {
        ConnectivityManager connManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        // Wifi
        NetworkInfo.State state = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                .getState();
        if (state == NetworkInfo.State.CONNECTED || state == NetworkInfo.State.CONNECTING) {
            return NETWORK_WIFI;
        }

        // Mobile
        state = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
                .getState();
        if (state == NetworkInfo.State.CONNECTED || state == NetworkInfo.State.CONNECTING) {
            return NETWORK_MOBILE;
        }
        return NETWORK_NONE;
    }


    public static final int NETWORK_NONE = 0;
    // wifi network
    public static final int NETWORK_WIFI = 1;
    // "2G" networks
    public static final int NETWORK_2G = 2;
    // "3G" networks
    public static final int NETWORK_3G = 3;
    // "4G" networks
    public static final int NETWORK_4G = 4;
    // moblie networks
    public static final int NETWORK_MOBILE = 5;

    /**
     * 检查当前网络是否可用
     *
     * @return 是否连接到网络
     */
    public static boolean isNetworkAvailable(Context context) {
        if (null != context) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivityManager != null) {
                NetworkInfo info = connectivityManager.getActiveNetworkInfo();
                if (info != null && info.isConnected()) {
                    if (info.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean isNetworkErrThenShowMsg(Context context) {
        if (!isNetworkAvailable(context)) {
            //TODO: 刚启动app Snackbar不起作用，延迟显示也不好使，这是why？
            //Toast.makeText(BaseApplication.getBaseApplication(), BaseApplication.getBaseApplication().getString(R.string.internet_error), Toast.LENGTH_SHORT).show();
            ToastUtils.showToast(context, context.getString(R.string.internet_error));
            return true;
        }
        return false;
    }

    /**
     * 检测GPS是否打开
     *
     * @param context 上下文
     * @return 结果
     */
    public static boolean isGpsEnabled(Context context) {
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        List<String> accessibleProviders = lm.getProviders(true);
        for (String name : accessibleProviders) {
            if ("gps".equals(name)) {
                return true;
            }
        }
        return false;
    }
}
