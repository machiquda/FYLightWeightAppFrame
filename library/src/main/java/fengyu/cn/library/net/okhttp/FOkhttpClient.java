package fengyu.cn.library.net.okhttp;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import fengyu.cn.library.net.okhttp.builder.GetBuilder;
import fengyu.cn.library.net.okhttp.cache.SetCookieCache;
import fengyu.cn.library.net.okhttp.persistence.SharedPrefsCookiePersistor;
import fengyu.cn.library.net.okhttp.request.RequestCall;
import fengyu.cn.library.net.okhttp.request.ResponseFormatType;
import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;
import okio.GzipSink;
import okio.Okio;

/**
 * Factory for calls, which can be used to send HTTP requests and read their responses.
 * Most applications can use a single OkHttpClient for all of their HTTP requests,
 * benefiting from a shared response cache, thread pool, connection re-use, etc
 * <p>
 * <br/>Created by fys on 2016/6/11.
 */
public class FOkhttpClient {

    public static final long DEFAULT_MILLISECONDS = 10_000L;
    private final static String TAG = "FOkhttpClient";
    /**
     * build后保持全局单例
     */
    private volatile static FOkhttpClient fOkhttpClient = null;
    private OkHttpClient client;

    /**
     * 缓存类型
     */
    private int cacheType = CacheType.NETWORK_ELSE_CACHED;
    private MediaType mediaType = MultipartBody.FORM;
    private GzipRequestInterceptor gzipRequestInterceptor = new GzipRequestInterceptor();


    public static FOkhttpClient getInstance() {

        if (fOkhttpClient == null) {
            fOkhttpClient = new FOkhttpClient(null);
        }
        return fOkhttpClient;
    }

    public OkHttpClient getClient() {
        return client;
    }


    public FOkhttpClient(OkHttpClient okHttpClient) {
        if (okHttpClient == null) {
            client = new OkHttpClient();
        } else {
            client = okHttpClient;
        }

    }


    private FOkhttpClient(Context context, int maxCacheSize, File cachedDir, @CacheType int cacheType, List<Interceptor> netWorkinterceptors, List<Interceptor> interceptors, boolean isGzip, long timeOut, boolean debug) {
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();

        this.cacheType = cacheType;
        if (cachedDir != null) {

            clientBuilder.cache(new Cache(cachedDir, maxCacheSize));
        } else {
            clientBuilder.cache(new Cache(context.getCacheDir(), maxCacheSize));
        }
        if (isGzip) {
            if (!clientBuilder.interceptors().contains(gzipRequestInterceptor)) {
                clientBuilder.addInterceptor(new GzipRequestInterceptor());
            }
        }
        if (netWorkinterceptors != null && !netWorkinterceptors.isEmpty()) {
            clientBuilder.networkInterceptors().addAll(netWorkinterceptors);
        }
        if (interceptors != null && !interceptors.isEmpty()) {
            clientBuilder.interceptors().addAll(interceptors);
        }
        clientBuilder.connectTimeout(timeOut, TimeUnit.MILLISECONDS);
        ClearableCookieJar cookieJar =
                new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(context));
        clientBuilder.cookieJar(cookieJar);

