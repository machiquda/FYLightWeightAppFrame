package fengyu.cn.library.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

public class HanXueToast {
	 public static void showToast(Context context, String text) {
	        Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
	        toast.setGravity(Gravity.CENTER, 0, 0);
	        toast.show();
	    }
	 
//	  private static View getToastView(Context c) {
//	        return LayoutInflater.from(c).inflate(R.layout.view_toast, null);
//	    }
}
