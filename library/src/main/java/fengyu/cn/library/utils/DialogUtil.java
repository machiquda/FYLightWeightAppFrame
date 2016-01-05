package fengyu.cn.library.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.text.TextUtils;
import android.widget.ProgressBar;

public class DialogUtil
{
	public static final ProgressDialog getWaitDlg(Context context,
			String title, String content, boolean cancelable, int sytle)
	{
		ProgressDialog dialog = new ProgressDialog(context);
		if (!TextUtils.isEmpty(title))
			dialog.setTitle(title);
		dialog.setProgressStyle(sytle);
		dialog.setMessage(content);
		dialog.setIndeterminate(false);
		dialog.setCancelable(cancelable);
		return dialog;
	}

	public static final ProgressDialog getWaitDlg(Context context,
			String title, String content, boolean cancelable, ProgressBar progressBar)
	{
		ProgressDialog dialog = new ProgressDialog(context);
		if (!TextUtils.isEmpty(title))
			dialog.setTitle(title);
		dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		dialog.setMessage(content);
		dialog.setIndeterminate(true);
		dialog.setCancelable(cancelable);
		return dialog;
	}

	// public static final AlertDialog getExitAlertDialog(Context context){
	// AlertDialog alert = new AlertDialog(context);
	// alert.setIcon(R.drawable.btn_i).setTitle("警告");
	// alert.setMessage("确认退出程序吗？");
	// return alert;
	// }
}
