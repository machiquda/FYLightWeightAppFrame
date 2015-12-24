package fengyu.cn.library.net;

import android.app.Activity;
import android.os.Looper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * 异步缓存扫描
 * 在再请求前先扫描缓存，如果有缓存则直接返回缓存结果
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
     * @param activity 请求的Activity 取消标记
     * @param apiKey   xml中api的描述Kay
     * @param data     请求的data
     * @param handler  请求回调
     *                 默认带Cookie
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
            //获取apikey对应的URL类实体
            final URLData urlData = UrlConfigManager.findURL(activity, apiKey);
            final StringBuffer paramBuffer = new StringBuffer();
            //如果是get请求拼接参数
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
            // 如果这个API有缓存时间（大于0）
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
