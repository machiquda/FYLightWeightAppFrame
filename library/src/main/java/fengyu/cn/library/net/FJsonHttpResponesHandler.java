package fengyu.cn.library.net;



import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import fengyu.cn.library.net.com.loopj.android.http.JsonHttpResponseHandler;


/**
 * Created by fys on 2015/11/26.
 */
public class FJsonHttpResponesHandler extends JsonHttpResponseHandler {


    /**
     * ����ʱURL ��Ϣ
     */
    private   URLData urlData ;

    /**
     * ��������ʱ��������Url
     */
    private String finalRequestUrl;

    public String getFinalRequestUrl() {
        return finalRequestUrl;
    }

    public void setFinalRequestUrl(String finalRequestUrl) {
        this.finalRequestUrl = finalRequestUrl;
    }

    public URLData getUrlData() {
        return urlData;
    }

    public void setUrlData(URLData urlData) {
        this.urlData = urlData;
    }

    /**
     * Return when request been deny
     *
     * @param ErrorMessage
     */
    public void onRequestDeny(String ErrorMessage) {

    }

    /**
     * Return when cookie expired
     */
    public void onCookieExpired() {
    }

    /**
     * Returns when request succeeds
     *
     * @param statusCode http response status line
     * @param headers    response headers if any
     * @param response   parsed response if any
     */
    public void onJsonArraySuccess(int statusCode, Header[] headers, JSONArray response) {

    }

    /**
     * Returns when request succeeds
     *
     * @param statusCode http response status line
     * @param headers    response headers if any
     * @param response   parsed response if any
     */
    public void onJsonObjectSuccess(int statusCode, Header[] headers, JSONObject response) {

    }

    /**
     * ��������������JSONObject
     *
     * @param statusCode
     * @param headers
     * @param response   ��JSONObject
     */

    @Override
    public void onSuccess(int statusCode, Header[] headers, final JSONObject response) {

        try {

            if (response.getInt("code") == ResponseStatusCode.STATE_COOKIE_EXPIRED) {
                onCookieExpired();
            } else if (response.getInt("code") == ResponseStatusCode.STATE_OK) {
                if(urlData != null && finalRequestUrl != null) {
                    CacheManager.getInstance().putFileCache(finalRequestUrl, response.toString(), urlData.getExpires());
                }
                onJsonObjectSuccess(statusCode, headers,
                        response);
            } else {
                onRequestDeny(response.getString("message"));
            }


        } catch (Exception e) {
        }
    }


    @Override
    public void onSuccess(int statusCode, Header[] headers, org.json.JSONArray response) {

    }
}