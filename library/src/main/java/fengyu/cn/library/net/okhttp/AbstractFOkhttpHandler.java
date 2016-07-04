package fengyu.cn.library.net.okhttp;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import fengyu.cn.library.net.okhttp.request.ResponseFormatType;

/**
 * FOkhttp请求回调抽象基类
 * Created by fys on 2016/6/15.
 */
public abstract class AbstractFOkhttpHandler extends Handler {

    public static final String TAG = "FOkhttpHandler";
    public static final int CALLBACK_SUCCESSFUL = 0x01;
    public static final int CALLBACK_FAILED = 0x02;
    public static final int CALLBACK_ONLOAD = 0x03;
    public static final int CALLBACK_FINISH = 0x04;
    public static final int CAllBACK_INPROGRESS = 0X05;

    private AbstractFOkhttpHandler abstractFOkhttpHandler;

    /**
     * 请求返回数据的格式
     */
    protected
    @ResponseFormatType
    int responseFormatType = ResponseFormatType.JSON_STRING;


    public AbstractFOkhttpHandler() {
        super(Looper.getMainLooper());
        abstractFOkhttpHandler = this;
    }


    @Override
    public void handleMessage(Message msg) {

        Result r = (Result) msg.obj;
        switch (msg.what) {
            case CALLBACK_SUCCESSFUL:
                syncOnSuccess(r);
                syncOnFinish(r);
                break;
            case CALLBACK_FAILED:
                syncOnFailure(r);
                syncOnFinish(r);
                break;
//            case CALLBACK_ONLOAD:
//                syncOnLoading();
            case CALLBACK_FINISH:
                syncOnFinish(r);
                break;
            case CAllBACK_INPROGRESS:
                syncInProgress(r.getProgress(), r.getTotal(), r.getTag());
                break;
        }
    }


    /**
     * 开始请求<br/>
     * <b>在UI线程调用</b>
     */
    protected abstract void syncOnLoading();

    /**
     * 请求失败<br/>
     * <b>在UI线程调用</b>
     *
     * @param result 返回结果
     */
    protected abstract void syncOnFailure(Result result);

    /**
     * 请求成功<br/>
     * <b>在子线程调用</b>
     *
     * @param result 返回结果
     */
    protected abstract void asyncOnSuccess(Result result);

    /**
     * 请求完成<br/>
     * <b>在子线程调用</b>
     *
     * @param result 返回结果
     */
    protected abstract void asyncOnFinish(Result result);


    /**
     * 请求失败<br/>
     * <b>在子线程调用</b>
     *
     * @param result 返回结果
     */
    protected abstract void asyncOnFailure(Result result);

    /**
     * 请求成功<br/>
     * <b>在UI线程调用</b>
     *
     * @param result 返回结果
     */
    protected abstract void syncOnSuccess(Result result);

    /**
     * 请求完成<br/>
     * <b>在UI线程调用</b>
     *
     * @param result 返回结果
     */
    protected abstract void syncOnFinish(Result result);

    /**
     * 请求进度<br/>
     * <b>在子线程调用</b>
     *
     * @param progress 当前
     * @param total    总共
     * @param id       标识
     */
    protected abstract void asyncInProgress(float progress, long total, Object id);

    /**
     * 请求进度<br/>
     * <b>在UI线程调用</b>
     *
     * @param progress 当前
     * @param total    总共
     * @param id       标识
     */
    protected abstract void syncInProgress(float progress, long total, Object id);

    public void callOnLoading() {

        syncOnLoading();

    }


    public void callOnSuccess(Result result) throws IllegalArgumentException {

        if (result == null) {
            throw new IllegalArgumentException("AbstractFOkhttpHandler  callOnSuccess  but result is null");
        }
        asyncOnSuccess(result);
        if (abstractFOkhttpHandler != null && abstractFOkhttpHandler.getLooper() == Looper.getMainLooper()) {
            Message message = Message.obtain();
            message.what = CALLBACK_SUCCESSFUL;
            message.obj = result;
            abstractFOkhttpHandler.sendMessage(message);
        }


    }

    public void callOnFailure(Result result) throws IllegalArgumentException {
        if (result == null) {
            throw new IllegalArgumentException("AbstractFOkhttpHandler  callOnFailure  but result is null");
        }
        asyncOnFailure(result);
        if (abstractFOkhttpHandler != null && abstractFOkhttpHandler.getLooper() == Looper.getMainLooper()) {
            Message message = Message.obtain();
            message.what = CALLBACK_FAILED;
            message.obj = result;
            abstractFOkhttpHandler.sendMessage(message);
        }

    }

    public void callInProgress(Result result) {
        if (result == null) {
            throw new IllegalArgumentException("AbstractFOkhttpHandler  callOnFailure  but result is null");
        }
        asyncInProgress(result.getProgress(), result.getTotal(), result.getTag());
        if (abstractFOkhttpHandler != null && abstractFOkhttpHandler.getLooper() == Looper.getMainLooper()) {
            Message message = Message.obtain();
            message.what = CAllBACK_INPROGRESS;
            message.obj = result;
            abstractFOkhttpHandler.sendMessage(message);
        }
    }

//    public void callOnFinish(Result result) throws IllegalArgumentException {
//        if (result == null) {
//            throw new IllegalArgumentException("AbstractFOkhttpHandler  callOnFinish  but result is null");
//        }
//        asyncOnFinish(result);
//        if (abstractFOkhttpHandler != null && abstractFOkhttpHandler.getLooper() == Looper.getMainLooper()) {
//            Message message = Message.obtain();
//            message.what = CALLBACK_FINISH;
//            message.obj = result;
//            abstractFOkhttpHandler.sendMessage(message);
//        }
//
//    }


    public int getResponseFormatType() {
        return responseFormatType;
    }

    public void setResponseFormatType(int responseFormatType) {
        this.responseFormatType = responseFormatType;
    }
}
