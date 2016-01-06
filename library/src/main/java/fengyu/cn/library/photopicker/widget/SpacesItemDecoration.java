package fengyu.cn.library.photopicker.widget;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by fys on 2016/1/6.
 */
public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
    private int leftSpace;
    private int topSpace;
    private int rightSpace;
    private int bottomSpace;

    public SpacesItemDecoration(int leftSpace, int topSpace, int rightSpace, int bottomSpace) {
        this.leftSpace = leftSpace;
        this.topSpace = topSpace;
        this.rightSpace = rightSpace;
        this.bottomSpace = bottomSpace;


    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
        if (leftSpace > 0) {
            outRect.left =leftSpace;
        }
        if (leftSpace > 0) {
            outRect.right =rightSpace;
        }
        if (leftSpace > 0) {
            outRect.bottom =bottomSpace;
        }
        if (leftSpace > 0) {
            outRect.top =topSpace;
        }

//        // Add top margin only for the first item to avoid double space between items
//        if (parent.getChildLayoutPosition(view) == 0)
//            outRect.top = space;
    }
}
