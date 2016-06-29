package fengyu.cn.library.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;



import fengyu.cn.library.R;


/**
 * Created by fys on 2015/10/12.
 */
public class ImageViewWithProgress extends FrameLayout {


    private Context context;
    private ImageView imageView;
    private ImageProgressAdapter imageProgressAdapter;

    public ImageViewWithProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.views_imageviewwithprogress, this);
        imageView = (ImageView) view.findViewById(R.id.ivLogo);

        ViewGroup mProgressLayout = (ViewGroup) findViewById(R.id.image_loading_layout);
        imageProgressAdapter = new ImageProgressAdapter(mProgressLayout);
        mProgressLayout.setVisibility(View.GONE);

    }

    public void setLogo(int resId) {
        imageView.setImageResource(resId);


    }

    public ImageView getImageView() {
        return imageView;
    }

    public ImageProgressAdapter getImageProgressAdapter() {
        return imageProgressAdapter;
    }
}
