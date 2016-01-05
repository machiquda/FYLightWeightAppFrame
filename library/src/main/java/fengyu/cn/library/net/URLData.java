package fengyu.cn.library.net;

/**
 * URL 实体
 */
public class URLData {
    /**
     * url key  匹配关键字
     */
    private String key;
    /**
     * 过期时长
     */
    private long expires;
    /**
     * 请求类别
     */
    private String netType;
    /**
     * 请求URL
     */
    private String url;
    /**
     * 数据返回对应实体
     */
    private String mockClass;

    public URLData() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public long getExpires() {
        return expires;
    }

    public void setExpires(long expires) {
        this.expires = expires;
    }

    public String getNetType() {
        return netType;
    }

    public void setNetType(String netType) {
        this.netType = netType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMockClass() {
        return mockClass;
    }

    public void setMockClass(String mockClass) {
        this.mockClass = mockClass;
    }
}
