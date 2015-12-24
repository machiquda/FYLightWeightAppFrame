package fengyu.cn.library.views;



import android.content.Context;
import android.os.Handler;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import fengyu.cn.library.R;

public class CircularProgressView extends RelativeLayout {
	CircularProgressViewSrc progressView;
	Thread updateThread;

	public CircularProgressView(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.view_loading, this);

		progressView = (CircularProgressViewSrc) findViewById(R.id.progress_view);
		startAnimationThreadStuff(0);
	}

	private void startAnimationThreadStuff(long delay) {
		if (updateThread != null && updateThread.isAlive())
			updateThread.interrupt();
		final Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				// Start animation after a delay so there's no missed frames
				// while the app loads up
				progressView.setProgress(0f);
				progressView.startAnimation(); // Alias for resetAnimation, it's
												// all the same
				// Run thread to update progress every half second until full
				updateThread = new Thread(new Runnable() {
					@Override
					public void run() {
						while (progressView.getProgress() < progressView
								.getMaxProgress() && !Thread.interrupted()) {
							// Must set progress in UI thread
							handler.post(new Runnable() {
								@Override
								public void run() {
									progressView.setProgress(progressView
											.getProgress() + 10);
								}
							});
							SystemClock.sleep(250);
						}
					}
				});
				updateThread.start();
			}
		}, delay);
	}
}
