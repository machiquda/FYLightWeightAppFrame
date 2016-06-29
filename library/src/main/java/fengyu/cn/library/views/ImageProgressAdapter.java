package fengyu.cn.library.views;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;


import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;

import fengyu.cn.library.R;


public class ImageProgressAdapter implements ImageLoadingProgressListener, ImageLoadingListener {

    private ViewGroup mProgressLayout;

    private ProgressBar mProgressBar;

    private final static int[] colors = {Color.parseColor("#EFEFEF"), Color.parseColor("#ECEFF3"), Color.parseColor("#EEEEE7")};

    private static int index = 0;

    public ImageProgressAdapter(ViewGroup progressLayout) {
        mProgressLayout = progressLayout;
        mProgressBar = (ProgressBar) mProgressLayout.findViewById(R.id.image_loading_progress);
        index++;
    }


    @Override
    public void onProgressUpdate(String imageUri, View view, int current, int total) {
        if (current < total) {
            mProgressLayout.setVisibility(View.VISIBLE);
            mProgressBar.setProgress(current * 100 / total);
        } else {
            mProgressLayout.setVisibility(View.GONE);
        }


    }

    @Override
    public void onLoadingStarted(String imageUri, View view) {
        mProgressBar.setMax(100);
        mProgressLayout.setVisibility(View.VISIBLE);
        mProgressBar.setProgress(0);
        mProgressLayout.setBackgroundColor(colors[index % 3]);
    }

    @Override
    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

    }


    @Override
    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
        mProgressLayout.setVisibility(View.GONE);
    }

    @Override
    public void onLoadingCancelled(String imageUri, View view) {

    }
}
