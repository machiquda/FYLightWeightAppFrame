package fengyu.cn.library.net;

import android.app.Activity;
import android.os.Looper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * �첽����ɨ��
 * ��������ǰ��ɨ�軺�棬����л�����ֱ�ӷ��ػ�����
 * Created by fys on 2015/12/6.
 */
public class RequestCacheScanThread implements Runnable {

    public static final String REQUEST_GET = "get";
    public static final String REQUEST_POST = "post";
    private String newUrl = null;

    private Activity activity;
    private String apiKey;
    private Map<String, String> data;
    private FJsonHttpResponesHandler handler;

    /**
     *
     * @param activity �����Activity ȡ�����
     * @param apiKey   xml��api������Kay
     * @param data     �����data
     * @param handler  ����ص�
     *                 Ĭ�ϴ�Cookie
     */
    public RequestCacheScanThread(final Activity activity,
                                  final String apiKey,
                                  final Map<String, String> data,
                                  final FJsonHttpResponesHandler handler) {

        this.activity = activity;
        this.apiKey = apiKey;
        this.data = data;
        this.handler = handler;
    }


    @Override
    public void run() {

        try {
            //��ȡapikey��Ӧ��URL��ʵ��
            final URLData urlData = UrlConfigManager.findURL(activity, apiKey);
            final StringBuffer paramBuffer = new StringBuffer();
            //�����get����ƴ�Ӳ���
            if ((data != null) && (data.size() > 0) && urlData.getNetType().equals(REQUEST_GET)) {
                for (String key : data.keySet()) {
                    System.out.println("key= " + key + " and value= " + data.get(key));
                    if (paramBuffer.length() == 0) {
                        paramBuffer.append(key + "="
                                + BaseUtils.UrlEncodeUnicode(data.get(key)));
                    } else {
                        paramBuffer.append("&" + key + "="
                                + BaseUtils.UrlEncodeUnicode(data.get(key)));
                    }
                }
                newUrl = urlData.getUrl() + "?" + paramBuffer.toString();
                handler.setUrlData(urlData);
                handler.setFinalRequestUrl(newUrl);
            } else {
                newUrl = urlData.getUrl();
                handler.setUrlData(urlData);
                handler.setFinalRequestUrl(newUrl);
            }
            // ������API�л���ʱ�䣨����0��
            if (urlData.getExpires() > 0) {
                final String content = CacheManager.getInstance()
                        .getFileCache(newUrl);

                if (content != null) {
                    try {
                        final JSONObject response = new JSONObject(content);
                        Looper.prepare();
                        handler.onJsonObjectSuccess(200, null, response);
                        Looper.loop();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    return;
                } else {
                    if (urlData.getNetType().equals(REQUEST_POST)) {
                        if (data != null) {
                            FAppHttpClient.post(activity, urlData.getUrl(), data, handler, true);
                        } else {
                            FAppHttpClient.post(activity, urlData.getUrl(), handler, true);
                        }
                    } else if (urlData.getNetType().equals(REQUEST_GET)) {
                        FAppHttpClient.get(activity, newUrl, handler, true);
                    }
                }
            } else if (urlData.getNetType().equals(REQUEST_POST)) {
                if (data != null) {
                    FAppHttpClient.post(activity, urlData.getUrl(), data, handler, true);
                } else {
                    FAppHttpClient.post(activity, urlData.getUrl(), handler, true);
                }
            } else if (urlData.getNetType().equals(REQUEST_GET)) {
                FAppHttpClient.get(activity, newUrl, handler, true);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
