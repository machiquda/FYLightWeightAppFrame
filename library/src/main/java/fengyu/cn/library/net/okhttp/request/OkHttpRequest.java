package fengyu.cn.library.net.okhttp.request;


import java.util.Map;

import fengyu.cn.library.net.okhttp.AbstractFOkhttpHandler;
import fengyu.cn.library.net.okhttp.CacheType;
import okhttp3.Headers;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * request 抽象类
 * Created by fys on 2016/6/25.
 */
public abstract class OkHttpRequest {
    protected String url;
    protected Object tag;
    protected Map<String, String> params;
    protected Map<String, String> headers;
    protected int id;
    protected Class<?> parseClass;
    protected @CacheType int cacheType;
    protected Request.Builder builder = new Request.Builder();

    protected OkHttpRequest(String url, Object tag,
                            Map<String, String> params, Map<String, String> headers, int id, Class<?> parseClass, @CacheType int cacheType ) {
        this.url = url;
        this.tag = tag;
        this.params = params;
        this.headers = headers;
        this.id = id;
        this.parseClass = parseClass;
        this.cacheType = cacheType;
        if (url == null) {
            throw new IllegalArgumentException("url can not be null.");
        }

        initBuilder();
    }


    /**
     * 初始化一些基本参数 url , tag , headers
     */
    private void initBuilder() {
        builder.url(url).tag(tag);
        appendHeaders();
    }

    /**
     *
     * @return
     */
    protected abstract RequestBody buildRequestBody();

    /**
     * 给子类提供包装RequestBody 的方法
     * @param requestBody
     * @param callback
     * @return
     */
    protected RequestBody wrapRequestBody(RequestBody requestBody, final AbstractFOkhttpHandler callback)
    {
        return requestBody;
    }

    protected abstract Request buildRequest(RequestBody requestBody);

    public RequestCall build() {
        return new RequestCall(this);
    }

    /**
     * 生成 Request
     * @param callback
     * @return
     */
    public Request generateRequest(AbstractFOkhttpHandler callback) {
        RequestBody requestBody = buildRequestBody();
        RequestBody wrappedRequestBody = wrapRequestBody(requestBody, callback);
        Request request = buildRequest(wrappedRequestBody);
        return request;
    }

    /**
     * 添加Headers
     */
    protected void appendHeaders() {
        Headers.Builder headerBuilder = new Headers.Builder();
        if (headers == null || headers.isEmpty()) return;

        for (String key : headers.keySet()) {
            headerBuilder.add(key, headers.get(key));
        }
        builder.headers(headerBuilder.build());
    }

    public int getId() {
        return id;
    }

}
