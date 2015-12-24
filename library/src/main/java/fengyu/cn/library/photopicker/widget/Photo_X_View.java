package fengyu.cn.library.photopicker.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;


import java.io.File;
import java.util.ArrayList;

import fengyu.cn.library.R;
import fengyu.cn.library.photopicker.PhotoPagerActivity;
import fengyu.cn.library.photopicker.utils.PhotoPickerIntent;


/**
 * Created by fys on 2015/9/6.
 *
 * 九宫格图片展示控件
 */
public class Photo_X_View extends LinearLayout {
    public ArrayList<String> selectedPhotos = new ArrayList<>();
    public final static int REQUEST_CODE = 1;
    private Context context;
    //最多选择图片限制
    int photoPickSize = 9;
    RecyclerView recyclerView;
    public  PhotoAdapter photoAdapter;

    public Photo_X_View(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.photo_x_view, this);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        photoAdapter = new PhotoAdapter(context, selectedPhotos);

//        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(4, OrientationHelper.VERTICAL));
//        recyclerView.setAdapter(photoAdapter);

    }


    public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder> {

        private ArrayList<String> photoPaths = new ArrayList<String>();
        private LayoutInflater inflater;

        private Context mContext;


        public PhotoAdapter(Context mContext, ArrayList<String> photoPaths) {
            this.photoPaths = photoPaths;
            this.mContext = mContext;
            inflater = LayoutInflater.from(mContext);

        }


        @Override
        public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = inflater.inflate(R.layout.item_photo, parent, false);
            return new PhotoViewHolder(itemView);
        }


        @Override
        public void onBindViewHolder(final PhotoViewHolder holder, final int position) {


            if (position == photoPaths.size()) {
                holder.ivPhoto
                        .setImageBitmap(BitmapFactory.decodeResource(
                                mContext.getResources(),
                                R.drawable.photo_9_display_add_img));
                if (position == photoPickSize) {
                    holder.ivPhoto.setVisibility(View.GONE);
                }
            } else {
                if (photoPaths.get(position).contains("http")) {
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


            holder.ivPhoto.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (position == photoPaths.size()) {
                        Log.i("ddddddd", "----------");
                        PhotoPickerIntent intent = new PhotoPickerIntent(mContext);
                        intent.setPhotoCount(photoPickSize);
                        ((Activity) mContext).startActivityForResult(intent, REQUEST_CODE);
                    } else {
                        Intent intent = new Intent(mContext, PhotoPagerActivity.class);
                        intent.putExtra(PhotoPagerActivity.EXTRA_CURRENT_ITEM, position);
                        intent.putExtra(PhotoPagerActivity.EXTRA_PHOTOS, photoPaths);
                        if (mContext instanceof Activity) {
                            ((Activity) mContext).startActivityForResult(intent, REQUEST_CODE);
                        }
                    }


                }
            });

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
            private View vSelected;

            public PhotoViewHolder(View itemView) {
                super(itemView);
                ivPhoto = (ImageView) itemView.findViewById(R.id.iv_photo);
                vSelected = itemView.findViewById(R.id.v_selected);
                vSelected.setVisibility(View.GONE);


            }


        }


    }

    public void setPhotoPickSize(int size) {
        photoPickSize = size;
    }

    public void setColumns(int columns) {
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(columns, OrientationHelper.VERTICAL));
        recyclerView.setAdapter(photoAdapter);
    }
}
