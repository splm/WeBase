package me.splm.app.core.component.http;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.HttpEntity;


public class RequestServer {
//    private static final String BASE_URL = "http://192.168.200.227:8080";
    //TODO BaseUrl需要从Application中读取
    private static final String BASE_URL = "https://live.gyss365.cn/api";

    private static AsyncHttpClient client = new AsyncHttpClient(true,80,443);

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post2(String url, HttpEntity entity, AsyncHttpResponseHandler responseHandler){
        client.post(null,getAbsoluteUrl(url), entity, "application/json",responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }
}
