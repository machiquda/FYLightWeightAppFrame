package fengyu.cn.library.utils;

import android.app.Activity;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ActivityStackControlUtil {
	private static List<Activity> activityList = new ArrayList<Activity>();
	private static Stack<Activity> activityStack = new Stack<Activity>();

	public static void remove(Activity activity) {

		if (activity != null) {
			activity.finish();
			activityList.remove(activity);
			activity = null;
		}

		D.log("------ActivityStackControlUtil afterremove----------"
				+ activityList.size() + "");
	}

	public static void add(Activity activity) {

		activityList.add(activity);
		D.log("------ActivityStackControlUtil afteradd----------"
				+ activityList.size() + "");
	}

	public static void finishProgram() {
		for (Activity activity : activityList) {
			activity.finish();
		}

		android.os.Process.killProcess(android.os.Process.myPid());
	}

	public static void goLogin(Activity activity) {
		for (Activity a : activityList) {
			a.finish();
		}
		Intent intent = new Intent();
		// intent.setClass(activity, LoginActivity.class);
		activity.startActivity(intent);
	}

	public static void goRegister(Activity activity) {
		for (Activity a : activityList) {
			a.finish();
		}
		Intent intent = new Intent();
		// intent.setClass(activity, RegisterActivity.class);
		intent.putExtra("type", "验证新手机");
		activity.startActivity(intent);
	}

	public static void goHome(Activity activity) {
		for (Activity a : activityList) {
			a.finish();
		}

		Intent intent = new Intent();
		// intent.setClass(activity, SuJianMainActivity.class);
		activity.startActivity(intent);

	}

	public Activity currentActivity() {
		Activity activity = activityStack.lastElement();
		return activity;
	}

	public void popAllActivityExceptOne(Class cls) {
		while (true) {
			Activity activity = currentActivity();
			if (activity == null) {
				break;
			}
			if (activity.getClass().equals(cls)) {
				break;
			}
			popActivity(activity);
		}
	}

	public void popActivity() {
		Activity activity = activityStack.lastElement();
		if (activity != null) {
			activity.finish();
			activity = null;
		}
	}

	public void popActivity(Activity activity) {
		if (activity != null) {
			activity.finish();
			activityStack.remove(activity);
			activity = null;
		}
	}

	public void pushActivity(Activity activity) {
		if (activityStack == null) {
			activityStack = new Stack<Activity>();
		}
		activityStack.add(activity);
	}

}
