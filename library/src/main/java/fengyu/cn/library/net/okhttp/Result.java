package fengyu.cn.library.net.okhttp;


import okhttp3.Headers;

/**
 * 网络请求返回结果类
 * Created by fys on 2016/6/15.
 */
public class Result {
    /**
     * 标准类型
     */
    public static final int STAND = 0;
    /**
     * 非标准类型
     */
    public static final int OTHER = 1;

    /**
     * 错误标识<br/>
     * <b>Called when the request could not be executed due to cancellation, a connectivity problem or timeout. Because networks can fail during an exchange
     */
    public static final int FAILURE_BEFORE_ACCEPTED = 1;
    /**
     * 错误标识<br/>
     * <b>服务器返回错误
     */
    public static final int FAILURE_RESPONSE = 0;


    public static final String ERROR_MESSAGE = "网络连接错误请稍候重试。";
    /**
     * 消息
     */
    private String message;
    /**
     * code 标识
     */
    private int code;
    /**
     * 当前进度
     */
    private float progress;
    /**
     * 全都进度
     */
    private long total;
    /**
     * 返回数据
     */
    private Object data;
    /**
     * 类型标识
     */
    private int dataType;
    /**
     * 网络StatusCode
     */
    private int netWorkStatusCode;
    /**
     * headers
     */
    private Headers headers;

    /**
     * 请求错误信息
     */
    private Throwable throwable;
    /**
     * 错误标识代码
     */
    private int errorCode;
    /**
     * 请求标识
     */
    private Object tag;

    public Object getTag() {
        return tag;
    }

    public void setTag(Object tag) {
        this.tag = tag;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        this.progress = progress;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    public Headers getHeaders() {
        return headers;
    }

    public void setHeaders(Headers headers) {
        this.headers = headers;
    }

    public int getNetWorkStatusCode() {
        return netWorkStatusCode;
    }

    public void setNetWorkStatusCode(int netWorkStatusCode) {
        this.netWorkStatusCode = netWorkStatusCode;
    }

    /**
     * 请求标识
     *
     * @return
     */
    private String requestTag;

    public String getRequestTag() {
        return requestTag;
    }

    public void setRequestTag(String requestTag) {
        this.requestTag = requestTag;
    }

    public int getDataType() {
        return dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
