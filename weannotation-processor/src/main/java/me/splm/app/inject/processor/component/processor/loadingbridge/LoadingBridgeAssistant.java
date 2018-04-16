package me.splm.app.inject.processor.component.processor.loadingbridge;


import com.squareup.javapoet.TypeName;

import me.splm.app.inject.processor.code.WeVar;
import me.splm.app.inject.processor.component.GenerateFieldKit;

public class LoadingBridgeAssistant {

    public static WeVar declareArray(TypeName limit, String fieldName, String[] data){
        return GenerateFieldKit.announceArray(limit, fieldName, data);
    }

    public static WeVar declareArray(Class<?> typeClass,String fieldName,String[] data){
        return GenerateFieldKit.announceArray(typeClass, fieldName, data);
    }

}
