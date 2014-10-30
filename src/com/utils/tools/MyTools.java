package com.utils.tools;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.utils.app.MyApplication;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * 工具类
 * 
 * @author hck
 * 
 */
public class MyTools {
	private static Context context;
	private static Display display;
	private static String TAG = "MyTools";
	private static MyTools myTools;

	public MyTools(Context context) {
		MyTools.context = context;
	}

	public static MyTools newMyTools() {
		if (myTools == null) {
			myTools = new MyTools(MyApplication.getInstance());
		}
		return myTools;
	}

	public int getScreenHeight() // 获取屏幕高度
	{
		if (context == null) {
			return 1000;
		}
		display = ((Activity) context).getWindowManager().getDefaultDisplay();
		return display.getHeight();
	}

	public int getScreenWidth() // 获取屏幕宽度
	{
		if (context == null) {
			return 500;
		}
		display = ((Activity) context).getWindowManager().getDefaultDisplay();
		return display.getWidth();
	}

	public String getSDK() {
		return android.os.Build.VERSION.SDK; // SDK号

	}

	public String getModel() // 手机型号
	{
		return android.os.Build.MODEL;
	}

	public String getRelease() // android系统版本号
	{
		return android.os.Build.VERSION.RELEASE;
	}

	public String getImei(Context context) // 获取手机身份证imei
	{
		TelephonyManager telephonyManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		return telephonyManager.getDeviceId();
	}

	public long totalMemory(int type) // 获取手机内存
	{
		Runtime myRun = Runtime.getRuntime();
		if (1 == type) {
			return myRun.totalMemory() / 1024 / 1024; // 使用的内存
		} else if (2 == type) { // 最大内存
			return myRun.maxMemory() / 1024 / 1024;
		} else if (3 == type) { // 剩余内存
			return myRun.freeMemory() / 1024 / 1024;
		}
		return 0;
	}

	public String getVerName(Context context) { // 获取版本名字
		try {
			String pkName = context.getPackageName();
			String versionName = context.getPackageManager().getPackageInfo(
					pkName, 0).versionName;

			return versionName;
		} catch (Exception e) {
		}
		return null;
	}

