package com.example.fys.fylightweightappframe.mvp;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.fys.fylightweightappframe.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

import fengyu.cn.library.net.okhttp.FOkhttpClient;

/**
 * Created by fengyu on 16/7/13.
 */
@EActivity(R.layout.activity_main)
public class MvpExActivity extends AppCompatActivity {


    private ExampleContract.Presenter presenter;

    private MvpExFragment mvpExFragment;


    @AfterViews
    void afterViews(){
        FOkhttpClient.with(MvpExActivity.this).debug(true).build();
        mvpExFragment = new MvpExFragment_();
        presenter = new ExamplePresenter(mvpExFragment);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.contentFrame,mvpExFragment);
        transaction.commit();
    }

}
