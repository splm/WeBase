package me.splm.app.inject.processor.component.processor.plumber;

import me.splm.app.inject.processor.component.proxy.TreeTrunk;
import me.splm.app.inject.processor.core.Config;
import me.splm.app.inject.processor.component.elder.NamePair;
import me.splm.app.inject.processor.component.proxy.AbsGenerateJavaAction;
import me.splm.app.inject.processor.component.proxy.ActionTask;
import me.splm.app.inject.processor.component.proxy.IWorkersProxy;
import me.splm.app.inject.processor.component.proxy.TreeLeavesFields;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.lang.model.element.Modifier;


public class GeneratePlumberAction extends AbsGenerateJavaAction {
    /**
     * collection of setters
     */
    private Set<MethodSpec> set_methodSpecs = new LinkedHashSet<>();
    /**
     * collection of inner class getters
     */
    private Set<MethodSpec> get_InnerMethodSpecs = new LinkedHashSet<>();
    /**
     * collection of inner class setters
     */
    private Set<MethodSpec> set_InnerMethodSpecs = new LinkedHashSet<>();
    /**
     * all of fields will appear here.
     */
    private Set<FieldSpec> fieldSpecs = new LinkedHashSet<>();

    private static final Map<String,ClassName> TYPENAMEMAPPER=new HashMap<>();

    public GeneratePlumberAction() {
        initBasicTypeMapper();
    }

    private MethodSpec.Builder genInjectMethodBuilder(ParameterSpec parm, TypeName field) {
        MethodSpec.Builder builder = MethodSpec.methodBuilder("inject")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(void.class)
                .addParameter(parm)
                .beginControlFlow("if($N==null)", parm)
                .addStatement("return")
                .endControlFlow()
                .addStatement("$T data=getArgument($N.getIntent())", field, parm);
        return builder;
    }

    public NamePair splitTargetStr2(String str) {
        NamePair pair=new NamePair();
        if (str.contains(".")) {
            int li = str.lastIndexOf(".");
            String s=str.substring(li + 1);
            String p=str.substring(0, li);
            pair.setPackageName(p);
            pair.setSimpleName(s);
        }else{
            pair.setPackageName("");
            pair.setSimpleName(str);
        }
        return pair;
    }

    private ClassName chooseWhichType(String type){
        return TYPENAMEMAPPER.get(type);
    }

    /**
     * init variable of basic type
     */
    private void initBasicTypeMapper(){
        ClassName BOXED_VOID = ClassName.get("java.lang", "Void");
        ClassName BOXED_BOOLEAN = ClassName.get("java.lang", "Boolean");
        ClassName BOXED_BYTE = ClassName.get("java.lang", "Byte");
        ClassName BOXED_SHORT = ClassName.get("java.lang", "Short");
        ClassName BOXED_INT = ClassName.get("java.lang", "Integer");
        ClassName BOXED_LONG = ClassName.get("java.lang", "Long");
        ClassName BOXED_CHAR = ClassName.get("java.lang", "Character");
        ClassName BOXED_FLOAT = ClassName.get("java.lang", "Float");
        ClassName BOXED_DOUBLE = ClassName.get("java.lang", "Double");

        TYPENAMEMAPPER.put("int",BOXED_INT);
        TYPENAMEMAPPER.put("short",BOXED_SHORT);
        TYPENAMEMAPPER.put("boolean",BOXED_BOOLEAN);
        TYPENAMEMAPPER.put("byte",BOXED_BYTE);
        TYPENAMEMAPPER.put("char",BOXED_CHAR);
        TYPENAMEMAPPER.put("double",BOXED_DOUBLE);
        TYPENAMEMAPPER.put("float",BOXED_FLOAT);
        TYPENAMEMAPPER.put("long",BOXED_LONG);
        TYPENAMEMAPPER.put("void",BOXED_VOID);
    }

    @Override
    protected String writeSuffix() {
        return Config.SUFFIX__PLUMBER;
    }

