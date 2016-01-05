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
 * �����������
 */
public class FAppHttpClient {


    private static AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
    /**
     * PersistentCookieStore cookie
     * ��� cookie see@ AsyncHttpClient.addCookie
     */
    private static PersistentCookieStore cookieStore;
    private static String newUrl = null;
    public static Context mApplicationContext;
    public static String DOMAIN = "yuemz.aetone.com/yuemz/api";
    //���̻߳ص�
    private static Handler ulHandler;
    public static final String REQUEST_GET = "get";
    public static final String REQUEST_POST = "post";

    /**
     * ����path����������·��
     *
     * @param path ����� URL
     * @return
     */
    private static String makeFullPath(String path) {
        String result = "http://" + DOMAIN + path;
        return result;
    }

    /**
     * Get ����
     *
     * @param context      �����Activity ȡ�����
     * @param path         ����URL
     * @param handler      ����ص�
     * @param isNeedCookie �Ƿ���Ҫ����cookie
     */
    public static void get(Context context, String path, AsyncHttpResponseHandler handler, boolean isNeedCookie) {

        if (isNeedCookie) {
            cookieStore = new PersistentCookieStore(mApplicationContext);
            asyncHttpClient.setCookieStore(cookieStore);
        }
        String fullPath = makeFullPath(path);
        D.log("��ʼ����:" + fullPath);
        asyncHttpClient.get(context, fullPath, handler);
    }

    /**
     * ��UDID��Get����
     *
     * @param context      �����Activity ȡ�����
     * @param path         ����URL
     * @param handler      ����ص�
     * @param isNeedCookie �Ƿ���Ҫ����cookie
     */
    public static void getWithUDID(Context context, String path, AsyncHttpResponseHandler handler, boolean isNeedCookie) {
        if (isNeedCookie) {
            asyncHttpClient.setCookieStore(new PersistentCookieStore(context));
        }
        String fullPath = makeFullPath(path + "&udid="
                + DeviceUtil.getDeviceId(mApplicationContext));
        D.log("��ʼ����:" + fullPath);
        asyncHttpClient.get(fullPath, handler);
    }

    /**
     * Post����
     *
     * @param context      �����Activity ȡ�����
     * @param path         ����URL
     * @param data         ��������  Map&lt;String, String&gt;data
     * @param handler      ����ص�
     * @param isNeedCookie �Ƿ���Ҫ����cookie
     */
    public static void post(Context context, String path, Map<String, String> data,
                            JsonHttpResponseHandler handler, boolean isNeedCookie) {
        if (isNeedCookie) {
            asyncHttpClient.setCookieStore(new PersistentCookieStore(mApplicationContext));
        }
        String fullPath = makeFullPath(path);
        D.log("��ʼ����:" + fullPath);
        RequestParams parm = new RequestParams(data);
        asyncHttpClient.post(context, fullPath, parm, handler);
    }

    /**
     * Post����
     *
     * @param context       �����Activity ȡ�����
     * @param path          ����URL
     * @param handler       ����ص�
     * @param keysAndValues �������� Object...
     * @param isNeedCookie  �Ƿ���Ҫ����cookie
     */
    public static void post(Context context, String path, JsonHttpResponseHandler handler, boolean isNeedCookie,
                            Object... keysAndValues) {
        if (isNeedCookie) {
            asyncHttpClient.setCookieStore(new PersistentCookieStore(mApplicationContext));
        }
        String fullPath = makeFullPath(path);
        RequestParams parm = new RequestParams(keysAndValues);
        D.log("��ʼ����:" + fullPath + "------" + keysAndValues.toString());
        asyncHttpClient.post(context, fullPath, parm, handler);
    }

    /**
     * ����������Post����
     *
     * @param context      �����Activity ȡ�����
     * @param path         ����URL
     * @param handler      ����ص�
     * @param isNeedCookie �Ƿ���Ҫ����cookie
     */
    public static void post(Context context, String path, JsonHttpResponseHandler handler, boolean isNeedCookie) {
        if (isNeedCookie) {
            cookieStore = new PersistentCookieStore(mApplicationContext);
            asyncHttpClient.setCookieStore(cookieStore);
        }
        String fullPath = makeFullPath(path);
//        final String udid = UDID.getInstance(mApplicationContext).getUDID();
//        handler.setContext(mContext);
        D.log("��ʼ����:" + fullPath);
//        _client.addHeader("UDID", udid);
        asyncHttpClient.post(context, fullPath, null, handler);
    }

    /**
     * �ϴ�ͼƬ
     *
     * @param context      �����Activity ȡ�����
     * @param path         ����URL
     * @param parm         ���ļ�
     * @param handler      ����ص�
     * @param isNeedCookie �Ƿ���Ҫ����cookie
     */
    public static void postImg(Context context, String path, RequestParams parm,
                               JsonHttpResponseHandler handler, boolean isNeedCookie) {
        if (isNeedCookie) {
            asyncHttpClient.setCookieStore(new PersistentCookieStore(mApplicationContext));
        }
        String fullPath = makeFullPath(path);
        D.log("��ʼ����:" + fullPath);
        // final String udid = UDID.getInstance(mApplicationContext).getUDID();
        asyncHttpClient.post(context, fullPath, parm, handler);
    }

    /**
     * ���� �����ݻ���� ����
     *
     * @param activity �����Activity ȡ�����
     * @param apiKey   xml��api������Kay
     * @param data     �����data
     * @param handler  ����ص�
     *                 Ĭ�ϴ�Cookie
     */
    public static void invokeCacheRequest(final Activity activity,
                                          final String apiKey,
                                          final Map<String, String> data,
                                          final FJsonHttpResponesHandler handler) {

        //���ɲ�ѯ�����߳�
        RequestCacheScanThread requestCacheScanThread = new RequestCacheScanThread(activity, apiKey, data, handler);
        //���̳߳�ִ��
        DefaultThreadPool.getInstance().execute(requestCacheScanThread);

    }


    /**
     * ��ȡCookie
     *
     * @return cookieStore
     */
    public static PersistentCookieStore getCookieStore() {
        return cookieStore;
    }

    /**
     * ����cookie
     *
     * @param cStore
     */
    public static void setCookieStore(PersistentCookieStore cStore) {
        FAppHttpClient.cookieStore = cStore;
        asyncHttpClient.setCookieStore(cookieStore);
    }

}
