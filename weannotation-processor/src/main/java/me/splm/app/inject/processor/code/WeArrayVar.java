package me.splm.app.inject.processor.code;


import com.squareup.javapoet.ArrayTypeName;
import com.squareup.javapoet.TypeName;

public class WeArrayVar extends WeBlurVar<ArrayTypeName>{

    @Override
    protected String constructInitValue(TypeName limit, String fieldName, String[] data){
        String exp="";
        if(data.length>0){
            String literal="";
            String limitType=limit.getClass().getCanonicalName().getClass().toString();
            if(String.class.toString().equals(limitType)){
                literal = "\"" + String.join("\",\"",data) + "\"";
            }else{
                literal=String.join(",",data);
            }
            exp="{"+literal+"}";
        }else{
            exp="{}";
        }
        return exp;
    }

    @Override
    protected ArrayTypeName feedbackType(TypeName limit) {
        return ArrayTypeName.of(limit);
    }
}
