package fengyu.cn.library.net.okhttp.request;


import java.io.IOException;
import java.util.concurrent.TimeUnit;

import fengyu.cn.library.net.okhttp.AbstractFOkhttpHandler;
import fengyu.cn.library.net.okhttp.FOkhttpClient;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by fys on 2016/6/27.
 * 在全局配置的基础上提供对外提供更多的接口：cancel(),readTimeOut()...<p/>
 * 每个请求可以单独配置
 */
public class RequestCall {
    private OkHttpRequest okHttpRequest;
    private Request request;
    private Call call;

    private long readTimeOut;
    private long writeTimeOut;
    private long connTimeOut;

    private OkHttpClient clone;

    public RequestCall(OkHttpRequest request) {
        this.okHttpRequest = request;
    }

    public RequestCall readTimeOut(long readTimeOut) {
        this.readTimeOut = readTimeOut;
        return this;
    }

    public RequestCall writeTimeOut(long writeTimeOut) {
        this.writeTimeOut = writeTimeOut;
        return this;
    }

    public RequestCall connTimeOut(long connTimeOut) {
        this.connTimeOut = connTimeOut;
        return this;
    }

    public Call buildCall(AbstractFOkhttpHandler callback) {
        request = generateRequest(callback);

        if (readTimeOut > 0 || writeTimeOut > 0 || connTimeOut > 0) {
            readTimeOut = readTimeOut > 0 ? readTimeOut : FOkhttpClient.DEFAULT_MILLISECONDS;
            writeTimeOut = writeTimeOut > 0 ? writeTimeOut : FOkhttpClient.DEFAULT_MILLISECONDS;
            connTimeOut = connTimeOut > 0 ? connTimeOut : FOkhttpClient.DEFAULT_MILLISECONDS;

            clone = FOkhttpClient.getInstance().getClient().newBuilder()
                    .readTimeout(readTimeOut, TimeUnit.MILLISECONDS)
                    .writeTimeout(writeTimeOut, TimeUnit.MILLISECONDS)
                    .connectTimeout(connTimeOut, TimeUnit.MILLISECONDS)
                    .build();

            call = clone.newCall(request);
        } else {
            call = FOkhttpClient.getInstance().getClient().newCall(request);
        }
        return call;
    }

    private Request generateRequest(AbstractFOkhttpHandler callback) {
        return okHttpRequest.generateRequest(callback);
    }

    /**
     * 执行请求
     *
     * @param callback
     */
    public void execute(AbstractFOkhttpHandler callback) {
        buildCall(callback);
        FOkhttpClient.getInstance().executeCall(this, okHttpRequest.cacheType, callback, okHttpRequest.parseClass, okHttpRequest.tag);
    }

    public Call getCall() {
        return call;
    }

    public Request getRequest() {
        return request;
    }

    public OkHttpRequest getOkHttpRequest() {
        return okHttpRequest;
    }

    public Response execute() throws IOException {
        buildCall(null);
        return call.execute();
    }

    public void cancel() {
        if (call != null) {
            call.cancel();
        }
    }


}
