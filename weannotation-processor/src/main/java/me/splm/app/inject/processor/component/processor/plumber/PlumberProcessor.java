package me.splm.app.inject.processor.component.processor.plumber;

import me.splm.app.inject.annotation.WeInjectPlumber;
import me.splm.app.inject.processor.component.elder.AbsProcessor;
import me.splm.app.inject.processor.component.elder.Member;
import me.splm.app.inject.processor.component.elder.NamePair;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;

//@AutoService(Processor.class)
@Deprecated
public class PlumberProcessor extends AbsProcessor {

    public static final String TARGET_PACKAGE_NAME = "com.jc.android.auto";
    private Set<String> supportedAnnotationTypes = new HashSet<>();
    private Map<String, Member> types = new HashMap<>();
    private Member member = new Member();
    private Map<String, TypeMirror> filedsMapper = new HashMap<>();
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
    /**
     * name of class
     */
    private String cName;
    /**
     * abssolute path of class
     */
    private String absName;
    /**
     * packagename of class
     */
    private String pName;

    /**
     * save some basic types tag.
     */
    private static final Map<String,ClassName> TYPENAMEMAPPER=new HashMap<>();

    @Override
    protected void memberOfMethod(Element element) {
        loge("This annotation is not apply for Methods!");
        return;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return supportedAnnotationTypes;
    }

    @Override
    protected void memberOfField(Element element) {
        String name = element.toString();
        member.addFields(name);
        loge(element.asType().toString());
        filedsMapper.put(name, element.asType());//fieldName
    }

    @Override
    protected void memberOfClass(Element element) {
        //Class
        cName = element.getSimpleName().toString();
        absName = element.toString();
        int lastIndex = absName.lastIndexOf(".");
        pName = absName.subSequence(0, lastIndex).toString();
    }

    @Override
    protected void initProcessor(ProcessingEnvironment processingEnv) {
        supportedAnnotationTypes.add(WeInjectPlumber.class.getCanonicalName());
        initBasicTypeMapper();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        return super.process(annotations, roundEnv);
    }

    @Override
    protected void action(Set<? extends TypeElement> annotations, RoundEnvironment renv) {
        try {
            ClassName clazzName = ClassName.get(TARGET_PACKAGE_NAME, "We" + cName);
            /**
             * Just like:private static final String TAG = "WeSecActivity";
             */
            FieldSpec tag = FieldSpec.builder(String.class, "TAG", Modifier.PRIVATE, Modifier.STATIC, Modifier.FINAL)
                    .addJavadoc("Intent object will find data with it!")
                    .initializer("$S", "We" + cName)
                    .build();
            /**
             * like: private static WeSecActivity instance;
             */
            FieldSpec instance = FieldSpec.builder(clazzName, "instance", Modifier.PRIVATE, Modifier.STATIC)
                    .addJavadoc("create the singleton of {@link " + clazzName + "} ")
                    .build();
            //TypeName classOfBridgeData=ClassName.get(TARGET_PACKAGE_NAME+".We"+cName,"BridgeDataClass");
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
            Set<String> fields = member.getFields();
            if (!filedsMapper.isEmpty()) {
                for (Map.Entry<String,TypeMirror> e : filedsMapper.entrySet()) {
                    String name = e.getKey().toString();
                    String type = e.getValue().toString();
                    String newStr = name.substring(0, 1).toUpperCase() + name.replaceFirst("\\w", "");
                    //TODO Setter()
                    NamePair pair=splitTargetStr2(type);
                    String p=pair.getPackageName();
                    String s=pair.getSimpleName();
                    ClassName fieldClassName;
                    if(p.equals("")){//Maybe the variable is a basic type
                        fieldClassName=chooseWhichType(s);
                    }else{
                        fieldClassName=ClassName.get(p,s);
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
            }
            MethodSpec inject = injectMethodBuilder.build();
            MethodSpec constuctor = MethodSpec.constructorBuilder()
                    .addJavadoc("Could not build object by new keyword!")
                    .addModifiers(Modifier.PRIVATE)
                    .build();
            MethodSpec getInstance = MethodSpec.methodBuilder("getIntance")
                    .addJavadoc("build a normal singleton")
                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                    .returns(clazzName)
                    .beginControlFlow("if($N==null)", instance)
                    .beginControlFlow("synchronized($T.class)",clazzName)
                    .beginControlFlow("if($N==null)", instance)
                    .addStatement("$N=new $T()", instance, clazzName)
                    .endControlFlow()
                    .endControlFlow()
                    .endControlFlow()
                    .addStatement("$N=new $T()", bridgeData, classOfBridgeData)
                    .addStatement("return $N", instance)
                    .build();
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
                    .addJavadoc("You will open target Activity which will return some surprise thingies.\n Two parameters without bundle")
                    .addModifiers(Modifier.PUBLIC)
                    .returns(void.class)
                    .addParameter(activityOfMethod, "activity")
                    .addParameter(int.class,"requestCode")
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
                    .addParameter(int.class,"requestCode")
                    .addParameter(bundleOfMethod,"options")
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
            TypeSpec targetClazz = TypeSpec.classBuilder("We" + cName)
                    .addModifiers(Modifier.PUBLIC)
                    .addField(tag)//begin to add Fields
                    .addField(instance)
                    .addField(bridgeData)
                    .addFields(fieldSpecs)
                    .addMethod(constuctor)//begin to add methods
                    .addMethod(getInstance)
                    .addMethod(start)
                    .addMethod(startForResult)
                    .addMethod(startForResultWithBundle)
                    .addMethod(inject)
                    .addMethod(getArguments)
                    .addMethods(set_methodSpecs)
                    .addType(bridgeDataClazz)//begiin to add class
                    .build();
            JavaFile javaFile = JavaFile.builder(TARGET_PACKAGE_NAME, targetClazz).build();
            javaFile.writeTo(processingEnv.getFiler());
            loge("Engine's job has been accomplishment!");
        } catch (IOException e) {
            e.printStackTrace();
        }
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
}
