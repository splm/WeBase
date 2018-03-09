package me.splm.app.inject.processor.code;


import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;

import java.util.HashMap;
import java.util.Map;

import me.splm.app.inject.processor.exception.NotDuplicateException;

public class WeBlock {

    private Map<String, WeVar> weVarMap = new HashMap<>();
    private Map<String, WeMethod> weMethodMap = new HashMap<>();
    private String methodName;
    private WeMethod mWeMethod;
    private MethodSpec.Builder mBuilder;

    public WeBlock() {
    }

    public WeBlock(MethodSpec.Builder builder) {
        this.mBuilder = builder;
    }

    public WeVar declare(String pkg, String name, String fieldName) throws NotDuplicateException {
        return this.declare(WeMod.NONE, pkg, name, fieldName);
    }

    public WeVar declare(int modifier, String pkg, String name, String fieldName) throws NotDuplicateException {
        WeVar weVar = new WeVar(modifier, pkg, name, fieldName);
        boolean isContain = weVarMap.containsKey(fieldName);
        if (isContain) {
            throw new NotDuplicateException("The field called "+ pkg+"."+name+"."+fieldName +" can't be declared repeatly.");
        }
        weVarMap.put(fieldName, weVar);
        return weVar;
    }

    public WeVar declare(WeVar weVar){
        weVarMap.put(weVar.getFieldName(), weVar);
        return weVar;
    }

    public WeMethod announce(int modifier, String name) {
        this.methodName = name;
        mWeMethod = new WeMethod(modifier, methodName);
        weMethodMap.put(methodName,mWeMethod);
        return mWeMethod;
    }

    public WeMethod announce(Class<?> annotation,int modifier,String methodName){
        WeMethod weMethod=announce(modifier,methodName);
        weMethod.addAnnotation(annotation);
        return weMethod;
    }

    public WeMethod announce(int modifier,WeVar returnType,String methodName){
        WeMethod weMethod=announce(modifier,methodName);
        weMethod.addReturnType(returnType);
        return weMethod;
    }

    public WeMethod announce(Class<?> annotation,int modifier,WeVar returnType,String methodName){
        WeMethod weMethod=announce(modifier,returnType,methodName);
        weMethod.addAnnotation(annotation);
        return weMethod;
    }

    public Map<String, WeVar> getWeVars() {
        return this.weVarMap;
    }

    public Map<String, WeMethod> getWeMethods() {
        return this.weMethodMap;
    }

    public void injectMethod(CodeBlock codeBlock) {
        mBuilder.addCode(codeBlock);
    }
}
