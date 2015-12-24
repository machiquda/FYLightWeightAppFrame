package fengyu.cn.library.utils;

import android.os.Handler;

public class DelayTimer {

	private int delayTime;
	private Handler handler;
	private Runnable mTickHandler;
	private Runnable delegate;
	private boolean ticking;

	public boolean getIsTicking() {
		return ticking;
	}

	public void start(int milliSeconds) {
		delayTime = milliSeconds;
		setOnTickHandler(mTickHandler); 
		handler.postDelayed(delegate, delayTime); 
	    ticking = true; 
	}

	public DelayTimer(Runnable onTickHandler) {
		mTickHandler = onTickHandler;
		handler = new Handler();
	}

	public void setOnTickHandler(Runnable onTickHandler) {
		if (onTickHandler == null)
			return;

		delegate = new Runnable() {
			public void run() {
				if (mTickHandler == null)
					return;
				mTickHandler.run();
				handler.postDelayed(delegate, delayTime);
				stop();
			}
		};
	}
	
	public void stop() 
	  { 
	    handler.removeCallbacks(delegate); 
	    ticking = false; 
	  } 
}
