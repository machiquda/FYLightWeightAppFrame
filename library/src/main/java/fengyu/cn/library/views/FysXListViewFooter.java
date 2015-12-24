/**
 * @file XFooterView.java
 * @create Mar 31, 2012 9:33:43 PM
 * @author Maxwin
 * @description XListView's footer
 */
package fengyu.cn.library.views;


import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import fengyu.cn.library.R;


public class FysXListViewFooter extends LinearLayout {
	public final static int STATE_NORMAL = 0;
	public final static int STATE_READY = 1;
	public final static int STATE_LOADING = 2;
	public final static int STATE_LOADFULL = 3;

	private Context mContext;

	private View mContentView;
	private View mProgressBar;
	private RelativeLayout mLoading;
	private TextView mLoadFull;
	private TextView mHintView;

	public FysXListViewFooter(Context context) {
		super(context);
		initView(context);
	}

	public FysXListViewFooter(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	public void setState(int state) {
		mHintView.setVisibility(View.INVISIBLE);
		mLoading.setVisibility(View.INVISIBLE);
		mLoadFull.setVisibility(View.INVISIBLE);

		if (state == STATE_READY) {
			mHintView.setVisibility(View.VISIBLE);
			mHintView.setText(R.string.xlistview_footer_hint_ready);
		} else if (state == STATE_LOADING) {
			mLoading.setVisibility(View.VISIBLE);
		} else if (state == STATE_LOADFULL) {

			mLoadFull.setVisibility(View.VISIBLE);

		} else {

			mHintView.setVisibility(View.VISIBLE);
			mHintView.setText(R.string.xlistview_footer_hint_normal);
		}
	}

	public void setBottomMargin(int height) {
		if (height < 0)
			return;
		LayoutParams lp = (LayoutParams) mContentView
				.getLayoutParams();
		lp.bottomMargin = height;
		mContentView.setLayoutParams(lp);
	}

	public int getBottomMargin() {
		LayoutParams lp = (LayoutParams) mContentView
				.getLayoutParams();
		return lp.bottomMargin;
	}

	/**
	 * normal status
	 */
	public void normal() {
		mHintView.setVisibility(View.VISIBLE);
		// mProgressBar.setVisibility(View.GONE);
		mLoading.setVisibility(View.GONE);
		mLoadFull.setVisibility(View.GONE);
	}

	/**
	 * loading status
	 */
	public void loading() {
		mHintView.setVisibility(View.GONE);
		mLoading.setVisibility(View.VISIBLE);
		mLoadFull.setVisibility(View.GONE);
	}

	/**
	 * loaded all data status
	 */
	public void setLoadFull() {
		mHintView.setVisibility(View.GONE);
		mLoading.setVisibility(View.GONE);
		mLoadFull.setVisibility(View.VISIBLE);
	}

	/**
	 * hide footer when disable pull load more
	 */
	public void hide() {
		LayoutParams lp = (LayoutParams) mContentView
				.getLayoutParams();
		lp.height = 0;
		mContentView.setLayoutParams(lp);
	}

	/**
	 * show footer
	 */
	public void show() {
		LayoutParams lp = (LayoutParams) mContentView
				.getLayoutParams();
		lp.height = LayoutParams.WRAP_CONTENT;
		mContentView.setLayoutParams(lp);
	}

	private void initView(Context context) {
		mContext = context;
		LinearLayout moreView = (LinearLayout) LayoutInflater.from(mContext)
				.inflate(R.layout.views_xlistview_footer, null);
		addView(moreView);
		moreView.setLayoutParams(new LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));

		mContentView = moreView.findViewById(R.id.xlistview_footer_content);
		mProgressBar = moreView.findViewById(R.id.xlistview_footer_progressbar);
		mHintView = (TextView) moreView
				.findViewById(R.id.xlistview_footer_textview);
		mLoadFull = (TextView) moreView.findViewById(R.id.loadFull);
		mLoading = (RelativeLayout) moreView
				.findViewById(R.id.xlistview_footer_loading);
	}

}
