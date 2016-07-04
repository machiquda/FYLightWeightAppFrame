package fengyu.cn.library.net.okhttp.builder;


import java.util.LinkedHashMap;
import java.util.Map;

import fengyu.cn.library.net.okhttp.CacheType;
import fengyu.cn.library.net.okhttp.request.RequestCall;

/**
 * 请求构造器 基类
 * Created by fys on 2016/6/27.
 */
public abstract class OkHttpRequestBuilder<T extends OkHttpRequestBuilder> {
    /**
     *请求URL
     */
    protected String url;
    /**
     * 标识
     */
    protected Object tag;
    /**
     * 请求头部
     */
    protected Map<String, String> headers;
    /**
     *参数
     */
    protected Map<String, String> params;
    /**
     * 缓存策略
     */
    protected
    @CacheType
    int cacheType = CacheType.NETWORK_ELSE_CACHED;
    /**
     * json 解析目标类
     */
    protected Class<?> aClass;
    /**
     * 标识
     */
    protected int id;



    public T parseClass(Class<?> aClass) {
        this.aClass = aClass;
        return (T) this;
    }

    public T cacheType(@CacheType int cacheType) {
        this.cacheType = cacheType;
        return (T) this;
    }

    public T id(int id) {
        this.id = id;
        return (T) this;
    }

    public T url(String url) {
        this.url = url;
        return (T) this;
    }


    public T tag(Object tag) {
        this.tag = tag;
        return (T) this;
    }

    public T headers(Map<String, String> headers) {
        this.headers = headers;
        return (T) this;
    }

    public T addHeader(String key, String val) {
        if (this.headers == null) {
            headers = new LinkedHashMap<>();
        }
        headers.put(key, val);
        return (T) this;
    }

    public abstract RequestCall build();
}
