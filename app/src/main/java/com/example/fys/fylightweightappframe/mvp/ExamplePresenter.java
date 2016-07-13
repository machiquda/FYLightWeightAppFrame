package com.example.fys.fylightweightappframe.mvp;

import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import fengyu.cn.library.net.okhttp.FOkhttpClient;
import fengyu.cn.library.net.okhttp.FileFOkhttpHandler;

/**
 * Created by fengyu on 16/7/13.
 */
public class ExamplePresenter implements ExampleContract.Presenter {


    private ExampleContract.View view;


    public ExamplePresenter(@NonNull ExampleContract.View view){
        this.view = view;
        this.view.setPresenter(this);
    }


    @Override
    public void startRequestImage() {
        String urla = "http://dl.zongheng.com/book/69507.zip?";
        FOkhttpClient.get().url(urla).build().execute(new FileFOkhttpHandler(Environment.getExternalStorageDirectory().getAbsolutePath(), "gson-2.2.1.jar") {
            @Override
            protected void syncInProgress(final float progress, long total, Object id) {
                view.updateProgress((int) (100 * progress),total);
            }
        });

    }

    @Override
    public void start() {

    }
}
