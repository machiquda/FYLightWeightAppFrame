package com.example.fys.fylightweightappframe;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import com.example.fys.fylightweightappframe.adapter.FysXListViewSamplerAdapter;

import fengyu.cn.library.views.FUtimateListView;
import fengyu.cn.library.views.FysXListView;

/**
 * Created by fys on 2015/12/23.
 */
public class FysXListViwSample extends AppCompatActivity {


    FUtimateListView<String> listView;
    List<String> dataList;
    FysXListViewSamplerAdapter adapter;
    Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fysxlistview_sample);
        mHandler = new Handler();
        dataList = new ArrayList<>();
        dataList.add("1");
        dataList.add("2");
        dataList.add("3");
        dataList.add("4");
        dataList.add("5");
        dataList.add("6");
        dataList.add("7");
        dataList.add("8");
        dataList.add("9");
        dataList.add("10");
        dataList.add("11");
        dataList.add("12");
        dataList.add("13");
        dataList.add("14");
        dataList.add("15");
        dataList.add("16");


        listView = (FUtimateListView) findViewById(R.id.listview);
        listView.setPullLoadEnable(true);
        listView.setPullRefreshEnable(true);
        listView.getListView().setXListViewListener(new FysXListView.IXListViewListener() {

            @Override
            public void onRefresh() {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refresh();
                    }
                }, 1500);

            }

            @Override
            public void onLoadMore() {
                loadMoreData();
            }
        });
        refresh();
    }


    private void refresh() {
        if (adapter == null) {
            if (listView.getDataSource() == null) {
                listView.setDataSouce(new ArrayList<String>());
            }
            listView.getDataSource().addAll(dataList);
            adapter = new FysXListViewSamplerAdapter(FysXListViwSample.this, listView.getDataSource());

            listView.setAdapter(adapter);

        } else {
            listView.refreshNotifyDataSetChanged(dataList);
        }

    }


    private void loadMoreData() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                listView.loadMoreNotifyDataSetChanged(dataList);
            }
        }, 1500);


    }
}
