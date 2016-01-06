package fengyu.cn.library.net;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;



import java.util.Map;

import fengyu.cn.library.net.com.loopj.android.http.AsyncHttpClient;
import fengyu.cn.library.net.com.loopj.android.http.AsyncHttpResponseHandler;
import fengyu.cn.library.net.com.loopj.android.http.JsonHttpResponseHandler;
import fengyu.cn.library.net.com.loopj.android.http.PersistentCookieStore;
import fengyu.cn.library.net.com.loopj.android.http.RequestParams;
import fengyu.cn.library.utils.D;
import fengyu.cn.library.utils.DeviceUtil;


/**
 * Created by fys on 2015/11/26.
 * 数据请求入口
 */
public class FAppHttpClient {


    private static AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
    /**
     * PersistentCookieStore cookie
     * 添加 cookie see@ AsyncHttpClient.addCookie
     */
    private static PersistentCookieStore cookieStore;
    private static String newUrl = null;
    public static Context mApplicationContext;
    public static String DOMAIN = "yuemz.aetone.com/yuemz/api";
    //主线程回掉
    private static Handler ulHandler;
    public static final String REQUEST_GET = "get";
    public static final String REQUEST_POST = "post";

    /**
     * 根据path生成完整的路径
     *
     * @param path 请求的 URL
     * @return
     */
    private static String makeFullPath(String path) {
        String result = "http://" + DOMAIN + path;
        return result;
    }

    /**
     * Get 请求
     *
     * @param context      请求的Activity 取消标记
     * @param path         具体URL
     * @param handler      请求回调
     * @param isNeedCookie 是否需要附带cookie
     */
    public static void get(Context context, String path, AsyncHttpResponseHandler handler, boolean isNeedCookie) {

        if (isNeedCookie) {
            cookieStore = new PersistentCookieStore(mApplicationContext);
            asyncHttpClient.setCookieStore(cookieStore);
        }
        String fullPath = makeFullPath(path);
        D.log("开始请求:" + fullPath);
        asyncHttpClient.get(context, fullPath, handler);
    }

    /**
     * 带UDID的Get请求
     *
     * @param context      请求的Activity 取消标记
     * @param path         具体URL
     * @param handler      请求回调
     * @param isNeedCookie 是否需要附带cookie
     */
    public static void getWithUDID(Context context, String path, AsyncHttpResponseHandler handler, boolean isNeedCookie) {
        if (isNeedCookie) {
            asyncHttpClient.setCookieStore(new PersistentCookieStore(context));
        }
        String fullPath = makeFullPath(path + "&udid="
                + DeviceUtil.getDeviceId(mApplicationContext));
        D.log("开始请求:" + fullPath);
        asyncHttpClient.get(fullPath, handler);
    }

    /**
     * Post请求
     *
     * @param context      请求的Activity 取消标记
     * @param path         具体URL
     * @param data         请求数据  Map&lt;String, String&gt;data
     * @param handler      请求回调
     * @param isNeedCookie 是否需要附带cookie
     */
    public static void post(Context context, String path, Map<String, String> data,
                            JsonHttpResponseHandler handler, boolean isNeedCookie) {
        if (isNeedCookie) {
            asyncHttpClient.setCookieStore(new PersistentCookieStore(mApplicationContext));
        }
        String fullPath = makeFullPath(path);
        D.log("开始请求:" + fullPath);
        RequestParams parm = new RequestParams(data);
        asyncHttpClient.post(context, fullPath, parm, handler);
    }

    /**
     * Post请求
     *
     * @param context       请求的Activity 取消标记
     * @param path          具体URL
     * @param handler       请求回调
     * @param keysAndValues 请求数据 Object...
     * @param isNeedCookie  是否需要附带cookie
     */
    public static void post(Context context, String path, JsonHttpResponseHandler handler, boolean isNeedCookie,
                            Object... keysAndValues) {
        if (isNeedCookie) {
            asyncHttpClient.setCookieStore(new PersistentCookieStore(mApplicationContext));
        }
        String fullPath = makeFullPath(path);
        RequestParams parm = new RequestParams(keysAndValues);
        D.log("开始请求:" + fullPath + "------" + keysAndValues.toString());
        asyncHttpClient.post(context, fullPath, parm, handler);
    }

    /**
     * 不带参数的Post请求
     *
     * @param context      请求的Activity 取消标记
     * @param path         具体URL
     * @param handler      请求回调
     * @param isNeedCookie 是否需要附带cookie
     */
    public static void post(Context context, String path, JsonHttpResponseHandler handler, boolean isNeedCookie) {
        if (isNeedCookie) {
            cookieStore = new PersistentCookieStore(mApplicationContext);
            asyncHttpClient.setCookieStore(cookieStore);
        }
        String fullPath = makeFullPath(path);
//        final String udid = UDID.getInstance(mApplicationContext).getUDID();
//        handler.setContext(mContext);
        D.log("开始请求:" + fullPath);
//        _client.addHeader("UDID", udid);
        asyncHttpClient.post(context, fullPath, null, handler);
    }

    /**
     * 上传图片
     *
     * @param context      请求的Activity 取消标记
     * @param path         具体URL
     * @param parm         流文件
     * @param handler      请求回调
     * @param isNeedCookie 是否需要附带cookie
     */
    public static void postImg(Context context, String path, RequestParams parm,
                               JsonHttpResponseHandler handler, boolean isNeedCookie) {
        if (isNeedCookie) {
            asyncHttpClient.setCookieStore(new PersistentCookieStore(mApplicationContext));
        }
        String fullPath = makeFullPath(path);
        D.log("开始请求:" + fullPath);
        // final String udid = UDID.getInstance(mApplicationContext).getUDID();
        asyncHttpClient.post(context, fullPath, parm, handler);
    }

    /**
     * 发起 带数据缓存的 请求
     *
     * @param activity 请求的Activity 取消标记
     * @param apiKey   xml中api的描述Kay
     * @param data     请求的data
     * @param handler  请求回调
     *                 默认带Cookie
     */
    public static void invokeCacheRequest(final Activity activity,
                                          final String apiKey,
                                          final Map<String, String> data,
                                          final FJsonHttpResponesHandler handler) {

        //生成查询请求线程
        RequestCacheScanThread requestCacheScanThread = new RequestCacheScanThread(activity, apiKey, data, handler);
        //用线程池执行
        DefaultThreadPool.getInstance().execute(requestCacheScanThread);

    }


    /**
     * 获取Cookie
     *
     * @return cookieStore
     */
    public static PersistentCookieStore getCookieStore() {
        return cookieStore;
    }

    /**
     * 设置cookie
     *
     * @param cStore
     */
    public static void setCookieStore(PersistentCookieStore cStore) {
        FAppHttpClient.cookieStore = cStore;
        asyncHttpClient.setCookieStore(cookieStore);
    }

}
