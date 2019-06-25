package com.jc.android.baselib.manager;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

import me.splm.app.core.component.tool.SPUtils;
import me.splm.app.core.component.tool.UtilsFactory;


public class SharePreferenceConfig implements IConfigMarker {

    private static final Map<String, Object> mConfigMap = new HashMap<>(10);

    private SPUtils mUtils;

    private SharePreferenceConfig(Builder builder) {
        inject(builder.context, builder.fileName);
    }

    private void inject(Context context, String fileName) {
        mUtils = UtilsFactory.getInstance().getCommUtils(UtilsFactory.UtilsMarker.SharePreference);
        mUtils.init(context, fileName);
        for (Map.Entry<String, Object> entry : mConfigMap.entrySet()) {
            String k = entry.getKey();
            Object v = entry.getValue();
            mUtils.put(k, v);
        }
    }

    public static class Builder {
        String fileName;
        String key;
        Object value;
        Context context;

        public Builder init(Context context, String fileName) {
            this.context = context;
            this.fileName = fileName;
            return this;
        }

        public Builder join(String key, Object value) {
            this.key = key;
            this.value = value;
            mConfigMap.put(key, value);
            return this;
        }

        public SharePreferenceConfig build() {
            return new SharePreferenceConfig(this);
        }
    }

    public <E> E getValue(String key, @NonNull E defaultValue) {
        return mUtils.get(key, defaultValue);
    }

    public <E> void putValueWhenNew(String key, @NonNull E defaultValue) {
        boolean isExist = mUtils.isContains(key);
        if (!isExist) {
            putValue(key, defaultValue);
        }
    }

    public <E> void putValue(String key, @NonNull E defaultValue) {
        mUtils.put(key, defaultValue);
    }
}
