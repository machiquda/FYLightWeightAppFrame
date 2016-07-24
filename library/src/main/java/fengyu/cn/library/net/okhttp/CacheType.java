package fengyu.cn.library.net.okhttp;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by fys on 2016/6/10.
 */
@Retention(RetentionPolicy.SOURCE)
@IntDef({CacheType.ONLY_NETWORK, CacheType.ONLY_CACHED, CacheType.CACHED_ELSE_NETWORK, CacheType.NETWORK_ELSE_CACHED})
public @interface CacheType {
    int ONLY_NETWORK = 0;
    int ONLY_CACHED = 1;
    int CACHED_ELSE_NETWORK = 2;
    int NETWORK_ELSE_CACHED = 3;


}
