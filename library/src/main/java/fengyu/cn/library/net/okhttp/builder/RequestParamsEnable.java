package fengyu.cn.library.net.okhttp.builder;

import java.util.Map;

/**
 * 添加参数的接口 需要添加can
 * Created by fys on 2016/6/27.
 */
public interface RequestParamsEnable {

    OkHttpRequestBuilder params(Map<String, String> params);

    OkHttpRequestBuilder addParams(String key, String val);
}
