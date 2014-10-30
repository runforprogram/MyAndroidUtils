package com.utils.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

/**
 * 应用程序Activity管理类：用于Activity管理和应用程序退出
 */
public class AppManager {

	// android.os.Process.killProcess(android.os.Process.myPid());

	private List<Activity> activities = new ArrayList<Activity>();
	private static AppManager app;

	public void addActivity(Activity activity) {
		activities.add(activity);
	}

	public void appExit() {
		
		for (int i = 0; i < activities.size(); i++) {
			activities.get(i).finish();
		}
	}

	public static AppManager getAppManager() {
		if (app == null) {
			app = new AppManager();
		}
		return app;
	}

}