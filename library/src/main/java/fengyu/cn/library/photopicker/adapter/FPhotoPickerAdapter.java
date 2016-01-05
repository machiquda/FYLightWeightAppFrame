package fengyu.cn.library.photopicker.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import fengyu.cn.library.R;
import fengyu.cn.library.photopicker.PhotoPagerActivity;
import fengyu.cn.library.photopicker.event.ItemTouchHelperAdapter;
import fengyu.cn.library.photopicker.event.OnStartDragListener;
import fengyu.cn.library.photopicker.utils.PhotoPickerIntent;
import fengyu.cn.library.photopicker.widget.FPhotoPicker;
import fengyu.cn.library.utils.VibratorUtil;

/**
 * Created by fys on 2015/12/30.
 * FPhotoPicker 对应的适配器
 */
public class FPhotoPickerAdapter extends RecyclerView.Adapter<FPhotoPickerAdapter.PhotoViewHolder> implements ItemTouchHelperAdapter {

    //最多选择图片限制
    public int photoPickSize = 9;
    //选择的图片路径
    private ArrayList<String> photoPaths = new ArrayList<String>();
    private LayoutInflater inflater;
    private Context mContext;
    //拖拽监听
    private OnStartDragListener mDragStartListener;
    private int currentMode = 1;

    private PhotoClickListener mPhotoClickListener;

    public FPhotoPickerAdapter(Context mContext, ArrayList<String> photoPaths) {
        this.photoPaths = photoPaths;
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);

    }


    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.item_fphotopicker, parent, false);
        return new PhotoViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final PhotoViewHolder holder, final int position) {

        if (position == photoPaths.size()) {
            holder.ivPhoto
                    .setImageBitmap(BitmapFactory.decodeResource(
                            mContext.getResources(),
                            R.drawable.photo_9_display_add_img));

            holder.ivDelete.setVisibility(View.GONE);
            if (position == photoPickSize) {
                holder.ivPhoto.setVisibility(View.GONE);

            }

        } else {
            holder.ivDelete.setVisibility(View.VISIBLE);
            if (photoPaths.get(position).startsWith("http")) {
                Glide.with(mContext)
                        .load(photoPaths.get(position))
                        .centerCrop()
                        .thumbnail(0.1f)
                        .placeholder(R.drawable.ic_photo_black_48dp)
                        .error(R.drawable.ic_broken_image_black_48dp)
                        .into(holder.ivPhoto);

            } else {
                Uri uri = Uri.fromFile(new File(photoPaths.get(position)));
                Glide.with(mContext)
                        .load(uri)
                        .centerCrop()
                        .thumbnail(0.1f)
                        .placeholder(R.drawable.ic_photo_black_48dp)
                        .error(R.drawable.ic_broken_image_black_48dp)
                        .into(holder.ivPhoto);
            }
        }


        holder.ivPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position < photoPaths.size()) {
                    if (mPhotoClickListener != null) {
                        //手动设置了点击事件
                        mPhotoClickListener.onPhotoClick(holder, position, photoPaths);
                    } else {
//                       //还没有达到挑选上限  开始挑选
//                        if (position == photoPaths.size()) {
//                            PhotoPickerIntent intent = new PhotoPickerIntent(mContext);
//                            intent.setPhotoCount(photoPickSize);
//                            intent.putExtra("storageMode", currentMode);
//                            if (photoPaths != null && photoPaths.size() > 0) {
//                                intent.putStringArrayListExtra("photoPaths", photoPaths);
//                            }
//                            ((Activity) mContext).startActivityForResult(intent, FPhotoPicker.REQUEST_CODE);
//                        } else {
                        //预览图片
                        Intent intent = new Intent(mContext, PhotoPagerActivity.class);
                        intent.putExtra(PhotoPagerActivity.EXTRA_CURRENT_ITEM, position);
                        intent.putExtra(PhotoPagerActivity.EXTRA_PHOTOS, photoPaths);
                        if (mContext instanceof Activity) {
                            ((Activity) mContext).startActivityForResult(intent, FPhotoPicker.REQUEST_CODE);
                        }
//                        }

                    }
                } else {
                    //还没有达到挑选上限  开始挑选
                    PhotoPickerIntent intent = new PhotoPickerIntent(mContext);
                    intent.setPhotoCount(photoPickSize);
                    intent.putExtra("storageMode", currentMode);
                    if (photoPaths != null && photoPaths.size() > 0) {
                        intent.putStringArrayListExtra("photoPaths", photoPaths);
                    }
                    ((Activity) mContext).startActivityForResult(intent, FPhotoPicker.REQUEST_CODE);
                }
            }
        });
        //长按拖动震动反馈
        holder.ivPhoto.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (position < photoPaths.size())
                    VibratorUtil.Vibrate((Activity) mContext, 100);
                return false;
            }
        });

//        //删除照片
//        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                    onItemDismiss(position - 1);
//
//            }
//        });
    }


    @Override
    public void onItemDismiss(int position) {
        photoPaths.remove(position);

        notifyItemRemoved(position);
        notifyItemChanged(position);
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        //添加图片按钮不移动
        if (toPosition >= photoPaths.size())
            return false;
        Collections.swap(photoPaths, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }


    @Override
    public int getItemCount() {

        if (photoPaths.size() == photoPickSize) {
            return photoPickSize;
        }

        return (photoPaths.size() + 1);
    }


    public class PhotoViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivPhoto;
        private ImageView ivDelete;

        public PhotoViewHolder(View itemView) {
            super(itemView);
            ivPhoto = (ImageView) itemView.findViewById(R.id.iv_photo);
            ivDelete = (ImageView) itemView.findViewById(R.id.delete);
            //删除照片
            int a = getLayoutPosition();
            int b = photoPaths.size();
            if (getLayoutPosition() + 1 == photoPaths.size()) {
                ivDelete.setVisibility(View.GONE);

            }
            ivDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    onItemDismiss(getLayoutPosition());

                }
            });
        }


    }

    /**
     * 设置拖拽监听器
     *
     * @param listener
     */
    public void setmDragStartListener(OnStartDragListener listener) {
        this.mDragStartListener = listener;
    }

    /**
     * 设置拍摄照片储存模式
     *
     * @param mode
     */
    public void setPhotoPickMode(int mode) {
        currentMode = mode;
    }


    public void setOnPhotoClickListener(PhotoClickListener l) {

        mPhotoClickListener = l;

    }

    public interface PhotoClickListener {
        /**
         * 该点击事件会覆盖掉默认点击事件
         *
         * @param holder           当前item的Viewholder  可以获取View
         * @param position         当前item的在数据源中的位置

         * @param photoPaths       当前adapter的数据源
         */
        void onPhotoClick(PhotoViewHolder holder, int position,  ArrayList<String> photoPaths);
    }

    public void setPhotoPickSize(int photoPickSize) {
        this.photoPickSize = photoPickSize;

    }

    public int getPhotoPickSize() {
        return photoPickSize;
    }

    public int getDataSourceCount() {
        return photoPaths.size();
    }
}




