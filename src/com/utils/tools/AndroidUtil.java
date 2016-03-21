package com.utils.tools;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.view.Display;

import java.util.Locale;

public class AndroidUtil {
    private static Display display;
    private static String TAG = "MyTools";
    private static AndroidUtil myTools;

    private AndroidUtil() {

    }

    /**
     * Gets SDK.
     *
     * @return the SDK
     */
    public int getSDK() {
        return Build.VERSION.SDK_INT; // SDK号
    }

    /**
     * Gets phone imei.
     *
     * @param context the context
     * @return the imei
     */
    public String getImei(Context context) //
    {
        TelephonyManager telephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
    }

    /**
     * Gets mac.if can not get return ""
     *
     * @param context the context
     * @return the mac
     */
    public String getMac(Context context) {
        final WifiManager wifi = (WifiManager) context
                .getSystemService(Context.WIFI_SERVICE);
        if (wifi != null) {
            WifiInfo info = wifi.getConnectionInfo();
            if (info.getMacAddress() != null) {
                return info.getMacAddress().toLowerCase(Locale.ENGLISH)
                        .replace(":", "");
            }
            return "";
        }
        return "";
    }

    /**
     * Gets app version name.
     *
     * @param context the context
     * @return the version name
     */
    public String getVersionName(Context context) throws NameNotFoundException {
        String pkName = context.getPackageName();
        String versionName = context.getPackageManager().getPackageInfo(
                pkName, 0).versionName;
        return versionName;
    }

    /**
     * Gets app version code.
     *
     * @param context the context
     * @return the version code
     * @throws NameNotFoundException the name not found exception
     */
    public int getVersionCode(Context context) throws NameNotFoundException { // 获取版本号
        String pkName = context.getPackageName();
        int versionCode = context.getPackageManager().getPackageInfo(
                pkName, 0).versionCode;
        return versionCode;
    }


    /**
     * Is network available.contain wifi and GPRS
     *
     * @param context the context
     * @return the boolean
     */
    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null)
            return false;
        NetworkInfo info = connectivity.getActiveNetworkInfo();
        return !(info == null || !info.isConnected()) && (info.getState() == NetworkInfo.State.CONNECTED);
    }


    /**
     * px to dp
     *
     * @param px 像素
     * @return dp
     */
    public int px2dp(Context context, int px) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    /**
     * dp 2 px.
     *
     * @param context the context
     * @param dp      the dip value
     * @return the int
     */
    public int dp2px(Context context, int dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    /**
     * px to sp
     *
     * @param px
     * @return sp
     */
    public int px2sp(Context context, int px) {
        float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (px / scaledDensity);
    }
/**
     * 得到状态栏的高度(px)
     *
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        Class<?> c;
        Object obj;
        Field field;
        int x, statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return statusBarHeight;
    }
    /**
     * open network setting.
     *
     * @param context the context
     */
    public void openNetworkSetting(Context context) {
        Intent intent;
        // 判断手机系统的版本 即API大于10 就是3.0或以上版本
        if (android.os.Build.VERSION.SDK_INT > 10) {
            intent = new Intent(
                    android.provider.Settings.ACTION_WIRELESS_SETTINGS);
        } else {
            intent = new Intent();
            ComponentName component = new ComponentName("com.android.settings",
                    "com.android.settings.WirelessSettings");
            intent.setComponent(component);
            intent.setAction("android.intent.action.VIEW");
        }
        context.startActivity(intent);
    }
}
