package me.splm.app.inject.processor.code;


import java.util.LinkedHashMap;
import java.util.Map;

public class WeMethodMarker {
    private Map<WeMethod,WeVar[]> methodMap=new LinkedHashMap<>();

    public void put(WeMethod weMethod,WeVar[] weVars){
        methodMap.put(weMethod,weVars);
    }

    public Map<WeMethod,WeVar[]> getAll(){
        return this.methodMap;
    }

}
