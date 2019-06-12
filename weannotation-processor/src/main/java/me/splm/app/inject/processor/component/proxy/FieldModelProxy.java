package me.splm.app.inject.processor.component.proxy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.splm.app.inject.processor.component.Utils.TextUtils;
import me.splm.app.inject.processor.component.processor.porter.PorterFieldModel;

public class FieldModelProxy {
    private Map<String, List<PorterFieldModel>> fieldLegend=new HashMap<>();
    private static volatile FieldModelProxy mInstance;
    private FieldModelProxy(){}

    public static FieldModelProxy getInstance(){
        if(mInstance==null){
            synchronized (FieldModelProxy.class){
                if(mInstance==null){
                    return new FieldModelProxy();
                }
            }
        }
        return mInstance;
    }

    public void put(String tag,PorterFieldModel model){
        if(fieldLegend.containsKey(tag)){
            List<PorterFieldModel> list=fieldLegend.get(tag);
            if(list!=null){
                list.add(model);
            }
            fieldLegend.replace(tag, list);
        }else{
            List<PorterFieldModel> list=new ArrayList<>();
            list.add(model);
            fieldLegend.put(tag,list);
        }
    }

    public void clearAll(){
        fieldLegend.clear();
    }

    public List<PorterFieldModel> getModels(String tag){
        if(TextUtils.isEmpty(tag)){
            return null;
        }
        if(fieldLegend.containsKey(tag)){
            return fieldLegend.get(tag);
        }
        return null;
    }
}
