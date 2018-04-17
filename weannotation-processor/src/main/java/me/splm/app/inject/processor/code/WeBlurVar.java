package me.splm.app.inject.processor.code;


import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.TypeName;

public abstract class WeBlurVar<T extends TypeName> {
    protected abstract String constructInitValue(TypeName limit, String fieldName, String...data);

    private WeVarValue initiate(TypeName limit, String fieldName, String[] data){
        String exp=constructInitValue(limit, fieldName, data);
        return encompass(fieldName,exp,limit);
    }

    public WeVar attachVar(TypeName limit, String fieldName, String...data){
        WeVarValue weVarValue=initiate(limit, fieldName, data);
        TypeName t=weVarValue.getTypeName();
        String f=weVarValue.getFieldName();
        CodeBlock c=weVarValue.getValueCode();
        WeVar weVar=new WeVar(t,f);
        weVar.initializer(c);
        return weVar;
    }

    protected abstract T feedbackType(TypeName limit);

    protected WeVarValue encompass(String fieldName,String exp,TypeName limit){
        T t=feedbackType(limit);
        CodeBlock codeBlock=CodeBlock.builder().add(exp).build();
        return new WeVarValue(fieldName,t,codeBlock);
    }
}
