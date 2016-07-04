package fengyu.cn.library.net.okhttp;


import android.util.Log;

import fengyu.cn.library.net.okhttp.request.ResponseFormatType;
import okhttp3.Headers;


/**
 * Created by fys on 2016/6/16.
 */
public class JsonFOkhttpHandler extends AbstractFOkhttpHandler {

    /**
     * cookie过期
     */
    private static final int COOKIE_EXPIRE = -4;
    /**
     * 请求拒绝
     */
    private static final int REQUEST_DENY = -5;


    public JsonFOkhttpHandler() {

    }

    public JsonFOkhttpHandler(@ResponseFormatType int responseFormatType) {

        this.responseFormatType = responseFormatType;

    }

    /**
     * call when request been deny
     *
     * @param ErrorMessage
     */
    public void onRequestDeny(String ErrorMessage) {


    }

    /**
     * call when cookie expired
     */
    public void onCookieExpired() {

    }

    /**
     * call when request loading
     */
    public void onRequestLoading() {


    }

    /**
     * call when request failed
     *
     * @param statusCode    http response status line
     * @param headers       response headers if any
     * @param throwable     throwable describing the way request failed
     * @param errorResponse parsed response if any
     */
    public void onFailure(int statusCode, Headers headers, Throwable throwable, String errorResponse) {

    }


    /**
     * call when request succeeds
     *
     * @param statusCode http response status line
     * @param headers    response headers if any
     */
    public void onSuccess(int statusCode, Headers headers, Object bean) {

    }

    /**
     * call when request succeeds
     *
     * @param statusCode http response status line
     * @param headers    response headers if any
     */
    public void onJsonStringSuccess(int statusCode, Headers headers, String responseJsonString) {

    }

    public void onFinish(int statusCode, Headers headers) {

    }

    @Override
    protected void syncOnLoading() {
        onRequestLoading();
    }

    @Override
    protected void syncOnFailure(Result result) {

        onFailure(result.getNetWorkStatusCode(), result.getHeaders(), result.getThrowable(), result.getMessage());
    }

    @Override
    protected void asyncOnSuccess(Result result) {
        Log.w(TAG, "asyncOnSuccess (Result result) was not overriden, but callback was received");
    }

    @Override
    protected void asyncOnFinish(Result result) {
        Log.w(TAG, "asyncOnFinish (Result result) was not overriden, but callback was received");
    }


    @Override
    protected void asyncOnFailure(Result result) {
        Log.w(TAG, "asyncOnFailure (Result result) was not overriden, but callback was received");
    }

    @Override
    protected void syncOnSuccess(Result result) {

        //cookie 过期
        if (result.getCode() == COOKIE_EXPIRE) {
            onCookieExpired();

        } else if (result.getCode() == REQUEST_DENY) {
            //请求被拒绝
            onRequestDeny(result.getMessage());
        } else {
            //data 为标准类型 已被转换成对应Bean
            if (result.getDataType() == Result.STAND) {
                onSuccess(result.getNetWorkStatusCode(), result.getHeaders(), result.getData());
            } else if (result.getDataType() == Result.OTHER) {
                //data 未被转换成Bean
                onJsonStringSuccess(result.getNetWorkStatusCode(), result.getHeaders(), result.getData().toString());
            }
        }
    }

    @Override
    protected void syncOnFinish(Result result) {
        onFinish(result.getNetWorkStatusCode(), result.getHeaders());
    }

    @Override
    protected void asyncInProgress(float progress, long total, Object id) {
        Log.w(TAG, "asyncInProgress (float progress, long total, Object id) was not overriden, but callback was received");
    }

    @Override
    protected void syncInProgress(float progress, long total, Object id) {
        Log.w(TAG, "syncInProgress (float progress, long total, Object id) was not overriden, but callback was received");
    }
}
