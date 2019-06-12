package me.splm.app.inject.processor.code;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;

import javax.lang.model.element.Modifier;

import me.splm.app.inject.processor.component.Utils.PackageUtils;
import me.splm.app.inject.processor.component.Utils.TextUtils;
import me.splm.app.inject.processor.log.Logger;
import me.splm.app.inject.processor.log.LoggerFactory;


public class WeVar {

    private String mPkg;
    private String mName;
    private String mFieldName;
    private ClassName mVarType;
    private FieldSpec mFieldSpec;
    private int mModifier;
    private Object mIllusionValue;
    private String mTAG;//TODO TAG标签需要设置一个默认值
    private FieldSpec.Builder mBuilder;

    public String mExtraForString1;
    public String mExtraForString2;

    public int mExtraForInt1;
    public int mExtraForInt2;

    public Object mExtraForAll;

    protected static final Logger LOGGER= LoggerFactory.getLogger(WeVar.class);

    public static final String SINGLE="SINGLE";
    public static final String GROUP="GROUP";
    public static final String PARAMS="parameters";

    /**
     * When you wanna transport a temporary value to CodeAssistant class,you may call this method.
     * This method doesn't own actual meaning but complete transportation task.
     * @param value
     */
    public WeVar(Object value) {
        initPortableValue(value);
    }

    public WeVar(Class<?> typeClass,String fieldName){
        this(WeMod.PRIVATE,typeClass,fieldName);
    }

    public WeVar(TypeName typeName,String fieldName){
        this(WeMod.PRIVATE,typeName,fieldName,null);
    }

    public WeVar(int modifier,TypeName typeName,String fieldName){
        this(modifier,typeName,fieldName,null);
    }

    public WeVar(int modifier,TypeName typeName,String fieldName,Object value){
        this(modifier,PackageUtils.split(typeName.toString())[0],PackageUtils.split(typeName.toString())[1],fieldName);
        set(null,typeName);
        initPortableValue(value);
    }

    public WeVar(int modifier,Class<?> typeClass,String fieldName){
        this(modifier,typeClass.getPackage().getName(),typeClass.getSimpleName(),fieldName);
        set(typeClass,null);
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

    public void addTAG(String tag){
        this.mTAG=tag;
    }

    private void set(Class<?> clzz,TypeName typeName){
        if(clzz==null&&typeName==null){
            throw new NullPointerException("Both of parameter's type can't be seen as NULL at same time!");
        }
        if(clzz!=null){
            Modifier[] modifiers=WeMod.rosolve(mModifier);
            this.mFieldSpec=FieldSpec.builder(clzz,mFieldName, modifiers).build();
        }
        if(typeName!=null){
            Modifier[] modifiers=WeMod.rosolve(mModifier);
            this.mFieldSpec=FieldSpec.builder(typeName,mFieldName, modifiers).build();
        }
    }

    public ClassName toClassType() {
        if(TextUtils.isEmpty(mPkg,mName)){
            return null;
        }
        return mVarType=ClassName.get(mPkg,mName);
    }

    public FieldSpec toFieldSpec(){
        if(mFieldSpec==null){
            Modifier[] modifiers=WeMod.rosolve(mModifier);
            return this.mFieldSpec=FieldSpec.builder(toClassType(),mFieldName, modifiers).build();
        }
        return this.mFieldSpec;
    }

    public ParameterSpec toParameterSpec(){
        return ParameterSpec.builder(toClassType(),mFieldName).build();
    }

    /**
     * 初始化WeVar的携带值，并非变量初始化值
     * @param value
     */
    public void initPortableValue(Object value){
        this.mIllusionValue=value;
    }

    public void initializer(CodeBlock initialCode){
        this.mFieldSpec=this.toFieldSpec().toBuilder().initializer(initialCode).build();
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
