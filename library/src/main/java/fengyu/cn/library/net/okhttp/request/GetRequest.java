package fengyu.cn.library.net.okhttp.request;

import java.util.Map;

import fengyu.cn.library.net.okhttp.CacheType;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * GET  请求
 * Created by fys on 2016/6/27.
 */
public class GetRequest extends OkHttpRequest
{
    public GetRequest(String url, Object tag, Map<String, String> params, Map<String, String> headers, int id, Class<?> parseClass, @CacheType int cacheType)
    {
        super(url, tag, params, headers,id,parseClass,cacheType);
    }

    @Override
    protected RequestBody buildRequestBody()
    {
        return null;
    }

    @Override
    protected Request buildRequest(RequestBody requestBody)
    {
        return builder.get().build();
    }


}
