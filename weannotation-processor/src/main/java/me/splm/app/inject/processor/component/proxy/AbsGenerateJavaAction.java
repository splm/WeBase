package me.splm.app.inject.processor.component.proxy;

import me.splm.app.inject.processor.component.GenerateActionKit;
import me.splm.app.inject.processor.component.TargetClassComponent;
import me.splm.app.inject.processor.core.Config;
import me.splm.app.inject.processor.exception.NotExtendException;
import me.splm.app.inject.processor.log.Logger;
import me.splm.app.inject.processor.log.LoggerFactory;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Map;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;


public abstract class AbsGenerateJavaAction implements IArborAction {
    protected TypeSpec mTargetClzz;
    protected static final String TARGET_PACKAGE_NAME = Config.GEN_fOLDER;
    protected static final Logger LOGGER= LoggerFactory.getLogger(IArborAction.class);
    private JavaFile createFile(String name, TypeSpec typeSpec){
        return JavaFile.builder(name, typeSpec).build();
    }

    protected abstract String writeSuffix();

    @Override
    public void performAction(ProcessingEnvironment processingEnvironment) {
        try{
            createFile(Config.GEN_fOLDER,mTargetClzz).writeTo(processingEnvironment.getFiler());
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
    }

    protected FieldSpec getDefineSingleton(ClassName className, String doc){
        return viaCushyKit().defineSingletonField(className,doc);
    }

    protected MethodSpec getDefAssistMethod(String absName){
        return viaCushyKit().defineAssistMethod(absName);
    }

    protected MethodSpec getDefSingletonMethod(ClassName className,FieldSpec instance){
        return viaCushyKit().defineSingletonMethod(className,instance);
    }

    protected MethodSpec getDefInitViewMethod(String fieldName,String absName,CodeBlock codeBlock){
        return viaCushyKit().defineInitViewMethod(fieldName,absName,codeBlock);
    }

    protected TypeSpec getTargetClzzSpec(String writeName){
        return viaCushyKit().defineTargetClzzSpec(writeName);
    }

    protected TypeSpec getDefTargetClzzSpec(TargetClassComponent component) throws NotExtendException{
        //These fields must be set.
        String writeName=component.getWriteName();
        ClassName classNameOfSingleton=component.getClassNameOfSingleton();

        String absNameOfTarget=component.getAbsNameOfTarget();
        String actNameOfInitView=component.getActNameOfField();
        CodeBlock codeOfIntView=component.getCodeOfInitView();
        Class<?> subInterface=component.getSubInterce();

        TypeSpec.Builder builder=viaCushyKit().defineTargetClzzSpec(writeName).toBuilder();
        FieldSpec instance=getDefineSingleton(classNameOfSingleton,"");
        MethodSpec getInstance=getDefSingletonMethod(classNameOfSingleton,instance);
        builder.addField(instance);
        builder.addMethod(getInstance);

        if(subInterface!=null){
            MethodSpec assist;
            MethodSpec initView;
            builder.addSuperinterface(subInterface);
            if(absNameOfTarget!=null){
                assist=getDefAssistMethod(absNameOfTarget);
                builder.addMethod(assist);
            }else{
                throw new NotExtendException("If you add subInterface,you must add implement it's abstract method.");
            }

            if(actNameOfInitView!=null&&codeOfIntView!=null){
                initView=getDefInitViewMethod(actNameOfInitView, absNameOfTarget, codeOfIntView);
                builder.addMethod(initView);
            }else{
               throw new NotExtendException("If you add subInterface,you must add implement it's abstract method.");
            }
        }
        return builder.build();
    }

    public GenerateActionKit viaCushyKit(){
        return new GenerateActionKit();
    }

    private AnnotationMirror getAnnotationMirror(TypeElement typeElement, Class<?> clazz) {
        String clazzName = clazz.getName();
        for (AnnotationMirror m : typeElement.getAnnotationMirrors()) {
            if (m.getAnnotationType().toString().equals(clazzName)) {
                return m;
            }
        }
        return null;
    }

    private AnnotationValue getAnnotationValue(AnnotationMirror annotationMirror, String key) {
        for (Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry : annotationMirror.getElementValues().entrySet()) {
            if (entry.getKey().getSimpleName().toString().equals(key)) {
                return entry.getValue();
            }
        }
        return null;
    }

    /**
     * Get the values of the Annotation<br />.
     * @param foo
     * @return
     */
    protected TypeMirror getValueOfAnnotation(TypeElement foo, Class<? extends Annotation> annotation,String key) {
        AnnotationMirror am = getAnnotationMirror(foo, annotation);
        if (am == null) {
            return null;
        }
        AnnotationValue av = getAnnotationValue(am, key);
        if (av == null) {
            return null;
        }
        return (TypeMirror) av.getValue();
    }
}
