package fengyu.cn.library.net.okhttp;

import android.util.Log;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import fengyu.cn.library.net.okhttp.request.ResponseFormatType;
import okhttp3.Response;

/**
 * Created by zhy on 15/12/15.
 */
public class FileFOkhttpHandler extends AbstractFOkhttpHandler {
    /**
     * cookie过期
     */
    private static final int COOKIE_EXPIRE = -4;
    /**
     * 请求拒绝
     */
    private static final int REQUEST_DENY = -5;


    /**
     * 目标文件存储的文件夹路径
     */
    private String destFileDir;
    /**
     * 目标文件存储的文件名
     */
    private String destFileName;


    public FileFOkhttpHandler(String destFileDir, String destFileName) {
        this.destFileDir = destFileDir;
        this.destFileName = destFileName;
        setResponseFormatType(ResponseFormatType.FILE_RESPONSE);
    }


    @Override
    protected void syncOnLoading() {

    }

    @Override
    protected void syncOnFailure(Result result) {

    }

    @Override
    protected void asyncOnSuccess(Result result) {

        //cookie 过期
        if (result.getCode() == COOKIE_EXPIRE) {
            onCookieExpired();

        } else if (result.getCode() == REQUEST_DENY) {
            //请求被拒绝
            onRequestDeny(result.getMessage());
        } else {
            if (result.getData() != null) {
                if (result.getData() instanceof Response) {
                    try {
                        int id = 0;
                        if (result.getTag() instanceof Integer) {
                            id = (int) result.getTag();
                        }
                        saveFile((Response) result.getData(), id);
                    } catch (IOException e) {
                        Log.e(TAG, "saveFile with exception  : ", e);

                    }
                } else {
                    Log.e(TAG, "saveFile with exception  :  result  type  is not response ");
                }
            }
        }

    }

    @Override
    protected void asyncOnFinish(Result result) {
        Log.w(TAG, "asyncOnFinish (Result result) was not overriden, but callback was received");
    }


    @Override
    protected void asyncOnFailure(Result result) {
        Log.w(TAG, "asyncOnFailure (Result result) was not overriden, but callback was received");
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

    @Override
    protected void syncOnSuccess(Result result) {

        Log.w(TAG, "syncOnSuccess (Result result) was not overriden, but callback was received");
    }

    @Override
    protected void syncOnFinish(Result result) {

    }

    @Override
    protected void asyncInProgress(float progress, long total, Object id) {

    }

    @Override
    protected void syncInProgress(float progress, long total, Object id) {

    }

    public File saveFile(Response response, final int id) throws IOException {
        InputStream is = null;
        byte[] buf = new byte[2048];
        int len = 0;
        FileOutputStream fos = null;
        try {
            is = response.body().byteStream();
            final long total = response.body().contentLength();

            long sum = 0;

            File dir = new File(destFileDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File file = new File(dir, destFileName);
            if( !file.exists()) {
                file.createNewFile();
            }
            fos = new FileOutputStream(file);
            while ((len = is.read(buf)) != -1) {
                sum += len;
                fos.write(buf, 0, len);
                final long finalSum = sum;


                abstractFOkhttpHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if ((finalSum * 1.0f / total) > 0.5 && (finalSum * 1.0f / total) < 0.6) {
                            String a = "";
                        }
                        syncInProgress(finalSum * 1.0f / total, total, id);
                    }
                }, 1000);

            }
            fos.flush();

            return file;

        } finally {
            try {
                if (response.body() != null) {
                    response.body().close();
                }
                if (is != null) is.close();
            } catch (IOException e) {
            }
            try {
                if (fos != null) fos.close();
            } catch (IOException e) {
            }

        }
    }


}
