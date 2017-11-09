package me.splm.app.inject.processor.code;


import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;

import java.util.List;

import javax.lang.model.element.Modifier;

public class WeMethod {

    private String mMethodName;
    private MethodSpec.Builder mBuilder;
    private int mModifier;

    public WeMethod(int modifier,String name){
        this.mMethodName=name;
        mBuilder=builder();
        WeMod weMod=new WeMod();
        List<Modifier> list=weMod.resolve(modifier);
        mBuilder.addModifiers(list);
        this.mModifier=modifier;
    }

    public void addModifier(int modifier){

    }

    public void reference(WeMethod weMethod,WeVar...weVars){
        String name=weMethod.getMethodName();
        StringBuilder fb=new StringBuilder();
        int l=weVars.length;
        for(int i=0;i<l;i++){
            String f=weVars[i].getFieldName();
            fb.append(f);
            if(i!=l-1){
                fb.append(",");
            }
        }
        CodeBlock codeBlock=CodeBlock.builder().add("this."+name+"("+fb.toString()+");\n").build();
        addBody(codeBlock);
    }

    public void addReturnType(WeVar weVar){
        addType(weVar);
    }

    private void addType(WeVar weVar){
        mBuilder.returns(weVar.toClassType());
    }

    public void addParameters(WeVar...weVars){
        for(WeVar weVar:weVars){
            mBuilder.addParameter(weVar.toParameterSpec());
        }
    }

    public void addAnnotation(Class<?> annotation){
        mBuilder.addAnnotation(annotation);
    }

    public void addBody(CodeBlock codeBlock){
        mBuilder.addCode(codeBlock);
    }

    private MethodSpec.Builder builder(){
        return MethodSpec.methodBuilder(mMethodName);
    }

    public MethodSpec toMethodSpec(){
        return mBuilder.build();
    }

    public String getMethodName() {
        return mMethodName;
    }

    public int getModifier() {
        return mModifier;
    }
}
