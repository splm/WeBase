package me.splm.app.inject.processor.code;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.ParameterSpec;

import java.util.List;

import javax.lang.model.element.Modifier;

import me.splm.app.inject.processor.component.Utils.TextUtils;


public class WeVar {

    private String mPkg;
    private String mName;
    private String mFieldName;
    private ClassName mVarType;
    private FieldSpec mFieldSpec;
    private int mModifier;
    private Object mIllusionValue;
    private FieldSpec.Builder mBuilder;

    /**
     * When you wanna transport a temporary value to CodeAssistant class,you may call this method.
     * This method doesn't own actual meaning but complete transportation task.
     * @param value
     */
    public WeVar(Object value) {
        this.mIllusionValue=value;
    }

    public WeVar(int modifier,String pkg,String typename,String fieldName) {
        this.mModifier=modifier;
        this.mPkg=pkg;
        this.mName=typename;
        this.mFieldName=fieldName;
    }

    public WeVar(String pkg,String typeName,String fieldName){
        this(WeMod.PUBLIC,pkg,typeName,fieldName);//TODO ???
    }

    public ClassName toClassType() {
        if(TextUtils.isEmpty(mPkg,mName)){
            return null;
        }
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
    public Object getIllusionValue(){
        return this.mIllusionValue;
    }
}
