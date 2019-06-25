package com.jc.android.baselib.manager;


public class ConfigFilesManager extends AbsManager implements IManagerMarker {

    public SharePreferenceConfig getSharePreferenceConfig() {
        return (SharePreferenceConfig) this.findManagerByTag(SharePreferenceConfig.class);
    }

    public HttpRequestConfig getHttpRequestConfig() {
        return (HttpRequestConfig) this.findManagerByTag(HttpRequestConfig.class);
    }
}
