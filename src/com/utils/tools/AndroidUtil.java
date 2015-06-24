package com.utils.tools;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

import static android.os.Environment.MEDIA_MOUNTED;

public class AndroidUtil {
    private static String TAG =AndroidUtil.class.getSimpleName();
    private AndroidUtil() {

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
    /**
     * Returns application cache directory. Cache directory will be created on SD card
     * <i>("/Android/data/[app_package_name]/cache")</i> if card is mounted and app has appropriate permission. Else -
     * Android defines cache directory on device's file system.
     *
     * @param context Application context
     * @return Cache {@link java.io.File directory}
     */
    public static File getCacheDirectory(Context context) {
        File appCacheDir = null;
        if (MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) && hasExternalStoragePermission(context)) {
            appCacheDir = getExternalCacheDir(context);
        }
        if (appCacheDir == null) {
            appCacheDir = context.getCacheDir();
        }
        if (appCacheDir == null) {
            Log.w(TAG, "Can't define system cache directory! The app should be re-installed.");
        }
        return appCacheDir;
    }



    private static File getExternalCacheDir(Context context) {
        File dataDir = new File(new File(Environment.getExternalStorageDirectory(), "Android"), "data");
        File appCacheDir = new File(new File(dataDir, context.getPackageName()), "cache");
        if (!appCacheDir.exists()) {
            if (!appCacheDir.mkdirs()) {
                Log.w(TAG,"Unable to create external cache directory");
                return null;
            }
            try {
                new File(appCacheDir, ".nomedia").createNewFile();
            } catch (IOException e) {
                Log.i(TAG,"Can't create \".nomedia\" file in application external cache directory");
            }
        }
        return appCacheDir;
    }

    private static boolean hasExternalStoragePermission(Context context) {
        int perm = context.checkCallingOrSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE");
        return perm == PackageManager.PERMISSION_GRANTED;
    }
}
