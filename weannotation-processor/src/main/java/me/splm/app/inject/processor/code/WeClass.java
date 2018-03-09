package me.splm.app.inject.processor.code;


import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.lang.model.element.Modifier;

import me.splm.app.inject.processor.component.GenerateActionKit;
import me.splm.app.inject.processor.exception.NotDuplicateException;

public class WeClass {
    private String mClassName;
    private WeBlock weBlock;
    private TypeSpec.Builder mBuilder;
    private static final Map<String,WeBlock> clzzBlockMap=new LinkedHashMap<>();
    private List<WeMethod> methods=new LinkedList<>();

    public WeClass(String name){
        this(WeMod.PUBLIC,name);
    }

    public WeClass(int modifier, String name){

    }

    public WeClass(int modifier,String pkg,String name){
        this.mClassName=name;
        mBuilder=TypeSpec.classBuilder(mClassName);
        WeMod weMod=new WeMod();
        List<Modifier> list=weMod.resolve(modifier);
        Modifier[] modifiers=list.toArray(new Modifier[]{});
        mBuilder.addModifiers(modifiers);
        WeBlock weBlock=clzzBlockMap.get(pkg+"."+name);
        if(weBlock==null){
            weBlock=new WeBlock();
            clzzBlockMap.put(name,weBlock);
        }
        bindBlock(weBlock);
    }

    public WeBlock body(){
        return this.weBlock=new WeBlock();
    }

    public void addExtends(TypeName subClassTypename){
        builder().superclass(subClassTypename);
    }

    public void addInterface(Class<?> clzzz){
        builder().addSuperinterface(clzzz);
    }

    public String getClassName(){
        return this.mClassName;
    }

    private TypeSpec.Builder builder(){
        return mBuilder;
    }

    public void canBeSingleton(){
        GenerateActionKit generateActionKit=new GenerateActionKit();
        generateActionKit.createSimpleSingleMethod(this);
    }

    public TypeSpec build(){
        Map<String,WeVar> globalMap= weBlock.getWeVars();
        Map<String,WeMethod> methodMap=weBlock.getWeMethods();
        List<FieldSpec> globalFieldSpecs=new ArrayList<>();
        List<MethodSpec> methodSpecs=new ArrayList<>();
        for(Map.Entry<String,WeVar> entry:globalMap.entrySet()){
            WeVar weVar=entry.getValue();
            FieldSpec fieldSpec=weVar.toFieldSpec();
            globalFieldSpecs.add(fieldSpec);
        }
        for(Map.Entry<String,WeMethod> entry:methodMap.entrySet()){
            WeMethod weMethod=entry.getValue();
            MethodSpec methodSpec=weMethod.toMethodSpec();
            methodSpecs.add(methodSpec);
        }
        builder().addFields(globalFieldSpecs);
        builder().addMethods(methodSpecs);
        return builder().build();
    }

    public void bindBlock(WeBlock weBlock){
        this.weBlock=weBlock;
    }
    public WeBlock fetchBlock(){
        return this.weBlock;
    }

    public WeVar declareVar(String pkg, String name, String fieldName) throws NotDuplicateException{
        return this.weBlock.declare(pkg,name,fieldName);
    }

    public WeVar declareVar(int modifier, String pkg, String name, String fieldName) throws NotDuplicateException {
        return this.weBlock.declare(modifier,pkg,name,fieldName);
    }

    public WeVar declareVar(WeVar weVar){
        return this.weBlock.declare(weVar);
    }

    public WeMethod declareMethod(int modifier,String name){
        return this.weBlock.announce(modifier,name);
    }

    public WeMethod declareMethod(int modifier,WeVar returnType,String name){
        return this.weBlock.announce(modifier,returnType,name);
    }

    @Override
    public String toString() {
        return this.getClassName();
    }
}
