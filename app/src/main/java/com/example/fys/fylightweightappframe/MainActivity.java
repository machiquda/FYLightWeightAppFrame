package com.example.fys.fylightweightappframe;

import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import fengyu.cn.library.net.okhttp.FOkhttpCallBack;
import fengyu.cn.library.net.okhttp.FOkhttpClient;
import fengyu.cn.library.net.okhttp.FileFOkhttpHandler;
import fengyu.cn.library.net.okhttp.JsonFOkhttpHandler;
import fengyu.cn.library.net.okhttp.Result;
import okhttp3.Call;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {


    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        mProgressBar = (ProgressBar) findViewById(R.id.mProgressBar);
//        mProgressBar.setMax(100);
//        setSupportActionBar(toolbar);
        // new FOkhttpClient.Builder(MainActivity.this).initBuild();
        FOkhttpClient.with(MainActivity.this).debug(true).build();
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                okHttpClient.get("http://publicobject.com/helloworld.txt", new JsonFOkhttpHandler() {
//                    @Override
//                    public void onSuccess(int statusCode, Headers headers, Object bean) {
//                        Toast.makeText(MainActivity.this, "sdasda", Toast.LENGTH_LONG).show();
//                    }
//
//                    @Override
//                    public void onJsonStringSuccess(int statusCode, Headers headers, String responseJsonString) {
//                        Toast.makeText(MainActivity.this, "sdasda", Toast.LENGTH_LONG).show();
//                    }
//
//                    @Override
//                    public void onFinish(int statusCode, Headers headers) {
//
//                        Toast.makeText(MainActivity.this, "sdasda", Toast.LENGTH_LONG).show();
//                    }
//                }, "a");

        //String urla = "http://dl.zongheng.com/book/69507.zip?";

//                FOkhttpClient
//                        .get()
//                        .url("http://api.k780.com:88/").addParams("app","life.time").addParams("appkey","10003").addParams("sign","b59bc3ef6191eb9f747dd4e83c99f2a4").addParams("format","json")
//                        .id(100)
//                        .build()
//                        .execute(new JsonFOkhttpHandler() {
//                            @Override
//                            public void onSuccess(int statusCode, Headers headers, Object bean) {
//                                Toast.makeText(MainActivity.this, "sdasda", Toast.LENGTH_LONG).show();
//                            }
//
//                            @Override
//                            public void onJsonStringSuccess(int statusCode, Headers headers, String responseJsonString) {
//                                Toast.makeText(MainActivity.this, "sdasda", Toast.LENGTH_LONG).show();
//                            }
//
//                            @Override
//                            public void onFinish(int statusCode, Headers headers) {
//
//                                Toast.makeText(MainActivity.this, "sdasda", Toast.LENGTH_LONG).show();
//                            }
//                        });


//                FOkhttpClient.get().url(urla).build().execute(new FileFOkhttpHandler(Environment.getExternalStorageDirectory().getAbsolutePath(), "gson-2.2.1.jar") {
//
//
//                    @Override
//                    protected void syncInProgress(final float progress, long total, Object id) {
//
//                       // mProgressBar.setProgress((int) (100 * progress));
//                        mProgressBar.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                mProgressBar.setProgress((int) (100 * progress));
//                            }
//                        });
//                        Toast.makeText(MainActivity.this,"1",Toast.LENGTH_SHORT).show();
//                        Log.e(TAG, "inProgress :" + (int) (100 * progress));
//                    }
//                });
//
//
//            }
//        });


//        okHttpClient.get("http://publicobject.com/helloworld.txt", new JsonFOkhttpHandler() {
//            @Override
//            public void onSuccess(int statusCode, Headers headers, Object bean) {
//                Toast.makeText(MainActivity.this, "sdasda", Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void onJsonStringSuccess(int statusCode, Headers headers, String responseJsonString) {
//                Toast.makeText(MainActivity.this, "sdasda", Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void onFinish(int statusCode, Headers headers) {
//
//                Toast.makeText(MainActivity.this, "sdasda", Toast.LENGTH_LONG).show();
//            }
//        }, "a");
//
//        Map<String, Object> data = new HashMap<>();
//        data.put("phone", "18815285479");
//        data.put("password", "e10adc3949ba59abbe56e057f20f883e");
//        okHttpClient.get("http://pdktest.aetone.com/api/driver/login", data, new JsonFOkhttpHandler() {
//            @Override
//            public void onSuccess(int statusCode, Headers headers, Object bean) {
//                Toast.makeText(MainActivity.this, "sdasda", Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void onJsonStringSuccess(int statusCode, Headers headers, String responseJsonString) {
//                Toast.makeText(MainActivity.this, "sdasda", Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void onFinish(int statusCode, Headers headers) {
//
//                Toast.makeText(MainActivity.this, "sdasda", Toast.LENGTH_LONG).show();
//            }
//        }, "a");

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
