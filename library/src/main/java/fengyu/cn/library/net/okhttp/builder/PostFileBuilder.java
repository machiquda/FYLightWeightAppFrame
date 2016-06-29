package fengyu.cn.library.net.okhttp.builder;


import java.io.File;

import fengyu.cn.library.net.okhttp.request.PostFileRequest;
import fengyu.cn.library.net.okhttp.request.RequestCall;
import okhttp3.MediaType;

/**
 * 文件 上传post构造器
 * Created by fys on 2016/6/27.
 */
public class PostFileBuilder extends OkHttpRequestBuilder<PostFileBuilder> {
    private File file;
    private MediaType mediaType;


    public OkHttpRequestBuilder file(File file) {
        this.file = file;
        return this;
    }

    public OkHttpRequestBuilder mediaType(MediaType mediaType) {
        this.mediaType = mediaType;
        return this;
    }

    @Override
    public RequestCall build() {
        return new PostFileRequest(url, tag, params, headers, file, mediaType, id, aClass, cacheType).build();
    }


}