    @Override
    public ActionTask prepareAction(TreeTrunk treeTrunk) {
        String cName= treeTrunk.getName();
        String absName= treeTrunk.getAbstractName();
        String pName= treeTrunk.getPackageName();
        ClassName clazzName = ClassName.get(Config.GEN_fOLDER, "We" + cName+ writeSuffix());
        /**
         * Just like:private static final String TAG = "WeSecActivity";
         */
        FieldSpec tag = FieldSpec.builder(String.class, "TAG", Modifier.PRIVATE, Modifier.STATIC, Modifier.FINAL)
                .addJavadoc("Intent object will find data with it!")
                .initializer("$S", "We" + cName+Config.SUFFIX__PLUMBER)
                .build();
        /**
         * like: private static WeSecActivity instance;
         */
        FieldSpec instance = FieldSpec.builder(clazzName, "instance", Modifier.PRIVATE, Modifier.STATIC)
                .addJavadoc("create the singleton of {@link " + clazzName + "} ")
                .build();
        TypeName classOfBridgeData = ClassName.get(clazzName.toString(), "BridgeDataClass");
        /**
         * android's activity
         */
        TypeName activityOfMethod = ClassName.get("android.app", "Activity");
        /**
         * android's bundle
         */
        TypeName bundleOfMethod=ClassName.get("android.os","Bundle");

        /**
         * android's intent
         */
        TypeName intentOfMethod = ClassName.get("android.content", "Intent");
        TypeName genClass = ClassName.get(pName, cName);//auto gen class's path
        /**
         * like: private static BridgeDataClass bridgeData;
         */
        FieldSpec bridgeData = FieldSpec.builder(classOfBridgeData, "bridgeData", Modifier.PRIVATE, Modifier.STATIC).build();
        ParameterSpec paramActivityOfInject = ParameterSpec.builder(genClass, "activity").build();
        MethodSpec.Builder injectMethodBuilder = genInjectMethodBuilder(paramActivityOfInject, classOfBridgeData);
        Set<TreeLeavesFields> fieldsSet= treeTrunk.fetchMemberOffileds();
        if(!fieldsSet.isEmpty()) {
            for (TreeLeavesFields field : fieldsSet) {
                String name = field.getName();
                String type = field.getOwnMirror().toString();
                String newStr = name.substring(0, 1).toUpperCase() + name.replaceFirst("\\w", "");
                NamePair pair = splitTargetStr2(type);
                String p = pair.getPackageName();
                String s = pair.getSimpleName();
                ClassName fieldClassName;
                if (p.equals("")) {//Maybe the variable is a basic type
                    fieldClassName = chooseWhichType(s);
                } else {
                    fieldClassName = ClassName.get(p, s);
                }
                FieldSpec fieldSpec = FieldSpec.builder(fieldClassName, name, Modifier.PUBLIC).build();//field name
                fieldSpecs.add(fieldSpec);
                MethodSpec set_methodSpec = MethodSpec.methodBuilder("set" + newStr)
                        .addModifiers(Modifier.PUBLIC)
                        .returns(clazzName)
                        .addParameter(fieldClassName, name)
                        .addStatement("this.$N.set" + newStr + "($N)", bridgeData, name)
                        .addStatement("return this")
                        .build();
                set_methodSpecs.add(set_methodSpec);

                //create getter/setter method for the innerClass's fields
                MethodSpec get_innerMethodSpec = MethodSpec.methodBuilder("get" + newStr)
                        .addModifiers(Modifier.PUBLIC)
                        .returns(fieldClassName)
                        .addStatement("return $N", name)
                        .build();
                get_InnerMethodSpecs.add(get_innerMethodSpec);

                MethodSpec set_innerMethodSpec = MethodSpec.methodBuilder("set" + newStr)
                        .addModifiers(Modifier.PUBLIC)
                        .addParameter(fieldClassName, name)
                        .addStatement("this.$N=$N", name, name)
                        .build();
                CodeBlock codeBlock = CodeBlock.builder().addStatement("$N." + name + "=data.get" + newStr + "()", paramActivityOfInject).build();
                injectMethodBuilder.addCode(codeBlock);
                set_InnerMethodSpecs.add(set_innerMethodSpec);
            }

            MethodSpec inject = injectMethodBuilder.build();
            MethodSpec implMethod = MethodSpec.methodBuilder("assist")
                    .addModifiers(Modifier.PUBLIC)
                    .addAnnotation(Override.class)
                    .addParameter(ClassName.get("java.lang", "Object"), "object")
                    .addStatement("inject(($N)object)", absName)
                    .build();
            MethodSpec constuctor = MethodSpec.constructorBuilder()
                    .addJavadoc("Could not build object by new keyword!")
                    .addModifiers(Modifier.PRIVATE)
                    .build();
            CodeBlock stateOfGetInstance = CodeBlock.builder().add("$N=new $T();\n", bridgeData, classOfBridgeData).build();
            MethodSpec getInstance = viaCushyKit().defineSingtionMethod(clazzName, instance, stateOfGetInstance);
            MethodSpec start = MethodSpec.methodBuilder("start")
                    .addJavadoc("Will open target Activity")
                    .addModifiers(Modifier.PUBLIC)
                    .returns(void.class)
                    .addParameter(activityOfMethod, "activity")
                    .addStatement("$T intent=new $T()", intentOfMethod, intentOfMethod)
                    .addStatement("intent.setClass($N, $N.class)", "activity", absName)
                    .addStatement("intent.putExtra($N, $N)", tag, bridgeData)
                    .addStatement("$N.startActivity(intent)", "activity")
                    .build();
            MethodSpec startForResult = MethodSpec.methodBuilder("startForResult")
                    .addJavadoc("You will open the target Activity which will return some surprise thingies.\n Two parameters without bundle")
                    .addModifiers(Modifier.PUBLIC)
                    .returns(void.class)
                    .addParameter(activityOfMethod, "activity")
                    .addParameter(int.class, "requestCode")
                    .addStatement("$T intent=new $T()", intentOfMethod, intentOfMethod)
                    .addStatement("intent.setClass($N, $N.class)", "activity", absName)
                    .addStatement("intent.putExtra($N, $N)", tag, bridgeData)
                    .addStatement("$N.startActivityForResult(intent,requestCode)", "activity")
                    .build();
            MethodSpec startForResultWithBundle = MethodSpec.methodBuilder("startForResult")
                    .addJavadoc("You will open Activity which will return some surprise thingies.\n Three parameters,1st:activity;2nd:RequestCode;3rd:bundle")
                    .addModifiers(Modifier.PUBLIC)
                    .returns(void.class)
                    .addParameter(activityOfMethod, "activity")
                    .addParameter(int.class, "requestCode")
                    .addParameter(bundleOfMethod, "options")
                    .addStatement("$T intent=new $T()", intentOfMethod, intentOfMethod)
                    .addStatement("intent.setClass($N, $N.class)", "activity", absName)
                    .addStatement("intent.putExtra($N, $N)", tag, bridgeData)
                    .addStatement("$N.startActivityForResult(intent,requestCode,options)", "activity")
                    .build();
            MethodSpec getArguments = MethodSpec.methodBuilder("getArgument")
                    .addModifiers(Modifier.PRIVATE, Modifier.STATIC)
                    .addParameter(intentOfMethod, "intent")
                    .returns(classOfBridgeData)
                    .beginControlFlow("if($N==null)", bridgeData)
                    .addStatement("return new $T()", classOfBridgeData)
                    .endControlFlow()
                    .addStatement("return ($T)intent.getSerializableExtra($N)", classOfBridgeData, tag)
                    .build();
            TypeSpec bridgeDataClazz = TypeSpec.classBuilder("BridgeDataClass")
                    .addModifiers(Modifier.PRIVATE, Modifier.STATIC)
                    .addSuperinterface(Serializable.class)
                    .addMethods(set_InnerMethodSpecs)
                    .addMethods(get_InnerMethodSpecs)
                    .addFields(fieldSpecs)
                    .build();
            mTargetClzz=getTargetClzzSpec("We" + cName + writeSuffix()).toBuilder()
                    .addModifiers(Modifier.PUBLIC)
                    .addField(tag)//begin to add Fields
                    .addField(instance)
                    .addField(bridgeData)
                    .addFields(fieldSpecs)
                    .addMethod(constuctor)//begin to add methods
                    .addMethod(implMethod)
                    .addMethod(getInstance)
                    .addMethod(start)
                    .addMethod(startForResult)
                    .addMethod(startForResultWithBundle)
                    .addMethod(inject)
                    .addMethod(getArguments)
                    .addMethods(set_methodSpecs)
                    .addType(bridgeDataClazz)//begiin to add class
                    .addSuperinterface(IWorkersProxy.class)
                    .build();
            return new ActionTask(this);
        }
        return null;
    }
}
