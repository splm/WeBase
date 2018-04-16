package me.splm.app.inject.processor.code;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.TypeName;

/**
 * Created by Administrator on 2018/3/13 0013.
 */

public class WeVarValue {
    private String fieldName;
    private TypeName typeName;
    private CodeBlock valueCode;

    public WeVarValue(String fieldName, TypeName typeName, CodeBlock valueCode) {
        this.fieldName = fieldName;
        this.typeName = typeName;
        this.valueCode = valueCode;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public TypeName getTypeName() {
        return typeName;
    }

    public void setTypeName(TypeName typeName) {
        this.typeName = typeName;
    }

    public CodeBlock getValueCode() {
        return valueCode;
    }

    public void setValueCode(CodeBlock valueCode) {
        this.valueCode = valueCode;
    }
}
