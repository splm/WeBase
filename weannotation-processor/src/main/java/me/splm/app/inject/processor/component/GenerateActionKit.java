package me.splm.app.inject.processor.component;


import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.Modifier;

/**
 * You can define some fields and specifect methods via this Class.
 */
public class GenerateActionKit {

    private FieldSpec.Builder createInstanceBuilder(ClassName clazzName){
        return  FieldSpec.builder(clazzName, "instance", Modifier.PRIVATE, Modifier.STATIC);
    }

    public TypeSpec defineTargetClzzSpec(String writeName){
        return TypeSpec.classBuilder(writeName).build();
    }
    /************Generate singleton instance field *****************/
    public FieldSpec defineSingletonField(ClassName clazzName){
        return defineSingletonField(clazzName,null);
    }

    public FieldSpec defineSingletonField(ClassName clazzName,String doc){
        return createInstanceBuilder(clazzName).addJavadoc(doc).build();
    }


    /************Generate singleton method *****************/
    public MethodSpec defineSingletonMethod(ClassName clazzName,FieldSpec instance){
        return defineSingtionMethod(clazzName,instance,null);
    }

    public MethodSpec defineSingtionMethod(ClassName clazzName,FieldSpec instance,CodeBlock block){
        return singletonMethodBuilder(clazzName,instance,block).build();
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
