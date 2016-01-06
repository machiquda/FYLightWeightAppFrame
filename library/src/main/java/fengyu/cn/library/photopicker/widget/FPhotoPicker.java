package fengyu.cn.library.photopicker.widget;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;


import java.util.ArrayList;
import java.util.List;

import fengyu.cn.library.R;
import fengyu.cn.library.photopicker.adapter.FPhotoPickerAdapter;
import fengyu.cn.library.photopicker.entity.PhotoDirectory;
import fengyu.cn.library.photopicker.event.OnStartDragListener;
import fengyu.cn.library.photopicker.event.FPhotoPickerItemTouchHelperCallback;


/**
 * Created by fys on 2015/9/6.
 * <p/>
 * 九宫格图片展示控件
 */
public class FPhotoPicker extends LinearLayout implements OnStartDragListener {
    //已选择的图片在手机中的路径
    public ArrayList<String> selectedPhotos = new ArrayList<>();
    public final static int REQUEST_CODE = 1;
    private Context context;
    //最多选择图片限制
    int photoPickSize = 9;
    RecyclerView recyclerView;
    private FPhotoPickerAdapter photoAdapter;
    private ItemTouchHelper mItemTouchHelper;

    public FPhotoPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.photo_x_view, this);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        photoAdapter = new FPhotoPickerAdapter(context, selectedPhotos);
        photoAdapter.setmDragStartListener(this);
        ItemTouchHelper.Callback callback = new FPhotoPickerItemTouchHelperCallback(photoAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);

    }

    /**
     * 设置选择图片的个数
     *
     * @param size
     */
    public void setPhotoPickSize(int size) {
        photoPickSize = size;
        if (size > 0)
            photoAdapter.setPhotoPickSize(size);
    }

    /**
     * 设置FPhotoPicker 展示列数
     *
     * @param columns
     */
    public void setColumns(int columns) {
        recyclerView.setLayoutManager(new GridLayoutManager(context, columns));
        recyclerView.setAdapter(photoAdapter);
    }

    public FPhotoPickerAdapter getPhotoAdapter() {
        return photoAdapter;
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        if (viewHolder.getLayoutPosition() == photoAdapter.getItemCount() - 1)
            return;
        mItemTouchHelper.startDrag(viewHolder);
    }

    /**
     * 设置拍照的模式:<br/>
     * ImageCaptureManager.STORAGE_STRATEGY_PERPETUAL 永久储存<br/>
     * ImageCaptureManager.STORAGE_STRATEGY_TEMPORARY 临时储存<br/>
     * <b>注意</b>设置为临时储存后,退出图片选择界面时将会<b>删除</b>临时图片文件
     *
     * @param mode
     */
    public void setPhotoPickMode(int mode) {
        getPhotoAdapter().setPhotoPickMode(mode);
    }

    /**
     * 图片点击事件  默认为大图预览
     *
     * @param l
     */
    public void setOnPhotoClickListener(FPhotoPickerAdapter.PhotoClickListener l) {

        if (l != null)
            photoAdapter.setOnPhotoClickListener(l);

    }

    /**
     * 返回已选择的图片在手机中路径 <br/>
     * 未选择则返回<b>null</b>
     *
     * @return
     */
    public ArrayList<String> getSelectedPhotoPaths() {
        if (selectedPhotos != null) {
            return selectedPhotos;
        }
        return null;
    }

    /**
     * 设置九宫格每个Item的间距
     *
     * @param leftSpace   左侧间距
     * @param topSpace    上方间距
     * @param rightSpace  右侧间距
     * @param bottomSpace 下方间距
     */
    public void setItemSpace(int leftSpace, int topSpace, int rightSpace, int bottomSpace) {
        recyclerView.addItemDecoration(new SpacesItemDecoration(leftSpace, topSpace, rightSpace, bottomSpace));

    }

}
