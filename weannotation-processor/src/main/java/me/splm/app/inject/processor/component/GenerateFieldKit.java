package me.splm.app.inject.processor.component;


import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;

import me.splm.app.inject.processor.code.WeArrayVar;
import me.splm.app.inject.processor.code.WeBlurVar;
import me.splm.app.inject.processor.code.WeMod;
import me.splm.app.inject.processor.code.WeNumericVar;
import me.splm.app.inject.processor.code.WeStringVar;
import me.splm.app.inject.processor.code.WeVar;
import me.splm.app.inject.processor.component.Utils.TransferCenter;
import me.splm.app.inject.processor.log.Logger;
import me.splm.app.inject.processor.log.LoggerFactory;

/**
 * To declare some types of Fields exploit this class
 */
public class GenerateFieldKit {

    protected static final Logger LOGGER= LoggerFactory.getLogger(GenerateFieldKit.class);

    public static WeVar announceArray(int mod,TypeName limit, String fieldName, String[] data){
        WeBlurVar arrayVar=new WeArrayVar();
        return arrayVar.attachVar(mod,limit, fieldName, data);
    }

    public static WeVar announceArray(int mod,Class<?> typeClass, String fieldName, String[] data){
        TypeName typeName=TransferCenter.class2TypeName(typeClass);
        return announceArray(mod,typeName,fieldName,data);
    }

    public static WeVar announceString(int mod,String fieldName,String data){
        WeBlurVar stringVar=new WeStringVar();
        TypeName typename=ClassName.get("java.lang","String");
        return stringVar.attachVar(mod,typename,fieldName,data);
    }


    public static WeVar announceString(String fieldName,String data){
        return announceString(WeMod.PRIVATE,fieldName,data);
    }

    public static WeVar announceNumber(String type,String fieldName,String data){
        return announceNumber(WeMod.PRIVATE,type,fieldName,data);
    }

    public static WeVar announceNumber(int mod,String type,String fieldName,String data){
        WeBlurVar weNumbericVar=new WeNumericVar();
        TypeName typeName=ClassName.get("java.lang",type);
        return weNumbericVar.attachVar(mod,typeName,fieldName,data);
    }

}
