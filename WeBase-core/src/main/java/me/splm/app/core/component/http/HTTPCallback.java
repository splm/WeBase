package me.splm.app.core.component.http;

import org.json.JSONObject;


public interface HTTPCallback {
    void onFailure(int statusCode, Throwable e);
    void onSuccess(JSONObject response);
}