        client = clientBuilder.build();


    }

    public FOkhttpClient initDefault(Context context) {
        return new Builder(context).build();
    }

    /**
     * Get 请求
     *
     * @param url                    目标Url
     * @param abstractFOkhttpHandler 回调
     */
    public void get(String url, AbstractFOkhttpHandler abstractFOkhttpHandler, String tag) {
        get(url, null, abstractFOkhttpHandler, null, tag);
    }

    /**
     * Get 请求
     *
     * @param url                   目标Url
     * @param abstractFOkhttpHandle 回调
     * @param classes               返回实体类
     */
    public void get(String url, AbstractFOkhttpHandler abstractFOkhttpHandle, Class<?> classes, String tag) {
        get(url, null, abstractFOkhttpHandle, classes, tag);
    }

    /**
     * Get 请求
     *
     * @param url                   目标Url
     * @param abstractFOkhttpHandle 回调
     */
    public void get(String url, Map<String, Object> params, AbstractFOkhttpHandler abstractFOkhttpHandle, String tag) {
        get(url, params, abstractFOkhttpHandle, null, tag);
    }

    /**
     * Get 请求
     *
     * @param url                    目标Url
     * @param params                 参数
     * @param abstractFOkhttpHandler 回调
     */
    public void get(String url, Map<String, Object> params, AbstractFOkhttpHandler abstractFOkhttpHandler, Class<?> classes, String tag) {


        if (params != null && params.size() > 0) {

            url = createGetUrlWithParams(url, params);
        }
        Request request = new Request.Builder()
                .url(url)
                .tag(tag)
                .get()

                .build();
        if (tag == null || tag.isEmpty()) {
            tag = "FOkhttpClient";
        }
        Call call = client.newCall(request);
        request(call, cacheType, abstractFOkhttpHandler, tag, classes);
    }


    /**
     * 执行由Builder构造的请求
     *
     * @param requestCall            RequestCall  see {@link RequestCall}
     * @param cacheType              缓存使用策略
     * @param abstractFOkhttpHandler AbstractFOkhttpHandler  see  {@link AbstractFOkhttpHandler}
     * @param classes                请求结果希望解析成的对象类
     * @param tag                    请求标识
     */
    public void executeCall(RequestCall requestCall, @CacheType int cacheType, AbstractFOkhttpHandler abstractFOkhttpHandler, Class<?> classes, Object tag) {
        request(requestCall.getCall(), cacheType, abstractFOkhttpHandler, tag, classes);
    }


    /**
     * post   请求
     *
     * @param url                    目标Url
     * @param abstractFOkhttpHandler 回调
     * @param classes                返回实体类
     * @param tag                    标识
     */
    public void post(String url, AbstractFOkhttpHandler abstractFOkhttpHandler, Class<?> classes, String tag) {
        post(url, null, abstractFOkhttpHandler, classes, tag);

    }

    /**
     * post   请求
     *
     * @param url                    目标Url
     * @param abstractFOkhttpHandler 回调
     * @param tag                    标识
     */
    public void post(String url, AbstractFOkhttpHandler abstractFOkhttpHandler, String tag) {
        post(url, null, abstractFOkhttpHandler, null, tag);

    }

    /**
     * post   请求
     *
     * @param url                    目标Url
     * @param params                 参数
     * @param abstractFOkhttpHandler 回调
     * @param tag                    标识
     */
    public void post(String url, Map<String, Object> params, AbstractFOkhttpHandler abstractFOkhttpHandler, String tag) {
        post(url, params, abstractFOkhttpHandler, null, tag);

    }

    /**
     * post   请求
     *
     * @param url                    目标Url
     * @param params                 参数
     * @param abstractFOkhttpHandler 回调
     * @param classes                返回实体类
     * @param tag                    标识
     */
    public void post(String url, Map<String, Object> params, AbstractFOkhttpHandler abstractFOkhttpHandler, Class<?> classes, String tag) {
        RequestBody requestBody = null;
        if (params != null && params.size() > 0) {
            requestBody = createRequestBody(params);
        } else {
            requestBody = new FormBody.Builder().build();
        }

        Request request = new Request.Builder()
                .url(url)
                .tag(tag)
                .post(requestBody)
                .build();

        Call call = client.newCall(request);
        request(call, cacheType, abstractFOkhttpHandler, tag, classes);
    }

    /**
     * 将参数添加到get请求Url的尾部
     *
     * @param url    请求Url
     * @param params 参数
     * @return 拼装结果
     */
    private String createGetUrlWithParams(String url, Map<String, Object> params) {
        StringBuilder stringBuilder = new StringBuilder(url);
        if (params != null && !params.isEmpty()) {
            stringBuilder.append("?");
            Set<String> keys = params.keySet();
            for (String key : keys) {
                stringBuilder.append(key);
                stringBuilder.append("=");
                stringBuilder.append(params.get(key));
                stringBuilder.append("&");

            }
        }
        String returnUrl = stringBuilder.toString();
        if (returnUrl.endsWith("&")) {
            returnUrl = returnUrl.substring(0, returnUrl.length() - 1);
        }
        return returnUrl;
    }

    /**
     * 创建 MultipartRequestBody
     *
     * @param params 参数
     * @return
     */
    private RequestBody createMultipartRequestBody(Map<String, Object> params) {
        MultipartBody.Builder multipartBuilder = new MultipartBody.Builder();
        multipartBuilder.setType(mediaType);
        if (params != null && !params.isEmpty()) {
            Set<String> keys = params.keySet();
            for (String key : keys) {
                multipartBuilder.addFormDataPart(key, params.get(key).toString());
            }
        }
        return multipartBuilder.build();
    }

    /**
     * 创建 RequestBody
     *
     * @param params 参数
     * @return
     */
    private RequestBody createRequestBody(Map<String, Object> params) {
        FormBody.Builder builder = new FormBody.Builder();
        if (params != null && !params.isEmpty()) {
            Set<String> keys = params.keySet();
            for (String key : keys) {
                builder.add(key, params.get(key).toString());
            }
        }
        return builder.build();
    }

    /**
     * 执行请求
     *
     * @param request                请求主体
     * @param cacheType              缓存请求策略
     * @param abstractFOkhttpHandler 请求回调
     * @param tag                    标识
     */
    private void request(final Request request, @CacheType final int cacheType, final AbstractFOkhttpHandler abstractFOkhttpHandler, final String tag) {
        Call call = client.newCall(request);
        request(call, cacheType, abstractFOkhttpHandler, tag, null);
    }

    /**
     * 执行请求
     *
     * @param call                   请求主体
     * @param cacheType              缓存请求策略
     * @param abstractFOkhttpHandler 请求回调
     * @param tag                    标识
     * @param classes                返回实体类
     */
    private void request(final Call call, @CacheType final int cacheType, final AbstractFOkhttpHandler abstractFOkhttpHandler, final Object tag, final Class<?> classes) {


        if (abstractFOkhttpHandler != null) {
            abstractFOkhttpHandler.callOnLoading();
            call.enqueue(new okhttp3.Callback() {

                             @Override
                             public void onFailure(Call call, IOException e) {
                                 if (abstractFOkhttpHandler != null) {
                                     sendFailResultCallback(call, e, abstractFOkhttpHandler, null, tag);
                                     return;
                                 }
                             }

                             @Override
                             public void onResponse(Call call, Response response) throws IOException {

                                 try {
                                     if (!response.isSuccessful()) {
                                         //请求失败
                                         if (abstractFOkhttpHandler != null) {
                                             sendFailResultCallback(call, null, abstractFOkhttpHandler, response, tag);
                                             return;
                                         }
                                     } else {
                                         //返回对象
                                         if (response.body() != null) {
                                             //文件下载请求  直接 返回response 不做处理
                                             if (abstractFOkhttpHandler.getResponseFormatType() == ResponseFormatType.FILE_RESPONSE || abstractFOkhttpHandler.getResponseFormatType() == ResponseFormatType.STRING) {
                                                 if (abstractFOkhttpHandler != null) {
                                                     Result result = new Result();
                                                     result.setNetWorkStatusCode(response.code());
                                                     result.setTag(tag);
                                                     result.setData(response);
                                                     result.setHeaders(response.headers());
                                                     result.setDataType(Result.OTHER);
                                                     abstractFOkhttpHandler.callOnSuccess(result);
                                                     return;
                                                 }

                                             }

                                             //检查返回数据是否为 json 字符串
                                             JSONObject jsonObject = null;
                                             try {
                                                 jsonObject = JSON.parseObject(response.body().string());
                                             } catch (Exception e) {
                                                 //不是json 字符串 检查 contentType 不是json格式的  直接返回数据
                                                 if (response.body() != null && response.body().contentType() != MediaType.parse("application/json") && response.body().contentType() != MediaType.parse("text/json")) {
                                                     if (abstractFOkhttpHandler != null) {
                                                         Result result = new Result();
                                                         result.setNetWorkStatusCode(response.code());
                                                         result.setTag(tag);
                                                         result.setData(response.body());
                                                         result.setHeaders(response.headers());
                                                         result.setDataType(Result.OTHER);
                                                         abstractFOkhttpHandler.callOnSuccess(result);
                                                         return;
                                                     }
                                                 } else {

                                                     if (abstractFOkhttpHandler != null) {
                                                         sendFailResultCallback(call, e, abstractFOkhttpHandler, response, tag);
                                                         return;
                                                     }
                                                 }
                                             }

                                             /**
                                              * 判断返回数据是否是 { data,code,message}格式
                                              */
                                             final String data = jsonObject.getString("data");
                                             final String code = jsonObject.getString("code");
                                             if (data == null || code == null) {
                                                 if (abstractFOkhttpHandler != null) {
                                                     Result result = new Result();
                                                     result.setNetWorkStatusCode(response.code());
                                                     result.setTag(tag);
                                                     result.setData(jsonObject.toJSONString());
                                                     result.setHeaders(response.headers());
                                                     result.setDataType(Result.OTHER);
                                                     abstractFOkhttpHandler.callOnSuccess(result);
                                                     return;

                                                 }
                                             } else {
                                                 if (classes == null) {
                                                     if (abstractFOkhttpHandler != null) {
                                                         Result result = new Result();
                                                         result.setMessage(jsonObject.getString("message"));
                                                         result.setNetWorkStatusCode(response.code());
                                                         result.setTag(tag);
                                                         if (data == null) {
                                                             if (jsonObject == null) {
                                                                 result.setData(response.body());
                                                             } else {
                                                                 result.setData(jsonObject);
                                                             }
                                                         } else {
                                                             result.setData(data);
                                                         }
                                                         result.setCode(jsonObject.getIntValue("code"));
                                                         result.setHeaders(response.headers());
                                                         result.setDataType(Result.OTHER);
                                                         abstractFOkhttpHandler.callOnSuccess(result);
                                                         return;
                                                     }
                                                 } else {

                                                     Object re = JSON.parseObject(data, classes);
                                                     Result result = new Result();
                                                     result.setMessage(jsonObject.getString("message"));
                                                     result.setNetWorkStatusCode(response.code());
                                                     result.setCode(jsonObject.getIntValue("code"));
                                                     result.setTag(tag);
                                                     result.setData(re);
                                                     result.setHeaders(response.headers());
                                                     result.setDataType(Result.STAND);
                                                     abstractFOkhttpHandler.callOnSuccess(result);
                                                     return;
                                                 }
                                             }
                                         }
                                     }
                                 } catch (Exception e) {
                                     sendFailResultCallback(call, e, abstractFOkhttpHandler, response, tag);
                                 } finally {
                                     // 如果是下载文件  先不关闭 inputSteam
                                     // if (response.body() != null && abstractFOkhttpHandler.getResponseFormatType() != ResponseFormatType.FILE_RESPONSE)
                                     response.body().close();
                                 }
                             }
                         }

            );
        }
    }

    private RequestBody createFormRequestBody(Map<String, String> params, String
            encodedKey, String encodedValue) {
        FormBody.Builder builder = new FormBody.Builder();
        if (params != null && !params.isEmpty()) {
            Set<String> keys = params.keySet();
            for (String key : keys) {
                builder.add(key, params.get(key));
            }
        }
        if (!TextUtils.isEmpty(encodedKey) && !TextUtils.isEmpty(encodedValue)) {
            builder.addEncoded(encodedKey, encodedValue);
        }
        return builder.build();
    }

    public static GetBuilder get() {
        return new GetBuilder();
    }

    static class GzipRequestInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request originalRequest = chain.request();
            if (originalRequest.body() == null || originalRequest.header("Content-Encoding") != null) {
                return chain.proceed(originalRequest);
            }

            Request compressedRequest = originalRequest.newBuilder()
                    .header("Accept-Encoding", "gzip")
                    .method(originalRequest.method(), gzip(originalRequest.body()))
                    .build();
            return chain.proceed(compressedRequest);
        }

        private RequestBody gzip(final RequestBody body) {
            return new RequestBody() {
                @Override
                public MediaType contentType() {
                    return body.contentType();
                }

                @Override
                public long contentLength() {
                    return -1;
                }

                @Override
                public void writeTo(BufferedSink sink) throws IOException {
                    BufferedSink gzipSink = Okio.buffer(new GzipSink(sink));
                    body.writeTo(gzipSink);
                    gzipSink.close();
                }
            };
        }
    }


    public static class Builder {
        private int defaultCacheSize = 10 * 1024 * 1024;
        private File cachedDir;
        private Context context;
        private List<Interceptor> networkInterceptors;
        private List<Interceptor> interceptors;
        private int cacheType = CacheType.NETWORK_ELSE_CACHED;
        private boolean isGzip = false;
        private long timeOut = 5000;
        private boolean debug = false;


        public Builder(Context context) {
            this.context = context;
        }

        private Builder() {
        }

        public FOkhttpClient build() {

            fOkhttpClient = new FOkhttpClient(context, defaultCacheSize, cachedDir, cacheType, networkInterceptors, interceptors, isGzip, timeOut, debug);
            return fOkhttpClient;
        }


        public Builder timeOut(long timeOut) {
            this.timeOut = timeOut;
            return this;
        }

        public Builder debug(boolean debug) {
            this.debug = debug;
            return this;
        }

        public Builder gzip(boolean openGzip) {
            this.isGzip = openGzip;
            return this;
        }

        public Builder cacheType(@CacheType int cacheType) {
            this.cacheType = cacheType;
            return this;
        }

        public Builder cachedDir(File cachedDir) {
            this.cachedDir = cachedDir;
            return this;
        }


        /**
         * 拦截器
         *
         * @param interceptors
         */
        public Builder interceptors(List<Interceptor> interceptors) {
            this.interceptors = interceptors;
            return this;
        }

        public Builder maxCachedSize(int maxCachedSize) {
            this.defaultCacheSize = maxCachedSize;
            return this;
        }

        /**
         * 拦截器
         *
         * @param networkInterceptors
         */
        public Builder networkInterceptors(List<Interceptor> networkInterceptors) {
            this.networkInterceptors = networkInterceptors;
            return this;
        }

    }

    /**
     * 取消请求
     *
     * @param tag
     */
    public void cancelRequest(Object tag) {

        if (tag != null && client != null) {
            for (Call call : client.dispatcher().queuedCalls()) {
                if (tag.equals(call.request().tag())) {
                    call.cancel();
                }
            }
            for (Call call : client.dispatcher().runningCalls()) {
                if (tag.equals(call.request().tag())) {
                    call.cancel();
                }
            }
        } else {
            Log.w(TAG, "cancelRequest:   tag  OR  client is null");
        }

    }

    /**
     * 发送请求失败回调消息
     *
     * @param call
     * @param e
     * @param callbacHandlerk
     * @param response
     * @param tag
     */
    private void sendFailResultCallback(final Call call, final Exception e,
                                        final AbstractFOkhttpHandler callbacHandlerk, Response response, Object tag) {
        Result result = new Result();
        result.setErrorCode(Result.FAILURE_RESPONSE);
        if (response != null) {
            result.setMessage(response.message());
            result.setNetWorkStatusCode(response.code());
            result.setHeaders(response.headers());
        }
        if (e != null) {
            result.setThrowable(e);
        }
        result.setTag(tag);
        callbacHandlerk.callOnFailure(result);
    }


}
