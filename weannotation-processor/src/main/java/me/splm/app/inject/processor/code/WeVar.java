package me.splm.app.inject.processor.code;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.ParameterSpec;

import java.util.List;

import javax.lang.model.element.Modifier;


public class WeVar {

    private String mPkg;
    private String mName;
    private String mFieldName;
    private ClassName mVarType;
    private FieldSpec mFieldSpec;
    private int mModifier;
    private FieldSpec.Builder mBuilder;
    public WeVar() {
    }

    public WeVar(int modifier,String pkg,String typename,String fieldName) {
        this.mModifier=modifier;
        this.mPkg=pkg;
        this.mName=typename;
        this.mFieldName=fieldName;
    }

    public WeVar(String pkg,String typeName,String fieldName){
        this(0,pkg,typeName,fieldName);
    }

    public ClassName toClassType() {
        return mVarType=ClassName.get(mPkg,mName);
    }

    public FieldSpec toFieldSpec(){
        WeMod weMod=new WeMod();
        List<Modifier> list=weMod.resolve(mModifier);
        Modifier[] modifiers=list.toArray(new Modifier[]{});
        return this.mFieldSpec=FieldSpec.builder(toClassType(),mFieldName, modifiers).build();//TODO need to modify modifier.
    }

    public ParameterSpec toParameterSpec(){
        return ParameterSpec.builder(toClassType(),mFieldName).build();
    }

    public void init(Object object){
    }

    public String getFieldName() {
        return mFieldName;
    }
    public int[] getModifiers(){
        return new int[]{};
    }
}
