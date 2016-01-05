package fengyu.cn.library.net;

/**
 * URL ʵ��
 */
public class URLData {
    /**
     * url key  ƥ��ؼ���
     */
    private String key;
    /**
     * ����ʱ��
     */
    private long expires;
    /**
     * �������
     */
    private String netType;
    /**
     * ����URL
     */
    private String url;
    /**
     * ���ݷ��ض�Ӧʵ��
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
