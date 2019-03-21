package me.splm.app.inject.processor.component.proxy;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.lang.annotation.Annotation;
import java.util.Map;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;

import me.splm.app.inject.processor.code.WeCodeModel;
import me.splm.app.inject.processor.component.GenerateActionKit;
import me.splm.app.inject.processor.component.TargetClassComponent;
import me.splm.app.inject.processor.component.Utils.TextUtils;
import me.splm.app.inject.processor.component.Utils.entity.LineInfo;
import me.splm.app.inject.processor.core.Config;
import me.splm.app.inject.processor.core.ForemanProcessor;
import me.splm.app.inject.processor.exception.NotExtendException;
import me.splm.app.inject.processor.log.Logger;
import me.splm.app.inject.processor.log.LoggerFactory;


public abstract class AbsGenerateJavaAction implements IArborAction {
    protected TypeSpec mTargetClzz;
    protected static final String TARGET_PACKAGE_NAME = Config.GEN_fOLDER;
    protected static final Logger LOGGER= LoggerFactory.getLogger(AbsGenerateJavaAction.class);
    private JavaFile createFile(String name, TypeSpec typeSpec){
        return JavaFile.builder(name, typeSpec).build();
    }

    protected abstract String writeSuffix();

    @Override
    public ActionTask prepareAction(TreeTrunk treeTrunk) throws NullPointerException {
        return new ActionTask(this);
    }

    /**
     * creating new CodeModel,This CodeModel object must be created before you are about to build your own class.
     * @return
     */
    protected WeCodeModel renewCodeModel(){
        return new WeCodeModel();
    }

    /**IO***Begin**/
    private LineInfo readMirror(String clzzname) throws IOException{
        //read
        InputStream inputStream=null;
        try{
            inputStream=new FileInputStream(new File(ForemanProcessor.ROOT));
            InputStreamReader inputStreamReader=new InputStreamReader(inputStream);
            BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
            String line;
            int count=0;
            while((line=bufferedReader.readLine())!=null){
                count++;
                if(line.contains(clzzname)){
                    int var=line.indexOf("=");
                    String k=line.substring(0,var);
                    String v=line.substring(var+1);
                    return new LineInfo(count, var, k, v);
                }
            }
        }
        finally {
            if(inputStream!=null)inputStream.close();
        }
        return null;
    }

    private void writeMirror(String content,int index) throws IOException{
        //write
        RandomAccessFile randomAccessFile=new RandomAccessFile(new File(ForemanProcessor.ROOT), "rw");
        long length=randomAccessFile.length();
        if(index>0){
            length=index;
        }
        randomAccessFile.seek(length);
        randomAccessFile.writeBytes(content+"\n");
        closeIO(randomAccessFile);
    }

    private void writeMirror(String content) throws IOException{
        writeMirror(content, -1);
    }

    private void closeIO(Closeable closeable) throws IOException{
        closeable.close();
    }

    /**IO***END**/

    @Override
    public void performAction(ProcessingEnvironment processingEnvironment) {
        try{
            createFile(Config.GEN_fOLDER,mTargetClzz).writeTo(processingEnvironment.getFiler());
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
        /*try{
            StringBuffer buffer=new StringBuffer();
            JavaFile jf=createFile(Config.GEN_fOLDER,mTargetClzz);
            jf.writeTo(buffer);
            String clzzname=mTargetClzz.name;
            LineInfo info=readMirror(clzzname);
            if(info!=null){
                String v=info.getValue();
                String var= TextUtils.md5(buffer.toString());
                if(v==null){
                    String kv=clzzname+"="+var;
                    writeMirror(kv);
                    jf.writeTo(processingEnvironment.getFiler());
                }else{
                    if(var!=null&&!TextUtils.isEquals(v, var)){
                        String nv=v.replace(v, var);
                        int index=info.getGlobalIndex();
                        writeMirror("="+nv, index);
                        jf.writeTo(processingEnvironment.getFiler());
                    }
                }
            }else{
                writeMirror(clzzname+"="+TextUtils.md5(buffer.toString()));
                jf.writeTo(processingEnvironment.getFiler());
            }
        }catch(IOException ioe){
            ioe.printStackTrace();
        }*/
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
