package me.splm.app.inject.processor.code;

import com.squareup.javapoet.TypeName;

import me.splm.app.inject.processor.component.Utils.TextUtils;


public class WeStringVar extends WeBlurVar<TypeName> {
    @Override
    protected String constructInitValue(TypeName limit, String fieldName, String... data) {
        if(TextUtils.isEmpty(data)){
            return "";
        }
        return "\"" + data[0] + "\"";
    }
}