	public int getVerCode(Context context) { // 获取版本号
		String pkName = context.getPackageName();
		try {
			int versionCode = context.getPackageManager().getPackageInfo(
					pkName, 0).versionCode;
			return versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public boolean isNetworkAvailable(Context context) { // 判断网络连接是否可用
		// 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity == null)
			return false;
		// 获取网络连接管理的对象
		NetworkInfo info = connectivity.getActiveNetworkInfo();
		if (info == null || !info.isConnected())
			return false;
		// 判断当前网络是否已经连接
		return (info.getState() == NetworkInfo.State.CONNECTED);
	}

	public String trim(String str, int limit) { // 字符串超出给定文字则截取
		String mStr = str.trim();
		return mStr.length() > limit ? mStr.substring(0, limit) : mStr;
	}

	public String getTel(Context context) { // 获取手机号码，很多手机获取不到
		TelephonyManager telManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		return telManager.getLine1Number();
	}

	public String getMac(Context context) { // 获取时机mac地址
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
	 * 將 像素 转换成 dp
	 * 
	 * @param pxValue
	 *            像素
	 * @return dp
	 */
	public int px2dip(int pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 將 畫素 轉換成 sp
	 * 
	 * @param pixel
	 * @return sp
	 */
	public int px2sp(int px) {
		float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (px / scaledDensity);
	}

	/**
	 * 將 dip 轉換成畫素 px
	 * 
	 * @param dipValue
	 *            dip 像素的值
	 * @return 畫素 px
	 */
	public int dip2px(float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	public static int[][] getViewsPosition(List<View> views) {
		int[][] location = new int[views.size()][2];
		for (int index = 0; index < views.size(); index++) {
			views.get(index).getLocationOnScreen(location[index]);
		}
		return location;
	}

	/**
	 * 传入一个view,返回一个int数组来存放 view在手机屏幕上左上角的绝对坐标
	 * 
	 * @param views
	 *            传入的view
	 * @return 返回int型数组,location[0]表示x,location[1]表示y
	 */
	public int[] getViewPosition(View view) {
		int[] location = new int[2];
		view.getLocationOnScreen(location);
		return location;
	}

	/**
	 * onTouch界面时指尖在views中的哪个view当中
	 * 
	 * @param event
	 *            ontouch事件
	 * @param views
	 *            view list
	 * @return view
	 */
	public View getOnTouchedView(MotionEvent event, List<View> views) {
		int[][] location = getViewsPosition(views);
		for (int index = 0; index < views.size(); index++) {
			if (event.getRawX() > location[index][0]
					&& event.getRawX() < location[index][0]
							+ views.get(index).getWidth()
					&& event.getRawY() > location[index][1]
					&& event.getRawY() < location[index][1]
							+ views.get(index).getHeight()) {
				return views.get(index);
			}
		}
		return null;
	}

	/**
	 * 将传进的图片存储在手机上,并返回存储路径
	 * 
	 * @param photo
	 *            Bitmap 类型,传进的图片
	 * @return String 返回存储路径
	 */
	public String savePic(Bitmap photo, String name, String path) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream(); // 创建一个
																	// outputstream
																	// 来读取文件流
		photo.compress(Bitmap.CompressFormat.PNG, 100, baos); // 把 bitmap 的图片转换成
																// jpge
																// 的格式放入输出流中
		byte[] byteArray = baos.toByteArray();
		String saveDir = Environment.getExternalStorageDirectory()
				.getAbsolutePath();
		File dir = new File(saveDir + "/" + path); // 定义一个路径
		if (!dir.exists()) { // 如果路径不存在,创建路径
			dir.mkdir();
		}
		File file = new File(saveDir, name + ".png"); // 定义一个文件
		if (file.exists())
			file.delete(); // 删除原来有此名字文件,删除
		try {
			file.createNewFile();
			FileOutputStream fos;
			fos = new FileOutputStream(file); // 通过 FileOutputStream 关联文件
			BufferedOutputStream bos = new BufferedOutputStream(fos); // 通过 bos
																		// 往文件里写东西
			bos.write(byteArray);
			bos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return file.getPath();
	}

	/**
	 * 回收 bitmap 减小系统占用的资源消耗
	 */
	public void destoryBimap(Bitmap photo) {
		if (photo != null && !photo.isRecycled()) {
			photo.recycle();
			photo = null;
		}
	}

	/**
	 * 將輸入字串做 md5 編碼
	 * 
	 * @param s
	 *            : 欲編碼的字串
	 * @return 編碼後的字串, 如失敗, 返回 ""
	 */
	public String md5(String s) {
		try {
			// Create MD5 Hash
			MessageDigest digest = java.security.MessageDigest
					.getInstance("MD5");
			digest.update(s.getBytes("UTF-8"));
			byte messageDigest[] = digest.digest();

			// Create Hex String
			StringBuffer hexString = new StringBuffer();
			for (byte b : messageDigest) {
				if ((b & 0xFF) < 0x10)
					hexString.append("0");
				hexString.append(Integer.toHexString(b & 0xFF));
			}
			return hexString.toString();
		} catch (NoSuchAlgorithmException e) {
			return "";
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}

	public boolean isNumber(char c) { // 是否是数字
		boolean isNumer = false;
		if (c >= '0' && c <= '9') {
			isNumer = true;
		}
		return isNumer;
	}

	public boolean isNumbers(String string) { // 是否是数字
		try {
			Long.parseLong(string);
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	public static boolean isMail(String email) {
		String regemail = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
		Pattern p = Pattern.compile(regemail);
		Matcher m = p.matcher(email);
		return m.matches();

	}

	public static boolean isMobile(String mobile) {
		Pattern p = Pattern
				.compile("^((13[0-9])|(14[5,7])|(15[^4,\\D])|(18[^1,^4]))\\d{8}$");
		Matcher m = p.matcher(mobile);
		return m.matches();
	}

	public boolean isNull(String string) // 字符串是否为空
	{
		if (null == string || "".equals(string.trim()) || "null".equals(string)) {
			return true;
		}
		return false;
	}

	public boolean isLenghtOk(String string, int max, int min) // 字符串长度检测
	{
		if (null != string) {
			if (string.length() > max || string.length() < min) {
				return false;
			}
		}
		return true;
	}

	public boolean checkPost(String post) {
		if (post.matches("[1-9]\\d{5}(?!\\d)")) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isLenghtOk(String string, int max) // 字符串长度是否
	{
		if (null != string) {
			if (string.length() > max) {
				return false;
			}
		} else if (string == null) {
			return false;
		}
		return true;
	}

	/**
	 * 
	 * 
	 * @param context
	 * @param resId
	 * @return
	 */
	public String geFileFromRaw(Context context, int resId) {// 从Raw里面获取数据
		if (context == null) {
			return null;
		}

		StringBuilder s = new StringBuilder();
		try {
			InputStreamReader in = new InputStreamReader(context.getResources()
					.openRawResource(resId));
			BufferedReader br = new BufferedReader(in);
			String line;
			while ((line = br.readLine()) != null) {
				s.append(line);
			}
			return s.toString();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 使用一个字符串获取指定长度的随机新字符串
	 */
	public String getRandom(char[] sourceChar, int length) {
		if (sourceChar == null || sourceChar.length == 0 || length < 0) {
			return null;
		}
		StringBuilder str = new StringBuilder(length);
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			str.append(sourceChar[random.nextInt(sourceChar.length)]);
		}
		return str.toString();
	}

	public void startNet(Context context) {
		Intent intent = null;
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

	public boolean checkIfInstallFlash(Context context) {

		PackageManager pm = context.getPackageManager();
		List<PackageInfo> infoList = pm
				.getInstalledPackages(PackageManager.GET_SERVICES);
		for (PackageInfo info : infoList) {
			if ("com.adobe.flashplayer".equals(info.packageName)) {
				return true;
			}
		}
		return false;
	}

	public void installFlash(Context context) {

		Intent installIntent = new Intent("android.intent.action.VIEW");
		installIntent.setData(Uri
				.parse("market://details?id=com.adobe.flashplayer"));
		context.startActivity(installIntent);
	}

}
