package fengyu.cn.library.net.okhttp.request;

import android.support.annotation.IntDef;

/**
 * Created by fys on 2016/6/28.
 */
@IntDef({ResponseFormatType.STAND, ResponseFormatType.FILE_RESPONSE, ResponseFormatType.STRING})
public @interface ResponseFormatType {
    int STAND = 0;
    int FILE_RESPONSE = 1;
    int STRING = 2;

}
