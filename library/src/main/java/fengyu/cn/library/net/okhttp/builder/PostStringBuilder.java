package fengyu.cn.library.net.okhttp.builder;


import fengyu.cn.library.net.okhttp.request.PostStringRequest;
import fengyu.cn.library.net.okhttp.request.RequestCall;
import okhttp3.MediaType;

/**
 * Created by fys on 2016/6/27.
 */
public class PostStringBuilder extends OkHttpRequestBuilder<PostStringBuilder> {
    private String content;
    private MediaType mediaType;


    public PostStringBuilder content(String content) {
        this.content = content;
        return this;
    }

    public PostStringBuilder mediaType(MediaType mediaType) {
        this.mediaType = mediaType;
        return this;
    }

    @Override
    public RequestCall build() {
        return new PostStringRequest(url, tag, params, headers, content, mediaType, id, aClass, cacheType).build();
    }


}
