package fengyu.cn.library.net.okhttp.request;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by fys on 2016/6/28.
 */
@Retention(RetentionPolicy.SOURCE)
@IntDef({ResponseFormatType.JSON_STRING, ResponseFormatType.FILE_RESPONSE, ResponseFormatType.STRING})
public @interface ResponseFormatType {
    int JSON_STRING = 0;
    int FILE_RESPONSE = 1;
    int STRING = 2;

}
