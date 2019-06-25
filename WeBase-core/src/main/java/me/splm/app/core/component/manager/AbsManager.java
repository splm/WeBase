package com.jc.android.baselib.manager;

import android.text.TextUtils;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public abstract class AbsManager {
    private static final int CAPACITY = 5;
    private Map<String, IManagerMarker> mManagerPool = new HashMap<>(CAPACITY);

    public void registManager(IManagerMarker manager) {
        if (manager != null) {
            String key = exchange(manager);
            boolean isContain = mManagerPool.containsKey(key);
            if (isContain) {
                remove(key);
            }
            put(key, manager);
        }
    }

    public void registConfig(IConfigMarker configMarker) {
        registManager(configMarker);
    }

    protected String exchange(IManagerMarker manager) {
        return exchange(manager.getClass());
    }

    protected String exchange(Class<? extends IManagerMarker> clzz) {
        return clzz.getCanonicalName();
    }

    private void put(String key, IManagerMarker manager) {
        mManagerPool.put(key, manager);
    }

    protected void put(IManagerMarker manager) {
        String key = exchange(manager);
        put(key, manager);
    }

    protected void remove(String key) {
        mManagerPool.remove(key);
    }

    public void unRegistManager(IManagerMarker manager) {
        if (manager != null) {
            mManagerPool.remove(manager.getClass().getCanonicalName());
        }
    }

    public String[] showAllManagerTags() {
        String[] tags = new String[mManagerPool.size()];
        int i = 0;
        for (Map.Entry e : mManagerPool.entrySet()) {
            tags[i] = (String) e.getKey();
            i++;
        }
        return tags;
    }

    public int showAllManagerCount(){
        return mManagerPool.size();
    }

    public IManagerMarker findManagerByTag(Class<? extends IManagerMarker> tag) {
        String key = exchange(tag);
        if (!TextUtils.isEmpty(key)) {
            return this.mManagerPool.get(key);
        }
        return null;
    }

    public void logAllManagerTags() {
        String[] tags = showAllManagerTags();
        for (String tag : tags) {
            Log.e("****************", "testManager: " + tag);
        }
    }

    public IManagerMarker findRecentAddManager() {
        return null;
    }

    public IManagerMarker findFirstManager() {
        return null;
    }

}
