package fengyu.cn.library.views;

import android.content.Context;
import android.os.Handler;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.widget.RelativeLayout;

import fengyu.cn.library.R;

/**
 * 状态指示器
 */
public class InitViewController extends RelativeLayout {

    public static final int LOADING = 1;
    public static final int NODATA = 2;
    public static final int NETERROR = 3;
    public static final int OK = 4;

    public static final int PG_MODE = 1;
    public static final int IMG_MODE = 2;

    private CircularProgressViewSrc progressView;
    private Thread updateThread;
    private RelativeLayout rlNetError;
    private RelativeLayout rlNoData;
    private View view;

    public InitViewController(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.views_initview_controller, this);
        rlNetError = (RelativeLayout) findViewById(R.id.network_error);
        rlNoData = (RelativeLayout) findViewById(R.id.no_data);
        progressView = (CircularProgressViewSrc) findViewById(R.id.progress_view_11);
        startAnimationThreadStuff(0);
    }

    /**
     * 改变指示器的状态
     *
     * @param state
     */
    public void setState(int state) {
        switch (state) {
            case LOADING:
                view.setVisibility(View.VISIBLE);
                progressView.setVisibility(View.VISIBLE);
                rlNetError.setVisibility(View.GONE);
                rlNoData.setVisibility(View.GONE);
                break;
            case NODATA:
                view.setVisibility(View.VISIBLE);
                progressView.setVisibility(View.GONE);
                rlNetError.setVisibility(View.GONE);
                rlNoData.setVisibility(View.VISIBLE);

                break;
            case NETERROR:
                view.setVisibility(View.VISIBLE);
                progressView.setVisibility(View.GONE);
                rlNetError.setVisibility(View.VISIBLE);
                rlNoData.setVisibility(View.GONE);

                break;
            case OK:
                view.setVisibility(View.GONE);
                progressView.setVisibility(View.GONE);
                rlNetError.setVisibility(View.GONE);
                rlNoData.setVisibility(View.GONE);

                break;
        }
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

    /**
     * 设置Indicater的模式
     *
     * @param Mode
     */
    public void setIndicaterMode(int Mode) {

        if (Mode == IMG_MODE) {
            ViewStub stub = (ViewStub) findViewById(R.id.indicater_loading_bar);
            stub.inflate();
            progressView.setVisibility(View.GONE);
        }
    }
}
