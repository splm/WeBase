package me.splm.app.inject.processor.component;


import com.squareup.javapoet.TypeName;

import me.splm.app.inject.processor.code.WeArrayVar;
import me.splm.app.inject.processor.code.WeBlurVar;
import me.splm.app.inject.processor.code.WeVar;
import me.splm.app.inject.processor.component.Utils.TransferCenter;
import me.splm.app.inject.processor.log.Logger;
import me.splm.app.inject.processor.log.LoggerFactory;

/**
 * To declare some types of Fields exploit this class
 */
public class GenerateFieldKit {

    protected static final Logger LOGGER= LoggerFactory.getLogger(GenerateFieldKit.class);

    public static WeVar announceArray(TypeName limit, String fieldName, String[] data){
        WeBlurVar arrayVar=new WeArrayVar();
        return arrayVar.attachVar(limit, fieldName, data);
    }

    public static WeVar announceArray(Class<?> typeClass, String fieldName, String[] data){
        TypeName typeName=TransferCenter.class2TypeName(typeClass);
        return announceArray(typeName,fieldName,data);
    }
}
