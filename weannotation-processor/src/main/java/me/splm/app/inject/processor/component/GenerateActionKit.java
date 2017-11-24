package me.splm.app.inject.processor.component;


import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.Modifier;

import me.splm.app.inject.processor.code.WeClass;
import me.splm.app.inject.processor.code.WeMethod;
import me.splm.app.inject.processor.code.WeMod;
import me.splm.app.inject.processor.code.WeVar;
import me.splm.app.inject.processor.core.Config;

/**
 * You can define some fields and specifect methods via this Class.
 */
public class GenerateActionKit {

    private static final String TARGET_PACKAGE_NAME= Config.GEN_fOLDER;

    private FieldSpec.Builder createInstanceBuilder(ClassName clazzName){
        return  FieldSpec.builder(clazzName, "instance", Modifier.PRIVATE, Modifier.STATIC);
    }

    public TypeSpec defineTargetClzzSpec(String writeName){
        return TypeSpec.classBuilder(writeName).build();
    }
    /************Generate singleton instance field *****************/
    @Deprecated
    public FieldSpec defineSingletonField(ClassName clazzName){
        return defineSingletonField(clazzName,null);
    }

    @Deprecated
    public FieldSpec defineSingletonField(ClassName clazzName,String doc){
        return createInstanceBuilder(clazzName).addJavadoc(doc).build();
    }


    /************Generate singleton method *****************/
    @Deprecated
    public MethodSpec defineSingletonMethod(ClassName clazzName,FieldSpec instance){
        return defineSingtionMethod(clazzName,instance,null);
    }
    @Deprecated
    public MethodSpec defineSingtionMethod(ClassName clazzName,FieldSpec instance,CodeBlock block){
        return singletonMethodBuilder(clazzName,instance,block).build();
    }

    /**
     * If you'd like to create a singleton for class,call it.
     * @param weClass
     */
    public void createSimpleSingleMethod(WeClass weClass){
        declareSingletonMethod(weClass,null);
    }

    private MethodSpec.Builder singletonMethodBuilder(ClassName clazzName, FieldSpec instance, CodeBlock block){
        if(block==null){
            block=CodeBlock.builder().add("").build();
        }
        return MethodSpec.methodBuilder("getInstance")
                .addJavadoc("build a normal singleton")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(clazzName)
                .beginControlFlow("if($N==null)", instance)
                .beginControlFlow("synchronized($T.class)", clazzName)
                .beginControlFlow("if($N==null)", instance)
                .addStatement("$N=new $T()", instance, clazzName)
                .endControlFlow()
                .endControlFlow()
                .endControlFlow()
                .addCode(block)
                .addStatement("return $N", instance);
    }

    private void declareSingletonMethod(WeClass weClass,CodeBlock ownMethod){
        String className=weClass.getClassName();
        WeVar clzType = new WeVar(WeMod.PRIVATE+WeMod.STATIC,TARGET_PACKAGE_NAME,className,"instance");
        WeVar instance=weClass.declareVar(clzType);
        CodeBlock codeBlock=codeBody(instance,ownMethod);
        WeMethod getInstance=weClass.declareMethod(WeMod.PUBLIC+WeMod.STATIC,instance,"getInstance");
        getInstance.addBody(codeBlock);
    }

    private CodeBlock codeBody(WeVar weVar,CodeBlock another){
        if(another==null){
            another=CodeBlock.builder().add("").build();
        }
        CodeBlock codeBlock=CodeBlock.builder()
                .beginControlFlow("if($N==null)", weVar.toFieldSpec())
                .beginControlFlow("synchronized($T.class)", weVar.toClassType())
                .beginControlFlow("if($N==null)", weVar.toFieldSpec())
                .addStatement("$N=new $T()", weVar.toFieldSpec(), weVar.toClassType())
                .endControlFlow()
                .endControlFlow()
                .endControlFlow()
                .add(another)
                .addStatement("return $N", weVar.toFieldSpec())
                .build();
        return codeBlock;
    }

    public MethodSpec defineAssistMethod(String absName){
        MethodSpec assist = MethodSpec.methodBuilder("assist")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .addParameter(ClassName.get("java.lang", "Object"), "object")
                .addStatement("initView(object)", absName)
                .build();
        return assist;
    }

    public MethodSpec defineInitViewMethod(String fieldName,String absName,CodeBlock codeBlock){
        MethodSpec initView = MethodSpec.methodBuilder("initView")
                .addModifiers(Modifier.PRIVATE)
                .addParameter(Object.class, "object")
                .addStatement(fieldName + "=($N)object", absName)
                .addCode(codeBlock)
                .returns(void.class).build();
        return initView;
    }

}
