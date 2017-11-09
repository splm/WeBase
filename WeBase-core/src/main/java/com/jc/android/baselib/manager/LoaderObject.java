package com.jc.android.baselib.manager;


public class LoaderObject<T> {
    private T manager;
    private Config config;

    public LoaderObject(T manager,Config config){
        this.manager=manager;
        this.config=config;
    }

    public T getManager() {
        return manager;
    }

    public Config getConfig() {
        return config;
    }
}
