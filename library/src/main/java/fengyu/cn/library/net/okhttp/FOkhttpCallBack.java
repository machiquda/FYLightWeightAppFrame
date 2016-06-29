package fengyu.cn.library.net.okhttp;

/**
 * okhttp 返回回调
 * Created by fys on 2016/6/10.
 */
public interface FOkhttpCallBack extends okhttp3.Callback {

    /**
     * 开始请求
     *
     * @param tag 标识
     */
    public void onLoading(String tag);

    /**
     * 请求失败
     *
     * @param errorMessage 错误信息
     * @param tag          标识
     */
    public void onFailure(String errorMessage, String tag);

    /**
     * 请求成功
     *
     * @param response bean对象
     * @param tag      标识
     */
    public void onSuccess(Object response, String tag);

    /**
     * 请求成功
     *
     * @param responseString
     * @param tag            标识
     */
    public void onSuccess(String responseString, String tag);

    /**
     * 请求成功 但返回Data为空
     *
     * @param tag 标识
     */
    public void onEmpty(String tag);

    /**
     * 请求成功 但被拒绝
     *
     * @param message 拒绝信息
     */
    public void onDeny(String message);

    /**
     * 请求完成
     *
     * @param tag 标识
     */
    public void onFinish(String tag);
}
