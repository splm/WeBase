package me.splm.app.core.component.http;

import android.text.TextUtils;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.HttpEntity;
import me.splm.app.core.component.manager.HttpRequestConfig;


public class RequestServer {
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

    public static final void init(HttpRequestConfig config) {
        if (config == null) {
            throw new NullPointerException("HttpRequestConfig is null");
        }
        mConfig = config;
        client = new AsyncHttpClient(config.isFixWhenException(), config.getPort(), config.getPorts());
        BASE_URL = config.getHostUrl();
        Log.e("************", "init: " + BASE_URL);
    }

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        get(url, params, responseHandler, false);
    }

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler, boolean isDependence) {
        checkInit();
        if (isDependence) {
            client.get(url, params, responseHandler);
        } else {
            client.get(getAbsoluteUrl(url), params, responseHandler);
        }
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        post(url, params, responseHandler, false);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler, boolean isDependence) {
        checkInit();
        if (isDependence) {
            client.get(url, params, responseHandler);
        } else {
            client.post(getAbsoluteUrl(url), params, responseHandler);
        }
    }

    public static void post2(String url, HttpEntity entity, AsyncHttpResponseHandler responseHandler) {
        post2(url, entity, responseHandler, false);
    }

    public static void post2(String url, HttpEntity entity, AsyncHttpResponseHandler responseHandler, boolean isDependence) {
        checkInit();
        if (isDependence) {
            client.post(null, url, entity, "application/json", responseHandler);
        } else {
            client.post(null, getAbsoluteUrl(url), entity, "application/json", responseHandler);
        }
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }
}
