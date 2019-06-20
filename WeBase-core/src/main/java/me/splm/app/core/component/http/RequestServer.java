package me.splm.app.core.component.http;

import android.text.TextUtils;
import android.util.Log;

import com.jc.android.baselib.manager.HttpRequestConfig;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.HttpEntity;


public class RequestServer {
    //TODO BaseUrl需要从Application中读取
    private static String BASE_URL;

    private static AsyncHttpClient client;
    private static HttpRequestConfig mConfig;

    private static void checkInit() {
        if (mConfig == null) {
            client = new AsyncHttpClient(true, 80, 443);
        }
        if (TextUtils.isEmpty(BASE_URL)) {
            throw new NullPointerException("Host url is empty.");
        }
    }

    public static void init(HttpRequestConfig config) {
        if (config == null) {
            throw new NullPointerException("HttpRequestConfig is null");
        }
        mConfig = config;
        client = new AsyncHttpClient(config.isFixWhenException(), config.getPort(), config.getPorts());
        BASE_URL = config.getHostUrl();
        Log.e("************", "init: " + BASE_URL);
    }

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        checkInit();
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        checkInit();
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post2(String url, HttpEntity entity, AsyncHttpResponseHandler responseHandler) {
        checkInit();
        client.post(null, getAbsoluteUrl(url), entity, "application/json", responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }
}
