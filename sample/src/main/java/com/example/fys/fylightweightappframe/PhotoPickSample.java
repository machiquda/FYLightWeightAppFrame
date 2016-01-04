package com.example.fys.fylightweightappframe;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.List;

import fengyu.cn.library.photopicker.PhotoPickerActivity;
import fengyu.cn.library.photopicker.adapter.FPhotoPickerAdapter;
import fengyu.cn.library.photopicker.utils.ImageCaptureManager;
import fengyu.cn.library.photopicker.widget.FPhotoPicker;

/**
 * Created by fys on 2015/12/30.
 */
public class PhotoPickSample extends AppCompatActivity {


    FPhotoPicker photo_x_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photopick_sample);
        photo_x_view = (FPhotoPicker) findViewById(R.id.photo_x_view);
        photo_x_view.setColumns(3);
        photo_x_view.setPhotoPickSize(9);
        photo_x_view.setPhotoPickMode(ImageCaptureManager.STORAGE_STRATEGY_TEMPORARY);
        photo_x_view.setOnPhotoClickListener(new FPhotoPickerAdapter.PhotoClickListener() {

            @Override
            public void onPhotoClick(FPhotoPickerAdapter.PhotoViewHolder holder, int position, String currentPhotoPath) {
                Toast.makeText(PhotoPickSample.this, "sdsda", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        List<String> photos = null;
        if (resultCode == RESULT_OK && requestCode == FPhotoPicker.REQUEST_CODE) {
            if (data != null) {
                photos = data.getStringArrayListExtra(PhotoPickerActivity.KEY_SELECTED_PHOTOS);
            }
            photo_x_view.selectedPhotos.clear();

            if (photos != null) {

                photo_x_view.selectedPhotos.addAll(photos);
            }

            photo_x_view.getPhotoAdapter().notifyDataSetChanged();
        }
    }


}
