package com.utils.app;


import android.app.Application;

/**
 * 初始化一些配置
 * 
 * @author run
 * 
 */
public class MyApplication extends Application {
	private static MyApplication instance;

	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
		setCrashHandler();

	}

	private void setCrashHandler() {
		CrashHandler crashHandler = CrashHandler.getInstance();
		crashHandler.init(getApplicationContext());
	}

	public static MyApplication getInstance() {
		return instance;
	}
}
