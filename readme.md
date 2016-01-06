
FYLightWeightAppFrame
======================
This is a useful app  frame  tool for Android


目前功能：

- FAppHttpClient      网络请求库，基于AsyncHttpClient封装，加入请求数据缓存
- FPhotoPicker        九宫格图片控件 可以拖拽排序
- Utils               一些实用工具类
- FUtimateListView    基于FysXListView 扩展 内部含有状态指示器 下拉刷新上拉加载更多在内部判断
- FysXListView        继承自ListView 支持下拉刷新，上拉加载，弹性拖动


目前还在持续迁移和更新中

##JCenter

```
dependencies {
     compile 'fengyu.cn.library:library:1.0.1'
}
```
###FPhotoPicker
Usage
-----
```xml
       <fengyu.cn.library.photopicker.widget.FPhotoPicker
            android:id="@+id/FPhotoPicker"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content">
        </fengyu.cn.library.photopicker.widget.FPhotoPicker>
    Add the following in AndroidMainfest.xml 
    <activity
           android:name="fengyu.cn.library.photopicker.PhotoPagerActivity"
           android:label="@string/app_name"
           android:theme="@style/AppTheme.NoActionBar"></activity>
    <activity
           android:name="fengyu.cn.library.photopicker.PhotoPickerActivity"
           android:label="@string/app_name"
           android:theme="@style/AppTheme.NoActionBar"></activity>
    Change AppTheme parent  to Theme.AppCompat.NoActionBar
    Add  permission
     <uses-permission android:name="android.permission.CAMERA" />
     <uses-permission android:name="android.permission.FLASHLIGHT" />
     <uses-feature android:name="android.hardware.camera" />
     <uses-feature android:name="android.hardware.camera.autofocus" />
     <uses-permission android:name="android.permission.VIBRATE" />
```



