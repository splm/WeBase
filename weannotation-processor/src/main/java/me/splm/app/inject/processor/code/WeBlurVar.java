package me.splm.app.inject.processor.code;


import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.TypeName;

import me.splm.app.inject.processor.log.Logger;
import me.splm.app.inject.processor.log.LoggerFactory;

public abstract class WeBlurVar<T extends TypeName> {

    protected static final Logger LOGGER= LoggerFactory.getLogger(WeBlurVar.class);

    public static final String SHORT="Short";
    public static final String INT="Integer";
    public static final String FLOAT="Float";
    public static final String LONG="Long";
    public static final String DOUBLE="Double";
    public static final String BOOLEAN="Boolean";
    public static final String BYTE="Byte";

    protected abstract String constructInitValue(TypeName limit, String fieldName, String...data);

    private WeVarValue initiate(TypeName limit, String fieldName, String[] data){
        String exp=constructInitValue(limit, fieldName, data);
        return encompass(fieldName,exp,limit);
    }

    public WeVar attachVar(TypeName limit, String fieldName, String...data){
        return attachVar(WeMod.PRIVATE,limit,fieldName,data);
    }

    public WeVar attachVar(int mod,TypeName limit, String fieldName, String...data){
        WeVarValue weVarValue=initiate(limit, fieldName, data);
        TypeName t=weVarValue.getTypeName();
        String f=weVarValue.getFieldName();
        CodeBlock c=weVarValue.getValueCode();
        WeVar weVar=new WeVar(mod,t,f);
        weVar.initializer(c);
        return weVar;
    }

    public T feedbackType(TypeName limit){
        return (T)limit;
    }

    protected WeVarValue encompass(String fieldName,String exp,TypeName limit){
        T t=feedbackType(limit);
        CodeBlock codeBlock=CodeBlock.builder().add(exp).build();
        return new WeVarValue(fieldName,t,codeBlock);
    }
}
