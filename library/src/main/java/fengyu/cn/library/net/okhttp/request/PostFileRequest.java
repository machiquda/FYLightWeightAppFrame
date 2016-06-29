package fengyu.cn.library.net.okhttp.request;


import java.io.File;
import java.util.Map;
import java.util.concurrent.Executors;

import fengyu.cn.library.net.okhttp.AbstractFOkhttpHandler;
import fengyu.cn.library.net.okhttp.CacheType;
import fengyu.cn.library.net.okhttp.FOkhttpClient;
import fengyu.cn.library.net.okhttp.Result;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by fys on 2016/6/27.
 */
public class PostFileRequest extends OkHttpRequest {
    private static MediaType MEDIA_TYPE_STREAM = MediaType.parse("application/octet-stream");

    private File file;
    private MediaType mediaType;

    public PostFileRequest(String url, Object tag, Map<String, String> params, Map<String, String> headers, File file, MediaType mediaType, int id, Class<?> parseClass, @CacheType int cacheType) {
        super(url, tag, params, headers, id, parseClass, cacheType);
        this.file = file;
        this.mediaType = mediaType;

        if (this.file == null) {

            throw new IllegalArgumentException("the file can not be null !");
        }
        if (this.mediaType == null) {
            this.mediaType = MEDIA_TYPE_STREAM;
        }
    }

    @Override
    protected RequestBody buildRequestBody() {
        return RequestBody.create(mediaType, file);
    }

    @Override
    protected RequestBody wrapRequestBody(RequestBody requestBody, final AbstractFOkhttpHandler callback) {
        if (callback == null) return requestBody;
        CountingRequestBody countingRequestBody = new CountingRequestBody(requestBody, new CountingRequestBody.Listener() {
            @Override
            public void onRequestProgress(final long bytesWritten, final long contentLength) {

                Executors.newCachedThreadPool().execute(new Runnable() {
                    @Override
                    public void run() {
                        Result result = new Result();
                        result.setProgress(bytesWritten * 1.0f / contentLength);
                        result.setTotal(contentLength);
                        result.setTag(id);
                        callback.callInProgress(result);
                    }
                });


            }
        });
        return countingRequestBody;
    }

    @Override
    protected Request buildRequest(RequestBody requestBody) {
        return builder.post(requestBody).build();
    }


}
