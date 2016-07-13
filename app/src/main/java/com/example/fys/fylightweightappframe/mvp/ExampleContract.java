package com.example.fys.fylightweightappframe.mvp;

/**
 * 契约类 <br/>
 * 包含一个Activity 或者 一个完整功能 的所有的View 和  presenter 接口
 * Created by fengyu on 16/7/13.
 */
public interface ExampleContract {

    interface View extends BaseView<Presenter>{


        void updateProgress(int progress, long total);
    }
    interface Presenter extends BasePresenter{

        void startRequestImage();

    }
}
