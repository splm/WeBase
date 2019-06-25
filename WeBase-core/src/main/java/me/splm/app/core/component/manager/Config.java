package com.jc.android.baselib.manager;

import java.util.HashMap;
import java.util.Map;


public class Config {
    private Map<String,Object> configMaps=new HashMap<>();
    public void put(String key,Object value){
        configMaps.put(key,value);
    }
    public Object get(String key){
        return configMaps.get(key);
    }
}
